package auth.api

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import static groovy.json.JsonOutput.toJson
import javax.annotation.security.RolesAllowed
import org.springframework.security.core.context.SecurityContextHolder

@RolesAllowed(["ROLE_CLIENT"])
@Transactional(readOnly = true)
class UserController {
    def oauth2Service

    def show(UserCommand user)  {
        render toJson(SecurityContextHolder.context?.authentication?.principal?.properties.findAll{it.key != 'class'})
    }

    @Transactional
    def save(UserCommand user) {
        render toJson(oauth2Service.createUser(user.username, user.password, user.email, user.fullName))
       
    }

}

class UserCommand{
    String fullName
    String username
    String email
    String password

    static constraints = {
        username(blank: false, unique: true)
        email(blank: false, unique: true, email: true)
        password(blank: false)
        fullName(blank: false)
    }
}
