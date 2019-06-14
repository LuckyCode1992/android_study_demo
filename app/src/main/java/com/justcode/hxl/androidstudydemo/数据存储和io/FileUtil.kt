package com.justcode.hxl.androidstudydemo.数据存储和io

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.PrintStream
import java.lang.Exception
import java.lang.StringBuilder

object FileUtil {
    val FILE_NAME = "androidstudydemo_name.txt"
    fun read(file_name: String = FILE_NAME, context: Context): String {
        val buff = ByteArray(1024)
        val sb = StringBuilder("")
        var fis: FileInputStream? = null
        try {
            fis = context.openFileInput(file_name)
            var hasRead = fis.read(buff)
            while (hasRead > 0) {
                sb.append(String(buff, 0, hasRead))
                hasRead = fis.read(buff)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fis?.close()
        }
        return sb.toString()
    }

    fun write(file_name: String = FILE_NAME, content: String, context: Context) {
        var fos: FileOutputStream? = null
        try {
            //追加模式，基本都用这种
            fos = context.openFileOutput(file_name, Context.MODE_APPEND)
            //将fos包装成PrintStream
            val ps = PrintStream(fos)
            //输出文件内容  println 自动换行
            ps.println(content)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fos?.close()
        }
    }

    fun delFile(file_name: String = FILE_NAME, context: Context) {
        var fos: FileOutputStream? = null
        try {
            //追加模式，基本都用这种
            fos = context.openFileOutput(file_name, Context.MODE_PRIVATE)
            //将fos包装成PrintStream
            val ps = PrintStream(fos)
            //输出文件内容  println 自动换行
            ps.print("")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fos?.close()
        }

    }
}