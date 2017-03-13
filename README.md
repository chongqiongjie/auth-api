# auth-api


auth-api(grails)：
grails create-app auth-api --profile=rest-api
grails create-domain-class user
    (String username

    String email

    String password

    String fullName)
grails create-service oauth2
grails generate-all auth.api.User
grails create-service CustomUserDetails(加入spring security 验证用户身份)


grails auth-api token:
https://github.com/bobbywarner/grails3-oauth2-api









curl -X POST -u jupiter:spiderdt http://192.168.1.2:2222/oauth/token -H "Accept: application/json" -d "password=spiderdt&username=chong&grant_type=password&scope=JUPITER_API"

curl  http://192.168.1.2:2222/user/token -H "Authorization: Bearer b6666a81-6f45-4a3b-81bf-8d80f9e8d627"

curl -X POST -u jupiter:spiderdt http://192.168.1.2:1111/oauth/check_token -H "Authorization: Bearer 9d5d0a66-08d2-4f1d-983e-01d8a0fb0504"
