package org.greens.VoterContactManager

class DistrictCourt {
    int districtCourtId
    State state
    County county
    
    static constraints = {
        districtCourtId(unique: ['state','county'],blank:false,nullable:false)
        state(unique: ['districtCourtId','county'],blank:false,nullable:false)
        county(unique: ['districtCourtId','state'],blank:false,nullable:false)
    }
}
