package org.greens.VoterContactManager

class Contact {
    Name name
    VoterKey matchedVoterKey
    static transients = ['voterRecords','historyRecords']
    static constraints = {
        matchedVoterKey(nullable:true)
    }
    static hasMany = [phoneNumbers: Phone]
    Set getHistoryRecords() {
        if(matchedVoterKey) {
            switch(matchedVoterKey.importKey.state.code) {
                case "FL":
                    return FloridaHistory.withCriteria {
                        and {
                            eq("voterId",matchedVoterKey.voterId)
                            eq("importKey.state.code","FL")
                        }
                    }
                    break
                default:
                    break
           }            
        }
        return new HashSet()
    }
    Set getVoterRecords() {
        if(matchedVoterKey) {
            switch(matchedVoterKey.importKey.state.code) {
                case "FL":
                    return FloridaVoter.withCriteria {
                        and {
                            eq("voterId",matchedVoterKey.voterId)
                            eq("importKey.state.code","FL")
                        }
                    }
                    break
                default:
                    break
           }
       } 
       return new HashSet()
    }
}
