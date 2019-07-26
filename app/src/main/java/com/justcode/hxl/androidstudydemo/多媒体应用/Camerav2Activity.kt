package com.justcode.hxl.androidstudydemo.多媒体应用

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Size
import android.util.SparseIntArray
import android.view.Surface
import android.view.TextureView
import com.justcode.hxl.androidstudydemo.R
import com.justcode.hxl.androidstudydemo.permission.RxPermissions
import com.justcode.hxl.progrect0.core.syntactic_sugar.toast
import kotlinx.android.synthetic.main.activity_camerav2.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.Comparator

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
            openCamera(width, height)
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

        val texture = textureView.surfaceTexture
        texture.setDefaultBufferSize(previewSize!!.width, previewSize!!.height)
        val surface = Surface(texture)
        //创建作为预览的CaptureRequest.Builder
        previewRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        //将textureView 的surface作为CaptureRequest.builder 的目标
        previewRequestBuilder!!.addTarget(surface)
        //创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
        cameraDevice!!.createCaptureSession(
            Arrays.asList(surface, imageReader!!.surface),
            object : CameraCaptureSession.StateCallback() {
                override fun onConfigureFailed(session: CameraCaptureSession) {
                    "配置失败".toast()
                }

                override fun onConfigured(session: CameraCaptureSession) {
                    //如果摄像头为null，则结束
                    if (null == cameraDevice) {
                        return
                    }
                    //当摄像头已经准备好时，开始显示预览
                    cameraCaptureSession = session
                    //设置自动对焦模式
                    previewRequestBuilder!!.set(
                        CaptureRequest.CONTROL_AF_MODE,
                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                    )
                    //设置自动曝光模式
                    previewRequestBuilder!!.set(
                        CaptureRequest.CONTROL_AE_MODE,
                        CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
                    )
                    //开始显示相机预览
                    previewRequest = previewRequestBuilder!!.build()
                    //设置预览时连续捕获图像数据
                    cameraCaptureSession!!.setRepeatingRequest(previewRequest!!, null, null)
                }

            }, null
        )
    }


    @SuppressLint("MissingPermission")
    fun openCamera(width: Int, height: Int) {
        setUpCameraOutputs(width, height)
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        //打开摄像头
        manager.openCamera(cameraId, stateCallback, null)
    }

    fun setUpCameraOutputs(width: Int, height: Int) {
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        //获取指定摄像头的特性
        val characteristics = manager.getCameraCharacteristics(cameraId)
        //获取摄像头支持的配置属性
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        //获取摄像头支持的最大尺寸（传给可变参数时要添加）
        val largest = Collections.max(Arrays.asList(*map.getOutputSizes(ImageFormat.JPEG)), CompareSizeByArea())
        //创建一个imagereader对象
        imageReader = ImageReader.newInstance(largest.width, largest.height, ImageFormat.JPEG, 2)
        imageReader!!.setOnImageAvailableListener({ reader ->
            //当照片数据可用时，激发该方法
            //获取捕获的照片数据
            val image = reader.acquireNextImage()
            val buffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            //使用IO 流将照片写入指定文件
            val file = File(getExternalFilesDir(null), "pic.jpg")
            buffer.get(bytes)
            image.use {
                FileOutputStream(file).use { output ->
                    output.write(bytes)

                    "保存：${file}".toast()

                }
            }
        }, null)
        //获取最佳预览尺寸
        previewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture::class.java), width, height, largest)
        //根据选中的预览尺寸来调整预览组件  的宽高比
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            textureView.setAspectRatio(previewSize!!.width,previewSize!!.height)
        }else{
            textureView.setAspectRatio(previewSize!!.height,previewSize!!.width)
        }

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

    companion object {
        val ORIENTATIONS = SparseIntArray()

        init {
            ORIENTATIONS.append(Surface.ROTATION_0, 90)
            ORIENTATIONS.append(Surface.ROTATION_90, 0)
            ORIENTATIONS.append(Surface.ROTATION_180, 270)
            ORIENTATIONS.append(Surface.ROTATION_270, 180)
        }

        fun chooseOptimalSize(choices: Array<Size>, width: Int, height: Int, aspectRatio: Size): Size {
            //收集摄像头支持的预览 Surface 的分辨率
            val w = aspectRatio.width
            val h = aspectRatio.height
            val bigEnough = choices.filter {
                it.height == it.width * h / w
            }
            //如果找到多个预览尺寸，获取其中面积最小的
            return if (bigEnough.isNotEmpty()) {
                Collections.min(bigEnough, CompareSizeByArea())
            } else {
                "找不到合适的预览尺寸".toast()
                choices[0]
            }
        }
    }

}

/**
 * 为size定义一个比较器
 */
class CompareSizeByArea : Comparator<Size> {
    override fun compare(lhs: Size, rhs: Size): Int {
        //强转为long，保证不会发生溢出
        return java.lang.Long.signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)
    }

}
