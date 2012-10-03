package org.greens.VoterContactManager

class VoterContactManagerTagLib {
    static namespace = "vcm"  
    
    def applicationStatus = {
        out << '<ul>'
        out << '    <li>App version: '+g.meta(name:"app.version")+'</li>'
        out << '    <li>Grails version: '+g.meta(name:"app.grails.version")+'</li>'
        out << '    <li>Groovy version: '+org.codehaus.groovy.runtime.InvokerHelper.getVersion()+'</li>'
        out << '    <li>JVM version: '+System.getProperty('java.version')+'</li>'
        out << '    <li>Reloading active: '+grails.util.Environment.reloadingAgentEnabled+'</li>'
        out << '    <li>Controllers: '+grailsApplication.controllerClasses.size()+'</li>'
        out << '    <li>Domains: '+grailsApplication.domainClasses.size()+'</li>'
        out << '    <li>Services: '+grailsApplication.serviceClasses.size()+'</li>'
        out << '    <li>Tag Libraries: '+grailsApplication.tagLibClasses.size()+'</li>'
        out << '</ul>'        
    }
    def installedPlugins = {
        out << '<ul>'
        applicationContext.getBean('pluginManager').allPlugins.each{ plugin ->
            out << '<li>'+plugin.name+' - '+plugin.version+'</li>'
        }
        out << '</ul>'        
    }
    def availableControllers = {
        out << '<ul>'
        grailsApplication.controllerClasses.sort { it.fullName }.each { c -> 
            out << '<li class="controller">'
            out << g.link(controller:c.logicalPropertyName) {
                out << c.fullName
            }
            out << '</li>'
        }
        out << '</ul>'        
    }
    def uploadStateSelect = {        
        out << "<select id='stateCode' name='stateCode'>"
        State.findAllByEnabled(true).each { state -> 
            out << "<option value='${state.code}'>${state.name}</option>"
        }
        out << "</select>"        
    }
    def jqDatePicker = { attrs,body ->
        def name=attrs.name
        
        out << g.textField(name:name)
        out << r.script() {
            out << '$("input#'+name+'").datepicker({ dateFormat: "yy-mm-dd"});'
        }

    }
    
}
