package com.chatty.compose.screens.contracts

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.view.SurfaceView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.chatty.compose.R
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.TopBar
import com.chatty.compose.ui.utils.LocalNavController
import com.chatty.compose.ui.utils.USER_CODE_PREFIX
import com.chatty.compose.ui.utils.hasTorch
import com.king.zxing.CaptureHelper
import com.king.zxing.OnCaptureCallback
import com.king.zxing.ViewfinderView
import com.king.zxing.camera.CameraConfigurationUtils


private fun setTorch(helper: CaptureHelper, on: Boolean) {
    val camera = helper.cameraManager.openCamera.camera
    val parameters = camera.parameters
    CameraConfigurationUtils.setTorch(parameters, on)
    camera.parameters = parameters
}


@Composable
fun QrCodeScan() {
    val naviController = LocalNavController.current
    val context = LocalContext.current
    val hasTorch = remember { context.hasTorch() }
    val surfaceView = remember { SurfaceView(context) }
    val viewfinderView = remember { ViewfinderView(context) }
    var isUsingFlashLight by remember { mutableStateOf(false) }
    val helper = remember {
        CaptureHelper(context as Activity, surfaceView, viewfinderView).apply {
            val captureCallback = OnCaptureCallback {
                // 待处理
                if (it.startsWith(USER_CODE_PREFIX)) {
                    var uid = it.removePrefix(USER_CODE_PREFIX)
                    naviController.navigate("${AppScreen.strangerProfile}/${uid}/二维码搜索")
                }
                Log.d("gzz", "scan result: $it")
                restartPreviewAndDecode()
                true
            }
            setOnCaptureCallback(captureCallback)
            playBeep(true)
            continuousScan(true)
            autoRestartPreviewAndDecode(false)
            onCreate()
            (context as? ComponentActivity)?.lifecycle?.addObserver(object: LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                fun onResume() {
                    this@apply.onResume()
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                fun onPause() {
                    this@apply.onPause()
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    this@apply.onDestroy()
                }
            })
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { surfaceView },
            modifier = Modifier.fillMaxSize()
        )
        AndroidView(
            factory = { viewfinderView },
            modifier = Modifier.fillMaxSize()
        )
        if (hasTorch) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = if (isUsingFlashLight) R.drawable.ec_on else R.drawable.ec_off),
                    contentDescription = "flash",
                    modifier = Modifier
                        .padding(top = 150.dp)
                        .clickable {
                            isUsingFlashLight = !isUsingFlashLight
                            setTorch(helper, isUsingFlashLight)
                        }
                )
            }
        }
        QrCodeScanTopBar()
    }
}

@Composable
fun QrCodeScanTopBar() {
    var naviController = LocalNavController.current
    TopBar(
        start = {
            IconButton(onClick = {
                naviController.popBackStack()
            }) {
                Icon(Icons.Rounded.ArrowBack, null, tint = Color.White)
            }
        },
        center = {
            Text("扫一扫", color = Color.White, fontWeight = FontWeight.Bold)
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}
