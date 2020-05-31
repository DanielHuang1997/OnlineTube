package com.daniel_huang.onlinetube

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tuanhav95.drag.DragView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_player.*
import kotlinx.android.synthetic.main.layout_player_background.*
import kotlinx.android.synthetic.main.module_video_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedReader
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var videoData :CategoriesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            InitData()
            RecyclerListener()

            dragView.close()
            DragViewListener()
            VideoURL()
    }



    fun InitView(array : ArrayList<ViewAdapter.Model>){
        val recyclerView = recycle
        val linearLayoutManager = LinearLayoutManager(this)
        val viewAdapter = ViewAdapter(this,array)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = viewAdapter
    }

    fun InitData(){
        val path = resources.getStringArray(R.array.videoURL)
        val videotitle = resources.getStringArray(R.array.video_title)
        val stringarray : ArrayList<ViewAdapter.Model> = ArrayList()
        for (i in 0..4) {
            stringarray.add(i,ViewAdapter.Model( path[i] , videotitle[i]))
        }
        if (stringarray.isNotEmpty()){
            InitView(stringarray)
        }else{
            Toast.makeText(MainActivity(),"Their is no video available", Toast.LENGTH_LONG).show()
        }
    }


    fun RecyclerListener(){
        //recyclerView的滑動狀態監聽
        val recyclerView = recycle
        val scrollListener = object : RecyclerView.OnScrollListener(){
            val layoutmanager = recyclerView.layoutManager as LinearLayoutManager

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                //super.onScrollStateChanged(recyclerView, newState)
                // 0 = 停止滑動
                // 1 = 正在滑動中
                // 2 = 滑動中

            }

            //監聽滑動時的狀態
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //super.onScrolled(recyclerView, dx, dy)

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
                            //Reload Fragment
                            initDragView(i)
                            //
                            dragView.refresh() //optional?
                            dragView.maximize()
                        }


                    }else{
                        //Toast.makeText(this@MainActivity,"Loading...", Toast.LENGTH_LONG).show()
                    }

                    //防止例外

                }
            }
        }
        //呼叫狀態監聽
        recyclerView.addOnScrollListener(scrollListener)

    }

    fun initDragView(position:Int = 0){
        supportFragmentManager.beginTransaction().add(R.id.player_frame,VideoFragment(position)).commit()
        supportFragmentManager.beginTransaction().add(R.id.player_background_frame,VideoTextFragment()).commit()
    }

    fun VideoURL() {
        CoroutineScope(Dispatchers.IO).launch {
            val reader =
                URL("https://drive.google.com/file/d/1k5pd92XQRP4BzSaE35AiCMK8yZu58JdB/view?usp=sharing")
                    .readText().replace(" ","")
            videoData = Gson().fromJson<CategoriesItem>(reader, object : TypeToken<CategoriesItem>(){}.type)
            Log.d("GetJSON", videoData.name.toString())
        }
    }

    private fun DragViewListener() {
        dragView.setDragListener(object : DragView.DragListener {
            override fun onChangePercent(percent: Float) {
                videoViewPager.alpha = 1 - percent
                player_view.alpha = percent

            }
            override fun onChangeState(state: DragView.State) {
                super.onChangeState(state)
            }
        })
        Close.setOnClickListener {
            dragView.close()

        }
    }




}