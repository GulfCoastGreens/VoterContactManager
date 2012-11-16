import grails.util.GrailsNameUtils
import grails.util.GrailsUtil
import org.greens.VoterContactManager.*

class BootStrap {

    def init = { servletContext ->
        switch(GrailsUtil.environment){
            case "development":
                println "#### Development Mode (Start Up)"
                println "#### Building some test topics"
                println "#### Building some test queues"
                // def contactService = new ContactService()
                // contactService.testBuilder()
                // contactService.getGoogleContactsList()
                if(State.findByCode('FL') == null) {
                    FloridaBootStrap.bootStrap()
                }
                if(State.findByCode('GA') == null) {
                    GeorgiaBootStrap.bootStrap()
                }
                def cs = new ContactService()
                // System.out.println(cs.getGoogleContacts())
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
