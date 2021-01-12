/**
 *
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license.
 *
 * Microsoft Cognitive Services (formerly Project Oxford): https://www.microsoft.com/cognitive-services
 *
 * Microsoft Cognitive Services (formerly Project Oxford) GitHub:
 * https://github.com/Microsoft/Cognitive-Emotion-Android
 *
 * Copyright (c) Microsoft Corporation
 * All rights reserved.
 *
 * MIT License:
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.mad.emottionanalysis.networking;


import com.mad.emottionanalysis.model.FaceRectangle;
import com.mad.emottionanalysis.model.RecognizeResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface EmotionServiceClient {
    /**
     * this interface is used to recognize image without face rectangle
     * @param inputStream input stream
     * @return  RecognizeResult list object
     * @throws EmotionServiceException service exception
     * @throws IOException IO exception
     */
    public RecognizeResult[] recognizeImage(InputStream inputStream) throws EmotionServiceException, IOException;

    /**
     * this interface is used to recognize image with face rectangle
     * @param inputStream input stream
     * @param faceRectangles face rectangle
     * @return  RecognizeResult list object
     * @throws EmotionServiceException service exception
     * @throws IOException IO exception
     */
    public List<RecognizeResult> recognizeImage(InputStream inputStream, FaceRectangle[] faceRectangles) throws EmotionServiceException, IOException;
}
