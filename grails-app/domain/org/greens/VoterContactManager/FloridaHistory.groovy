package org.greens.VoterContactManager

class FloridaHistory extends History implements Serializable {        
    HistoryVoteType historyVoteType
    static constraints = {
        voterKey(unique:['county','electionDate','electionType','historyVoteType'],nullable:false,validator: { vk -> 
            vk.importKey.state.code == 'FL'
        })        
        county(unique:['voterKey','electionDate','electionType','historyVoteType'],nullable:false,validator: { c -> 
            c.state.code == 'FL'
        })   
    }


}
