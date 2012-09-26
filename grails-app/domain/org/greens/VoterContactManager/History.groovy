package org.greens.VoterContactManager

abstract class History {
    VoterKey voterKey
    County county
    ElectionType electionType
    Date electionDate
    static constraints = {
    }
    // static hasMany = [voterKey: VoterKey]
    // static belongsTo = [VoterKey]    
}
