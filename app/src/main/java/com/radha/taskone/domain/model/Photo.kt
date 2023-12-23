package com.radha.taskone.domain.model

import android.os.Parcel
import android.os.Parcelable


data class Photo(
    val farm: Int,
    val height_s: Int,
    val id: String?,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String?,
    val secret: String?,
    val server: String?,
    val title: String?,
    val url_s: String?,
    val width_s: Int
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(farm)
        parcel.writeInt(height_s)
        parcel.writeString(id)
        parcel.writeInt(isfamily)
        parcel.writeInt(isfriend)
        parcel.writeInt(ispublic)
        parcel.writeString(owner)
        parcel.writeString(secret)
        parcel.writeString(server)
        parcel.writeString(title)
        parcel.writeString(url_s)
        parcel.writeInt(width_s)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

}