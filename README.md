# Social Media RESTful API
___
**Social Media API** - This project is an example of a Social Network REST API backend. It was developed solely for the purpose of self-education. With the help of this service, you can create accounts, add friends, make posts and subscribe to the authors you are interested in.

___
## Project Stack
* Java 17
* Spring Boot, REST, DATA, Security
* PostgreSQL
* Lombock, Swagger UI
* Docker, Maven
___
## Instalation
**Docker:**
```
docker pull andreynachevny/socialmediarest:latest
```
___
## Security
***This project uses two tokens.*** </br>
The first is an access token, required for secure endpoints (Lifetime - 5 minutes). </br>
The second is the refresh token, needed to refresh the access token. </br>
:white_check_mark: After successful registration, you will receive two tokens in json format:
```json
{
    "type": "Bearer ",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MjBAbWFpbC5jb20iLCJpYXQiOjE2OTMyNTAwMTIsImV4cCI6MTY5MzI1MDMxMiwiaWQiOjExLCJuYW1lIjoidGVzdDIwIn0.P68NT17QH0RP0NdxCAiZfGXflqMZITJ8S55gWw423Sw",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MjBAbWFpbC5jb20iLCJpYXQiOjE2OTMyNTAwMTIsImV4cCI6MTY5NTg0MjAxMn0.VUo-I_gbTXaay7pIGexgS4UQITN7YxJeF7OMuHSAMK8"
}
``` 

