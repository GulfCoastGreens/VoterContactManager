package org.greens.VoterContactManager

abstract class Voter {
    VoterKey voterKey
    County county
    Name name
    Address residentAddress
    Address mailingAddress
    String mailingCountry
    VoterStatus voterStatus
    Date birthDate
    Date registrationDate
    Gender gender
    Race race
    String congressionalDistrict
    String senateDistrict
    String houseDistrict
    String countyCommissionDistrict
    static constraints = {
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
    // static belongsTo = [VoterKey]
}
