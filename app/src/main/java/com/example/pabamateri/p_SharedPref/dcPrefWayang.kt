package com.example.pabamateri.p_SharedPref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class dcPrefWayang(
    var foto: String,
    var nama: String,
    var karakter: String,
    var deskripsi: String
) : Parcelable
