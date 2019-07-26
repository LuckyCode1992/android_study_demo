package com.justcode.hxl.androidstudydemo.openGL

import android.opengl.GLSurfaceView
import java.nio.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyRenderer : GLSurfaceView.Renderer {

    var triangleData = floatArrayOf(
        0.1f, 0.6f, 0.0f,//上顶点
        -0.3f, 0.0f, 0.0f,//左顶点
        0.3f, 0.1f, 0.0f//右顶点

    )
    var triangleColor = intArrayOf(
        65535, 0, 0, 0,//上顶点 红色
        0, 65535, 0, 0,//左顶点 绿色
        0, 0, 65535, 0//右顶点 蓝色
    )
    var rectData = floatArrayOf(
        0.4f, 0.4f, 0.0f,//右上顶点
        0.4f, -0.4f, 0.0f,//右下顶点
        -0.4f, 0.4f, 0.0f,//左上顶点
        -0.4f, -0.4f, 0.0f//左下顶点
    )
    var rectColor = intArrayOf(
        65535, 0, 0, 0,//右上顶点 红色
        0, 65535, 0, 0,//右下点 绿色
        0, 0, 65535, 0,//左上点 蓝色
        65535, 65535, 0, 0//左下 黄色
    )
    var rectData2 = floatArrayOf(
        -0.4f, 0.4f, 0.0f,//左上顶点
        0.4f, 0.4f, 0.0f,//右上顶点
        0.4f, -0.4f, 0.0f,//右下顶点
        -0.4f, -0.4f, 0.0f//左下顶点
    )
    var pentacle = floatArrayOf(
        0.4f, 0.4f, 0.0f, -0.2f, 0.3f,
        0.0f, 0.5f, 0.0f, 0f, -0.4f,
        0.0f, 0f, -0.1f, -.03f, 0f
    )

    var triangleDataBuffer: FloatBuffer
    var trangleColorBuffer: IntBuffer
    var rectDataBuffer: FloatBuffer
    var rectColorBuffer: IntBuffer
    var rectDataBuffer2: FloatBuffer
    var pentacleBuffer: FloatBuffer

    init {
        //将顶点位置数据组成转换程FloatBuffer
        //将顶点颜色数组转换成IntBuffer

        triangleDataBuffer = floatBufferUtil(triangleData)
        rectDataBuffer = floatBufferUtil(rectData)
        rectDataBuffer2 = floatBufferUtil(rectData2)
        pentacleBuffer = floatBufferUtil(pentacle)

        trangleColorBuffer = intBufferUtil(triangleColor)
        rectColorBuffer = intBufferUtil(rectColor)

    }


    /**
     * 数组转换为OpenGL ES 所需要的 buffer
     */
    fun intBufferUtil(arr: IntArray): IntBuffer {
        val buffer: IntBuffer
        //初始化byteBuffer，长度为arr数组的长度为4，因为int占4个字节
        val qbb = ByteBuffer.allocateDirect(arr.size * 4)
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder())
        buffer = qbb.asIntBuffer()
        buffer.put(arr)
        buffer.position(0)
        return buffer
    }

    fun floatBufferUtil(arr: FloatArray): FloatBuffer {
        val buffer: FloatBuffer
        //float 也占4字节
        val qbb = ByteBuffer.allocateDirect(arr.size * 4)
        qbb.order(ByteOrder.nativeOrder())
        buffer = qbb.asFloatBuffer()
        buffer.put(arr)
        buffer.position(0)
        return buffer
    }


    /**
     * 绘制图形的方法
     */
    override fun onDrawFrame(gl: GL10) {
        //清除屏幕缓存和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or  GL10.GL_DEPTH_BUFFER_BIT)
        //启动顶点坐标数据
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        //启动顶点颜色数据
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY)
        //设置当前矩阵堆栈为模型堆栈
        gl.glMatrixMode(GL10.GL_MODELVIEW)


    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        //设置3D视窗的大小和位置
        gl.glViewport(0, 0, width, height)
        //将当前矩阵模式设为投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION)
        //初始化单位矩阵
        gl.glLoadIdentity()
        //计算透视视窗的宽高比
        val ratio = width.toFloat() / height
        //设置透视视窗的空间大小
        gl.glFrustumf(-ratio, ratio, -1f, 1f, 1f, 10f)
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        //关闭抖动
        gl.glDisable(GL10.GL_DITHER)
        //设置系统对透视的修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST)
        gl.glClearColor(0f, 0f, 0f, 0f)
        //设置阴影平滑模式
        gl.glShadeModel(GL10.GL_SMOOTH)
        //设置深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST)
        //设置深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL)
    }

}