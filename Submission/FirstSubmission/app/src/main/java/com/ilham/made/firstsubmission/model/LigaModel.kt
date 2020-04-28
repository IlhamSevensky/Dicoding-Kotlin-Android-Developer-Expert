package com.ilham.made.firstsubmission.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LigaModel(
    val name: String?,
    val image: Int?,
    val overview: String?
) : Parcelable