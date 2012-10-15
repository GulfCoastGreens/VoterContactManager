package org.greens.VoterContactManager

class GeorgiaHistory extends History implements Serializable {
    String oldVoterId
    Party party
    String absentee = ''
    static constraints = {
        voterKey(unique:['county','electionDate','electionType'],nullable:false,validator: { vk -> 
            vk.importKey.state.code == 'GA'
        })        
        county(unique:['voterKey','electionDate','electionType'],nullable:false,validator: { c -> 
            c.state.code == 'GA'
        })  
        absentee(blank:true,nullable:true,unique:false)
        oldVoterId(blank:true,nullable:true,unique:false)
    }
}
