package com.tapark.military_quest.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@SuppressLint("StaticFieldLeak")
object PrefManager {

    private lateinit var context: Context
    private lateinit var prefs: SharedPreferences

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

}