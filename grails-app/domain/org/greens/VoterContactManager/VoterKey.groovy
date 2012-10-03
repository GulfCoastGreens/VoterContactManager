package org.greens.VoterContactManager

class VoterKey implements Serializable {
    String voterId
    ImportKey importKey
    
    static constraints = {
        voterId(unique: ['importKey'],blank:false,nullable:false)
        importKey(unique: ['voterId'],blank:false,nullable:false)
    }
    static mapping = {
        id composite: ['voterId', 'importKey']
    }
    
}
