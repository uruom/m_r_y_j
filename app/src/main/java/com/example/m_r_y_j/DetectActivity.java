// Tencent is pleased to support the open source community by making ncnn available.
//
// Copyright (C) 2020 THL A29 Limited, a Tencent company. All rights reserved.
//
// Licensed under the BSD 3-Clause License (the "License"); you may not use this file except
// in compliance with the License. You may obtain a copy of the License at
//
// https://opensource.org/licenses/BSD-3-Clause
//
// Unless required by applicable law or agreed to in writing, software distributed
// under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
// CONDITIONS OF ANY KIND, either express or implied. See the License for the
// specific language governing permissions and limitations under the License.

package com.example.m_r_y_j;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;


public class DetectActivity extends Activity
{
    private static final int SELECT_IMAGE = 1;

    private ImageView imageView;
    private Bitmap bitmap = null;
    private Bitmap yourSelectedImage = null;

    private final YoloV5Ncnn yolov5ncnn = new YoloV5Ncnn();

//    private final String[] objectLabelEng = {"person", "car", "bus", "bicycle", "traffic light"};
//    private final String[] objectLabelChi = {"人", "汽车", "公共汽车", "自行车", "信号灯"};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        boolean ret_init = yolov5ncnn.Init(getAssets());
        if (!ret_init)
        {
            Log.e("DetectActivity", "yolov5ncnn Init failed");
        }

        imageView = findViewById(R.id.imageView);

        Button buttonImage = findViewById(R.id.buttonImage);
        buttonImage.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i, SELECT_IMAGE);
        });

        Button buttonDetect = findViewById(R.id.buttonDetect);
        buttonDetect.setOnClickListener(view -> {
            if (yourSelectedImage == null)
                return;

            YoloV5Ncnn.Obj[] objects = yolov5ncnn.Detect(yourSelectedImage, false);

            showObjects(objects);

//                convertObjectsToSentences(objects);
        });

        Button buttonDetectGPU = findViewById(R.id.buttonDetectGPU);
        buttonDetectGPU.setOnClickListener(view -> {
            if (yourSelectedImage == null)
                return;

            YoloV5Ncnn.Obj[] objects = yolov5ncnn.Detect(yourSelectedImage, true);

            showObjects(objects);

//                convertObjectsToSentences(objects);
        });
    }

//    private void convertObjectsToSentences(YoloV5Ncnn.Obj[] objects) {
//
//        String res = null;
//        int[] numOfObjects = new int[]{0,0,0,0,0};
//        float probThresh = (float)0.75;
//
//        if (objects == null)
//        {
//            imageView.setImageBitmap(bitmap);
//            return;
//        }
//
//        for(int i=0; i < objects.length; ++i){
//            if(objects[i].prob >= probThresh) {
//                for(int j=0; j < objectLabelEng.length; ++j){
//                    if(objects[i].label.equals(objectLabelEng[j])){
//                        numOfObjects[j]++;
//                        break;
//                    }
//                }
//            }
//        }
//
//        res = "前方有";
//        for(int j=0; j < numOfObjects.length; ++j){
//            res += numOfObjects[j] + objectLabelChi[j] + " ";
//        }
//
//        Bitmap rgba = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//        Canvas canvas = new Canvas(rgba);
//
//        Paint textPaint = new Paint();
//        textPaint.setColor(Color.BLACK);
//        textPaint.setTextSize(26);
//        textPaint.setTextAlign(Paint.Align.LEFT);
//
//        Paint textBgPaint = new Paint();
//        textBgPaint.setColor(Color.WHITE);
//        textBgPaint.setStyle(Paint.Style.FILL);
//
//        canvas.drawRect(100, 100, 100, 100, textBgPaint);
//        canvas.drawText(res, 100, 100, textPaint);
//        imageView.setImageBitmap(rgba);
//
//    }

    private void showObjects(YoloV5Ncnn.Obj[] objects)
    {
        if (objects == null)
        {
            imageView.setImageBitmap(bitmap);
            return;
        }

        // draw objects on bitmap
        Bitmap rgba = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        final int[] colors = new int[] {
            Color.rgb( 54,  67, 244),
            Color.rgb( 99,  30, 233),
            Color.rgb(176,  39, 156),
            Color.rgb(183,  58, 103),
            Color.rgb(181,  81,  63),
            Color.rgb(243, 150,  33),
            Color.rgb(244, 169,   3),
            Color.rgb(212, 188,   0),
            Color.rgb(136, 150,   0),
            Color.rgb( 80, 175,  76),
            Color.rgb( 74, 195, 139),
            Color.rgb( 57, 220, 205),
            Color.rgb( 59, 235, 255),
            Color.rgb(  7, 193, 255),
            Color.rgb(  0, 152, 255),
            Color.rgb( 34,  87, 255),
            Color.rgb( 72,  85, 121),
            Color.rgb(158, 158, 158),
            Color.rgb(139, 125,  96)
        };

        Canvas canvas = new Canvas(rgba);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        Paint textBgPaint = new Paint();
        textBgPaint.setColor(Color.WHITE);
        textBgPaint.setStyle(Paint.Style.FILL);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(26);
        textPaint.setTextAlign(Paint.Align.LEFT);

        for (int i = 0; i < objects.length; i++)
        {
            paint.setColor(colors[i % 19]);

            canvas.drawRect(objects[i].x, objects[i].y, objects[i].x + objects[i].w, objects[i].y + objects[i].h, paint);

            // draw filled text inside image
            {
                String text = objects[i].label + " = " + String.format("%.1f", objects[i].prob * 100) + "%";

                float text_width = textPaint.measureText(text);
                float text_height = - textPaint.ascent() + textPaint.descent();

                float x = objects[i].x;
                float y = objects[i].y - text_height;
                if (y < 0)
                    y = 0;
                if (x + text_width > rgba.getWidth())
                    x = rgba.getWidth() - text_width;

                canvas.drawRect(x, y, x + text_width, y + text_height, textBgPaint);

                canvas.drawText(text, x, y - textPaint.ascent(), textPaint);
            }
        }

        imageView.setImageBitmap(rgba);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            try
            {
                if (requestCode == SELECT_IMAGE) {
                    bitmap = decodeUri(selectedImage);

                    yourSelectedImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                    imageView.setImageBitmap(bitmap);
                }
            }
            catch (FileNotFoundException e)
            {
                Log.e("DetectActivity", "FileNotFoundException");
                return;
            }
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException
    {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 640;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
               || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

        // Rotate according to EXIF
        int rotate = 0;
        try
        {
            ExifInterface exif = new ExifInterface(getContentResolver().openInputStream(selectedImage));
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        }
        catch (IOException e)
        {
            Log.e("DetectActivity", "ExifInterface IOException");
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

}
