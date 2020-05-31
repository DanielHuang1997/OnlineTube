package com.daniel_huang.onlinetube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_video.*
import java.util.*

class VideoFragment(var position:Int = 0) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Log.d("Value",position.toString())
        return layoutInflater.inflate(R.layout.fragment_video,container,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Position",position.toString())
        initData(position)
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    fun initData(position: Int){
        val path = resources.getStringArray(R.array.videoURL)
        val stringarray : ArrayList<String> = ArrayList()
        for (i in 0..4) {
            stringarray.add(path[i])
        }
        if (stringarray.isNotEmpty()){
            video_page.setUp(stringarray[position],false,"")
            video_page.backButton.visibility = View.GONE
            video_page.startPlayLogic()
            //video_page
        }else{
            Toast.makeText(MainActivity(),"Their is no video available", Toast.LENGTH_LONG).show()
        }
    }


}