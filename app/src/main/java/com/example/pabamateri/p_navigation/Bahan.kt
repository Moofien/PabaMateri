package com.example.pabamateri.p_navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bahan(
    var nama: String,
    var kategori: String,
    var imageUrl: String
) : Parcelable