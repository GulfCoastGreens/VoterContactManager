package org.greens.VoterContactManager

class FloridaVoter extends Voter implements Serializable { 
    String suppressAddress = ''
    Party party
    Precinct precinct
    String schoolBoardDistrict = ''
    Phone daytimePhone
    static constraints = {
        voterKey(unique:['county'],blank:false,nullable:false,validator: { vk -> 
            vk.importKey.state.code == 'FL'
        })        
        county(unique:['voterKey'],blank:false,nullable:false,validator: { c -> 
            c.state.code == 'FL'
        }) 
        suppressAddress(nullable:true,blank:true)
        residentAddress(nullable:true)
        mailingAddress(nullable:true)
        mailingCountry(nullable:true,blank:true)
        gender(nullable:true)
        race(nullable:true)
        party(nullable:true)
        precinct(nullable:true)
        voterStatus(nullable:true)
        congressionalDistrict(nullable:true,blank:false)
        houseDistrict(nullable:true,blank:false)
        senateDistrict(nullable:true,blank:false)
        countyCommissionDistrict(nullable:true,blank:false)
        schoolBoardDistrict(nullable:true,blank:false)
        daytimePhone(nullable:true)
        
        name(nullable:true)
        residentAddress(nullable:true)
        mailingAddress(nullable:true)
        mailingCountry(blank:true,nullable:true)
        voterStatus(nullable:true)
        birthDate(nullable:true)
        registrationDate(nullable:true)
        gender(nullable:true)
        race(nullable:true)
        congressionalDistrict(blank:true,nullable:true)
        senateDistrict(blank:true,nullable:true)
        houseDistrict(blank:true,nullable:true)
        countyCommissionDistrict(blank:true,nullable:true)        
    }
}
