package com.justcode.hxl.androidstudydemo.多媒体应用


import android.graphics.Point
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.view.Surface
import android.view.SurfaceHolder
import android.widget.FrameLayout
import android.widget.RelativeLayout

import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_media_player_surface.*


class MediaPlayerSurfaceActivity : AppCompatActivity(), SurfaceHolder.Callback {

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        val surface = holder.surface
        setupMediaPlayer(surface)
        prepareMediaPlayer()
    }

    private lateinit var mediaPlayer: MediaPlayer
    private var playbackPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer()
        try {
            val afd = resources.openRawResourceFd(R.raw.texiao)
            mediaPlayer.setDataSource(
                afd.fileDescriptor, afd.startOffset, afd.length
            )
            afd.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        setContentView(R.layout.activity_media_player_surface)

        val holder = surface.holder
        holder.addCallback(this)
    }

    override fun onPause() {
        super.onPause()

        playbackPosition = mediaPlayer.currentPosition
    }

    override fun onStop() {
        mediaPlayer.stop()
        mediaPlayer.release()

        super.onStop()
    }

    private fun createAudioAttributes(): AudioAttributes {
        val builder = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
        return builder.build()
    }

    private fun setupMediaPlayer(surface: Surface) {
        mediaPlayer.setSurface(surface)
        val audioAttributes = createAudioAttributes()
        mediaPlayer.setAudioAttributes(audioAttributes)
    }

    private fun setSurfaceDimensions(player: MediaPlayer, width: Int, height: Int) {
        if (width > 0 && height > 0) {
            val aspectRatio = height.toFloat() / width.toFloat()
            val screenDimensions = Point()
            windowManager.defaultDisplay.getSize(screenDimensions)
            val surfaceWidth = screenDimensions.x
            val surfaceHeight = (surfaceWidth * aspectRatio).toInt()
            val params = RelativeLayout.LayoutParams(surfaceWidth, surfaceHeight)
            surface.layoutParams = params
            val holder = surface.holder
            player.setDisplay(holder)
        }
    }

    private fun prepareMediaPlayer() {
        try {
            mediaPlayer.prepareAsync()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        mediaPlayer.setOnPreparedListener {
            mediaPlayer.seekTo(playbackPosition)
            mediaPlayer.start()
        }

        mediaPlayer.setOnVideoSizeChangedListener { player, width, height ->
            setSurfaceDimensions(player, width, height)
        }
    }

}