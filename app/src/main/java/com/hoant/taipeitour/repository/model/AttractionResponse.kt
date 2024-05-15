package com.hoant.taipeitour.repository.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AttractionResponse(
    var total: Int = 0,
    var data: List<Attraction> = arrayListOf(),
)

data class Attraction(
    val id: Long,
    val name: String,
    @SerializedName("name_zh")
    val nameZh: Any?,
    @SerializedName("open_status")
    val openStatus: Long,
    val introduction: String,
    @SerializedName("open_time")
    val openTime: String,
    val zipcode: String,
    val distric: String,
    val address: String,
    val tel: String,
    val fax: String,
    val email: String,
    val months: String,
    val nlat: Double,
    val elong: Double,
    @SerializedName("official_site")
    val officialSite: String,
    val facebook: String,
    val ticket: String,
    val remind: String,
    val staytime: String,
    val modified: String,
    val url: String,
    val category: List<Category>,
    val target: List<Target>,
    val service: List<Service>,
    val friendly: List<Any?>,
    val images: List<Image>,
    val files: List<Any?>,
    val links: List<Any?>,
)

data class Category(
    val id: Long,
    val name: String,
)

data class Target(
    val id: Long,
    val name: String,
)

data class Service(
    val id: Long,
    val name: String,
)

data class Image(
    val src: String,
    val subject: String,
    val ext: String,
)