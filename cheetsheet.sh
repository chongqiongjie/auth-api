curl -X  POST -u jupiter:spiderdt http://192.168.1.2:2222/oauth/token \
     -H "Accept: application/json" \
     -d "password=spiderdt&username=chong&grant_type=password&scope=JUPITER_API"
curl  http://192.168.1.2:2222/user/token -H "Authorization: Bearer [TOKEN]"

curl -k -X  POST -u jupiter:spiderdt https://192.168.1.2:8443/auth-api-v1/oauth/token \
     -H "Accept: application/json" \
     -d "password=spiderdt&username=chong&grant_type=password&scope=JUPITER_API"
curl -k https://192.168.1.2:8443/auth-api-v1/user/token -H "Authorization: Bearer [TOKEN]"

curl -k -X  POST -u jupiter:spiderdt http://192.168.1.2:8081/auth-api-v1/oauth/token \
     -H "Accept: application/json" \
     -d "password=spiderdt&username=chong&grant_type=password&scope=JUPITER_API"



