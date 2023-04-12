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

package examples.json;

import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.v1.Status;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Example application that load raw JSON forms from statuses/ directory and dump status texts.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class LoadRawJSON {
    /**
     * Usage: java twitter4j.examples.json.LoadRawJSON
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        try {
            File[] files = new File("statuses").listFiles((dir, name) -> name.endsWith(".json"));
            for (File file : files) {
                String rawJSON = readFirstLine(file);
                Status status = TwitterObjectFactory.createStatus(rawJSON);
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
            System.exit(0);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to store tweets: " + ioe.getMessage());
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }

    private static String readFirstLine(File fileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            return br.readLine();
        }
    }
}
