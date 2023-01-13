package com.tapark.military_quest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tapark.military_quest.R
import com.tapark.military_quest.data.SubQuestInfo
import com.tapark.military_quest.data.UserInfo
import java.io.ByteArrayOutputStream


@SuppressLint("StaticFieldLeak")
object PrefManager {

    private lateinit var context: Context
    private lateinit var prefs: SharedPreferences

    const val KEY_USER_INFO = "KEY_USER_INFO"
    const val KEY_PROFILE_BITMAP = "KEY_PROFILE_BITMAP"
    const val KEY_SUB_QUEST_LIST = "KEY_SUB_QUEST_LIST"

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
        return Gson().fromJson(json, object: TypeToken<UserInfo>() {}.type) ?: UserInfo()
    }
    fun setUserInfo(userInfo: UserInfo) {
        val json = Gson().toJson(userInfo)
        prefs.edit().putString(KEY_USER_INFO, json).apply()
    }

    fun getSubQuestList(): MutableList<SubQuestInfo> {
        val json = prefs.getString(KEY_SUB_QUEST_LIST, null)
        return Gson().fromJson(json, object: TypeToken<MutableList<SubQuestInfo>>() {}.type) ?: mutableListOf<SubQuestInfo>()
    }
    fun setSubQuestList(subQuestList: MutableList<SubQuestInfo>) {
        val json = Gson().toJson(subQuestList)
        prefs.edit().putString(KEY_SUB_QUEST_LIST, json).apply()
    }

    fun getBitmap(key: String): Bitmap? {
        val bitmapString = prefs.getString(key, null) ?: return null
        val bytes = Base64.decode(bitmapString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    fun setBitmap(key: String, bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream)
        val bytes = stream.toByteArray()
        val bitmapString = Base64.encodeToString(bytes, Base64.DEFAULT)
        Log.d("박태규", "bitmapString : $bitmapString")
        prefs.edit().putString(key, bitmapString).apply()
    }

}