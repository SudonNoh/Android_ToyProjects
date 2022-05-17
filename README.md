# Android ToyProject

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
