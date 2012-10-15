package org.greens.VoterContactManager

class County {
    String code = ''
    String name = ''
    State state
    static constraints = {
        code(unique: ['state'],blank:false,nullable:false)
        name(unique: false,blank:true,nullable:true)        
        state(unique: ['code'],blank:false,nullable:false)
    }
}
