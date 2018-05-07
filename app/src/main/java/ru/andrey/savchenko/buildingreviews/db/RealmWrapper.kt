package ru.andrey.savchenko.buildingreviews.db

import io.realm.Realm

/**
 * Created by savchenko on 07.05.18.
 */
class RealmWrapper {
    fun executeRealmMethod(realmMethod: () -> Unit){
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realmMethod() }
        realm.close()
    }
}