package org.greens.VoterContactManager

// include org.greens.VoterContactManager.State

class UploadDataTagLib {
    static namespace = "ud"
    def uploadVoterForm = {
        
    }
    def uploadHistoryForm = {
        
    }
    def uploadStateSelect = {        
        out << "<select id='stateCode' name='stateCode'>"
        State.findAllByEnabled(true).each { state -> 
            out << "<option value='${state.code}'>${state.name}</option>"
        }
        out << "</select>"        
    }
    def uploadVoterZipForm = { attrs,body ->
        out << g.form(controller: 'uploadData', action: 'uploadVoterData', 'data-ajax': 'false', method: 'POST', enctype: 'multipart/form-data')
    }
}
