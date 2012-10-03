package org.greens.VoterContactManager

import grails.converters.*

class SearchController {
    def searchService
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
    
    def search = {
        withFormat {
            html {
                return searchService.search(params.search)
            }
            xml {
                render searchService.search(params.search) as XML
            }
            json {
                render searchService.search(params.search) as JSON
            }
        }                
    }
}
