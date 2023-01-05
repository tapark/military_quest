package com.tapark.military_quest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tapark.military_quest.data.UserInfo


@SuppressLint("StaticFieldLeak")
object PrefManager {

    private lateinit var context: Context
    private lateinit var prefs: SharedPreferences

    const val KEY_USER_INFO = "KEY_USER_INFO"

    fun init(mContext: Context) {
        context = mContext
        prefs = context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
    }

    fun getString(key: String): String = prefs.getString(key, null).toString()
    fun setString(key: String, str: String) = prefs.edit().putString(key, str).apply()


    fun getIntList(key: String): MutableList<Int> {
        val json = prefs.getString(key, null)
        return Gson().fromJson(json, object: TypeToken<MutableList<Int>>() {}.type) ?: mutableListOf<Int>()
    }

    fun setIntList(key: String, list: MutableList<Int>) {
        val json = Gson().toJson(list)
        prefs.edit().putString(key, json).apply()
    }

    fun getUserInfo(): UserInfo {
        val json = prefs.getString(KEY_USER_INFO, null)
        return Gson().fromJson(json, object: TypeToken<UserInfo>() {}.type)
    }
    fun setUserInfo(userInfo: UserInfo) {
        val json = Gson().toJson(userInfo)
        prefs.edit().putString(KEY_USER_INFO, json).apply()
    }

}