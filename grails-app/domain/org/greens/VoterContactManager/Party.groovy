package org.greens.VoterContactManager

class Party {
    String code = ''
    String name = ''
    String simpleName = ''
    State state
    static constraints = {
        code(unique: ['state','name','simpleName'],blank:false,nullable:false)
        name(unique: ['code','state','simpleName'],blank:true,nullable:false)
        simpleName(unique: ['code','name','state'],blank:true,nullable:false)
        state(unique: ['code','name','simpleName'],blank:false,nullable:false)
    }
}
