package com.enescanpolat.cameraappcompose.presentation

import android.content.Context
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enescanpolat.cameraappcompose.domain.repository.CameraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo:CameraRepository
):ViewModel() {



    fun showCameraPreview(previewView: PreviewView, lifecycleOwner: LifecycleOwner){
        viewModelScope.launch {
            repo.showCameraPreView(previewView,lifecycleOwner)
        }
    }

    fun captureAndSave(context: Context){
        viewModelScope.launch {
            repo.captureAndSaveImage(context)
        }
    }


}