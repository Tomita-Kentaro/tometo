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
import twitter4j.v1.Paging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Execution(ExecutionMode.CONCURRENT)
class PagingTest {
    @Test
    void testPaging() {
        java.util.List<HttpParameter> params;
        Paging paging = TimelinesResourcesImpl.empty;
        params = TimelinesResourcesImpl.asPostParameterList(paging);
        assertEquals(0, params.size());
        // parameter validation test
        params = TimelinesResourcesImpl.asPostParameterList(TimelinesResourcesImpl.S, paging);
        assertEquals(0, params.size());

        // setter validation test
        try {
            Paging.ofSinceId(-1L);
            fail("sinceId should not accept negative value");
        } catch (IllegalArgumentException ignore) {
        }
        // parameter validation test
        try {
            TimelinesResourcesImpl.asPostParameterList(TimelinesResourcesImpl.S, paging);
        } catch (IllegalStateException ise) {
            fail("IllegalStateException should not be thrown.");
        }
        params = TimelinesResourcesImpl.asPostParameterList(paging);
        assertEquals(0, params.size());
        paging = Paging.ofSinceId(2000L);
        params = TimelinesResourcesImpl.asPostParameterList(paging);
        assertEquals(1, params.size());
        assertContains(params, "since_id", 2000L);

        // setter validation test
        try {
            Paging.ofPage(-1);
            fail("page should not accept negative value");
        } catch (IllegalArgumentException ignore) {
        }
        paging = paging.withPage(10);
        params = TimelinesResourcesImpl.asPostParameterList(paging);
        assertEquals(2, params.size());
        assertContains(params, "page", 10);
        assertContains(params, "since_id", 2000L);

        // setter validation test
        try {
            Paging.ofMaxId(-1L);
            fail("maxId should not accept negative value");
        } catch (IllegalArgumentException ignore) {
        }
        // parameter validation test
        try {
            TimelinesResourcesImpl.asPostParameterList(TimelinesResourcesImpl.S, paging);
            fail("should accept only since_id parameter");
        } catch (IllegalStateException ignore) {
        }
        params = TimelinesResourcesImpl.asPostParameterList(paging);
        assertEquals(2, params.size());
        paging = paging.maxId(1000L);
        params = TimelinesResourcesImpl.asPostParameterList(paging);
        assertEquals(3, params.size());
        assertContains(params, "page", 10);
        assertContains(params, "max_id", 1000L);
        assertContains(params, "since_id", 2000L);


        // setter validation test
        try {
            Paging.ofCount(-1);
            fail("count should not accept negative value");
        } catch (IllegalArgumentException ignore) {
        }
        // parameter validation test
        try {
            TimelinesResourcesImpl.asPostParameterList(TimelinesResourcesImpl.S, paging);
            fail("should accept only since_id parameter");
        } catch (IllegalStateException ignore) {
        }
        params = TimelinesResourcesImpl.asPostParameterList(paging);
        assertEquals(3, params.size());
        paging = paging.count(3000);
        params = TimelinesResourcesImpl.asPostParameterList(paging);
        assertEquals(4, params.size());
        assertContains(params, "page", 10);
        assertContains(params, "max_id", 1000L);
        assertContains(params, "since_id", 2000L);
        assertContains(params, "count", 3000);

    }

    private void assertContains(java.util.List<HttpParameter> params, String name, long value) {
        boolean contains = false;
        for (HttpParameter param : params) {
            if (param.getName().equals(name) && param.getValue().equals(String.valueOf(value))) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            fail("expected parameter " + name + ":" + value + " not found.");
        }

    }
}
