package com.enescanpolat.cameraappcompose.presentation

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.enescanpolat.cameraappcompose.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val permissions = if (Build.VERSION.SDK_INT<=28){
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }else listOf(Manifest.permission.CAMERA)


    val permissionState = rememberMultiplePermissionsState(permissions = permissions)

    if (!permissionState.allPermissionsGranted){
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
    }



    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var previewview : PreviewView


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {

        if (permissionState.allPermissionsGranted){
            Box(modifier= Modifier
                .height(height = screenHeight * 0.85f)
                .width(width = screenWidth * 0.85f)){
                AndroidView(
                factory = {
                    previewview = PreviewView(it)
                    viewModel.showCameraPreview(previewview,lifecycleOwner)
                    previewview
                },
                modifier = Modifier
                    .height(screenHeight * 0.85f)
                    .width(screenWidth * 0.85f)
             )

            }
        }

        Box(
            modifier = Modifier
                .height(screenHeight*0.15f),
            contentAlignment = Alignment.Center
        ){
            IconButton(onClick = {
                if (permissionState.allPermissionsGranted){
                    viewModel.captureAndSave(context)
                }else{
                    Toast.makeText(context,"Please Accept Permissions in app settings",Toast.LENGTH_LONG).show()
                }
            }) {

                Icon(
                    painter = painterResource(id = R.drawable.baseline_camera_24),
                    contentDescription = "",
                    modifier = Modifier.size(45.dp),
                    tint = Color.Blue
                )


            }
        }

    }


}