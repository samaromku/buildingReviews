package ru.andrey.savchenko.buildingreviews

import org.junit.Test

import org.junit.Assert.*
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.Method
import org.junit.runner.Request.method


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        val testDBRequest = TestDBRequest()
        testDBRequest.baseQuery(listOf(Param("userId", "123")))

    }

    class MethodWrapper() {
        fun wrap(wrapped: () -> Unit) {
            for (method in wrapped()::class.java.methods) {
                if (method.isAnnotationPresent(QueryDB::class.java)) {
                    val a = method.getAnnotation(QueryDB::class.java)
                    println(a)
                }
            }
        }
    }

    class TestDBRequest {
        @QueryDB(query = "select * from users;")
        fun getAllUsers() {

        }

        @QueryDB(query = "select * from users where id={userId};")
        fun getAllUserById(userId: String) {


            for (method in this::class.java.methods) {
                if (method.name == Thread.currentThread().stackTrace[1].methodName) {
                    if (method.isAnnotationPresent(QueryDB::class.java)) {
                        val a = method.getAnnotation(QueryDB::class.java)
                                .toString().replace("{userId}", userId)
                        println(a)
                    }
                }
            }
        }

        @QueryDB(query = "select * from users where id={userId};")
        fun baseQuery(list: List<Param>) {
            fun getAnnotationsValue(): String {
                for (method in this::class.java.methods) {
                    if (method.name == Thread.currentThread().stackTrace[1].methodName) {
                        if (method.isAnnotationPresent(QueryDB::class.java)) {
                            val a = method.getAnnotation(QueryDB::class.java)
                            return a.toString()
                        }
                    }
                }
                return ""
            }

            val annotationValue = getAnnotationsValue()

            fun replaceString(all: String,
                              oldValue: String,
                              newValue: String): String {
                return all.replace(oldValue, newValue)
            }

            for (param in list) {
                println(replaceString(annotationValue, param.name, param.value))
            }
        }
    }

    data class Param(val name: String, val value: String)

    @Target(AnnotationTarget.FUNCTION)
    annotation class QueryDB(val query: String)

}
