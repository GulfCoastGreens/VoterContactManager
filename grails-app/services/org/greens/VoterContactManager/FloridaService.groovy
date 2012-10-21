package org.greens.VoterContactManager

import java.util.zip.ZipInputStream
import java.util.zip.ZipEntry
import java.text.SimpleDateFormat
import grails.util.GrailsNameUtils

class FloridaService {
    static transactional = true
    def dataMap

    static processVoters(ZipInputStream zin) {
        def importKey
        FloridaService floridaService = new FloridaService()
        // wrap the ZipInputStream with an InputStreamReader 
        InputStreamReader isr = new InputStreamReader(zin);
        BufferedReader br = new BufferedReader(isr);        
        ZipEntry ze = null
        String line
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        while ((ze = zin.getNextEntry()) != null) {
            System.out.println("Unzipping " + ze.getName())
            if(ze.getName().size() < 24) {
                continue
            }
            if(!!!importKey) {
                Date snapshotDate = new SimpleDateFormat("yyyyMMdd").parse(ze.getName()[17..24])
                importKey = ImportKey.findBySnapshotDateAndState(snapshotDate,State.findByCode('FL'))
                if(importKey == null) {
                    importKey = new ImportKey([state: State.findByCode('FL'),snapshotDate: snapshotDate])
                    if(!importKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        importKey.errors.allErrors.each {
                            println it
                        }                
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(importKey.class)} ${importKey.state.name}"                
                    }                    
                }
            }
            System.out.println("Date " + importKey.snapshotDate)
            while ((line = br.readLine()) != null) {
                floridaService.mergeVoterRecord(line,importKey)
            }            
        }
        br.close()
        zin.close()        
    }
    static processHistories(ZipInputStream zin) {
        def importKey
        FloridaService floridaService = new FloridaService()
        // wrap the ZipInputStream with an InputStreamReader 
        InputStreamReader isr = new InputStreamReader(zin);
        BufferedReader br = new BufferedReader(isr);        
        ZipEntry ze = null
        String line
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        while ((ze = zin.getNextEntry()) != null) {
            System.out.println("Unzipping " + ze.getName())
            if(ze.getName().size() < 26) {
                continue
            }
            if(!!!importKey) {
                Date snapshotDate = new SimpleDateFormat("yyyyMMdd").parse(ze.getName()[19..26])
                importKey = ImportKey.findBySnapshotDateAndState(snapshotDate,State.findByCode('FL'))
                if(importKey == null) {
                    importKey = new ImportKey([state: State.findByCode('FL'),snapshotDate: snapshotDate])
                    if(!importKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        importKey.errors.allErrors.each {
                            println it
                        }                
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(importKey.class)} ${importKey.state.name}"                
                    }                    
                }
            }
            System.out.println("Date " + importKey.snapshotDate)
            while ((line = br.readLine()) != null) {
                floridaService.mergeHistoryRecord(line,importKey)
            }            
        }
        br.close()
        zin.close()        
    }
    
    def mergeVoterRecord(String row,ImportKey importKey) {
        InputStream is = new ByteArrayInputStream(row.getBytes())
        is.toCsvReader('separatorChar':"\t").eachLine { tokens ->
            tokens = tokens.collect { it.trim() }
            if(tokens[1]) {            
                { map -> 
                    def floridaVoter = FloridaVoter.findWhere([ voterKey: map.voterKey ])
                    if(!!floridaVoter) {
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
                    } else if(!!map.voterKey) {
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
                        if(!!voterKey) {
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
                        if(!!name) {
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
                        middle: tokens[5],
                        prefix: "",
                        salutation: ""
                    ]),
                    suppressAddress: tokens[6],
                    residentAddress: (tokens[6].toBoolean())?null:{ map ->
                        def address = Address.findWhere(map)
                        if(!!address) {
                            println "Found existing address ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"                
                        } else {
                            address = map as Address
                            if(!address.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                address.errors.allErrors.each {
                                    println it
                                }
                                address = null
                            } else {
                                println "Created new address ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"                
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
                            } else if(!!code.trim()) {
                                def state = State.findByCode(code.trim())
                                if(!!state) {
                                    println "Found existing ${GrailsNameUtils.getShortName(state.class)} ${state.name}"  
                                    return state
                                } else {
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
                            return importKey.state
                        }.call(tokens[10]),
                        zip: tokens[11]                                
                    ]), 
                    mailingAddress: (tokens[6].toBoolean())?null:{ map ->
                        def address = Address.findWhere(map)
                        if(!!address) {
                            println "Found existing addres ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"                
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
                            } else if(!!code.trim()) {
                                def state = State.findByCode(code.trim())
                                if(!!state) {
                                    println "Found existing ${GrailsNameUtils.getShortName(state.class)} ${state.name}" 
                                    return state
                                } else {
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
                            return importKey.state
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
                    party: { map ->
                        def party = Party.findByCodeAndState(map.code,map.state)                        
                        if(!!party) {
                            println "Found existing ${GrailsNameUtils.getShortName(party.class)} ${party.code}"                
                        } else if(!!map.code) {
                            party = map as Party
                            if(!party.save(failOnError:true, flush: true, insert: true, validate: true)) {
                                party.errors.allErrors.each {
                                    println it
                                }
                                party = null
                            } else {
                                println "Created new ${GrailsNameUtils.getShortName(party.class)} ${party.code}"                
                            }                            
                        }
                        return party
                    }.call([
                        code: tokens[23].trim(),
                        state: importKey.state
                    ]),
                    precinct: (tokens[24] || tokens[25] || tokens[26] || tokens[27])?{ map -> 
                        def precinct = Precinct.findWhere(map)
                        if(!!precinct) {
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
                        if(!!phone) {
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
    
    def mergeHistoryRecord(String line,ImportKey importKey) {
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
                    if(!!floridaHistories) {
                        println "Found existing ${GrailsNameUtils.getShortName(floridaHistories[0].class)} ${floridaHistories[0].electionDate} - ${floridaHistory.electionType.code}"   
                    } else if(!!map.voterKey) {
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
                        if(!!voterKey) {
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
}
