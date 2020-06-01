package com.daniel_huang.onlinetube

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_videotext.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL
import java.util.*

class VideoTextFragment(var position:Int = 0) : Fragment() {
    private lateinit var movies : List<VideosItem>
    private var downloadID = 0
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
        CoroutineScope(Dispatchers.IO).launch {
            val json = URL("https://raw.githubusercontent.com/DanielHuang1997/OnlineTube/master/data.json")
                .readText()
            val videoResult = Gson().fromJson<VideoDB>(json, object : TypeToken<VideoDB>(){}.type)
            Log.d("Error",videoResult.videos.toString())
            movies = videoResult.videos

            withContext(Dispatchers.Main){
                val movie = movies.get(position)
                VideoTextTitle.setText(movies.get(position).title)
                val path = context?.getExternalFilesDir(Environment.DIRECTORY_MOVIES).toString()
                val file = movie.title
                Download.setOnClickListener {
                    downloadID = PRDownloader.download(movie.sources,path,"$file.mp4")
                        .build()
                        .setOnStartOrResumeListener{

                        }
                        .setOnPauseListener{

                        }
                        .setOnCancelListener{

                        }
                        .setOnProgressListener{
                            Log.d("System","Downloading...")
                        }
                        .start(object : OnDownloadListener {
                            override fun onDownloadComplete() {
                                Log.d("System","Finish")
                            }

                            override fun onError(error: Error?) {
                                //
                            }
                        })
                }

            }
        }
    }




}