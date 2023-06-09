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

package twitter4j.v1;


/**
 * A data interface representing Trend.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.0.2
 */
public interface Trend extends java.io.Serializable {
    /**
     * @return name
     */
    String getName();

    /**
     * @return url
     */
    String getURL();

    /**
     * @return query
     */
    String getQuery();

    /**
     * The tweet volume for the last 24 hours if available, -1 otherwise.
     *
     * @return The tweet volume for the last 24 hours if available, -1 otherwise.
     */
    int getTweetVolume();
}
