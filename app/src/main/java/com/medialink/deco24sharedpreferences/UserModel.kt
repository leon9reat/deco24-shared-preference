package com.medialink.deco24sharedpreferences

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel (
    var name: String? = "",
    var email: String? = "",
    var age: Int = 0,
    var phoneNumber: String? = "",
    var isLove: Boolean = false
) : Parcelable
