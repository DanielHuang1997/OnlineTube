package com.daniel_huang.onlinetube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_download.*
import kotlinx.android.synthetic.main.fragment_download.*
import kotlinx.android.synthetic.main.fragment_video.*
import kotlinx.android.synthetic.main.module_download.view.*
import java.util.ArrayList

class DownloadActivity : AppCompatActivity() {
    lateinit var stringarray: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        initData()
    }

    fun initData(){
        val path = resources.getStringArray(R.array.video_title)
       stringarray = ArrayList()
        for (i in 0..4) {
            stringarray.add(path[i])
        }
        if (stringarray.isNotEmpty()){
            downloadView.setHasFixedSize(true)
            downloadView.layoutManager = LinearLayoutManager(this)
            downloadView.adapter = Adapter
            //video_page
        }else{
            Toast.makeText(MainActivity(),"Their is no video available", Toast.LENGTH_LONG).show()
        }
    }



        val Adapter = object : RecyclerView.Adapter<ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(layoutInflater.inflate(R.layout.module_download, parent, false))
            }

            override fun getItemCount(): Int {
                return stringarray.size
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.video.setText(stringarray[position])
            }

        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var delete = itemView.DeleteDownload
        var video = itemView.ListDownload
    }

}
