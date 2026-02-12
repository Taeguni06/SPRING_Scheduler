
<h1>ERD</h1>

![img.png](img.png)
<h1>디렉토리</h1>


    src
    ├── main
    │   └── java
    │       └── com.example.schedulerdevelop
    │           ├── global             
    │           │   ├── config           
    │           │   ├── customConst    
    │           │   ├── dto               
    │           │   ├── entity             
    │           │   ├── exception         
    │           │   ├── handler            
    │           │   └── security           
    │           ├── scheduler             
    │           │   ├── comment            
    │           │   │   ├── controller
    │           │   │   ├── dto
    │           │   │   ├── entity
    │           │   │   ├── repository
    │           │   │   └── service
    │           │   ├── schedule          
    │           │   │   ├── controller
    │           │   │   ├── dto
    │           │   │   ├── entity
    │           │   │   ├── repository
    │           │   │   └── service
    │           │   └── user              
    │           │       ├── controller
    │           │       ├── dto
    │           │       ├── entity
    │           │       ├── repository
    │           │       └── service
    │           └── SchedulerDevelopApplication.java  
    ├── resources                          
    └── test                               
    └── java
    └── com.example.schedulerdevelop
    └── SchedulerDevelopApplicationTests.java


## 회원 관리 API

| 기능 | Method | URL | 설명 |
|------|--------|-----|------|
| 회원 가입 | `POST` | `/signup` | 새로운 유저를 등록합니다. |
| 로그인 | `POST` | `/login` | 유저 인증을 진행합니다. |
| 로그아웃 | `POST` | `/logout` | 유저 세션을 종료합니다. |
| 유저 조회 | `GET` | `/users/{userId}` | ID를 통해 특정 유저 정보를 조회합니다. |
| 유저 수정 | `PUT` | `/users/{userId}` | 유저 정보를 업데이트합니다. |
| 회원 탈퇴 | `DELETE` | `/users/{userId}` | 유저 계정을 삭제합니다. |
| 유저 전체 조회 | `GET` | `/users` | 모든 유저 목록을 조회합니다. |

## 일정 관리 API

| 기능 | Method | URL | 설명 |
|------|--------|-----|------|
| 일정 생성 | `POST` | `/schedules` | 새로운 일정을 생성합니다. |
| 일정 단건 조회 | `GET` | `/schedules/{scheduleId}` | ID를 통해 일정을 상세 조회합니다. |
| 일정 목록 조회 | `GET` | `/schedules` | 페이징 및 이름 조건(`?userName=`)으로 일정을 조회합니다. |
| 일정 전체 조회 | `GET` | `/schedules/all` | 모든 일정을 조회합니다. (테스트용) |
| 일정 수정 | `PUT` | `/schedules/{scheduleId}` | 해당 일정의 내용을 수정합니다. |
| 일정 삭제 | `DELETE` | `/schedules/{scheduleId}` | 해당 일정을 삭제합니다. |

## 댓글 관리 API

| 기능 | Method | URL | 설명 |
|------|--------|-----|------|
| 댓글 작성 | `POST` | `/schedules/{scheduleId}/comments` | 특정 일정에 댓글을 작성합니다. |
| 댓글 상세 조회 | `GET` | `/schedules/{sId}/comments/{cId}` | 특정 댓글 내용을 조회합니다. |
| 댓글 수정 | `PUT` | `/schedules/{sId}/comments/{cId}` | 작성한 댓글을 수정합니다. |
| 댓글 삭제 | `DELETE` | `/schedules/{sId}/comments/{cId}` | 특정 댓글을 삭제합니다. |

# API 명세서

## 1. 일정 관리 API

### 1.1 일정 등록
**URL:** `POST /schedules`

#### REQUEST BODY
```json
{
    "title": "String (최대 30자, 필수)",
    "content": "String (최대 200자, 필수)",
    "name": "String (필수)",
    "password": "String (필수)"
}
```

#### RESPONSE STATUS CODE
| 코드 | 설명 |
|------|------|
| 201 | 생성 성공 |
| 400 | 잘못된 요청 |
| 500 | 서버 오류 |

#### RESPONSE
```json
{
    "id": 1
}
```

---

### 1.2 일정 단건 조회
**URL:** `GET /schedules/{scheduleId}`

#### RESPONSE STATUS CODE
| 코드 | 설명 |
|------|------|
| 200 | 조회 성공 |
| 400 | 잘못된 요청 |
| 404 | 존재하지 않는 일정 |
| 500 | 서버 오류 |

#### RESPONSE
```json
{
    "getResponse": {
        "id": 1,
        "title": "제목",
        "content": "내용",
        "name": "이름",
        "createdAt": "생성 시간",
        "modifiedAt": "수정 시간"
    },
    "comments": [
        {
            "id": 1,
            "scheduleId": 1,
            "content": "댓글 내용",
            "name": "댓글 작성자 이름",
            "createdAt": "댓글 생성 시간",
            "modifiedAt": "댓글 수정 시간"
        }
    ]
}
```

#### ERROR CASE
```json
{
    "timestamp": "2026-02-12T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "오류: 존재하지 않음",
    "path": "/schedules/1"
}
```

---

### 1.3 사용자 이름으로 일정 조회
**URL:** `GET /schedules?name={userName}`

#### RESPONSE STATUS CODE
| 코드 | 설명 |
|------|------|
| 200 | 조회 성공 |
| 400 | 잘못된 요청 |
| 404 | 존재하지 않는 일정 |
| 500 | 서버 오류 |

#### RESPONSE
```json
[
    {
        "id": 1,
        "title": "제목",
        "content": "내용",
        "name": "사용자 이름",
        "createdAt": "생성 시간",
        "modifiedAt": "수정 시간"
    },
    {
        "id": 2,
        "title": "제목2",
        "content": "내용2",
        "name": "사용자 이름",
        "createdAt": "생성 시간",
        "modifiedAt": "수정 시간"
    }
]
```

#### ERROR CASE
```json
{
    "timestamp": "2026-02-12T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "오류: 해당하는 일정이 없습니다.",
    "path": "/schedules"
}
```

---

### 1.4 일정 전체 조회
**URL:** `GET /schedules/all`

#### RESPONSE STATUS CODE
| 코드 | 설명 |
|------|------|
| 200 | 조회 성공 |
| 400 | 잘못된 요청 |
| 404 | 존재하지 않는 일정 |
| 500 | 서버 오류 |

#### RESPONSE
```json
[
    {
        "id": 1,
        "title": "제목",
        "content": "내용",
        "name": "이름",
        "createdAt": "생성 시간",
        "modifiedAt": "수정 시간"
    }
]
```

#### ERROR CASE
```json
{
    "timestamp": "2026-02-12T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "오류: 일정이 없습니다.",
    "path": "/schedules/all"
}
```

---

### 1.5 일정 수정
**URL:** `PUT /schedules/{scheduleId}`

#### REQUEST BODY
```json
{
    "title": "String (최대 30자, 필수)",
    "content": "String (최대 200자, 필수)",
    "name": "String (필수)",
    "password": "String (필수)"
}
```

#### RESPONSE STATUS CODE
| 코드 | 설명 |
|------|------|
| 200 | 수정 성공 |
| 400 | 잘못된 요청 |
| 403 | 비밀번호 불일치 |
| 404 | 존재하지 않는 일정 |
| 500 | 서버 오류 |

#### RESPONSE
```json
{
    "id": 1,
    "title": "수정된 제목",
    "content": "수정된 내용",
    "name": "이름",
    "modifiedAt": "수정 시간"
}
```

#### ERROR CASE
```json
{
    "status": 403,
    "message": "오류: 비밀번호 불일치"
}
```
```json
{
    "timestamp": "2026-02-12T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "오류: 존재하지 않음",
    "path": "/schedules/1"
}
```

---

### 1.6 일정 삭제
**URL:** `DELETE /schedules/{scheduleId}`

#### REQUEST BODY
```json
{
    "password": "String (필수)"
}
```

#### RESPONSE STATUS CODE
| 코드 | 설명 |
|------|------|
| 204 | 삭제 성공 |
| 400 | 잘못된 요청 |
| 403 | 비밀번호 불일치 |
| 404 | 존재하지 않는 일정 |
| 500 | 서버 오류 |

#### ERROR CASE
```json
{
    "status": 403,
    "message": "오류: 비밀번호 불일치"
}
```
```json
{
    "timestamp": "2026-02-12T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "오류: 존재하지 않음",
    "path": "/schedules/1"
}
```

---

## 2. 댓글 관리 API

### 2.1 댓글 생성
**URL:** `POST /schedules/{scheduleId}/comments`

#### REQUEST BODY
```json
{
    "content": "String (최대 100자, 필수)",
    "name": "String (필수)",
    "password": "String (필수)"
}
```

#### RESPONSE STATUS CODE
| 코드 | 설명 |
|------|------|
| 201 | 생성 성공 |
| 400 | 잘못된 요청 |
| 404 | 존재하지 않는 일정 |
| 500 | 서버 오류 |

#### RESPONSE
```json
{
    "id": 1
}
```

---

### 2.2 댓글 수정
**URL:** `PUT /schedules/{scheduleId}/comments/{commentId}`

#### REQUEST BODY
```json
{
    "content": "String (최대 100자, 필수)",
    "password": "String (필수)"
}
```

#### RESPONSE STATUS CODE
| 코드 | 설명 |
|------|------|
| 200 | 수정 성공 |
| 400 | 잘못된 요청 |
| 403 | 비밀번호 불일치 |
| 404 | 존재하지 않는 댓글 |
| 500 | 서버 오류 |

#### RESPONSE
```json
{
    "id": 1,
    "scheduleId": 1,
    "content": "수정된 댓글 내용",
    "name": "작성자 이름",
    "modifiedAt": "수정 시간"
}
```

#### ERROR CASE
```json
{
    "status": 403,
    "message": "오류: 비밀번호 불일치"
}
```
```json
{
    "timestamp": "2026-02-12T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "오류: 존재하지 않음",
    "path": "/schedules/1/comments/1"
}
```

---

### 2.3 댓글 삭제
**URL:** `DELETE /schedules/{scheduleId}/comments/{commentId}`

#### REQUEST BODY
```json
{
    "password": "String (필수)"
}
```

#### RESPONSE STATUS CODE
| 코드 | 설명 |
|------|------|
| 204 | 삭제 성공 |
| 400 | 잘못된 요청 |
| 403 | 비밀번호 불일치 |
| 404 | 존재하지 않는 댓글 |
| 500 | 서버 오류 |

#### ERROR CASE
```json
{
    "status": 403,
    "message": "오류: 비밀번호 불일치"
}
```
```json
{
    "timestamp": "2026-02-12T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "오류: 존재하지 않음",
    "path": "/schedules/1/comments/1"
}
```

---

## 3. 공통 사항

### 3.1 날짜/시간 형식
- ISO 8601 형식 사용: `YYYY-MM-DDThh:mm:ss`
- 예시: `2026-02-12T10:30:00`

### 3.2 문자 제한
- 일정 제목: 최대 30자
- 일정 내용: 최대 200자
- 댓글 내용: 최대 100자

### 3.3 인증 방식
- 비밀번호 기반 인증
- 생성/수정/삭제 시 비밀번호 필수

### 3.4 에러 응답 구조
```json
{
    "timestamp": "발생 시간",
    "status": "HTTP 상태 코드",
    "error": "에러 타입",
    "message": "에러 메시지",
    "path": "요청 경로"
}
```