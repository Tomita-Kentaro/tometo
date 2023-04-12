/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package twitter4j;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import twitter4j.v1.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.2.4
 */
@Execution(ExecutionMode.CONCURRENT)
class PlaceGeoResourcesTest extends TwitterTestBase {
    @Test
    void testGeoMethods() throws Exception {
        GeoQuery query;
        ResponseList<Place> places;
        query = GeoQuery.ofGeoLocation(0, 0);

        try {

            places = twitter1.v1().placesGeo().reverseGeoCode(query);
            assertEquals(0, places.size());
        } catch (TwitterException te) {
            // no data for this place
        }

        query = GeoQuery.ofGeoLocation(37.78215, -122.40060);
        places = twitter1.v1().placesGeo().reverseGeoCode(query);
        assertNotNull(TwitterObjectFactory.getRawJSON(places));
        assertEquals(places.get(0), TwitterObjectFactory.createPlace(TwitterObjectFactory.getRawJSON(places.get(0))));

        assertTrue(places.size() > 0);

        places = twitter1.v1().placesGeo().searchPlaces(query);
        assertNotNull(TwitterObjectFactory.getRawJSON(places));
        assertEquals(places.get(0), TwitterObjectFactory.createPlace(TwitterObjectFactory.getRawJSON(places.get(0))));
        assertTrue(places.size() > 0);

        try {
            Place place = twitter1.v1().placesGeo().getGeoDetails("5a110d312052166f");
            assertNotNull(TwitterObjectFactory.getRawJSON(place));
            assertEquals(place, TwitterObjectFactory.createPlace(TwitterObjectFactory.getRawJSON(place)));
            assertEquals("San Francisco, CA", place.getFullName());
            assertEquals("SAN FRANCISCO-OAK-SAN JOSE", place.getContainedWithIn()[0].getFullName());
        } catch (TwitterException te) {
            // is being rate limited
            assertEquals(400, te.getStatusCode());
        }
        String sanFrancisco = "5a110d312052166f";
        Status status = twitter1.v1().tweets().updateStatus(StatusUpdate.of(LocalDateTime.now() + " status with place").
                placeId(sanFrancisco));
        assertNotNull(TwitterObjectFactory.getRawJSON(status));
        assertEquals(status, TwitterObjectFactory.createStatus(TwitterObjectFactory.getRawJSON(status)));
        assertEquals(sanFrancisco, status.getPlace().getId());
    }

    @Test
    void testGeoLocation() throws Exception {
        final double LATITUDE = 12.3456;
        final double LONGITUDE = -34.5678;

        StatusUpdate location = StatusUpdate.of(LocalDateTime.now() + ": updating geo location").location(LATITUDE, LONGITUDE).displayCoordinates(true);
        Status withgeo = twitter1.v1().tweets().updateStatus(location);
        assertNotNull(TwitterObjectFactory.getRawJSON(withgeo));
        assertEquals(withgeo, TwitterObjectFactory.createStatus(TwitterObjectFactory.getRawJSON(withgeo)));
        assertTrue(withgeo.getUser().isGeoEnabled());
        assertEquals(LATITUDE, withgeo.getGeoLocation().latitude);
        assertEquals(LONGITUDE, withgeo.getGeoLocation().longitude);
        assertFalse(twitter2.v1().users().verifyCredentials().isGeoEnabled());
    }

}
