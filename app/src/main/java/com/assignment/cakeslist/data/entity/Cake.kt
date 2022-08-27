package com.assignment.cakeslist.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cake(
    val title: String,
    val desc: String,
    val image: String
) : Parcelable