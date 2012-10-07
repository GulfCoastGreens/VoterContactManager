package org.greens.VoterContactManager

import java.util.zip.ZipInputStream
import java.util.zip.ZipEntry
import java.text.SimpleDateFormat
import grails.util.GrailsNameUtils

class GeorgiaService {
    static transactional = true
    def dataMap

    static processVoters(ZipInputStream zin) {
        def importKey
        GeorgiaService georgiaService = new GeorgiaService()
        // wrap the ZipInputStream with an InputStreamReader 
        InputStreamReader isr = new InputStreamReader(zin);
        BufferedReader br = new BufferedReader(isr);        
        ZipEntry ze = null
        String line
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        while ((ze = zin.getNextEntry()) != null) {
            System.out.println("Unzipping " + ze.getName())
            if(importKey == null) {
                Date snapshotDate = sdf.parse(sdf.format(new Date(ze.getTime())))
                importKey = ImportKey.findBySnapshotDateAndState(snapshotDate,State.findByCode('GA'))
                if(importKey == null) {
                    importKey = new ImportKey([state: State.findByCode('GA'),snapshotDate: snapshotDate])
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
                georgiaService.mergeVoterRecord(line,importKey)
                // println(line)
            }            
        }
        br.close()
        zin.close()        
    }
    static processHistories(ZipInputStream zin) {
        def importKey
        GeorgiaService georgiaService = new GeorgiaService()
        // wrap the ZipInputStream with an InputStreamReader 
        InputStreamReader isr = new InputStreamReader(zin);
        BufferedReader br = new BufferedReader(isr);        
        ZipEntry ze = null
        String line
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        while ((ze = zin.getNextEntry()) != null) {
            System.out.println("Unzipping " + ze.getName())
            if(importKey == null) {
                Date snapshotDate = sdf.parse(sdf.format(new Date(ze.getTime())))
                importKey = ImportKey.findBySnapshotDateAndState(snapshotDate,State.findByCode('GA'))
                if(importKey == null) {
                    importKey = new ImportKey([state: State.findByCode('GA'),snapshotDate: snapshotDate])
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
                georgiaService.mergeHistoryRecord(line,importKey)
                // println(line)
            }            
        }
        br.close()
        zin.close()        
    }
    
    def mergeVoterRecord(String row,ImportKey importKey) {
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
                salutation: row[75..<78].trim(),
                prefix: ""
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
                line3: "",
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
    
    def mergeHistoryRecord(String line,ImportKey importKey) {
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
}
