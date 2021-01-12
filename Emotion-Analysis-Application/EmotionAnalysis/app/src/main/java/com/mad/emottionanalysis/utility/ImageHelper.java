package com.mad.emottionanalysis.utility;

//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license.
//
// Microsoft Cognitive Services (formerly Project Oxford): https://www.microsoft.com/cognitive-services
//
// Microsoft Cognitive Services (formerly Project Oxford) GitHub:
// https://github.com/Microsoft/Cognitive-Face-Android
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.mad.emottionanalysis.model.FaceRectangle;
import com.mad.emottionanalysis.model.RecognizeResult;
/**
 * Defined several functions to load, draw, save, resize, and rotate images.
 */
public class ImageHelper {

    // Ratio to scale a detected face rectangle, the face rectangle scaled up looks more natural.
    private static final double FACE_RECT_SCALE_RATIO = 1.3;

    // Draw detected face rectangles in the original image. And return the image drawn.
    // If drawLandmarks is set to be true, draw the five main landmarks of each face.
    public static Bitmap drawFaceRectanglesOnBitmap(
            Bitmap originalBitmap, RecognizeResult[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        int stokeWidth = Math.max(originalBitmap.getWidth(), originalBitmap.getHeight()) / 100;
        if (stokeWidth == 0) {
            stokeWidth = 1;
        }
        paint.setStrokeWidth(stokeWidth);

        if (faces != null) {
            for (RecognizeResult face : faces) {
                FaceRectangle faceRectangle =
                        calculateFaceRectangle(bitmap, face.faceRectangle, FACE_RECT_SCALE_RATIO);

                canvas.drawRect(
                        faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);

            }
        }
        return bitmap;
    }

    // Resize face rectangle, for better view for human
    // To make the rectangle larger, faceRectEnlargeRatio should be larger than 1, recommend 1.3
    private static FaceRectangle calculateFaceRectangle(
            Bitmap bitmap, FaceRectangle faceRectangle, double faceRectEnlargeRatio) {
        // Get the resized side length of the face rectangle
        double sideLength = faceRectangle.width * faceRectEnlargeRatio;
        sideLength = Math.min(sideLength, bitmap.getWidth());
        sideLength = Math.min(sideLength, bitmap.getHeight());

        // Make the left edge to left more.
        double left = faceRectangle.left
                - faceRectangle.width * (faceRectEnlargeRatio - 1.0) * 0.5;
        left = Math.max(left, 0.0);
        left = Math.min(left, bitmap.getWidth() - sideLength);

        // Make the top edge to top more.
        double top = faceRectangle.top
                - faceRectangle.height * (faceRectEnlargeRatio - 1.0) * 0.5;
        top = Math.max(top, 0.0);
        top = Math.min(top, bitmap.getHeight() - sideLength);

        // Shift the top edge to top more, for better view for human
        double shiftTop = faceRectEnlargeRatio - 1.0;
        shiftTop = Math.max(shiftTop, 0.0);
        shiftTop = Math.min(shiftTop, 1.0);
        top -= 0.15 * shiftTop * faceRectangle.height;
        top = Math.max(top, 0.0);

        // Set the result.
        FaceRectangle result = new FaceRectangle();
        result.left = (int)left;
        result.top = (int)top;
        result.width = (int)sideLength;
        result.height = (int)sideLength;
        return result;
    }

}

