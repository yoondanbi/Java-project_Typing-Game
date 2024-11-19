## 🚩 프로젝트 개요
- **주제**
  - 슈와 초콜릿 공장(Java를 이용한 타자 연습 게임)
- **게임 구성**
  - 게임 화면
    - 게임 진행 방식
     <br> 게임 시작 시, 초콜릿들이 오른쪽에서 왼쪽으로 컨베이어 벨트를 타고 밀려나오는 구조, 초콜릿은 일정한 간격을 두고 랜덤한 단어가 선택되어 생산.
     <br> 단어를 타이핑하여 초콜릿을 수집<br>
      (올바르게 타이핑 시, 초콜릿 1개 획득 / 잘못된 단어 입력 시, 초콜릿 2개 반납)<br>
      (단어를 틀리면 캐릭터의 표정이 슬퍼지고, 맞추면 캐릭터의 표정이 밝아짐)
    - 게임 레벨
     <br> 총 3단계, 1분 소요(1단계: ~20초 / 2단계 : ~40초 / 3단계 : ~60초)
     <br> 단계가 올라갈수록 초콜릿의 생산 속도 & 컨베이어 벨트의 속도가 증가
    - 게임 유효 생명
     <br> 3개, (게임 레벨과 상관없이 생명은 유지)
     <br> 플레이 타임 동안 흘려보낸 초콜릿의 수가 15개가 될 때마다 생명 1개 감소
    - 순위 기록
     <br> 게임 종료(1분 후 자동 종료) 후, 사용자의 이름 입력받아 자동 기록 및 랭킹.
  - 시작 화면
    - 게임 시작, 순위 확인, 단어 편집의 3가지 버튼으로 구성된다.
  - 순위 확인 화면
    - 사용자가 입력한 이름, 수집한 초콜릿 개수 순으로 나열되어 결정된 순위가 보인다.
  - 단어 편집 화면
    - 단어를 추가하거나 삭제하는 등 편집할 수 있으며 단어들을 직접 리스트로 직접 볼 수 있다.
<br>

## 🧑‍🤝‍🧑 맴버구성
 - 설계 및 구현 : [윤단비](https://github.com/yoondanbi) 

<br>

 ## ⚙️ 개발 환경
- **Operating System**: Windows
- **IDE** : eclipse 2023-06 (4.28.0)
- **Programming Language/Development Kit**: Java / jdk 17.0.8 2023-07-18 LTS
<br>

## 💝 구현 방법
### 구조 및 작동 관계
#### [프레임] 
게임의 전반적인 프레임을 담당하는 GameFrame.java 파일.  
초기 화면 패널, 순위 확인 패널, 단어 편집 패널, 게임 실행 패널은 CardLayout 배치관리자로 
GameFrame에 배치되었다.
<br>
![스크린샷 2024-11-19 173536](https://github.com/user-attachments/assets/d36336cc-faaf-463e-af9f-263b98aab2ec)
<br>
이들의 작동 관계도는 다음과 같다. 
<br>
![스크린샷 2024-11-19 173544](https://github.com/user-attachments/assets/89b6568a-99e8-41db-b587-cdae8b14ad1a)
<br>
#### [게임 실행 패널]  
게임 실행 바탕이 되는 GamePanel.java 파일,  
타이핑 게임이 진행되는 GameGround.java 파일,  
랜덤한 단어를 감싼 초콜릿이 컨베이어를 타고 이동하는 RealGamePanel.java 파일,  
단어를 입력하여 맞추는 InputPanel.java 파일,  
획득하는 초콜릿의 개수(점수)와 흘려 보낸 초콜릿 수, 사용자의 유효 생명 수 출력하 
ScorePanel.java 파일,  
현재 레벨을 출력하는 CurrentLevelPanel.java 파일, 
초콜릿 이미지와 랜덤한 단어를 포함하는 레이블을 생성하는 스레드 CreateLabelThread.java 파일 
초콜릿 레이블을 움직이게 하는 스레드 MovingThread.java 파일 
이들의 관계도는 다음과 같다.
<br>
![스크린샷 2024-11-19 173553](https://github.com/user-attachments/assets/d90b0f7a-054c-4ce2-910f-6f016fb742d9)
<br>
#### [초기 화면 패널]  
게임 시작, 순위 확인, 단어 편집에 해당하는 버튼 3개로 구성되는 StartPanel.java 파일  
초기화면에서 슈 캐릭터가 움직일 수 있도록 하는 스레드 BounceSueThread.java 파일 
#### [순위 확인 패널]  
저장된 사용자의 정보를 바탕으로 순위를 출력해주는 RankingPanel.java 파일  
사용자의 이름과 점수를 저장하는 rankData.txt 파일 
사용자의 이름과 점수를 저장하는 UserData.java 파일 
#### [단어 편집 패널]  
단어를 추가적으로 입력하거나 삭제하여 편집할 수 있게 하는 EditPanel.java 파일,  
단어가 들어간 파일을 읽어 벡터에 저장할 TextSource.java 파일
