package ru.andrey.savchenko.buildingreviews.parcel

import android.os.Parcel
import ru.andrey.savchenko.buildingreviews.parcel.ParseCreator.Companion.parcelableCreator

/**
 * Created by savchenko on 10.05.18.
 */
class TestUser(val name: String, val surName: String) : IParcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(surName)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator (::TestUser)
    }
}