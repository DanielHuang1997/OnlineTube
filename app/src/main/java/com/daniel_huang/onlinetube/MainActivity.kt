package com.daniel_huang.onlinetube

import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.downloader.PRDownloader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tuanhav95.drag.DragView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_videotext.*
import kotlinx.android.synthetic.main.layout_player.*
import kotlinx.android.synthetic.main.layout_player_background.*
import kotlinx.android.synthetic.main.module_video_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : MovieAdapter
    private lateinit var movies : List<VideosItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            InitData()
            RecyclerListener()

            dragView.close()
            DragViewListener()

    }

    fun InitData(){
        CoroutineScope(Dispatchers.IO).launch {
            val json = URL("https://raw.githubusercontent.com/DanielHuang1997/OnlineTube/master/data.json")
                .readText()
            val videoResult = Gson().fromJson<VideoDB>(json, object : TypeToken<VideoDB>(){}.type)
            Log.d("Error",videoResult.videos.toString())
            movies = videoResult.videos

            withContext(Dispatchers.Main){
                recycle.setHasFixedSize(true)
                recycle.layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = MovieAdapter()
                recycle.adapter = adapter
            }
        }
    }

    fun RecyclerListener(){
        //recyclerView的滑動狀態監聽
        recycle.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            //監聽滑動時的狀態
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //super.onScrolled(recyclerView, dx, dy)
                val layoutmanager = recycle.layoutManager as LinearLayoutManager
                //抓取顯示在範圍內的Item(Position)
                val firstPosition = layoutmanager.findFirstVisibleItemPosition()
                val lastPosition = layoutmanager.findLastVisibleItemPosition()
                val rect = Rect()
                recyclerView.getGlobalVisibleRect(rect)

                //檢測View佔畫面百分比
                for ( i in firstPosition..lastPosition){
                    val rowrect = Rect()
                    layoutmanager.findViewByPosition(i)?.getGlobalVisibleRect(rowrect)
                    var percent :Int
                    if (rowrect.bottom >= rect.bottom){
                        val visibleHeight = rect.bottom - rowrect.top
                        percent = (visibleHeight * 100) / layoutmanager.findViewByPosition(i)!!.height
                    }else{
                        val visibleHeight = rowrect.bottom - rect.top
                        percent = (visibleHeight * 100) / layoutmanager.findViewByPosition(i)!!.height
                    }
                    //當百分比超過100時,則一律設為100(元件大小 小於顯示範圍)
                    if (percent > 100){
                        percent = 100
                    }
                    //當Item的百分比為100時,播放影片; 不滿100時則暫停
                    if (percent < 100) {
                        recyclerView.findViewHolderForAdapterPosition(i)?.itemView?.video_view?.pause()
                    } else if (percent >= 80) {
                        recyclerView.findViewHolderForAdapterPosition(i)?.itemView?.video_view?.start()
                        recyclerView.findViewHolderForAdapterPosition(i)?.itemView?.setOnClickListener{
                            recyclerView.findViewHolderForAdapterPosition(i)?.itemView?.video_view?.pause()

                            initDragView(i)  //初始化DragView
                            dragView.maximize()
                        }
                    }else{
                        Toast.makeText(this@MainActivity,"Loading...", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    fun initDragView(position:Int = 0){
        supportFragmentManager.beginTransaction().add(R.id.player_frame,VideoFragment(position)).commit()
        supportFragmentManager.beginTransaction().add(R.id.player_background_frame,VideoTextFragment(position)).commit()
    }

    private fun DragViewListener() {
        dragView.setDragListener(object : DragView.DragListener {
            override fun onChangePercent(percent: Float) {
                videoViewPager.alpha = 1 - percent
                player_view.alpha = percent
            }
        })
        Close.setOnClickListener{
            dragView.close()
        }
    }

    inner class MovieAdapter : RecyclerView.Adapter<MovieHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
            return MovieHolder(layoutInflater.inflate(R.layout.module_video_layout,parent,false))
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            val movie = movies.get(position)
            holder.videoname.setVideoURI(Uri.parse(movie.sources))
            holder.videotitle.setText(movie.title)
        }
    }
    class MovieHolder(view:View) : RecyclerView.ViewHolder(view){
        val videoname = view.video_view
        val videotitle = view.video_text
    }


}