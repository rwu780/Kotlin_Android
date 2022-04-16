package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.KEY_IMAGE_URI
import com.example.background.R

class BlurWorker (
    ctx: Context,
    params: WorkerParameters
    ) : Worker(ctx, params){



    override fun doWork(): Result {

        val appContext = applicationContext

        val resourceUri = inputData.getString(KEY_IMAGE_URI)

        return try {
            val picture = BitmapFactory.decodeResource(
                appContext.resources,
                R.drawable.android_cupcake
            )

            val blurPicture = blurBitmap(picture, appContext)

            val tempUrl = writeBitmapToFile(appContext, blurPicture)
            makeStatusNotification("Output is $tempUrl", appContext)

            Result.success()
        } catch (throwable: Throwable){
            Result.failure()
        }


    }


}