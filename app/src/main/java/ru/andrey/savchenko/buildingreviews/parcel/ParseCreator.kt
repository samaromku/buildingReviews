package ru.andrey.savchenko.buildingreviews.parcel

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by savchenko on 10.05.18.
 */
class ParseCreator {
    companion object {
        inline fun <reified T> parcelableCreator(
                crossinline create: (Parcel) -> T) =
                object : Parcelable.Creator<T> {
                    override fun createFromParcel(source: Parcel) = create(source)
                    override fun newArray(size: Int) = arrayOfNulls<T>(size)
                }

        inline fun <reified T> parcelableClassLoaderCreator(
                crossinline create: (Parcel, ClassLoader) -> T) =
                object : Parcelable.ClassLoaderCreator<T> {
                    override fun createFromParcel(source: Parcel, loader: ClassLoader) = create(source, loader)

                    override fun createFromParcel(source: Parcel) = createFromParcel(source, T::class.java.classLoader)

                    override fun newArray(size: Int) = arrayOfNulls<T>(size)
                }
    }
}