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
    def getContactsByType(String contactTypeName = "") {
        def contactType = ContactType.findByName(contactTypeName)
        if(!!contactType) {
            return [ contacts : Contact.createCriteria().listDistinct {
                createAlias("contactTypes", "c")
                eq("c.name",contactType.name)
            }]
        }
        return [ contacts : Contacts.findAll() ]
    }
    def addNewContact(nameMap) {        
        Name name = Name.findWhere(nameMap as Name)
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
            Contact contact = new Contact(name: name)
            if(!contact.save(failOnError:true, flush: true, insert: true, validate: true)) {
                contact.errors.allErrors.each {
                    println it
                }
                contact = null
            } else {
                println "Created new ${GrailsNameUtils.getShortName(contact.class)} ${contact.name.first}"                
            }                                                                
            return [ contact: contact ]
        }
        return [ contact: "error" ]
    }
}
