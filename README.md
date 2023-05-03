# Aurora(Springboot)
![image](https://user-images.githubusercontent.com/115364621/235844960-11851733-f3d7-433a-be69-74aee6edc92c.png)

### 루트3은 4조 팀원
* 오승재 - os133517@gmail.com
* 서지수 - seojs3045@gmail.com
* 김수용 - ssssong125@gmail.com 
* 정근호 -  jgh337337@gmail.com
* 허재홍 - zero5140@gmail.com

------------

그룹웨어 

팀 구성 : Frontend/Backend 5인  
진행기간 : 23.03.06 ~ 23.04.18  
주요내용 :
- React로 메인페이지를 구현하였고 네비게이션 사이드바를 사용하여 다른 메뉴로 이동이 가능하도록 구현하였습니다.
- 주요 메뉴로는 메일, 보고, 일정관리, 업무일지, ToDo, 주소록, 근태, 인사, 설문, 예약, 결재, 모니터링, 비품관리, 게시판 등이 있습니다. 

------------

# [개발언어]
![이미지](./stack.png)
### 1. Frontend
#### - Javascript
#### - JQuery (latest)
#### - Ajax
#### - React 
------------  
### 2. Backend
#### - Java 11
------------  
### 3. DB
#### - MySQL 8.0.32 
------------  
### 4. Framework
#### - SpringBoot
#### - Mybatis
------------  
### 5. Tool
#### - Visual Studio Code
#### - Intelli J
------------  
### 6. ETC
#### - Figma
#### - Chrome DevTools

# Git Flow

## 🏁 main 브랜치

배포할 프로젝트를 담는 브랜치 

## 👨‍💻develop 브랜치

개발시 사용, main의 소스코드를 복제해서 main에 발생할 오류들을 예방
## 🤔feature 브랜치

devlop 브랜치에 각자 바로 커밋하지말고 만든 기능을 여기서 테스트를 해보고 잘 됬을 때 머지

feature/user, feature/board, feature/planner, feature/recode...로 작명하여 각 기능들이 무엇인지 명시

## 🔎release 브랜치

develop에 feature기능을 머지 다하면 main 브랜치에 머지 하기전에 테스트하는 브랜치 테스트 후 정상적으로 구동하면 main에다가 머지를 한다.

feature => develop => release => main 순으로 

![경로](./useStack.png)
