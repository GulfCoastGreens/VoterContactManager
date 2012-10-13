package org.greens.VoterContactManager

import grails.converters.*

class ContactController {
    def contactService
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
                return contactService.addNewContact(params.name)
            }
            xml {
                render contactService.addNewContact(params.name) as XML
            }
            json {
                render contactService.addNewContact(params.name) as JSON
            }
        }                
    }
}
