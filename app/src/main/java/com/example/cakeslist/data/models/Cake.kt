package com.example.cakeslist.data.models

import com.google.gson.annotations.SerializedName

class Cake(
    @SerializedName("title") val title: String,
    @SerializedName("desc") val description: String,
    @SerializedName("image") val url: String
) {}