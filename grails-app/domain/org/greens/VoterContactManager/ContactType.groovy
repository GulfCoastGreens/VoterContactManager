package org.greens.VoterContactManager

class ContactType {
    String name
    static constraints = {
        name(unique:true,nullable:false,blank:false)
    }
}
