# Android ToyProject

    **Version
    1. Android studio 2021.1.1 patch 2
    2. DRF

### Content
    1. Melon
    2. Youtube
    3. Instagram

## 1. Melon
<div align=center>
  
  ![Android-melon-brightgreen](https://user-images.githubusercontent.com/69226800/168702611-e3ea39cc-71dc-4b03-ad6e-0b4442b15359.svg)
  
  ![melon](https://user-images.githubusercontent.com/69226800/168709620-77e582d2-774e-45f9-b7dc-b46f51a67b3d.gif)
  
</div>

### Description

    1) Retofit을 이용한 서버통신 기능
        - 서버로부터 앨범 커버 이미지, 음악, 제목 등을 불러옵니다.
    2) RecyclerView 사용
        - melon 재생리스트를 불러와 RecyclerView로 표현합니다.
        - Adapter에서 play 버튼을 누를 경우 intent로 PlayList를 보냅니다.(serializable)
        - PlayList는 음악을 재생한 후에 다음 곡, 이전 곡을 재생하기 위해 사용됩니다.
    3) Glide 사용
        - 앨범 커버 이미지를 나타내기 위해 glide 라이브러리를 사용합니다.
    4) switch 추가
        - 음악이 재생 중일 때는 play 버튼이, 일시정지 상태일 때는 pause 버튼이 나타나도록 합니다.
        - serializable로 받은 PlayList를 통해 다음 곡, 이전 곡 재생이 가능하도록 했습니다.
    5) MediaPlayer 활용
        - MediaPlayer를 활용해 음악을 재생하고 멈추는 기능을 만들었습니다.

## 2. Youtube
<div align=center>
  
  ![Android-youtube-blueviolet](https://user-images.githubusercontent.com/69226800/168702500-4e673f94-0eaa-4d9c-8abd-d22da799e992.svg)
  
  ![youtube(1)](https://user-images.githubusercontent.com/69226800/168701908-e3933171-e39a-4e9b-a04b-4dd9948d74e7.gif)
    
  ![youtube(2)](https://user-images.githubusercontent.com/69226800/168701941-3dc62ff5-084d-4e6c-8993-5dfc44ce0513.gif)
  
</div>

### Description

    1) Retrofit, RecyclerView를 사용
        - 서버로부터 동영상 객체들을 불러옵니다.
        - RecyclerView를 활용해 동영상, 제목, 설명을 목록화 합니다.
    2) Glide & MediaPlayer를 활용한 동영상 재생
    
## 3. Instagram
<div align=center>
    
  ![Android-instagram-ff69b4](https://user-images.githubusercontent.com/69226800/168710684-6cc70164-a7bb-4682-8420-05aecfaf6987.svg)

</div>

### Description

<div align=center>
    
![join](https://user-images.githubusercontent.com/69226800/168744331-9370d625-d5e2-4919-82d0-de9c0d1ba0cf.gif)
![login](https://user-images.githubusercontent.com/69226800/168744340-d022b3af-4540-4105-ad5d-896ca90797bb.gif)

</div>

    1) SplashActivity를 통해 로그인 여부 확인 후 로그인 여부에 따라 LoginActivity / FeedActivity 로 이동
    2) 회원가입 기능
        - 서버와 연동해 사용자로부터 ID/PW 를 입력 받아 회원가입
        - 회원가입 완료시 SharedPreference에 Token 값과 user id를 저장
        - 회원가입 완료시 자동 로그인
    3) 로그인 기능
        - 사용자로부터 ID, PW를 받아 로그인
        - 로그인시 서버로부터 Token 값을 받아 SharedPreference에 저장
        - 한번 로그인이 되면 SharedPreference의 데이터를 삭제하기 전까지 자동 로그인
        
<div align=center>

![favorite](https://user-images.githubusercontent.com/69226800/168744345-383d2373-2fce-45ea-9879-c8c3996d96f1.gif)

</div>

    4) 좋아요 기능
        - layout을 활용해 이미지를 누르면 "좋아요"문구와 함께 하트모양 노출 후 자동으로 돌아아옴

<div align=center>
    
![post(1)](https://user-images.githubusercontent.com/69226800/168744462-08b86913-8b94-421c-9c0c-250895ef869e.gif)
![post(2)](https://user-images.githubusercontent.com/69226800/168744465-0e85d44a-b4d1-4f27-b4e4-f634ae1625ad.gif)

</div>

    5) Post 기능
        - Intent ACTION_PICK 기능을 이용해 이미지 선택 창으로 접근
        - 이미지를 선택해 서버에 POST 요청을 보냄
        - 이때 Multipart를 활용해 텍스트와 함께 전송
        - Feed에 나타나는 지 확인
        
<div align=center>
    
![profile](https://user-images.githubusercontent.com/69226800/168744453-f9100a92-2d29-40c6-9683-d1df75079792.gif)

</div>

    6) Profile 수정 기능
        - Profile 정보를 서버로 보내 Profile 정보를 바꾸는 기능
