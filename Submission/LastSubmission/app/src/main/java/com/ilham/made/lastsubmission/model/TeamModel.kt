package com.ilham.made.lastsubmission.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamModel(
    @SerializedName("idTeam")
    val idTeam: Int = 0,
    @SerializedName("strTeam")
    val strTeam: String? = null,
    @SerializedName("strStadium")
    val strStadium: String? = null,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String? = null,
    @SerializedName("strTeamBadge")
    val strTeamBadge: String? = null,
    @SerializedName("strSport")
    val strSport: String? = null
): Parcelable