package com.vishalgaur.shoppingapp.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var productId: String = "",
    var name: String = "",
    var owner: String = "",
    var description: String = "",
    var price: Long = 0L,
    var availableSizes: ArrayList<Int>,
    var availableColors: ArrayList<String>,
    var rating: Double = 0.0
) : Parcelable {
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "productId" to productId,
            "name" to name,
            "owner" to owner,
            "price" to price,
            "description" to description,
            "availableSizes" to availableSizes,
            "availableColors" to availableColors,
            "rating" to rating
        )
    }
}
