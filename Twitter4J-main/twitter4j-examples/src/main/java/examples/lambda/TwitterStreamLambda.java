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

package examples.lambda;

import twitter4j.Twitter;
import twitter4j.v1.FilterQuery;

/**
 * example code to explain lambda expression. Prints tweets containing twitter4j or #twitter4j.
 */
public class TwitterStreamLambda {
    /**
     * example code to explain lambda expression. Prints tweets containing twitter4j or #twitter4j.
     *
     * @param args not used
     */
    public static void main(String... args) {
        Twitter.newBuilder()
                .onStatus(e -> System.out.printf("@%s %s%n", e.getUser().getScreenName(), e.getText()))
                .onException(Throwable::printStackTrace)
                .build().v1().stream().filter(FilterQuery.ofTrack("twitter4j", "#twitter4j"));

    }
}
