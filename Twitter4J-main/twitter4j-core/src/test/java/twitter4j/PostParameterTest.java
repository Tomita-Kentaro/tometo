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

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
@Execution(ExecutionMode.CONCURRENT)
class PostParameterTest {

    @Test
    void testBooleanParameter() {
        assertEquals("true", new HttpParameter("test", true).getValue());
        assertEquals("false", new HttpParameter("test", false).getValue());
    }

    @Test
    void testgetContentType() {
        assertValidContentType("image/jpeg", "img.jpeg");
        assertValidContentType("image/jpeg", "img.JPEG");
        assertValidContentType("image/jpeg", "img.jpg");
        assertValidContentType("image/jpeg", "img.JPG");
        assertValidContentType("image/jpeg", "img.JpG");
        assertValidContentType("image/jpeg", "img.jPg");

        assertValidContentType("image/gif", "img.gif");
        assertValidContentType("image/gif", "img.GIF");
        assertValidContentType("image/gif", "img.GiF");
        assertValidContentType("image/gif", "img.gIf");

        assertValidContentType("image/png", "img.png");
        assertValidContentType("image/png", "img.PNG");
        assertValidContentType("image/png", "img.PnG");
        assertValidContentType("image/png", "img.pNg");

        assertValidContentType("application/octet-stream", "img.jpegjpeg");
        assertValidContentType("application/octet-stream", "img.");
        assertValidContentType("application/octet-stream", "img.els");
        assertValidContentType("application/octet-stream", "img.ai");
        assertValidContentType("application/octet-stream", "img.ps");
        assertValidContentType("application/octet-stream", "img.txt");
        assertValidContentType("application/octet-stream", "img");

    }

    private void assertValidContentType(String expected, String fileName) {
        HttpParameter param = new HttpParameter("file", new File(fileName));
        assertEquals(expected, param.getContentType());

    }
}
