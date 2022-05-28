package com.mongsil.mongsildiary.ui.home.today

// TODO 아이템 클릭 효과
// 선택된 이모티콘을 저장하는 라이브데이터 선언
// 라이브데이터가 각 이모티콘 뷰홀더에서 관찰하면서 선택된 이모티콘이 자기자신이면 표시
// 뷰모델을 리사이클러뷰 어댑터로 보내고, onBindViewHolder에서 옵저버

data class Emoticon(
    val image: Int,
    val name: String
)

data class TodayEmoticon(
    val emoticon: Emoticon,
    val isSelected: Boolean = false
)
