package org.greens.VoterContactManager

class ElectionType {
    State state
    String code
    String name
    static constraints = {
        code(unique: ['state','name'],blank:false,nullable:false)
        name(unique: ['code','state'],blank:false,nullable:false)
        state(unique: ['code','name'],blank:false,nullable:false)
    }
}
