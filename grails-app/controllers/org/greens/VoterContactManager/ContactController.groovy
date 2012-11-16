package org.greens.VoterContactManager

import grails.converters.*
import uk.co.desirableobjects.oauth.scribe.OauthService
import org.scribe.model.Token 

class ContactController {
    def contactService
    OauthService oauthService
    def index = { 
        switch(request.method){
            case "POST":
                render "Create\n"
                break
            case "GET":
                // render "Retrieve\n"
                break
            case "PUT":
                render "Update\n"
                break
            case "DELETE":
                render "Delete\n"
                break
        }           
    }    
    // Try here http://www.grailsblog.pl/2012/04/oauth-logging-in-grails-made-easy-with.html
    def getGoogleAccessToken() {
      String sessionKey = oauthService.findSessionKeyForAccessToken('google')
      return session[sessionKey]
    }    
    def show(){
      def calendars = oauthService.getGoogleResource(getGoogleAccessToken(), 'http://www.google.com/m8/feeds/contacts/default/full?key=${grailsApplication.config.oauth.providers.google.key}')
      def calendarsJSON = JSON.parse(calendars.body)
      render calendarsJSON

    }    
    Token getToken() {
        Token googleAccessToken = session[oauthService.findSessionKeyForAccessToken('google')]
        oauthService.getGoogleResource(googleAccessToken, 'http://www.google.com/m8/feeds/contacts/default/full')
        // String sessionKey = oauthService.findSessionKeyForAccessToken('google')
        System.out.println(sessionKey)
        System.out.println("Session Key is ${session[sessionKey]}")
        // return session[sessionKey]
        redirect(uri:'/')
    }    
    def getGoogleContacts() {
        
    }
    def getContactTypes() { 
        withFormat {
            html {
                return contactService.getContactTypes()
            }
            xml {
                render contactService.getContactTypes() as XML
            }
            json {
                render contactService.getContactTypes() as JSON
            }
        }                
        
    }
    def addNewContactType() {
        withFormat {
            html {
                return contactService.addNewContactType(params.name)
            }
            xml {
                render contactService.addNewContactType(params.name) as XML
            }
            json {
                render contactService.addNewContactType(params.name) as JSON
            }
        }                
    }
    def editContactType() {
        withFormat {
            html {
                return contactService.editContactType(params.id,params.name)
            }
            xml {
                render contactService.editContactType(params.id,params.name) as XML
            }
            json {
                render contactService.editContactType(params.id,params.name) as JSON
            }
        }                
    }
    def removeContactType() {
        withFormat {
            html {
                return contactService.removeContactType(params.id)
            }
            xml {
                render contactService.removeContactType(params.id) as XML
            }
            json {
                render contactService.removeContactType(params.id) as JSON
            }
        }                
    }
    def getContactsByType() {
        withFormat {
            html {
                return contactService.getContactsByType(params.name)
            }
            xml {
                render contactService.getContactsByType(params.name) as XML
            }
            json {
                render contactService.getContactsByType(params.name) as JSON
            }
        }                
    }
    
    def addNewContact() {
        withFormat {
            html {
                return contactService.addNewContact(params.name,params.nickname,params.contactType.name)
            }
            xml {
                render contactService.addNewContact(params.name,params.nickname,params.contactType.name) as XML
            }
            json {
                render contactService.addNewContact(params.name,params.nickname,params.contactType.name) as JSON
            }
        }                
    }
    def removeContact() {
        withFormat {
            html {
                return contactService.removeContact(params.id)
            }
            xml {
                render contactService.removeContact(params.id) as XML
            }
            json {
                render contactService.removeContact(params.id) as JSON
            }
        }                
    }
}
