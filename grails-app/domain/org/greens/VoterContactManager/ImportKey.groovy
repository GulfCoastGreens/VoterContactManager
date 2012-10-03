package org.greens.VoterContactManager

class ImportKey {
    State state
    Date snapshotDate
    String status = 'pending'
    
    static constraints = {
        status(inList:['in-progress','pending','completed','error'],blank:false,nullable:false)                
        state(unique: ['snapshotDate'],blank:false,nullable:false)
        snapshotDate(unique: ['state'],blank:false,nullable:false)
    }
}
