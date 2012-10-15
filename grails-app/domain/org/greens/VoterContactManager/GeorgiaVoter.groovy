package org.greens.VoterContactManager

class GeorgiaVoter extends Voter implements Serializable {
    String absentee = ''
    String landDistrict = ''
    String landLot = ''
    Date oldRegistrationDate
    String oldVoterId = ''
    String countyPrecinctId = ''
    String cityPrecinctId = ''
    String judicialDistrict = ''
    String schoolDistrict = ''
    String countyDistrictAName = ''
    String countyDistrictAValue = ''
    String countyDistrictBName = ''
    String countyDistrictBValue = ''
    String municipalName = ''
    String municipalCode = ''
    String wardCityCouncilName = ''
    String wardCityCouncilValue = ''
    String citySchoolDistrictName = ''
    String citySchoolDistrictValue = ''
    String cityDistrictAName = ''
    String cityDistrictAValue = ''
    String cityDistrictBName = ''
    String cityDistrictBValue = ''
    String cityDistrictCName = ''
    String cityDistrictCValue = ''
    String cityDistrictDName = ''
    String cityDistrictDValue = ''
    Date dateLastVoted
    ElectionType electionType
    Party partyLastVoted
    Date lastContactDate
    Date dateAdded
    Date dateChanged
    String districtCombo = ''
    String residenceBuildingDesignation = ''
    String mailAddressRuralRouteOrPOB = ''
    String combinedStreetAddress = ''
    static constraints = {
        voterKey(unique:['county'],blank:false,nullable:false,validator: { vk -> 
            vk.importKey.state.code == 'GA'
        })        
        county(unique:['voterKey'],blank:false,nullable:false,validator: { c -> 
            c.state.code == 'GA'
        }) 
        absentee(blank:true,nullable:true)
        landDistrict(blank:true,nullable:true)
        landLot(blank:true,nullable:true)
        oldRegistrationDate(nullable:true)
        oldVoterId(blank:true,nullable:true)
        countyPrecinctId(blank:true,nullable:true)
        cityPrecinctId(blank:true,nullable:true)
        judicialDistrict(blank:true,nullable:true)
        schoolDistrict(blank:true,nullable:true)
        countyDistrictAName(blank:true,nullable:true)
        countyDistrictAValue(blank:true,nullable:true)
        countyDistrictBName(blank:true,nullable:true)
        countyDistrictBValue(blank:true,nullable:true)
        municipalName(blank:true,nullable:true)
        municipalCode(blank:true,nullable:true)
        wardCityCouncilName(blank:true,nullable:true)
        wardCityCouncilValue(blank:true,nullable:true)
        citySchoolDistrictName(blank:true,nullable:true)
        citySchoolDistrictValue(blank:true,nullable:true)
        cityDistrictAName(blank:true,nullable:true)
        cityDistrictAValue(blank:true,nullable:true)
        cityDistrictBName(blank:true,nullable:true)
        cityDistrictBValue(blank:true,nullable:true)
        cityDistrictCName(blank:true,nullable:true)
        cityDistrictCValue(blank:true,nullable:true)
        cityDistrictDName(blank:true,nullable:true)
        cityDistrictDValue(blank:true,nullable:true)
        dateLastVoted(nullable:true)
        electionType(nullable:true)
        partyLastVoted(nullable:true)
        lastContactDate(nullable:true)
        dateAdded(nullable:true)
        dateChanged(nullable:true)
        districtCombo(blank:true,nullable:true)
        residenceBuildingDesignation(blank:true,nullable:true)
        mailAddressRuralRouteOrPOB(blank:true,nullable:true)
        combinedStreetAddress(blank:true,nullable:true)
        
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
