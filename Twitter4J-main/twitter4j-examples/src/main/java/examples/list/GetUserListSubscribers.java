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

package examples.list;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.v1.PagableResponseList;
import twitter4j.v1.User;

/**
 * Lists list subscribers.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class GetUserListSubscribers {
    /**
     * Usage: java twitter4j.examples.list.GetUserListSubscribers [list id]
     *
     * @param args message
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java twitter4j.examples.list.GetUserListSubscribers [list id]");
            System.exit(-1);
        }
        try {
            var list = Twitter.getInstance().v1().list();
            long cursor = -1;
            PagableResponseList<User> users;
            do {
                users = list.getUserListSubscribers(Integer.parseInt(args[0]), cursor);
                for (User user : users) {
                    System.out.println("@" + user.getScreenName());
                }
            } while ((cursor = users.getNextCursor()) != 0);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get list subscribers: " + te.getMessage());
            System.exit(-1);
        }
    }
}
