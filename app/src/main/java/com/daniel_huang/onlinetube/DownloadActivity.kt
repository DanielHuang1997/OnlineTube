package com.daniel_huang.onlinetube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.module_download.view.*
import java.util.ArrayList

class DownloadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        initData()
    }

    fun initData(){
        //Get Files.size
    }

        val Adapter = object : RecyclerView.Adapter<ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(layoutInflater.inflate(R.layout.module_download, parent, false))
            }

            override fun getItemCount(): Int {
                return 0
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            }
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var delete = itemView.DeleteDownload
        var video = itemView.ListDownload
    }

}
