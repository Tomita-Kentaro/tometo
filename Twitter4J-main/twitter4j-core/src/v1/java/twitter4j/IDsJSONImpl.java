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

import twitter4j.v1.IDs;

import java.util.Arrays;

/**
 * A data class representing array of numeric IDs.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
/*package*/ final class IDsJSONImpl extends TwitterResponseImpl implements IDs {

    private static final long serialVersionUID = 6999637496007165672L;
    private long[] ids;
    private long previousCursor = -1;
    private long nextCursor = -1;

    /*package*/ IDsJSONImpl(HttpResponse res, boolean jsonStoreEnabled) throws TwitterException {
        super(res);
        String json = res.asString();
        init(json);
        if (jsonStoreEnabled) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    /*package*/ IDsJSONImpl(String json) throws TwitterException {
        init(json);
    }

    private void init(String jsonStr) throws TwitterException {
        JSONArray idList;
        try {
            if (jsonStr.startsWith("{")) {
                JSONObject json = new JSONObject(jsonStr);
                idList = json.getJSONArray("ids");
                ids = new long[idList.length()];
                for (int i = 0; i < idList.length(); i++) {
                    try {
                        ids[i] = Long.parseLong(idList.getString(i));
                    } catch (NumberFormatException nfe) {
                        throw new TwitterException("Twitter API returned malformed response: " + json, nfe);
                    }
                }
                previousCursor = ParseUtil.getLong("previous_cursor", json);
                nextCursor = ParseUtil.getLong("next_cursor", json);
            } else {
                idList = new JSONArray(jsonStr);
                ids = new long[idList.length()];
                for (int i = 0; i < idList.length(); i++) {
                    try {
                        ids[i] = Long.parseLong(idList.getString(i));
                    } catch (NumberFormatException nfe) {
                        throw new TwitterException("Twitter API returned malformed response: " + idList, nfe);
                    }
                }
            }
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        }
    }

    @Override
    public long[] getIDs() {
        return ids;
    }

    @Override
    public boolean hasPrevious() {
        return 0 != previousCursor;
    }

    @Override
    public long getPreviousCursor() {
        return previousCursor;
    }

    @Override
    public boolean hasNext() {
        return 0 != nextCursor;
    }

    @Override
    public long getNextCursor() {
        return nextCursor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IDsJSONImpl iDsJSON = (IDsJSONImpl) o;
        return previousCursor == iDsJSON.previousCursor && nextCursor == iDsJSON.nextCursor && Arrays.equals(ids, iDsJSON.ids);
    }

    @Override
    public int hashCode() {
        return ids != null ? Arrays.hashCode(ids) : 0;
    }

    @Override
    public String toString() {
        return "IDsJSONImpl{" +
                "ids=" + Arrays.toString(ids) +
                ", previousCursor=" + previousCursor +
                ", nextCursor=" + nextCursor +
                '}';
    }
}