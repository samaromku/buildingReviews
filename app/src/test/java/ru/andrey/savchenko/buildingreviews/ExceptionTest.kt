package ru.andrey.savchenko.buildingreviews

import org.junit.Test
import java.io.IOException

/**
 * Created by savchenko on 24.05.18.
 */
class ExceptionTest {
    @Test
    fun testException(){
        println(getEx())
    }

    @Throws(IOException::class)
    private fun getEx():Int {
        if(false){
            return 1
        }
        throw IOException("fuck u")
    }
}