package org.greens.VoterContactManager

class Email {
    String email
    static constraints = {
        email(email:true,blank:false,unique:true)
    }
}
