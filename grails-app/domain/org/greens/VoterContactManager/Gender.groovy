package org.greens.VoterContactManager

class Gender {
    String code = ''
    String name = ''
    State state
    static constraints = {
        code(unique: ['state','name'],blank:false,nullable:false)
        name(unique: ['code','state'],blank:false,nullable:false)
        state(unique: ['code','name'],blank:false,nullable:false)
    }
}
