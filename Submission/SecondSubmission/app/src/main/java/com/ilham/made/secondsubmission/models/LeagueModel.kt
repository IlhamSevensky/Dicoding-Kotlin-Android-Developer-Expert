package com.ilham.made.secondsubmission.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueModel(
    @SerializedName("idLeague")
    val idLeague: Int = 0,
    @SerializedName("strLeague")
    val strLeague: String? = null,
    @SerializedName("strSport")
    val strSport: String? = null,
    @SerializedName("strDescriptionEN")
    val strDescription: String? = null,
    @SerializedName("strBadge")
    val strBadge: String? = null
) : Parcelable