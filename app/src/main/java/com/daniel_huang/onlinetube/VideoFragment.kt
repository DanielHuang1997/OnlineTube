package com.daniel_huang.onlinetube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_video.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.*

class VideoFragment(var position:Int = 0) : Fragment(){
    private lateinit var movies : List<VideosItem>
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
        CoroutineScope(Dispatchers.IO).launch {
            val json = URL("https://raw.githubusercontent.com/DanielHuang1997/OnlineTube/master/data.json")
                .readText()
            val videoResult = Gson().fromJson<VideoDB>(json, object : TypeToken<VideoDB>(){}.type)
            Log.d("Error",videoResult.videos.toString())
            movies = videoResult.videos

            withContext(Dispatchers.Main){
                video_page.setUp(movies.get(position).sources,true,"")
                video_page.startPlayLogic()
                video_page.backButton.visibility = View.GONE

            }
        }


    }


}