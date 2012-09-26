import grails.util.GrailsNameUtils
import grails.util.GrailsUtil
import org.greens.VoterContactManager.State
import org.greens.VoterContactManager.FloridaBootStrap
import org.greens.VoterContactManager.GeorgiaBootStrap
import org.greens.VoterContactManager.SecRole
import org.greens.VoterContactManager.SecUser
import org.greens.VoterContactManager.SecUserSecRole

class BootStrap {
    def springSecurityService
    
    def init = { servletContext ->
        def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)
        def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
                username: 'jam',
                password: springSecurityService.encodePassword('admin'),
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            SecUserSecRole.create adminUser, adminRole
        }        
        switch(GrailsUtil.environment){
            case "development":
                println "#### Development Mode (Start Up)"
                println "#### Building some test topics"
                println "#### Building some test queues"
                if(State.findByCode('FL') == null) {
                    FloridaBootStrap.bootStrap()
                }
                if(State.findByCode('GA') == null) {
                    GeorgiaBootStrap.bootStrap()
                }
                break
            case "test":
                println "#### Test Mode (Start Up)"
                break
            case "production":
                println "#### Production Mode (Start Up)"
                if(State.findByCode('FL') == null) {
                    FloridaBootStrap.bootStrap()
                }
                if(State.findByCode('GA') == null) {
                    GeorgiaBootStrap.bootStrap()
                }
                break
        }
    }
    def destroy = {
        switch(GrailsUtil.environment){
            case "development":
                println "#### Development Mode (Shut Down)"
                break
            case "test":
                println "#### Test Mode (Shut Down)"
                break
            case "production":
                println "#### Production Mode (Shut Down)"
                break
        }
    }
}
