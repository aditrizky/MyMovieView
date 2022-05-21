package com.binar.mymovieview.data.local.userauth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreManager(private val context: Context) {
    companion object {

        private const val DATASTORE_NAME = "application_data_store"

        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val EMAIL_KEY = stringPreferencesKey("email_key")
        private val ID_KEY= intPreferencesKey("id_key")

        private val Context.prefDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }

    //preferences data store
    suspend fun setUsername(username: String){
        context.prefDataStore.edit {
            it[USERNAME_KEY]=username
        }
    }

    suspend fun setEmail(email: String){
        context.prefDataStore.edit {
            it[EMAIL_KEY]=email
        }
    }

    fun getUsernameValue(): Flow<String> {
        return context.prefDataStore.data.map {
            it[USERNAME_KEY]?: "default"
        }
    }
    fun getEmailValue(): Flow<String> {
        return context.prefDataStore.data.map {
            it[EMAIL_KEY]?: "default"
        }
    }


    suspend fun clearDataStore(){
        context.prefDataStore.edit {
            it.clear()
        }
    }
}