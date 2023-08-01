# MongsilDiary

<img src="https://user-images.githubusercontent.com/42116216/201831806-f3f134b8-9e47-4069-bbcd-7c1f554cd367.png" width="100%" height="100%"/>

### Google PlayStore : https://play.google.com/store/apps/details?id=com.mongsil.mongsildiary

몽실은 하루의 감정을 솔직하게 기록할 수 있는 감정일기장 앱입니다.
두루뭉술한 무난한 하루를 보낸 분도, 파란만장한 하루를 겪은 분도 누구나 자유롭게 사용할 수 있도록 다양한 감정과 어느 시간때든 자유롭게 감정을 기록할 수 있도록 타임라인을 제공합니다.

오늘 기분은 어때요?
하루에도 몇번씩 바뀌는 감정을 기록해야 하기에 몽실은 15가지 (행복, 기쁨, 만족, 보통, 피곤, 창피, 지루함, 화남, 불쾌, 실망, 불안, 우울, 슬픔, 놀람, 외로움) 감정을 제공합니다. 언제든지 감정에 변화가 일어날때마다 앱을 실행시켜서 감정을 기록해보세요.

일기쓰기가 어려우신가요?
하루 한번씩 성공과 자존감을 높일 수 있는 동기를 부여해주는 엄선된 명언과 함께 배경화면으로 사용하기 좋은 이미지를 제공합니다. 하루하루 명언을 읽고 그 명언으로 느낀 감정을 솔직하게 입력한 후 일주일, 한달, 일년동안 달라진 나의 감정들을 비교해보세요. 몽실을 통해 당신이 좀 더 긍정적인 사람이 될 수 있도록 하는 것, 그것이 우리가 몽실을 개발하고 서비스하는 이유입니다.

몽실 서비스 안내
- 매일 원하는 시간에 알람 기능을 통해 긍정적인 명언을 전달해드립니다.
- 캘린더에 감정을 기록하여, 지난 날의 감정 통계를 낼 수 있습니다.
- 원하는 시간별로 감정을 기록하여 하루의 감정 타임라인을 한 눈에 볼 수 있습니다.
- 마음에 드는 명언이나 배경화면을 스크랩하시면 언제든 다시 확인 할 수 있습니다.


<img src="https://user-images.githubusercontent.com/42116216/201831616-1706a33d-b219-4289-8c0b-fe6558ef1e1e.png" width="300" height="533.33"/> <img src="https://user-images.githubusercontent.com/42116216/201831577-60f62352-6741-4996-a9d8-faa4dd40ff4e.png" width="300" height="533.33"/> <img src="https://user-images.githubusercontent.com/42116216/201831642-a6574fc2-95d1-4957-8a8f-cf32846384f2.png" width="300" height="533.33"/> <img src="https://user-images.githubusercontent.com/42116216/201831686-2fb94698-3f81-47b3-a5a4-539b45f0c430.png" width="300" height="533.33"/> <img src="https://user-images.githubusercontent.com/42116216/201831724-1310fc55-1815-456a-8cca-3f7852ab841c.png" width="300" height="533.33"/> <img src="https://user-images.githubusercontent.com/42116216/211296094-9377d303-0790-429a-9489-6d2f941761d4.gif" width="300" height="533.33"/>

사용 기술 스택 : Kotlin, MVVM, Android AAC, Retrofit2, Coroutine
 
외부 라이브러리 :
- Calendar : https://github.com/prolificinteractive/material-calendarview
- ThreeTen Android Backport, https://github.com/JakeWharton/ThreeTenABP/


### 프로젝트 진행하면서 어려웠던 점
1. 데이터와 UI의 불일치 현상 - ○ 파일의 저장, 삭제에 대한 동기와 데이터 호출에서의 비동기적 부분 공부 (코루틴)
2. 프로젝트 구조

3. 데이터의 흐름을 역할의 관점에서 고려
아키텍처 공부, 패키지를 계층으로 나누어 관심사 분리
○ data, domain, presentation
○ remote/local에서 받아오는 모델과 UI에서 사용하는 모델 분리
● 파일 형식의 SharedPreferences를 사용

