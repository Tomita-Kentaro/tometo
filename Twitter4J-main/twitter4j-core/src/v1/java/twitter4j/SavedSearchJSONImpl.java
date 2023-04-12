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

import twitter4j.v1.ResponseList;
import twitter4j.v1.SavedSearch;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A data class representing a Saved Search
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.0.8
 */
/*package*/ final class SavedSearchJSONImpl extends TwitterResponseImpl implements SavedSearch {

    private static final long serialVersionUID = -2281949861485441692L;
    private LocalDateTime createdAt;
    private String query;
    private int position;
    private String name;
    private long id;

    /*package*/ SavedSearchJSONImpl(HttpResponse res, boolean jsonStoreEnabled) throws TwitterException {
        super(res);
        if (jsonStoreEnabled) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        JSONObject json = res.asJSONObject();
        init(json);
        if (jsonStoreEnabled) {
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    /*package*/ SavedSearchJSONImpl(JSONObject savedSearch) throws TwitterException {
        init(savedSearch);
    }

    /*package*/
    static ResponseList<SavedSearch> createSavedSearchList(HttpResponse res, boolean jsonStoreEnabled) throws TwitterException {
        if (jsonStoreEnabled) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        JSONArray json = res.asJSONArray();
        ResponseList<SavedSearch> savedSearches;
        try {
            savedSearches = new ResponseListImpl<>(json.length(), res);
            for (int i = 0; i < json.length(); i++) {
                JSONObject savedSearchesJSON = json.getJSONObject(i);
                SavedSearch savedSearch = new SavedSearchJSONImpl(savedSearchesJSON);
                savedSearches.add(savedSearch);
                if (jsonStoreEnabled) {
                    TwitterObjectFactory.registerJSONObject(savedSearch, savedSearchesJSON);
                }
            }
            if (jsonStoreEnabled) {
                TwitterObjectFactory.registerJSONObject(savedSearches, json);
            }
            return savedSearches;
        } catch (JSONException jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + res.asString(), jsone);
        }
    }

    private void init(JSONObject savedSearch) throws TwitterException {
        createdAt = ParseUtil.getDate("created_at", savedSearch, "EEE MMM dd HH:mm:ss Z yyyy");
        query = ParseUtil.getUnescapedString("query", savedSearch);
        position = ParseUtil.getInt("position", savedSearch);
        name = ParseUtil.getUnescapedString("name", savedSearch);
        id = ParseUtil.getLong("id", savedSearch);
    }

    @Override
    public int compareTo(SavedSearch that) {
        return (int) (this.id - that.getId());
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedSearchJSONImpl that = (SavedSearchJSONImpl) o;
        return position == that.position && id == that.id && Objects.equals(createdAt, that.createdAt) && Objects.equals(query, that.query) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = createdAt.hashCode();
        result = 31 * result + query.hashCode();
        result = 31 * result + position;
        result = 31 * result + name.hashCode();
        result = 31 * result + (int) id;
        return result;
    }

    @Override
    public String toString() {
        return "SavedSearchJSONImpl{" +
                "createdAt=" + createdAt +
                ", query='" + query + '\'' +
                ", position=" + position +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
