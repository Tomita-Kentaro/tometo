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
import twitter4j.v1.RateLimitStatus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
@Execution(ExecutionMode.CONCURRENT)
class TwitterTest extends TwitterTestBase {

    RateLimitStatus rateLimitStatus = null;
    boolean accountLimitStatusAcquired;
    boolean ipLimitStatusAcquired;

    //need to think of a way to test this, perhaps mocking out Twitter is the way to go
    @Test
    void testRateLimitStatus() throws Exception {
        Twitter twitter = Twitter.newBuilder().load(subProperty(p, "id1")).onRateLimitStatus(event -> {
            System.out.println("onRateLimitStatus" + event);
            accountLimitStatusAcquired = event.isAccountRateLimitStatus();
            ipLimitStatusAcquired = event.isIPRateLimitStatus();
            TwitterTest.this.rateLimitStatus = event.getRateLimitStatus();
        }).build();

        // the listener doesn't implement serializable and deserialized form should not be equal to the original object
        assertDeserializedFormIsNotEqual(twitter);

        Map<String, RateLimitStatus> rateLimitStatus = twitter.v1().help().getRateLimitStatus();
        assertNotNull(TwitterObjectFactory.getRawJSON(rateLimitStatus));
        assertEquals(rateLimitStatus, TwitterObjectFactory.createRateLimitStatus(TwitterObjectFactory.getRawJSON(rateLimitStatus)));
        RateLimitStatus status = rateLimitStatus.values().iterator().next();
        assertTrue(10 < status.getLimit());
        assertTrue(10 < status.getRemaining());
        assertTrue(0 < status.getSecondsUntilReset());

        rateLimitStatus = twitter.v1().help().getRateLimitStatus("block", "statuses");
        assertTrue(rateLimitStatus.values().size() > 5);

        // the listener doesn't implement serializable and deserialized form should not be equal to the original object
        assertDeserializedFormIsNotEqual(twitter);

        twitter.v1().timelines().getMentionsTimeline();
        assertTrue(accountLimitStatusAcquired);
        assertFalse(ipLimitStatusAcquired);
        RateLimitStatus previous = this.rateLimitStatus;
        twitter.v1().timelines().getMentionsTimeline();
        assertTrue(accountLimitStatusAcquired);
        assertFalse(ipLimitStatusAcquired);
        assertTrue(previous.getRemaining() > this.rateLimitStatus.getRemaining());
        assertEquals(previous.getLimit(), this.rateLimitStatus.getLimit());
    }

    @Test
    void testGetAccessLevel() throws Exception {
        TwitterResponse response;
        response = rwPrivateMessage.v1().users().verifyCredentials();
        assertEquals(TwitterResponse.AccessLevel.READ_WRITE_DIRECTMESSAGES, response.getAccessLevel());
    }

    public static void assertDeserializedFormIsNotEqual(Object obj) throws Exception {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteOutputStream);
        oos.writeObject(obj);
        byteOutputStream.close();
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(byteInputStream);
        Object that = ois.readObject();
        byteInputStream.close();
        ois.close();
        assertNotEquals(obj, that);
    }
}
