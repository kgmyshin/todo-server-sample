## 起動方法

./gradlew modules:http-adapter:bootRun

## API

- POST /auth/signup
- POST /auth/signin
- POST /auth/refresh
- GET /tasks
- POST /tasks
- GET /tasks/{id}
- PUT /tasks/{id}
- DELETE /tasks/{id}

## 動作確認

```
$ curl -X POST -H "Content-Type: application/json" -d '{"user_id":"kgmyshin", "password":"aaaaa"}' localhost:8080/auth/signup

{"user_id":"kgmyshin","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDAxODF9.IGcAK-gOSErxIv0Trdz2Rd5aZ220_tm2mx64o6LsqGQ","refresh_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDg4MjF9.GVZ5NWT1CG8QwDfsNyqfCetNcQyBeBChr0OAoLLcxVw"}

$ curl -X POST -H "Content-Type: application/json" -d '{"user_id":"kgmyshin", "password":"aaaaa"}' localhost:8080/auth/signin

{"user_id":"kgmyshin","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDAyMTB9.QJoEihljH145BpdgBJvU2eTbFsszBFj4QyC-RQ_SwDQ","refresh_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDg4NTB9.oI6f6PQhZ4GSuAHOdW1LzLLm_mTyBp5MTPiSzHlQBsw"}

$ curl -X POST \
      -H 'Content-Type: application/json' \
      -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDAyMTB9.QJoEihljH145BpdgBJvU2eTbFsszBFj4QyC-RQ_SwDQ' \
      localhost:8080/auth/refresh

{"user_id":"kgmyshin","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDAyMTB9.QJoEihljH145BpdgBJvU2eTbFsszBFj4QyC-RQ_SwDQ","refresh_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDg4NTB9.oI6f6PQhZ4GSuAHOdW1LzLLm_mTyBp5MTPiSzHlQBsw"}

$ curl -X POST \
      -H 'Content-Type: application/json' \
      -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDAyMTB9.QJoEihljH145BpdgBJvU2eTbFsszBFj4QyC-RQ_SwDQ' \
      -d '{"title":"タイトル", "description":"説明説明説明説明説明説明説明"}' \
      localhost:8080/tasks

$ curl -X GET \
     -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDAyMTB9.QJoEihljH145BpdgBJvU2eTbFsszBFj4QyC-RQ_SwDQ' \
     localhost:8080/tasks?page=0&per=2

[{"id":"9fa35320-efb9-11e9-a2fb-acde48001122","title":"タイトル1","description":"説明説明説明説明説明説明説明1","completed":false},{"id":"9fa35320-efb9-11e9-a2fb-acde48001122","title":"タイトル2","description":"説明説明説明説明説明説明説明2","completed":false}]

$ curl -X GET \
     -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDAyMTB9.QJoEihljH145BpdgBJvU2eTbFsszBFj4QyC-RQ_SwDQ' \
     localhost:8080/tasks/9fa35320-efb9-11e9-a2fb-acde48001122

{"id":"9fa35320-efb9-11e9-a2fb-acde48001122","title":"タイトル","description":"説明説明説明説明説明説明説明","completed":false}

$ curl -X PUT \
      -H 'Content-Type: application/json' \
      -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDAyMTB9.QJoEihljH145BpdgBJvU2eTbFsszBFj4QyC-RQ_SwDQ' \
      -d '{"id":"9fa35320-efb9-11e9-a2fb-acde48001122", "title":"タイトル", "description":"説明説明説明説明説明説明説明", "completed":true}' \
      localhost:8080/tasks/9fa35320-efb9-11e9-a2fb-acde48001122

{"id":"9fa35320-efb9-11e9-a2fb-acde48001122","title":"タイトル","description":"説明説明説明説明説明説明説明","completed":true}

$ curl -X DELETE \
     -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoia2dteXNoaW4iLCJleHAiOjE1NzEyMDAyMTB9.QJoEihljH145BpdgBJvU2eTbFsszBFj4QyC-RQ_SwDQ' \
     localhost:8080/tasks/9fa35320-efb9-11e9-a2fb-acde48001122

{}
```
