package org.greens.VoterContactManager

class State {
    String code
    String name = ""
    Boolean enabled = false
    static constraints = {
        code(unique: true,blank:false,nullable:false)
        name(unique: false,blank:true,nullable:false)
    }
}
