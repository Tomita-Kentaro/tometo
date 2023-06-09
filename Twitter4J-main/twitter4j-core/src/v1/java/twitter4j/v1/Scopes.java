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

import java.io.Serializable;

/**
 * A data interface representing targeting scopes applied to a status.
 *
 * @author Aaron Rankin - aaron at sproutsocial.com
 * @since Twitter4J 3.0.6
 */
public interface Scopes extends Serializable {

    /**
     * Returns the place_ids that identify the scope of the status.
     *
     * @return the place_ids that identify the scope of the status.
     */
    String[] getPlaceIds();

}
