package com.maad.jobmarket.ahasan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    var uid : String? = null,
    var details: Details? = null,
    var address: Address? = null,
    var academic: Academic? = null,
): Parcelable

@Parcelize
data class Details(
    var username: String = "",
    var email: String = "",
    var imageUrl: String = "",
    var sapId: String = "",
    var mobile: String = "",
    var dob: String = "",
    var gender: String = ""
): Parcelable

@Parcelize
data class Address(
    var address: String = "",
    var city: String = "",
    var state: String = "",
    var zipCode: String = "",
): Parcelable

@Parcelize
data class Academic(
    var sem1: String = "",
    var sem2: String = "",
    var sem3: String = "",
    var sem4: String = "",
    var avgScore: String = "",
    var resumeUrl: String = "",
): Parcelable