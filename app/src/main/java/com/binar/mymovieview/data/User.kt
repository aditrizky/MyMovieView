package com.binar.mymovieview.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var email : String? =null,
    var username : String? = null,
    var password : String? = null,
    var fullName : String? = null,
    var birthDate: String? = null,
    var address  : String? = null
): Parcelable
