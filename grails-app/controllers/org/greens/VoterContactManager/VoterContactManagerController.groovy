package org.greens.VoterContactManager

import grails.converters.*

class VoterContactManagerController {
    def voterContactManagerService
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
    
    def getInit = {
        withFormat {
            html {
                return voterContactManagerService.getInit(params.stateCode)
            }
            xml {
                render voterContactManagerService.getInit(params.stateCode) as XML
            }
            json {
                render voterContactManagerService.getInit(params.stateCode) as JSON
            }
        }                
    }
}
