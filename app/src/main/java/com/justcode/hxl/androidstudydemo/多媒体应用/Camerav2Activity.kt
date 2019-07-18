package com.justcode.hxl.androidstudydemo.多媒体应用

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Size
import android.view.TextureView
import com.justcode.hxl.androidstudydemo.R
import com.justcode.hxl.androidstudydemo.permission.RxPermissions
import kotlinx.android.synthetic.main.activity_camerav2.*

class Camerav2Activity : AppCompatActivity() {

    //摄像头ID (通常0表示后置摄像头，1表示前置摄像头)
    val cameraId = "0"
    //定义代表摄像头的成员变量
    var cameraDevice: CameraDevice? = null
    //预览尺寸
    var previewSize: Size? = null

    var previewRequestBuilder: CaptureRequest.Builder? = null

    //定义用于预览照片的捕获请求
    var previewRequest: CaptureRequest? = null
    //定义CameraCaptureSession 成员变量
    var cameraCaptureSession: CameraCaptureSession? = null
    var imageReader: ImageReader? = null

    val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture?, width: Int, height: Int) {

        }

        override fun onSurfaceTextureUpdated(texture: SurfaceTexture?) {


        }

        override fun onSurfaceTextureDestroyed(texture: SurfaceTexture?): Boolean {
            return true
        }

        override fun onSurfaceTextureAvailable(texture: SurfaceTexture?, width: Int, height: Int) {
            // textureview可用时，打开摄像头 1
            openCamera(width,height)
        }

    }

    val stateCallback = object : CameraDevice.StateCallback() {
        //摄像头被打开时，调用此方法
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            //开始预览 2
            creatCameraPreviewSession()
        }

        //摄像头断开连接时，调用
        override fun onDisconnected(camera: CameraDevice) {
            camera.close()
            cameraDevice = null
        }

        override fun onError(camera: CameraDevice, error: Int) {
            camera.close()
            cameraDevice = null
            finish()
        }

    }

    fun creatCameraPreviewSession() {

    }


    @SuppressLint("MissingPermission")
    fun openCamera(width: Int, height: Int) {
        setUpCameraOutputs(width,height)
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        //打开摄像头
        manager.openCamera(cameraId,stateCallback,null)
    }

     fun setUpCameraOutputs(width: Int, height: Int) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camerav2)

        RxPermissions(this)
            .request(Manifest.permission.CAMERA)
            .subscribe {
                if (it) {
                    textureView.surfaceTextureListener = surfaceTextureListener
                    ib_capture.setOnClickListener {
                        captureStillPicture()
                    }
                }
            }.dispose()


    }

    fun captureStillPicture() {
        if (cameraDevice == null || imageReader == null) {
            return
        }
        //创建 作为拍照的CaptureRequest.Builder
        val captureRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        //将imageReder的surface作为CaptureRequest.Builder 的目标
        captureRequestBuilder.addTarget(imageReader!!.surface)
        //设置自动对焦模式
        captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
        //设置自动曝光模式
        captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
        //设置设备方向
        val rotation = windowManager.defaultDisplay.rotation
        //根据设备方向计算照片的方向
        captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation))
        //停止连续取景
        cameraCaptureSession!!.stopRepeating()
        //捕获静态图像
        cameraCaptureSession!!.capture(captureRequestBuilder.build(), object : CameraCaptureSession.CaptureCallback() {
            //拍照完成时，调用
            override fun onCaptureCompleted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                result: TotalCaptureResult
            ) {
                //重设自动对焦模式
                previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_TRIGGER, CaptureRequest.CONTROL_AF_TRIGGER_CANCEL)
                //重设自动曝光
                previewRequestBuilder!!.set(
                    CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
                )
                //打开连续取景模式
                cameraCaptureSession!!.setRepeatingRequest(previewRequest!!, null, null)
            }
        }, null)
    }
}
