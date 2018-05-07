package ru.andrey.savchenko.buildingreviews.db

import io.realm.Realm
import io.realm.RealmObject


/**
 * Created by savchenko on 07.05.18.
 */
const val ID = "id"
class BaseDao<T : RealmObject>(private val type: Class<T>) {

    fun addItem(t:T){
        val instance = Realm.getDefaultInstance()
        instance.executeTransaction { realm -> realm.insertOrUpdate(t) }
        instance.close()
    }

    fun addList(t:List<T>){
        val instance = Realm.getDefaultInstance()
        instance.executeTransaction { realm -> realm.insertOrUpdate(t) }
        instance.close()
    }

    fun removeItem(t:T){
        val instance = Realm.getDefaultInstance()
        instance.executeTransaction { t.deleteFromRealm() }
        instance.close()
    }


    fun getItem(): T? {
        val instance = Realm.getDefaultInstance()
        val t = instance.copyFromRealm(instance.where(type).findFirst())
        instance.close()
        return t
    }

    fun getAll(): List<T> {
        val instance = Realm.getDefaultInstance()
        val tList = instance.copyFromRealm(instance.where(type).findAll())
        instance.close()
        return tList
    }

    fun getItemById(id: Int): T? {
        val instance = Realm.getDefaultInstance()
        val t = instance.copyFromRealm(instance.where(type).equalTo(ID, id).findFirst())
        instance.close()
        return t
    }

    fun getMaxIdPlusOne(): Int {
        val instance = Realm.getDefaultInstance()
        var id = 0
        if (instance.where(type).max(ID) != null) {
            val stringId = instance.where(type).max(ID)
            if(stringId!=null){
                id = stringId.toInt()
            }
            instance.close()
        }
        return id + 1
    }
}