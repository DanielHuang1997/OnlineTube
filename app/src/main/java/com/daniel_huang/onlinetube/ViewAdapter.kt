package com.daniel_huang.onlinetube

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.render.view.GSYVideoGLView
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper
import com.shuyu.gsyvideoplayer.video.GSYADVideoPlayer
import com.shuyu.gsyvideoplayer.video.GSYSampleADVideoPlayer
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import kotlinx.android.synthetic.main.module_video_layout.view.*
import java.util.concurrent.Executors

class ViewAdapter : RecyclerView.Adapter<ViewAdapter.ViewHolder>{
    private var context: Context
    private var data: ArrayList<Model>

    constructor(context: Context,data:ArrayList<Model>): super(){
        this.context = context
        this.data = data
    }

    //====================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cell = LayoutInflater.from(context).inflate(R.layout.module_video_layout,parent,false)
        val viewHolder = ViewHolder(cell)
        viewHolder.videoView = cell.video_view
        viewHolder.videotext = cell.video_text
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]
        val video = holder.videoView
        val text = holder.videotext
        val uri = Uri.parse(model.videourl)
        text.setText(model.text)
        video.setVideoURI(uri)
        video.start()

//        Detect Holder By Clicking
//        holder.itemView.setOnClickListener{
//            //影片設為全屏,新增全螢幕按鈕
//        }

    }

    //====================

    //inner class
    class ViewHolder : RecyclerView.ViewHolder{
        lateinit var videoView: VideoView
        lateinit var videotext: TextView
        constructor(itemView: View) : super(itemView)
    }

    class Model{
        var videourl: String
        var text: String

        constructor(videourl: String,text: String){
            this.videourl = videourl
            this.text = text
        }
    }
}

