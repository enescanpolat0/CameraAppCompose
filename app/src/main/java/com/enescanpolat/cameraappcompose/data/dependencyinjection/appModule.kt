package com.enescanpolat.cameraappcompose.data.dependencyinjection

import android.app.Application
import android.content.Context
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
import com.enescanpolat.cameraappcompose.data.repository.CameraRepositoryImpl
import com.enescanpolat.cameraappcompose.domain.repository.CameraRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object appModule {

    @Singleton
    @Provides
    fun injectCameraSelector():CameraSelector{
        return CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
    }


    @Singleton
    @Provides
    fun injectCameraProvider(application: Application):ProcessCameraProvider{
        return ProcessCameraProvider.getInstance(application).get()
    }

    @Singleton
    @Provides
    fun injectCameraPreview():Preview{
        return Preview.Builder().build()
    }


    @Singleton
    @Provides
    fun injectImageCapture():ImageCapture{
        return ImageCapture.Builder()
            .setFlashMode(FLASH_MODE_ON)
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
    }


    @Singleton
    @Provides
    fun injectImageAnalysis():ImageAnalysis{
        return ImageAnalysis.Builder()
            .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }

    @Singleton
    @Provides
    fun injectCameraRepository(
        cameraProvider: ProcessCameraProvider,
        selector: CameraSelector,
        preview: Preview,
        imageAnalysis: ImageAnalysis,
        imageCapture: ImageCapture
    ): CameraRepository {
        return CameraRepositoryImpl(cameraProvider, selector, preview, imageAnalysis, imageCapture)
    }

}