package org.greens.VoterContactManager

import grails.util.GrailsNameUtils

class ContactService {
    static transactional = true

    def getContactTypes() {
        return [ contactTypes: ContactType.findAll() ]
    }
    def addNewContactType(name) {
        ContactType contactType = ContactType.findByName(name)
        if(!contactType && !!name) {
            contactType = new ContactType(name: name.trim())
            if(!contactType.save(failOnError:true, flush: true, insert: true, validate: true)) {
                contactType.errors.allErrors.each {
                    println it
                }
                contactType = null
                return [ contactType: "error"]
            } else {
                println "Created new ${GrailsNameUtils.getShortName(contactType.class)} ${contactType.name}"                
            }                                                                
            return [ contactType : contactType ]
        }
        return [ contactType: "error"]
    }
    def editContactType(id,name) {
        ContactType contactType = ContactType.get(id)
        if(!!contactType && !!name) {
            contactType.name = name.trim()
            if(!contactType.save(failOnError:true, flush: true, validate: true)) {
                contactType.errors.allErrors.each {
                    println it
                }
                contactType = null
                return [ contactType: "error"]
            } else {
                println "Created new ${GrailsNameUtils.getShortName(contactType.class)} ${contactType.name}"                
            }                                                                
            return [ contactType : contactType ]
        }
        return [ contactType: "error"]        
    }
    def removeContactType(id) {
        ContactType contactType = ContactType.get(id)
        if(!!contactType) {
            Contact.createCriteria().listDistinct {
                createAlias("contactTypes","c")
                eq("c.name",contactType.name)
            }.each { contact ->
                contact.removeFromContactTypes(contactType)
            }
            contactType.delete(flush: true)
            return [ status: "success"]
        }
        return [ status: "error" ]
    }
    def getContactsByType(String contactTypeName = "") {
        def contactType = ContactType.findByName(contactTypeName)
        if(!!contactType) {
            return [ contacts : Contact.createCriteria().listDistinct {
                createAlias("contactTypes", "c")
                eq("c.name",contactType.name)
            }]
        }
        return [ contacts : Contact.findAll()*.properties ]
    }
    def addNewContact(nameMap,nickname,contactTypeName) {        
        Name name = Name.findWhere(nameMap)
        if(!name) {
            name = nameMap as Name
            if(!name.save(failOnError:true, flush: true, insert: true, validate: true)) {
                name.errors.allErrors.each {
                    println it
                }
                name = null
            } else {
                println "Created new ${GrailsNameUtils.getShortName(name.class)} ${name.first}"                
            }                                                                
        }
        if(!!name) {
            println("First Name is ${name.first}")
            Contact contact = new Contact(name: name,nickname: nickname)
            if(!contact.save(failOnError:true, flush: true, insert: true, validate: true)) {
                contact.errors.allErrors.each {
                    println it
                }
                return [ contact: "error" ]
            } else {
                println "Created new ${GrailsNameUtils.getShortName(contact.class)} ${contact.name.first}"                
            }                           
            def contactType = ContactType.findByName(contactTypeName)
            if(!!contactType) {
                contact.addToContactTypes(contactType)
                if(!contact.save(failOnError:true, flush: true, validate: true)) {
                    contact.errors.allErrors.each {
                        println it
                    }
                    return [ contact: "error" ]
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(contact.class)} ${contact.name.first}"                
                }                           
            }
            return [ contact: contact.properties ]
        }
        return [ contact: "error" ]
    }
    
    def removeContact(id) {
        def contact = Contact.get(id)
        if(!!contact) {
            contact.delete(flush: true)
            return [ status: "success"]
        }
        return [ status: "error" ]
    }
}
