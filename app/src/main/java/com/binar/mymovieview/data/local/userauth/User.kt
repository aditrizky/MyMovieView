package com.binar.mymovieview.data.local.userauth

import android.graphics.Bitmap
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
    var address  : String? = null,
    var imagePath: String? = null
): Parcelable
