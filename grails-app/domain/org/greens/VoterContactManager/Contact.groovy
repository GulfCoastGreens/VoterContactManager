package org.greens.VoterContactManager

class Contact {
    Name name
    VoterKey matchedVoterKey
    static transients = ['voterRecords','historyRecords','allContactPhones']
    static constraints = {
        matchedVoterKey(nullable:true)
    }
    static hasMany = [
        contactPhones: ContactPhone,
        contactEmails: ContactEmail,
        contactTypes : ContactType
    ]
    Set getAllContactPhones() {
        if(matchedVoterKey) {
            def voterKeys = VoterKey.withCriteria {
                eq('voterId',matchedVoterKey.voterId)
                'in'('importKey',ImportKey.findAllByState(matchedVoterKey.importKey.state))
            }
            def voterPhones = Voter.withCriteria {
                'in'('voterKey',voterKeys)
            }.findAll( it.phone != null ).collect { phone ->
                return new ContactPhone(phone: phone,phoneType: [name: "Voter Data Provided"])
            }
            voterPhones.addAll(contactPhones)
            return voterPhones
        }
        return contactPhones
    }
    Set getHistoryRecords() {
        if(matchedVoterKey) {
            // Find all the voterKeys
            def voterKeys = VoterKey.withCriteria {
                eq('voterId',matchedVoterKey.voterId)
                'in'('importKey',ImportKey.findAllByState(matchedVoterKey.importKey.state))
            }
            return History.withCriteria {
                'in'('voterKey',voterKeys)
            }
        }
        return new HashSet()
    }
    Set getVoterRecords() {
        if(matchedVoterKey) {
            def voterKeys = VoterKey.withCriteria {
                eq('voterId',matchedVoterKey.voterId)
                'in'('importKey',ImportKey.findAllByState(matchedVoterKey.importKey.state))
            }
            return Voter.withCriteria {
                'in'('voterKey',voterKeys)
            }
       } 
       return new HashSet()
    }
}
