package com.daniel_huang.onlinetube

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.downloadservice.filedownloadservice.manager.FileDownloadManager
import kotlinx.android.synthetic.main.fragment_video.*
import kotlinx.android.synthetic.main.fragment_videotext.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class VideoTextFragment(var position:Int = 0) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return layoutInflater.inflate(R.layout.fragment_videotext,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData(position)
    }

    fun initData(position: Int){
        val path = resources.getStringArray(R.array.video_title)
        val stringarray : ArrayList<String> = ArrayList()
        for (i in 0..4) {
            stringarray.add(path[i])
        }
        if (stringarray.isNotEmpty()){
            VideoTextTitle.setText(stringarray[position])

        }else{
            Toast.makeText(MainActivity(),"Their is no video available", Toast.LENGTH_LONG).show()
        }
    }


}