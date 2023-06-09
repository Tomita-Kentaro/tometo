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


import twitter4j.v1.RateLimitStatus;

/**
 * A data interface representing Twitter REST API's rate limit status
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @see <a href="https://dev.twitter.com/docs/rate-limiting">Rate Limiting | Twitter Developers</a>
 */
public final class RateLimitStatusEvent extends java.util.EventObject {

    private static final long serialVersionUID = 3749366911109722414L;
    /**
     * rateLimitStatus
     */
    private final RateLimitStatus rateLimitStatus;

    /**
     * isAccountRateLimitStatus
     */
    private final boolean isAccountRateLimitStatus;

    RateLimitStatusEvent(Object source, RateLimitStatus rateLimitStatus, boolean isAccountRateLimitStatus) {
        super(source);
        this.rateLimitStatus = rateLimitStatus;
        this.isAccountRateLimitStatus = isAccountRateLimitStatus;
    }

    /**
     * @return rate limit status
     */
    public RateLimitStatus getRateLimitStatus() {
        return rateLimitStatus;
    }

    /**
     * @return returns true if it's an account based rate limit status
     */
    public boolean isAccountRateLimitStatus() {
        return isAccountRateLimitStatus;
    }

    /**
     * @return returns true if it's an IP based rate limit status
     */
    public boolean isIPRateLimitStatus() {
        return !isAccountRateLimitStatus;
    }

}
