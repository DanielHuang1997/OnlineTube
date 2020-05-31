package com.daniel_huang.onlinetube

data class VideosItem(val sources: List<String>?,
                      val thumb: String = "",
                      val subtitle: String = "",
                      val description: String = "",
                      val title: String = "")


data class Type(val categories: List<CategoriesItem>?)


data class CategoriesItem(val name: String = "",
                          val videos: List<VideosItem>?)


