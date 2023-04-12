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

import twitter4j.TwitterResponse;

import java.time.LocalDateTime;

/**
 * A data interface representing sent/received direct message.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public interface DirectMessage extends TwitterResponse, EntitySupport, java.io.Serializable {

    /**
     * @return id
     */
    long getId();

    /**
     * @return text
     */
    String getText();

    /**
     * @return sender id
     */
    long getSenderId();

    /**
     * @return recipient id
     */
    long getRecipientId();

    /**
     * @return created_at
     * @since Twitter4J 1.1.0
     */
    LocalDateTime getCreatedAt();


    /**
     * @return quick reply options
     * @see <a href="https://developer.twitter.com/en/docs/twitter-api/v1/direct-messages/quick-replies/overview">Quick Replies</a>
     * @since Twitter4J 4.0.8
     */
    QuickReply[] getQuickReplies();

    /**
     * @return quick reply response metadata
     * @see <a href="https://developer.twitter.com/en/docs/twitter-api/v1/direct-messages/quick-replies/overview">Quick Replies</a>
     * @since Twitter4J 4.0.8
     */
    @SuppressWarnings("unused")
    String getQuickReplyResponse();

    // currently type is always "message_create". So we're not providing a getter for that.
}
