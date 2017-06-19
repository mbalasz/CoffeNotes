package com.example.mateusz.coffeenotes

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class PictureUtils {
    companion object {
        fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
            var bitmapFactoryOptions = BitmapFactory.Options()
            bitmapFactoryOptions.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, bitmapFactoryOptions)

            val srcWidth = bitmapFactoryOptions.outWidth
            val srcHeight = bitmapFactoryOptions.outHeight

            var inSampleSize = 1
            if (srcHeight > destHeight || srcWidth > destHeight) {
                val widthRatio = srcWidth / destWidth
                val heightRatio = srcHeight / destHeight

                inSampleSize = if (widthRatio > heightRatio) heightRatio else widthRatio
            }

            bitmapFactoryOptions = BitmapFactory.Options()
            bitmapFactoryOptions.inSampleSize = inSampleSize
            return BitmapFactory.decodeFile(path, bitmapFactoryOptions)
        }
    }
}