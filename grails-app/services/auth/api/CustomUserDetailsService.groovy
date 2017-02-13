package auth.api

import grails.transaction.Transactional
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException

class CustomUserDetailsService implements UserDetailsService {
    def oauth2Server = new Oauth2Service()

    @Override
    def UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        log.info("username:" + username)
        def user_info = oauth2Server.getUser(username)    
        log.info("user_info:" + user_info.toString())
        new User(
            user_info.username, 
            user_info.password, 
            user_info.securityRoles.collect{new SimpleGrantedAuthority(it)}
        )
    }
}
