package org.greens.VoterContactManager

import grails.gorm.*
import java.text.SimpleDateFormat

class SearchService {

    def search(search) {
        def state = State.findByCode(search.state.code)
        def vcriteria
        def vcriteriaNV
        def hcriteria
        if(!!state) {
            switch(state.code) {
                case 'FL':
                    vcriteria = new DetachedCriteria(FloridaVoter)
                    vcriteriaNV = new DetachedCriteria(FloridaVoter)
                    hcriteria = new DetachedCriteria(FloridaHistory)
                    break
                case 'GA':
                    vcriteria = new DetachedCriteria(GeorgiaVoter)
                    vcriteriaNV = new DetachedCriteria(GeorgiaVoter)
                    hcriteria = new DetachedCriteria(GeorgiaHistory)
                    break
            }
        }
        if(!!vcriteria) {
            def names = {
                if(!!search.firstName || !!search.middleName || !!search.lastName) {
                    return Name.where {
                        or {
                            if(!!search.firstName) ilike('first',"%${search.firstName}%")
                            if(!!search.middleName) ilike('middle',"%${search.middleName}%")
                            if(!!search.lastName) ilike('last',"%${search.lastName}%")
                        }
                    }
                }
                return null
            }.call()
            def mailingAddresses = {
                if(!!search.line1 || !!search.city || !!search.zip) {
                    return Address.where {
                        and {
                            if(!!search.address) {
                                or {
                                    ilike('line1',"%${search.address}%")
                                    ilike('line2',"%${search.address}%")
                                    ilike('line3',"%${search.address}%")
                                }
                            }
                            if(!!search.city) ilike('city',"%${search.city}%")
                            if(!!search.zip) ilike('zip',"%${search.zip}%")
                        }
                    }
                }
                return null
            }.call()
            def county = (!!search.county)?County.findByCodeAndState(search.county.code,state):null
            def gender = (!!search.gender)?Gender.findByCodeAndState(search.gender.code,state):null
            def race = (!!search.race)?Race.findByCodeAndState(search.race.code,state):null
            def party = (!!search.party)?Party.findByCodeAndState(search.party.code,state):null
            def precincts = {
                if(!!search.precinct) {
                    if(!!search.precinct.precinct || !!search.precinct.precinctGroup || !!search.precinct.precinctSplit || !!search.precinct.precinctSuffix) {
                        return Precinct.where {
                            if(!!search.precinct.precinct) eq('precinct',search.precinct.precinct) 
                            if(!!search.precinct.precinctGroup) eq('precinctGroup',search.precinct.precinctGroup)
                            if(!!search.precinct.precinctSplit) eq('precinctSplit',search.precinct.precinctSplit)
                            if(!!search.precinct.precinctSuffix) eq('precinctSuffix',search.precinct.precinctSuffix)
                            eq('state',state)
                        }
                    }
                }
                return null
            }.call()
            def electionType = (!!search.electionType)?ElectionType.findByCodeAndState(search.electionType,state):null
            def historyVoteType = (!!search.historyVoteType)?HistoryVoteType.findByCodeAndState(search.historyVoteType,state):null
            
            return vcriteria.list(max: 2500) {
                if(!!search.bornBefore || !!search.bornAfter) {
                    if(!!search.bornBefore && !!search.bornAfter) {
                        between('birthDate',new SimpleDateFormat("yyyy-MM-dd").parse(search.bornAfter),new SimpleDateFormat("yyyy-MM-dd").parse(search.bornBefore))
                    } else if(!!search.bornBefore) {
                        le('birthDate',new SimpleDateFormat("yyyy-MM-dd").parse(search.bornBefore))
                    } else if(!!search.bornAfter) {
                        ge('birthDate',new SimpleDateFormat("yyyy-MM-dd").parse(search.bornAfter))
                    }                    
                }
                if(!!county) {
                    eq('county',county)
                }
                if(!!names) {
                    'in'('name',names)
                }
                if(!!search.congressionalDistrict) {
                    eq('congressionalDistrict',search.congressionalDistrict)
                }
                if(!!search.senateDistrict) {
                    eq('senateDistrict',search.senateDistrict)
                }                
                if(!!gender) {
                    eq('gender',gender)
                }
                if(!!search.houseDistrict) {
                    eq('houseDistrict',search.houseDistrict)
                }
                if(!!mailingAddresses) {
                    or {
                        'in'('mailingAddress',mailingAddresses)
                        'in'('residentAddress',mailingAddresses)
                    }                    
                }
                if(!!race) {
                    eq('race',race)
                }
                if(!!party) {
                    if(state.code in ['FL']) eq('party',party)
                    if(state.code in ['GA']) eq('partyLastVoted',party)
                }
                if(!!precincts) {
                    'in'('precinct',precincts)
                }
                if(!!search.schoolBoardDistrict) {
                    eq('schoolBoardDistrict',search.schoolBoardDistrict)
                }
                if(!!search.cityPrecinct) {
                    eq('cityPrecinctId',search.cityPrecinct)
                }
                if(!!search.countyPrecinct) {
                    eq('countyPrecinctId',search.countyPrecinct)
                }
                if(!!search.judicialDistrict) {
                    eq('judicialDistrict',search.judicialDistrict)
                }
                if(!!search.landDistrict) {
                    eq('landDistrict',search.landDistrict)
                }
                if(!!search.schoolDistrict) {
                    eq('schoolDistrict',search.schoolDistrict)
                }
                if(!!search.importDate) {
                    'in'('voterKey',VoterKey.findAllByImportKey(ImportKey.findBySnapshotDateAndState(new SimpleDateFormat("yyyy-MM-dd").parse(search.importDate),state)))
                }
                if(!!search.useNewlyRegistered || !!search.months || !!search.party) {
                    def importKeys = ImportKey.where {
                        if(!!search.importDate) le('snapshotDate',new SimpleDateFormat("yyyy-MM-dd").parse(importDate))
                        maxResults(search.months)
                        order('snapshotDate','desc')
                    }
                    def importKey = (importKeys.count())?importKeys[0]:null
                    if(!!importKey) {
                        def voterKeysReq = vcriteriaNV.list() {
                            'in'('voterKey',VoterKey.where { eq('importKey',importKey) })
                            if(state.code in ['FL']) eq('party',party)
                            if(state.code in ['GA']) eq('partyLastVoted',party)
                        }.collect { v ->
                            return v.voterKey
                        }
                        def voterKeysMatch = vcriteriaNV.list() {
                            'in'('voterKey',VoterKey.where { 'in'('importKey',importKeys) })
                            if(state.code in ['FL']) ne('party',party)
                            if(state.code in ['GA']) ne('partyLastVoted',party)
                        }.collect { v ->
                            return v.voterKey
                        }
                        'in'('voterKey',voterKeysReq)
                        'in'('voterKey',voterKeysMatch)                        
                    }
                }
                if(!!search.electionType || !!search.votedBefore || !!search.votedAfter || !!search.minVoteCount || !!search.partyVoted) {
                    def voterKeys = hcriteria.list() {
                        if(!!search.historyVoteType) eq('electionType',historyVoteType)
                        if(!!search.electionType) eq('electionType',electionType)
                        if(!!search.votedBefore || !!search.votedAfter) {
                            if(!!search.votedBefore && !!search.votedAfter) {
                                between('electionDate',new SimpleDateFormat("yyyy-MM-dd").parse(search.votedAfter),new SimpleDateFormat("yyyy-MM-dd").parse(search.votedBefore))
                            } else if(!!search.votedBefore) {
                                le('electionDate',new SimpleDateFormat("yyyy-MM-dd").parse(search.votedBefore))
                            } else if(!!search.votedAfter) {
                                ge('electionDate',new SimpleDateFormat("yyyy-MM-dd").parse(search.votedAfter))
                            }
                        }
                        if(!!search.minVoteCount) sizeGe("electionDate", search.minVoteCount)
                        if(!!search.partyVoted) {
                            def partyVoted = Party.findByCode(search.partyVoted)
                            if(!!partyVoted) eq('partyVoted',partyVoted)
                        }
                    }.collect { h ->
                        return h.voterKey
                    }
                    'in'('voterKey',voterKeys)
                }
                
            }.collect { v ->
                switch(state.code) {
                    case 'FL':
                        return [
                            v.voterKey.voterId,
                            v.county.code,
                            v.name.last,
                            v.name.suffix,
                            v.name.first,
                            v.name.middle,
                            v.suppressAddress,
                            (!!v.residentAddress)?v.residentAddress.line1:'',
                            (!!v.residentAddress)?v.residentAddress.line2:'',
                            (!!v.residentAddress)?v.residentAddress.city:'',
                            (!!v.residentAddress)?v.residentAddress.state.code:'',
                            (!!v.residentAddress)?v.residentAddress.zip:'',
                            (!!v.mailingAddress)?v.mailingAddress.line1:'',
                            (!!v.mailingAddress)?v.mailingAddress.line2:'',
                            (!!v.mailingAddress)?v.mailingAddress.line3:'',
                            (!!v.mailingAddress)?v.mailingAddress.city:'',
                            (!!v.mailingAddress)?v.mailingAddress.state.code:'',
                            (!!v.mailingAddress)?v.mailingAddress.zip:'',
                            v.mailingCountry,
                            v.gender.code,
                            v.race.code,
                            (!!v.birthDate)?v.birthDate:'',
                            (!!v.registrationDate)?v.registrationDate:'',
                            v.party.code,
                            (!!v.precinct)?v.precinct.precinct:'',
                            (!!v.precinct)?v.precinct.precinctGroup:'',
                            (!!v.precinct)?v.precinct.precinctSplit:'',
                            (!!v.precinct)?v.precinct.precinctSuffix:'',
                            v.voterStatus.code,
                            v.congressionalDistrict,
                            v.houseDistrict,
                            v.senateDistrict,
                            v.countyCommissionDistrict,
                            v.schoolBoardDistrict,
                            (!!v.phone)?v.phone.areaCode:v.phone.areaCode,
                            (!!v.phone)?v.phone.number:v.phone.number,
                            (!!v.phone)?v.phone.extension:v.phone.extension,
                            v.voterKey.importKey.snapshotDate
                        ]
                        break
                    case 'GA':
                        return [
                            v.voterKey.voterId,
                            v.county.code,
                            v.voterStatus.code,
                            v.name.last,
                            v.name.first,
                            v.name.middle,
                            v.name.suffix,
                            v.name.salutation,
                            (!!v.residentAddress)?v.residentAddress.line1:'',
                            (!!v.residentAddress)?v.residentAddress.line2:'',
                            (!!v.residentAddress)?v.residentAddress.city:'',
                            (!!v.residentAddress)?v.residentAddress.state.code:'',
                            (!!v.residentAddress)?v.residentAddress.zip:'',
                            (!!v.birthDate)?v.birthDate:'',
                            (!!v.registrationDate)?v.registrationDate:'',
                            v.race.code,
                            v.gender.code,
                            v.absentee,
                            v.landDistrict,
                            v.landLot,
                            (!!v.oldRegistrationDate)?v.oldRegistrationDate:'',
                            v.oldVoterId,
                            v.countyPrecinctId,
                            v.cityPrecinctId,
                            v.congressionalDistrict,
                            v.senateDistrict,
                            v.houseDistrict,
                            v.judicialDistrict,
                            v.countyCommissionDistrict,
                            v.schoolBoardDistrict,
                            v.countyDistrictAName,
                            v.countyDistrictAValue,
                            v.countyDistrictBName,
                            v.countyDistrictBValue,
                            v.municipalName,
                            v.municipalCode,
                            v.wardCityCouncilName,
                            v.wardCityCouncilValue,
                            v.citySchoolDistrictName,
                            v.citySchoolDistrictValue,
                            v.cityDistrictAName,
                            v.cityDistrictAValue,
                            v.cityDistrictBName,
                            v.cityDistrictBValue,
                            v.cityDistrictCName,
                            v.cityDistrictCValue,
                            v.cityDistrictDName,
                            v.cityDistrictDValue,
                            (!!v.dateLastVoted)?v.dateLastVoted:'',
                            (!!v.electionType)?v.electionType.code:'',
                            (!!v.partyLastVoted)?v.partyLastVoted.code:'',
                            (!!v.lastContactDate)?v.lastContactDate:'',
                            (!!v.mailingAddress)?v.mailingAddress.line1:'',
                            (!!v.mailingAddress)?v.mailingAddress.line2:'',
                            (!!v.mailingAddress)?v.mailingAddress.line3:'',
                            (!!v.mailingAddress)?v.mailingAddress.city:'',
                            (!!v.mailingAddress)?v.mailingAddress.state.code:'',
                            (!!v.mailingAddress)?v.mailingAddress.zip:'',
                            v.mailingCountry,
                            (!!v.dateAdded)?v.dateAdded:'',
                            (!!v.dateChanged)?v.dateChanged:'',
                            v.districtCombo,
                            v.residenceBuildingDesignation,
                            v.mailAddressRuralRouteOrPOB,
                            v.combinedStreetAddress,
                            v.voterKey.importKey.snapshotDate
                        ]
                        break
                }
            }
        }
    }
}
