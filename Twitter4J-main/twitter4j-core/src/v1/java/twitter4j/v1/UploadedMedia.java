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


/**
 * Represents result of "/1.1/media/upload.json"
 *
 * @author Hiroaki TAKEUCHI - takke30 at gmail.com
 * @since Twitter4J 4.0.2
 */
public interface UploadedMedia extends java.io.Serializable {
    /**
     * @return image width
     */
    int getImageWidth();

    /**
     * @return image height
     */
    int getImageHeight();

    /**
     * @return image type
     */
    String getImageType();

    /**
     * @return media id
     */
    long getMediaId();

    /**
     * @return size
     */
    long getSize();

    /**
     * @return processing state
     */
    String getProcessingState();

    /**
     * @return processingCheckAfterSecs
     */
    int getProcessingCheckAfterSecs();

    /**
     * @return progressPercent
     */
    int getProgressPercent();
}
