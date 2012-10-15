package org.greens.VoterContactManager

class ContactEmail {
    Email email
    EmailType emailType
    static constraints = {
        email(unique: ['emailType'],blank:false,nullable:false)
        emailType(unique: ['email'],blank:false,nullable:false)
    }
}
