package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R

class BlurWorker (
    ctx: Context,
    params: WorkerParameters
    ) : Worker(ctx, params){

    override fun doWork(): Result {

        val picture = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.android_cupcake
        )

        val blurPicture = blurBitmap(picture,applicationContext)

        // Write bitmap to a temp file
        val outputUri = writeBitmapToFile(applicationContext, blurPicture)

    }


}