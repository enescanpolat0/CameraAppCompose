package com.enescanpolat.cameraappcompose.domain.repository

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

interface CameraRepository {

    suspend fun captureAndSaveImage(context:Context)
    suspend fun showCameraPreView(previewview:PreviewView, lifecycleOwner: LifecycleOwner)
}