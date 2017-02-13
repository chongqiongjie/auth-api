package auth.api

import grails.transaction.Transactional
import groovy.sql.Sql
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.postgresql.ds.PGSimpleDataSource

//@Transactional
class Oauth2Service {
    //def dataSource = _getDataSource("10.212.36.41", "5432", "ms", "spiderdt")
    def dataSource = _getDataSource("192.168.1.3", "5432", "ms", "spiderdt")
    def sqlClient = _openSqlClient(dataSource)
    def _getDataSource(hostname, port, username, password) {
        def dataSource = new PGSimpleDataSource().each {
                             it.url = "jdbc:postgresql://${hostname}:${port}/ms?useSSL=falsaracterEncoding=utf-8&stringtype=unspecified&sslmode=require&sslkey=/.clojurians-org/opt/tomcat/ssl/client/client.key.pk8&sslcert=/.clojurians-org/opt/tomcat/ssl/client/client.cert.pem&sslrootcert=/.clojurians-org/opt/tomcat/ssl/client/root.cert.pem&sslfactory=org.postgresql.ssl.jdbc4.LibPQFactory".toString()
                             it.user = username
                             it.password = password
                         }
        [_dataSource: dataSource, args: [hostname: hostname, port: port, username: username, password: password]]
    }
   def _openSqlClient(dataSource) {
       [client: new Sql(dataSource._dataSource)] + dataSource
   }
   def openSqlClient(hostname, port, username, password) {
       _openSqlClient(_getDataSource(hostname, port, username, password))
   }

    def closeSqlClient() { sqlClient.client.close() }
     
    def getUser(userId){
        def userInfo = sqlClient.client.firstRow("select username, password, email, fullName  from Auth_User where username = ?",
            [userId]
        )
        def userSecurityRoles = sqlClient.client.rows("select securityRole from Auth_UserSecurityRole where username = ?",
            [userId]
        )*.securityRole
        userInfo + [securityRoles: userSecurityRoles]
    }

    def addDbUser(username, password, email,fullName) {
        log.info("addDbUser...: $username, $password, $email, $fullName")
        def data = [username: username, password: new BCryptPasswordEncoder().encode(password), email:email, fullName: fullName]
        sqlClient.client.execute("insert into Auth_User(username,password,email,fullName) values (?,?,?,?)",
            [username,password,email,fullName]
        )
    }
    def addDbUserSecurityRole(username, securityRole) {
        log.info("addDbUserSecurityRole...: $username,  $securityRole")
        def data = [username: username, securityRole: securityRole]
        sqlClient.client.execute("insert into Auth_UserSecurityRole(username,securityRole) values (?, ?)",
            [username,securityRole]
        )
    }
    def createUser(username, password, email, fullName) {
        addDbUser(username, password, email, fullName)
        addDbUserSecurityRole(username, 'ROLE_CLIENT')
        [username: username, password: password,  email:email, fullName:fullName,securityRole: "ROLE_CLIENT"]
    }
}
