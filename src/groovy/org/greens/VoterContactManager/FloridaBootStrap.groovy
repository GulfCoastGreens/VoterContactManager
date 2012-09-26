/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.greens.VoterContactManager

import grails.util.GrailsNameUtils
import org.greens.VoterContactManager.*

/**
 *
 * @author jam
 */
class FloridaBootStrap {
    static bootStrap() {
        [
            [code: 'FL',name:'Florida',enabled:true] as State,
        ].each { state ->
            if(!state.save(failOnError:true, flush: true, insert: true, validate: true)) {
                state.errors.allErrors.each {
                    println it
                }                
            } else {
                println "Created new ${GrailsNameUtils.getShortName(state.class)} ${state.name}"                
            }                                        
        }                
        def state = State.findByCode('FL')
        [
            [code:'ALA',name:'Alachua',state:state] as County,
            [code:'BAK',name:'Baker',state:state] as County,
            [code:'BAY',name:'Bay',state:state] as County,
            [code:'BRA',name:'Bradford',state:state] as County,
            [code:'BRE',name:'Brevard',state:state] as County,
            [code:'BRO',name:'Broward',state:state] as County,
            [code:'CAL',name:'Calhoun',state:state] as County,
            [code:'CHA',name:'Charlotte',state:state] as County,
            [code:'CIT',name:'Citrus',state:state] as County,
            [code:'CLA',name:'Clay',state:state] as County,
            [code:'CLL',name:'Collier',state:state] as County,
            [code:'CLM',name:'Columbia',state:state] as County,
            [code:'DAD',name:'Miami-Dade',state:state] as County,
            [code:'DES',name:'Desoto',state:state] as County,
            [code:'DIX',name:'Dixie',state:state] as County,
            [code:'DUV',name:'Duval',state:state] as County,
            [code:'ESC',name:'Escambia',state:state] as County,
            [code:'FLA',name:'Flagler',state:state] as County,
            [code:'FRA',name:'Franklin',state:state] as County,
            [code:'GAD',name:'Gadsden',state:state] as County,
            [code:'GIL',name:'Gilchrist',state:state] as County,
            [code:'GLA',name:'Glades',state:state] as County,
            [code:'GUL',name:'Gulf',state:state] as County,
            [code:'HAM',name:'Hamilton',state:state] as County,
            [code:'HAR',name:'Hardee',state:state] as County,
            [code:'HEN',name:'Hendry',state:state] as County,
            [code:'HER',name:'Hernando',state:state] as County,
            [code:'HIG',name:'Highlands',state:state] as County,
            [code:'HIL',name:'Hillsborough',state:state] as County,
            [code:'HOL',name:'Holmes',state:state] as County,
            [code:'IND',name:'Indian River',state:state] as County,
            [code:'JAC',name:'Jackson',state:state] as County,
            [code:'JEF',name:'Jefferson',state:state] as County,
            [code:'LAF',name:'Lafayette',state:state] as County,
            [code:'LAK',name:'Lake',state:state] as County,
            [code:'LEE',name:'Lee',state:state] as County,
            [code:'LEO',name:'Leon',state:state] as County,
            [code:'LEV',name:'Levy',state:state] as County,
            [code:'LIB',name:'Liberty',state:state] as County,
            [code:'MAD',name:'Madison',state:state] as County,
            [code:'MAN',name:'Manatee',state:state] as County,
            [code:'MON',name:'Monroe',state:state] as County,
            [code:'MRN',name:'Marion',state:state] as County,
            [code:'MRT',name:'Martin',state:state] as County,
            [code:'NAS',name:'Nassau',state:state] as County,
            [code:'OKA',name:'Okaloosa',state:state] as County,
            [code:'OKE',name:'Okeechobee',state:state] as County,
            [code:'ORA',name:'Orange',state:state] as County,
            [code:'OSC',name:'Osceola',state:state] as County,
            [code:'PAL',name:'Palm Beach',state:state] as County,
            [code:'PAS',name:'Pasco',state:state] as County,
            [code:'PIN',name:'Pinellas',state:state] as County,
            [code:'POL',name:'Polk',state:state] as County,
            [code:'PUT',name:'Putnam',state:state] as County,
            [code:'SAN',name:'Santa Rosa',state:state] as County,
            [code:'SAR',name:'Sarasota',state:state] as County,
            [code:'SEM',name:'Seminole',state:state] as County,
            [code:'STJ',name:'St. Johns',state:state] as County,
            [code:'STL',name:'St. Lucie',state:state] as County,
            [code:'SUM',name:'Sumter',state:state] as County,
            [code:'SUW',name:'Suwannee',state:state] as County,
            [code:'TAY',name:'Taylor',state:state] as County,
            [code:'UNI',name:'Union',state:state] as County,
            [code:'VOL',name:'Volusia',state:state] as County,
            [code:'WAK',name:'Wakulla',state:state] as County,
            [code:'WAL',name:'Walton',state:state] as County,
            [code:'WAS',name:'Washington',state:state] as County,
        ].each { county ->
            if(!county.save(failOnError:true, flush: true, insert: true, validate: true)) {
                county.errors.allErrors.each {
                    println it
                }                
            } else {
                println "Created new ${GrailsNameUtils.getShortName(county.class)} ${county.name}"                
            }                                                            
        }
        [
            [districtCourtId:'1',county:County.findByCode('ALA'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('BAK'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('BAY'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('BRA'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('BRE'),state:state] as DistrictCourt,
            [districtCourtId:'4',county:County.findByCode('BRO'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('CAL'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('CHA'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('CIT'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('CLA'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('CLL'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('CLM'),state:state] as DistrictCourt,
            [districtCourtId:'3',county:County.findByCode('DAD'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('DES'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('DIX'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('DUV'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('ESC'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('FLA'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('FRA'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('GAD'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('GIL'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('GLA'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('GUL'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('HAM'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('HAR'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('HEN'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('HER'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('HIG'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('HIL'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('HOL'),state:state] as DistrictCourt,
            [districtCourtId:'4',county:County.findByCode('IND'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('JAC'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('JEF'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('LAF'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('LAK'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('LEE'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('LEO'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('LEV'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('LIB'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('MAD'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('MAN'),state:state] as DistrictCourt,
            [districtCourtId:'3',county:County.findByCode('MON'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('MRN'),state:state] as DistrictCourt,
            [districtCourtId:'4',county:County.findByCode('MRT'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('NAS'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('OKA'),state:state] as DistrictCourt,
            [districtCourtId:'4',county:County.findByCode('OKE'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('ORA'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('OSC'),state:state] as DistrictCourt,
            [districtCourtId:'4',county:County.findByCode('PAL'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('PAS'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('PIN'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('POL'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('PUT'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('SAN'),state:state] as DistrictCourt,
            [districtCourtId:'2',county:County.findByCode('SAR'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('SEM'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('STJ'),state:state] as DistrictCourt,
            [districtCourtId:'4',county:County.findByCode('STL'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('SUM'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('SUW'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('TAY'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('UNI'),state:state] as DistrictCourt,
            [districtCourtId:'5',county:County.findByCode('VOL'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('WAK'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('WAL'),state:state] as DistrictCourt,
            [districtCourtId:'1',county:County.findByCode('WAS'),state:state] as DistrictCourt,
        ].each { districtCourt ->
            if(!districtCourt.save(failOnError:true, flush: true, insert: true, validate: true)) {
                districtCourt.errors.allErrors.each {
                    println it
                }                
            } else {
                println "Created new ${GrailsNameUtils.getShortName(districtCourt.class)} ${districtCourt.county.name}"                
            }                                                            
        }
        [
            [code:'F',name:'Female',state:state] as Gender,
            [code:'M',name:'Male',state:state] as Gender,
            [code:'U',name:'Unknown',state:state] as Gender                    
        ].each { gender ->
            if(!gender.save(failOnError:true, flush: true, insert: true, validate: true)) {
                gender.errors.allErrors.each {
                    println it
                }                
            } else {
                println "Created new ${GrailsNameUtils.getShortName(gender.class)} ${gender.name}"                
            }                                                            
        }
        [
            [code:'A',name:'Voted Absentee',state:state] as HistoryVoteType,
            [code:'B',name:'Absentee Ballot NOT Counted',state:state] as HistoryVoteType,
            [code:'E',name:'Voted Early',state:state] as HistoryVoteType,
            [code:'F',name:'Provisional Ballot – Early Vote',state:state] as HistoryVoteType,
            [code:'N',name:'Did Not Vote',state:state] as HistoryVoteType,
            [code:'P',name:'Provisional Ballot Not Counted',state:state] as HistoryVoteType,
            [code:'Y',name:'Voted at Polls',state:state] as HistoryVoteType,
            [code:'Z',name:'Provisional Ballot – Vote at Poll',state:state] as HistoryVoteType                    
        ].each { historyVoteType ->
            if(!historyVoteType.save(failOnError:true, flush: true, insert: true, validate: true)) {
                historyVoteType.errors.allErrors.each {
                    println it
                }                
            } else {
                println "Created new ${GrailsNameUtils.getShortName(historyVoteType.class)} ${historyVoteType.name}"                
            }                                                            
        }
        [
            [code: 'PPP',name: 'PPP',state:state] as ElectionType,
            [code: 'PRI',name: 'Primary',state:state] as ElectionType,
            [code: 'RUN',name: 'Runoff',state:state] as ElectionType,
            [code: 'GEN',name: 'General',state:state] as ElectionType,
            [code: 'OTH',name: 'Other category covers election that are special, local, municipal, etc.',state:state] as ElectionType
        ].each { electionType ->
            if(!electionType.save(failOnError:true, flush: true, insert: true, validate: true)) {
                electionType.errors.allErrors.each {
                    println it
                }                
            } else {
                println "Created new ${GrailsNameUtils.getShortName(electionType.class)} ${electionType.name}"                
            }                                                            
        }
        [
            [code:'AEL',name:'Americans Elect Florida',simpleName:'Americans Elect',state:state] as Party,
            [code:'AIP',name:'American\'s Party of Florida ',simpleName:'Americans',state:state] as Party,
            [code:'CPF',name:'Constitution Party of Florida ',simpleName:'Constitution',state:state] as Party,
            [code:'DEM',name:'Florida Democratic Party',simpleName:'Democratic',state:state] as Party,
            [code:'ECO',name:'Ecology Party of Florida ',simpleName:'Ecology',state:state] as Party,
            [code:'FPP',name:'Florida Pirate Party',simpleName:'Pirate',state:state] as Party,
            [code:'FWP',name:'Florida Whig Party ',simpleName:'Whig',state:state] as Party,
            [code:'GRE',name:'Green Party of Florida',simpleName:'Green',state:state] as Party,
            [code:'IDP',name:'Independence Party of Florida ',simpleName:'Independence',state:state] as Party,
            [code:'INT',name:'Independent Party of Florida ',simpleName:'Independent',state:state] as Party,
            [code:'LIB',name:'Libertarian Party of Florida',simpleName:'Libertarian',state:state] as Party,
            [code:'OBJ',name:'Objectivist Party of Florida',simpleName:'Objectivist',state:state] as Party,
            [code:'PSL',name:'Party for Socialism and Liberation-Florida',simpleName:'Socialism and Liberation',state:state] as Party,
            [code:'REF',name:'Reform Party',simpleName:'Reform',state:state] as Party,
            [code:'REP',name:'Republican Party of Florida',simpleName:'Republican',state:state] as Party,
            [code:'TPF',name:'Tea Party of Florida',simpleName:'Tea',state:state] as Party                    
        ].each { party ->
            if(!party.save(failOnError:true, flush: true, insert: true, validate: true)) {
                party.errors.allErrors.each {
                    println it
                }                
            } else {
                println "Created new ${GrailsNameUtils.getShortName(party.class)} ${party.name}"                
            }                                                            
        }
        [
            [code:'1',name:'American Indian or Alaskan Native',state:state] as Race,
            [code:'2',name:'Asian Or Pacific Islander',state:state] as Race,
            [code:'3',name:'Black, Not Hispanic',state:state] as Race,
            [code:'4',name:'Hispanic',state:state] as Race,
            [code:'5',name:'White, Not Hispanic',state:state] as Race,
            [code:'6',name:'Other',state:state] as Race,
            [code:'7',name:'Multi-racial',state:state] as Race,
            [code:'9',name:'Unknown',state:state] as Race                    
        ].each { race ->
            if(!race.save(failOnError:true, flush: true, insert: true, validate: true)) {
                race.errors.allErrors.each {
                    println it
                }                
            } else {
                println "Created new ${GrailsNameUtils.getShortName(race.class)} ${race.name}"                
            }                                                            
        }
        [
            [code:'ACT',name:'Active',state:state] as VoterStatus,
            [code:'INA',name:'Inactive',state:state] as VoterStatus,
            [code:'PRE',name:'Preregister Minors (17 Years Of Age)',state:state] as VoterStatus                    
        ].each { voterStatus ->
            if(!voterStatus.save(failOnError:true, flush: true, insert: true, validate: true)) {
                voterStatus.errors.allErrors.each {
                    println it
                }                
            } else {
                println "Created new ${GrailsNameUtils.getShortName(voterStatus.class)} ${voterStatus.name}"                
            }                                                            
        }
        
    }
}

