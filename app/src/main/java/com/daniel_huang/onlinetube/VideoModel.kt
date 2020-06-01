package com.daniel_huang.onlinetube

data class VideosItem(val sources: String,
                      val thumb: String,
                      val subtitle: String,
                      val description: String,
                      val title: String)


data class VideoDB(val videos: List<VideosItem>)


