package org.greens.VoterContactManager

class EmailType {
    String name
    static constraints = {
        name(unique:true,nullable:false,blank:false)
    }
}
