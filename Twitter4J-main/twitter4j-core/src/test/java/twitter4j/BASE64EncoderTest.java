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

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
@Execution(ExecutionMode.CONCURRENT)
class BASE64EncoderTest {

    @Test
    void testEncode() {
        assertEquals("QUJDREVGRw==", BASE64Encoder.encode("ABCDEFG".getBytes()));
        assertEquals("VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wZWQgb3ZlciB0aGUgbGF6eSBkb2cu", BASE64Encoder.encode("The quick brown fox jumped over the lazy dog.".getBytes()));
        assertEquals("bGVhc3VyZS4=", BASE64Encoder.encode("leasure.".getBytes()));
        assertEquals("ZWFzdXJlLg==", BASE64Encoder.encode("easure.".getBytes()));
        assertEquals("YXN1cmUu", BASE64Encoder.encode("asure.".getBytes()));
        assertEquals("c3VyZS4=", BASE64Encoder.encode("sure.".getBytes()));
    }
}
