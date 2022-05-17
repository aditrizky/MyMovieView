package com.binar.mymovieview.data.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavoriteMovie(
  @PrimaryKey(autoGenerate = true)
  var id: Int=0,
  var title: String?=null,
  var overview:String?=null,
  var poster : String?=null,
  var backdrop:String?=null,
  var language: String?=null,
  var voteAverage: Double?=null,
  var releaseDate: String?=null,
  var voteCount: Int?=null
)
