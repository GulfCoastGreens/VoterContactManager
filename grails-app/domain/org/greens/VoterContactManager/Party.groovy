package org.greens.VoterContactManager

class Party {
    String code = ''
    String name = ''
    String simpleName = ''
    State state
    static constraints = {
        code(unique: ['state','name'],blank:false,nullable:false)
        name(unique: ['code','state'],blank:false,nullable:false)
        simpleName(unique: true,blank:false,nullable:false)
        state(unique: ['code','name'],blank:false,nullable:false)
    }
}
