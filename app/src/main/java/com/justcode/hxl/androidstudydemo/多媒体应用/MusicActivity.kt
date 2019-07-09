package com.justcode.hxl.androidstudydemo.多媒体应用


import android.content.Context
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_music.*
import kotlinx.android.synthetic.main.file_item.view.*


class MusicActivity : AppCompatActivity() {
    private val FILE_SELECT_CODE = 155
    var contentUtil: ContentUtil? = null
    val list: MutableList<FileItem> = ArrayList()
    val player = MediaPlayer.create(this,R.raw.love_mail)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentUtil = ContentUtil(this)
        setContentView(R.layout.activity_music)
        val adapter = MyAdapter(list, this)

        btn_play_raw.setOnClickListener {
            player.start()
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recycle.layoutManager = layoutManager
        recycle.adapter = adapter

        adapter.itemClick = {

        }

        btn_get_music.setOnClickListener {
            contentUtil?.getAllMusic {
                list.clear()
                if (it.size != 0) {
                    list.addAll(it)
                    adapter.updale(list)
                }

            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }
}

class MyAdapter(var list: MutableList<FileItem>, var context: Context) : RecyclerView.Adapter<MyHolder>() {

    var itemClick: (FileItem) -> Unit = {}
    override fun onCreateViewHolder(parant: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parant.context).inflate(R.layout.file_item, parant, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        with(holder.itemView) {
            tv_id.text = list[position].fileId
            tv_name.text = list[position].fileName
            tv_path.text = list[position].filePath

            setOnClickListener {
                itemClick.invoke(list[position])
            }
        }

    }

    fun updale(list0: MutableList<FileItem>) {
        list = list0
        notifyDataSetChanged()
    }
}

class MyHolder(view: View) : RecyclerView.ViewHolder(view)