package org.greens.VoterContactManager

import org.greens.VoterContactManager.ImportKey
import grails.util.GrailsNameUtils
import org.apache.commons.lang.StringUtils
import java.text.SimpleDateFormat
import org.greens.VoterContactManager.*
import grails.converters.*

class MergeDataService {
    static transactional = true

    def mergeGeorgiaHistoryRecord(String row,ImportKey importKey) {
        { map ->
            def georgiaHistories = GeorgiaHistory.withCriteria(max: 1) {
                def voterKeys = ImportKey.findAllWhere(state: importKey.state)
                'in'('voterKey',VoterKey.withCriteria {
                    eq('voterId',map.voterKey.voterId)
                    'in'('importKey',voterKeys)
                })
                eq('county',map.county)
                eq('oldVoterId',map.oldVoterId)
                eq('electionDate',map.electionDate)
                eq('electionType',map.electionType)
                eq('historyVoteType',map.historyVoteType)
                eq('party',map.party)
                eq('absentee',map.absentee)                
            }
            if(georgiaHistories) {
                println "Found existing ${GrailsNameUtils.getShortName(georgiaHistories[0].class)} ${georgiaHistories[0].voterKey.voterId} "                   
            } else {
                def georgiaHistory = map as GeorgiaHistory
                if(!georgiaHistory.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    georgiaHistory.errors.allErrors.each {
                        println it
                    }             
                    georgiaHistory = null
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(georgiaHistory.class)} ${georgiaHistory.voterKey.voterId} "   
                }                                    
            }
        }.call([
            county: { map ->
                def county = County.findWhere(map)
                if(county) {
                    println "Found existing ${GrailsNameUtils.getShortName(county.class)} ${county.code} - ${county.name}"                       
                } else {
                    county = map as County
                    if(!county.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        county.errors.allErrors.each {
                            println it
                        }             
                        county = null
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(county.class)} ${county.code} - ${county.name}"   
                    }                                        
                }
                return county            
            }.call([ 
                code: row[0..<3].trim(), 
                state: importKey.state 
            ]),
            voterKey: { map ->
                dev voterKey = VoterKey.findWhere(map)
                if(voterKey) {
                    println "Found existing ${GrailsNameUtils.getShortName(voterKey.class)} ${voterKey.voterId}"   
                } else {
                    voterKey = map as VoterKey
                    if(!voterKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        voterKey.errors.allErrors.each {
                            println it
                        }             
                        voterKey = null
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(voterKey.class)} ${voterKey.voterId}"   
                    }                    
                }
                return voterKey            
            }.call([ 
                voterId: row[3..<11].trim(), 
                importKey: importKey 
            ]),
            oldVoterId: row[11..<25].trim(),
            electionDate: (row[25..<33].trim())?new SimpleDateFormat("yyyyMMdd").parse(row[25..<33]):null,
            electionType: { map ->
                def electionType = ElectionType.findWhere(map)
                if(electionType) {
                    println "Found existing ${GrailsNameUtils.getShortName(electionType.class)} ${electionType.code} - ${electionType.name}"   
                } else {
                    electionType = map as ElectionType
                    if(!electionType.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        electionType.errors.allErrors.each {
                            println it
                        }             
                        electionType = null
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(electionType.class)} ${electionType.code} - ${electionType.name}"   
                    }                    
                }
                return electionType            
            }.call([ 
                code: row[33..<36].trim(),
                state: importKey.state 
            ]),
            party: Party.findByCodeAndState(row[36..<37].trim(),importKey.state),
            absentee: row[37..<38].trim()                
        ])
    }

    def mergeFloridaHistoryRecord(String row,ImportKey importKey) {
        // def tokens = StringUtils.chomp(row).tokenize("\t")
        InputStream is = new ByteArrayInputStream(row.getBytes())
        is.toCsvReader('separatorChar':"\t").eachLine { tokens ->
            tokens = tokens.collect { it.trim() }
            if(tokens[1]) {            
                { map ->
                    def floridaHistories = FloridaHistory.withCriteria(max: 1) {
                        def voterKeys = ImportKey.findAllWhere(state: importKey.state)
                        'in'('voterKey',VoterKey.withCriteria {
                            eq('voterId',map.voterKey.voterId)
                            'in'('importKey',voterKeys)
                        })
                        eq('county',map.county)
                        eq('electionDate',map.electionDate)
                        eq('electionType',map.electionType)
                        eq('historyVoteType',map.historyVoteType)
                    }
                    if(floridaHistories) {
                        println "Found existing ${GrailsNameUtils.getShortName(floridaHistories[0].class)} ${floridaHistories[0].electionDate} - ${floridaHistory.electionType.code}"   
                    } else if(map.voterKey) {
                        def floridaHistory = map as FloridaHistory
                        if(!floridaHistory.save(failOnError:true, flush: true, insert: true, validate: true)) {
                            floridaHistory.errors.allErrors.each {
                                println it
                            }             
                            floridaHistory = null
                        } else {
                            println "Created new ${GrailsNameUtils.getShortName(floridaHistory.class)} ${floridaHistory.electionDate} - ${floridaHistory.electionType.code}"   
                        }                                            
                    }
                }.call([
                    voterKey: { map ->
                        def voterKey = VoterKey.findWhere(map)
                        if(voterKey) {
                            println "Found existing ${GrailsNameUtils.getShortName(voterKey.class)} ${voterKey.voterId}"                
                        } else {
                            voterKey = map as VoterKey
                            if(!voterKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                voterKey.errors.allErrors.each {
                                    println it
                                }
                                voterKey = null
                            } else {
                                println "Created new ${GrailsNameUtils.getShortName(voterKey.class)} ${voterKey.voterId}"                
                            }                    
                        }
                        return voterKey
                    }.call([
                        voterId: tokens[1],
                        importKey: importKey
                    ]),
                    county: County.findByCode(tokens[0]),
                    electionDate: new SimpleDateFormat("MM/dd/yyyy").parse(tokens[2]),
                    electionType: ElectionType.findByCode(tokens[3]),
                    historyVoteType: HistoryVoteType.findByCode(tokens[4])                                        
                ])            
            } else {
                println "Empty line, skipping"
            }
        }
        is.close()        
    }
    def mergeGeorgiaVoterRecord(String row,ImportKey importKey) {
        { map ->            
            def georgiaVoter = GeorgiaVoter.findWhere([ voterKey: map.voterKey ])
            if(georgiaVoter) {
                // update
                georgiaVoter.properties = map
                if(!georgiaVoter.save(failOnError:true, flush: true, validate: true)) {
                    georgiaVoter.errors.allErrors.each {
                        println it
                    }             
                    georgiaVoter = null
                } else {
                    println "Updated existing ${GrailsNameUtils.getShortName(georgiaVoter.class)} ${georgiaVoter.voterKey.voterId} "   
                }                    
            } else if(map.voterKey) {
                georgiaVoter = map as GeorgiaVoter
                if(!georgiaVoter.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    georgiaVoter.errors.allErrors.each {
                        println it
                    }             
                    georgiaVoter = null
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(georgiaVoter.class)} ${georgiaVoter.voterKey.voterId} "   
                }                    
            }
            return georgiaVoter                    
        }.call([
            county: { map ->
                def county = County.findWhere(map)
                if(county) {
                    println "Found existing ${GrailsNameUtils.getShortName(county.class)} ${county.code} - ${county.name}"   
                } else {
                    county = map as County
                    if(!county.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        county.errors.allErrors.each {
                            println it
                        }
                        county = null
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(county.class)} ${county.code} - ${county.name}"   
                    }                    
                }
                return county            
            }.call([ 
                code: row[0..<3].trim(), 
                state: importKey.state 
            ]),
            voterKey: { map ->
                def voterKey = VoterKey.findWhere(map)
                if(voterKey) {
                    println "Found ${GrailsNameUtils.getShortName(voterKey.class)} ${voterKey.voterId}"                       
                } else {
                    voterKey = map as VoterKey
                    if(!voterKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        voterKey.errors.allErrors.each {
                            println it
                        }
                        println voterKey
                        voterKey = null
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(voterKey.class)} ${voterKey.voterId}"   
                    }                    
                }
                return voterKey
            }.call([ 
                voterId: row[3..<11].trim(), 
                importKey: importKey 
            ]),
            voterStatus: { map -> 
                def voterStatus = VoterStatus.findWhere(map)
                if(voterStatus) {
                    println "Found existing ${GrailsNameUtils.getShortName(voterStatus.class)} ${voterStatus.code} - ${voterStatus.name}"   
                } else {
                    voterStatus = map as VoterStatus
                    if(!voterStatus.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        voterStatus.errors.allErrors.each {
                            println it
                        }             
                        voterStatus = null
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(voterStatus.class)} ${voterStatus.code} - ${voterStatus.name}"   
                    }                    
                }
                return voterStatus            
            }.call([
                code: row[11..11].trim(),
                state: importKey.state
            ]),
            name: { map ->
                def name = Name.findWhere(map)
                if(name) {
                    println "Found existing ${GrailsNameUtils.getShortName(name.class)} ${name.last}, ${name.first}"   
                } else {
                    name = map as Name
                    if(!name.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        name.errors.allErrors.each {
                            println it
                        }             
                        name = null
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(name.class)} ${name.last}, ${name.first}"   
                    }                    
                }
                return name
            }.call([
                last: row[12..<32].trim(),
                first: row[32..<52].trim(),
                middle: row[52..<72].trim(),
                suffix: row[72..<75].trim(),
                salutation: row[75..<78].trim()
            ]),
            residentAddress: { map ->
                def address = Address.findWhere(map)
                if(address) {
                    println "Found existing ${GrailsNameUtils.getShortName(address.class)} ${address.city} - ${address.city}"   
                } else {
                    address = map as Address
                    if(!address.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        address.errors.allErrors.each {
                            println it
                        }             
                        address = null
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(address.class)} ${address.city} - ${address.city}"   
                    }                    
                }
                return address
            }.call([
                line1: [row[78..<84].trim(),row[84..<114].trim(),row[114..<116].trim()].findAll { (it) }.join(" "),
                line2: row[116..<124].trim(),
                city: row[124..<141].trim(),
                state: importKey.state,
                zip: [row[141..<146].trim(),row[146..<150].trim()].join()
            ]),
            birthDate: new SimpleDateFormat("yyyyMMdd").parse(row[161..<169]),
            registrationDate: new SimpleDateFormat("yyyyMMdd").parse(row[169..<177]),
            race: Race.findByCodeAndState(row[177..177].trim(),importKey.state),
            gender: (row[178..178].trim())?Gender.findByCodeAndState(row[178..178].trim(),importKey.state):Gender.findByCodeAndState('U',importKey.state),
            absentee: row[179..179].trim(),
            landDistrict: row[180..<183].trim(),
            landLot: row[183..<187].trim(),
            oldRegistrationDate: (row[187..<195].trim())?new SimpleDateFormat("yyyyMMdd").parse(row[187..<195]):null,
            oldVoterId: row[195..<209].trim(),
            countyPrecinctId: row[209..<214].trim(),
            cityPrecinctId: row[214..<219].trim(),
            congressionalDistrict: row[219..<222].trim(),
            senateDistrict: row[222..<225].trim(),
            houseDistrict: row[225..<228].trim(),
            judicialDistrict: row[228..<231].trim(),
            countyCommissionDistrict: row[231..<234].trim(),
            schoolDistrict: row[234..<237].trim(),
            countyDistrictAName: row[237..<250].trim(),
            countyDistrictAValue: row[250..<253].trim(),
            countyDistrictBName: row[253..<266].trim(),
            countyDistrictBValue: row[266..<269].trim(),
            municipalName: row[269..<286].trim(),
            municipalCode: row[286..<289].trim(),
            wardCityCouncilName: row[289..<302].trim(),
            wardCityCouncilValue: row[302..<305].trim(),
            citySchoolDistrictName: row[305..<318].trim(),
            citySchoolDistrictValue: row[318..<321].trim(),
            cityDistrictAName: row[321..<334].trim(),
            cityDistrictAValue: row[334..<337].trim(),
            cityDistrictBName: row[337..<350].trim(),
            cityDistrictBValue: row[350..<353].trim(),
            cityDistrictCName: row[353..<366].trim(),
            cityDistrictCValue: row[366..<369].trim(),
            cityDistrictDName: row[369..<382].trim(),
            cityDistrictDValue: row[382..<385].trim(),
            dateLastVoted: (row[385..<393].trim())?new SimpleDateFormat("yyyyMMdd").parse(row[385..<393]):null,
            electionType: { map ->
                def electionType = ElectionType.findWhere(map)
                if(electionType) {
                    println "Found existing ${GrailsNameUtils.getShortName(electionType.class)} ${electionType.code} - ${electionType.name}"   
                } else {
                    electionType = map as ElectionType
                    if(!electionType.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        electionType.errors.allErrors.each {
                            println it
                        }             
                        electionType = null
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(electionType.class)} ${electionType.code} - ${electionType.name}"   
                    }                    
                }
                return electionType            
            }.call([ 
                code: row[393..<396].trim(),
                state: importKey.state 
            ]),
            partyLastVoted: Party.findByCodeAndState(row[396..<397].trim(),importKey.state),
            lastContactDate: (row[397..<405].trim())?new SimpleDateFormat("yyyyMMdd").parse(row[397..<405]):null,
            mailingAddress: { map ->
                def address = Address.findWhere(map)
                if(address) {
                    println "Found existing ${GrailsNameUtils.getShortName(address.class)} ${address.city} - ${address.city}"   
                } else {
                    address = map as Address
                    if(!address.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        address.errors.allErrors.each {
                            println it
                        }             
                        address = null
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(address.class)} ${address.city} - ${address.city}"   
                    }                    
                }
                return address            
            }.call([
                line1: [row[405..<411].trim(),row[411..<441].trim(),row[441..<443].trim(),row[443..<451].trim()].findAll { (it) }.join(" "),
                line2: row[481..<511].trim(),
                line3: row[511..<541].trim(),
                city: row[451..<468].trim(),
                state: State.findByCode(row[468..<470].trim()),
                zip: [row[470..<475].trim(),row[475..<479].trim()].join()
            ]),
            mailingCountry: (row[541..<561].trim())?row[541..<561].trim():"",
            dateAdded: new SimpleDateFormat("yyyyMMdd").parse(row[561..<569]),
            dateChanged: new SimpleDateFormat("yyyyMMdd").parse(row[569..<577]),
            districtCombo: row[577..<580].trim(),
            residenceBuildingDesignation: row[580..<583].trim(),
            mailAddressRuralRouteOrPOB: row[583..<591].trim(),
            combinedStreetAddress: row[591..<651].trim()
        ])
    }
    def mergeFloridaVoterRecord(String row,ImportKey importKey) {
        // def tokens = StringUtils.chomp(row).tokenize("\t")
        InputStream is = new ByteArrayInputStream(row.getBytes())
        is.toCsvReader('separatorChar':"\t").eachLine { tokens ->
            tokens = tokens.collect { it.trim() }
            if(tokens[1]) {            
                { map -> 
                    def floridaVoter = FloridaVoter.findWhere([ voterKey: map.voterKey ])
                    if(floridaVoter) {
                        // update
                        floridaVoter.properties = map
                        if(!floridaVoter.save(failOnError:true, flush: true, validate: true)) {
                            floridaVoter.errors.allErrors.each {
                                println it
                            }
                            floridaVoter = null
                        } else {
                            println "Updated existing ${GrailsNameUtils.getShortName(floridaVoter.class)} ${floridaVoter.voterKey.voterId}"                
                        }                                                                            
                    } else if(map.voterKey) {
                        floridaVoter = map as FloridaVoter
                        if(!floridaVoter.save(failOnError:true, flush: true, insert: true, validate: true)) {
                            floridaVoter.errors.allErrors.each {
                                println it
                            }
                            floridaVoter = null
                        } else {
                            println "Created new ${GrailsNameUtils.getShortName(floridaVoter.class)} ${floridaVoter.voterKey.voterId}"                
                        }                                                    
                    }
                    return floridaVoter
                }.call([
                    county: County.findByCode(tokens[0]),
                    voterKey: { map ->
                        def voterKey = VoterKey.findWhere(map)
                        if(voterKey) {
                            println "Found existing ${GrailsNameUtils.getShortName(voterKey.class)} ${voterKey.voterId}"   
                        } else {
                            voterKey = map as VoterKey
                            if(!voterKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                voterKey.errors.allErrors.each {
                                    println it
                                }
                                voterKey = null
                            } else {
                                println "Created new ${GrailsNameUtils.getShortName(voterKey.class)} ${voterKey.voterId}"                
                            }                                                
                        }
                        return voterKey
                    }.call([
                        voterId: tokens[1],
                        importKey: importKey
                    ]),
                    name: { map ->
                        def name = Name.findWhere(map)
                        if(name) {
                            println "Found existing ${GrailsNameUtils.getShortName(name.class)} ${name.last}, ${name.first}"                
                        } else {
                            name = map as Name
                            if(!name.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                name.errors.allErrors.each {
                                    println it
                                }                
                                name = null
                            } else {
                                println "Created new ${GrailsNameUtils.getShortName(name.class)} ${name.last}, ${name.first}"                
                            }                            
                        }
                        return name
                    }.call([
                        last: tokens[2],
                        suffix: tokens[3],
                        first: tokens[4],
                        middle: tokens[5]
                    ]),
                    suppressAddress: tokens[6],
                    residentAddress: (tokens[6] == 'Y')?null:{ map ->
                        def address = Address.findWhere(map)
                        if(address) {
                            println "Found existing ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"                
                        } else {
                            address = map as Address
                            if(!address.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                address.errors.allErrors.each {
                                    println it
                                }
                                address = null
                            } else {
                                println "Created new ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"                
                            }                            
                        }
                        return address
                    }.call([
                        line1: tokens[7],
                        line2: tokens[8],
                        line3: "",
                        city: tokens[9],
                        state: { String code ->
                            if(code == importKey.state.code) {
                                return importKey.state
                            } else if(code.trim()) {
                                def state = State.findByCode(code.trim())
                                if(state == null) {
                                    state = new State([code: code.trim()])
                                    if(!state.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                        state.errors.allErrors.each {
                                            println it
                                        }
                                        return null
                                    } else {
                                        println "Created new ${GrailsNameUtils.getShortName(state.class)} ${state.name}"                
                                    }
                                    return state
                                }
                            }
                            return null
                        }.call(tokens[10]),
                        zip: tokens[11]                                
                    ]), 
                    mailingAddress: (tokens[6] == 'Y')?null:{ map ->
                        def address = Address.findWhere(map)
                        if(address) {
                            println "Found existing ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"                
                        } else {
                            address = map as Address
                            if(!address.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                address.errors.allErrors.each {
                                    println it
                                }
                                address = null
                            } else {
                                println "Created new ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"                
                            }                            
                        }
                        return address
                    }.call([
                        line1: tokens[12],
                        line2: tokens[13],
                        line3: tokens[14],
                        city: tokens[15],
                        state: { String code ->
                            if(code == importKey.state.code) {
                                return importKey.state
                            } else if(code.trim()) {
                                def state = State.findByCode(code.trim())
                                if(state == null) {
                                    state = new State([code: code.trim()])
                                    if(!state.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                        state.errors.allErrors.each {
                                            println it
                                        }
                                        return null
                                    } else {
                                        println "Created new ${GrailsNameUtils.getShortName(state.class)} ${state.name}"                
                                    }
                                    return state
                                }
                            }
                            return null
                        }.call(tokens[16]),
                        zip: tokens[17]                                
                    ]),
                    mailingCountry: tokens[18],
                    gender: Gender.findWhere(
                        code: tokens[19],
                        state: importKey.state
                    ),
                    race: Race.findWhere(
                        code: tokens[20],
                        state: importKey.state
                    ),
                    birthDate: new SimpleDateFormat("MM/dd/yyyy").parse(tokens[21]),
                    registrationDate: new SimpleDateFormat("MM/dd/yyyy").parse(tokens[22]),
                    party: Party.findWhere(
                        code: tokens[23],
                        state: importKey.state
                    ),
                    precinct: (tokens[24] || tokens[25] || tokens[26] || tokens[27])?{ map -> 
                        def precinct = Precinct.findWhere(map)
                        if(precinct) {
                            println "Found existing ${GrailsNameUtils.getShortName(precinct.class)} ${precinct.precinct}"                
                        } else {
                            precinct = map as Precinct
                            if(!precinct.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                precinct.errors.allErrors.each {
                                    println it
                                }
                                precinct = null
                            } else {
                                println "Created new ${GrailsNameUtils.getShortName(precinct.class)} ${precinct.precinct}"                
                            }                            
                        }
                        return precinct
                    }.call([
                        precinct: tokens[24],
                        precinctGroup: tokens[25],
                        precinctSplit: tokens[26],
                        precinctSuffix: tokens[27],
                        state: importKey.state                            
                    ]):null,
                    voterStatus: VoterStatus.findWhere(
                        code: tokens[28],
                        state: importKey.state
                    ),
                    congressionalDistrict: tokens[29],
                    houseDistrict: tokens[30],
                    senateDistrict: tokens[31],
                    countyCommissionDistrict: tokens[32],
                    schoolBoardDistrict: tokens[33],
                    daytimePhone: (tokens[34] && tokens[35])?{ map -> 
                        def phone = Phone.findWhere(map)
                        if(phone) {
                            println "Found existing ${GrailsNameUtils.getShortName(phone.class)} ${phone.phoneNumber}"                
                        } else {
                            phone = map as Phone
                            if(!phone.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                phone.errors.allErrors.each {
                                    println it
                                }
                                phone = null
                            } else {
                                println "Created new ${GrailsNameUtils.getShortName(phone.class)} ${phone.phoneNumber}"                
                            }                            
                        }
                        return phone
                    }.call([
                        areaCode: tokens[34],
                        number: tokens[35],
                        extension: tokens[36]
                    ]):null                
                ])
            } else {
                println "Empty line, skipping"
            }
        }
        is.close()
    }
}
