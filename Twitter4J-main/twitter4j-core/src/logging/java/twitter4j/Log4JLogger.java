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

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.1
 */
final class Log4JLogger extends Logger {
    private final org.apache.logging.log4j.Logger LOGGER;

    Log4JLogger(org.apache.logging.log4j.Logger logger) {
        LOGGER = logger;
    }

    @Override
    public boolean isDebugEnabled() {
        return LOGGER.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return LOGGER.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return LOGGER.isEnabled(org.apache.logging.log4j.Level.WARN);
    }

    @Override
    public boolean isErrorEnabled() {
        return LOGGER.isEnabled(org.apache.logging.log4j.Level.WARN);
    }

    @Override
    public void debug(String message) {
        LOGGER.debug(message);
    }

    @Override
    public void debug(String message, String message2) {
        debug(message + message2);
    }

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void info(String message, String message2) {
        info(message + message2);
    }

    @Override
    public void warn(String message) {
        LOGGER.warn(message);
    }

    @Override
    public void warn(String message, String message2) {
        warn(message + message2);
    }

    @Override
    public void warn(String message, Throwable th) {
        LOGGER.warn(message, th);
    }

    @Override
    public void error(String message) {
        LOGGER.error(message);
    }

    @Override
    public void error(String message, Throwable th) {
        LOGGER.error(message, th);
    }
}
