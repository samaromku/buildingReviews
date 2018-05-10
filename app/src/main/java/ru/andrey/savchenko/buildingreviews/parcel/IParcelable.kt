package ru.andrey.savchenko.buildingreviews.parcel

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by savchenko on 10.05.18.
 */
interface IParcelable:Parcelable {
    override fun describeContents(): Int {
        return 0
    }
    override fun writeToParcel(dest: Parcel, flags: Int)

}