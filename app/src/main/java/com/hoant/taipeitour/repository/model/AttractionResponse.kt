package com.hoant.taipeitour.repository.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AttractionResponse(
    var total: Int = 0,
    var data: List<Attraction> = arrayListOf(),
)

@Parcelize
data class Attraction(
    val id: Long,
    val name: String,
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
    val images: List<Image>,
): Parcelable

@Parcelize
data class Category(
    val id: Long,
    val name: String,
): Parcelable

@Parcelize
data class Target(
    val id: Long,
    val name: String,
): Parcelable

@Parcelize
data class Service(
    val id: Long,
    val name: String,
): Parcelable

@Parcelize
data class Image(
    val src: String,
    val subject: String,
    val ext: String,
): Parcelable