package org.greens.VoterContactManager

class ContactPhone implements Serializable {
    Phone phone
    PhoneType phoneType
    static constraints = {
        phone(unique: ['phoneType'],blank:false,nullable:false)
        phoneType(unique: ['phone'],blank:false,nullable:false)
    }
    static mapping = {
        id composite: ['phone','phoneType']
    }
}
