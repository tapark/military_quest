package com.tapark.military_quest.data

const val MONTH_18 = 18 // "육군", "해병", "의무경찰, 상근예비역"
const val MONTH_20 = 20 // "해군", "해양의무경찰", "의무소방"
const val MONTH_21 = 21 // "공군", "사회복무요원"
const val MONTH_23 = 23 // "산업기능요원(보충역)"
const val MONTH_34 = 34 // "산업기능요원(현역), 예술체육요원"
const val MONTH_36 = 36 // "전문연구요원", "대체복무요원", "승선근무예비역", "장교"
const val MONTH_48 = 48 // "부사관"

data class SubQuestData(
    val currentRank: String,
    val nextRank: String,
    val month: Int
)

// 병사 공통
val subQuest1 = listOf<SubQuestData>(
    SubQuestData("이병", "일병", 2),
    SubQuestData("일병", "상병", 8),
    SubQuestData("상병", "병장", 14)
)

// 의무 경찰 공통
val subQuest2 = listOf<SubQuestData>(
    SubQuestData("이경", "일경", 2),
    SubQuestData("일경", "상경", 8),
    SubQuestData("상경", "수경", 14)
)

// 의무 소방 공통
val subQuest3 = listOf<SubQuestData>(
    SubQuestData("이방", "일방", 2),
    SubQuestData("일방", "상방", 8),
    SubQuestData("상방", "수방", 14)
)

// 부사관 공통
val subQuest4 = listOf<SubQuestData>(
    SubQuestData("하사", "중사", 24),
    SubQuestData("중사", "상사", 84)
)

// 장교 공통
val subQuest5 = listOf<SubQuestData>(
    SubQuestData("소위", "중위", 12),
    SubQuestData("중위", "대위", 36)
)

// 보충역 공통
val subQuest6 = listOf<SubQuestData>(
    SubQuestData("1호봉", "2호봉", 2),
    SubQuestData("2호봉", "3호봉", 8),
    SubQuestData("3호봉", "4호봉", 14)
)