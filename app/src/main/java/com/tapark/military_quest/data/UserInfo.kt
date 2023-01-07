package com.tapark.military_quest.data

data class UserInfo(
    val firstInit: Boolean = true,
    val name: Info = Info(),
    val birth: Info = Info(),
    val company: Info = Info(),
    val rank: Info = Info(),
    val enter: Info = Info(),
    val retire: Info = Info(),
)

data class Info(
    val value: String = "",
    val private: Boolean = false
)