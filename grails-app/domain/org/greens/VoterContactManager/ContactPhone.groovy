package org.greens.VoterContactManager

class ContactPhone {
    Phone phone
    PhoneType phoneType
    static constraints = {
        phone(unique: ['phoneType'],blank:false,nullable:false)
        phoneType(unique: ['phone'],blank:false,nullable:false)
    }
}
