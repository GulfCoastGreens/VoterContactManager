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
class GeorgiaBootStrap {
    static bootStrap() {
        [
            [code: 'GA',name:'Georgia',enabled:true] as State
        ].each { state ->
            if(!state.save(failOnError:true, flush: true, insert: true, validate: true)) {
                state.errors.allErrors.each {
                    println it
                }                
            } else {
                println "Created new ${GrailsNameUtils.getShortName(state.class)} ${state.name}"                
            }                                        
        }                
        def state = State.findByCode('GA')
        [
            [ name: 'ACTIVE-VOTER',code: 'A',state: state ] as VoterStatus,
            [ name: 'INACTIVE-VOTER',code: 'I',state: state ] as VoterStatus,
            [ name: 'DELETED-VOTER',code: '',state: state ] as VoterStatus            
        ].each { voterStatus ->
            if(!VoterStatus.find(voterStatus)) {
                if(!voterStatus.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    voterStatus.errors.allErrors.each {
                        println it
                    }                
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(voterStatus.class)} ${voterStatus.code} - ${voterStatus.name}"                
                }                                                        
            }
        }
        [
            [ name: 'WHITE', code: 'W', state: state ] as Race,       
            [ name: 'BLACK', code: 'B', state: state ] as Race,           
            [ name: 'ANOTHER', code: 'O', state: state ] as Race,   
            [ name: 'UNKNOWN-RACE', code: 'U', state: state ] as Race               
        ].each { race ->
            if(!Race.find(race)) {
                if(!race.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    race.errors.allErrors.each {
                        println it
                    }                
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(race.class)} ${race.code} - ${race.name}"                
                }                                                        
            }
        }
        [
            [ name: 'MALE', code: 'M', state: state ] as Gender,     
            [ name: 'FEMALE', code: 'F', state: state ] as Gender,   
            [ name: 'UNKNOWN-GENDER', code: 'U', state: state ] as Gender       
        ].each { gender ->
            if(!Gender.find(gender)) {
                if(!gender.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    gender.errors.allErrors.each {
                        println it
                    }                
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(gender.class)} ${gender.code} - ${gender.name}"                
                }                                                        
            }
        }
        [
            [ code: '000', name: 'UNKNOWN', state: state ] as ElectionType,
            [ code: '001', name: 'GENERAL PRIMARY', state: state ] as ElectionType,
            [ code: '002', name: 'GENERAL PRIMARY RUNOFF', state: state ] as ElectionType,
            [ code: '003', name: 'GENERAL ELECTION', state: state ] as ElectionType,
            [ code: '004', name: 'GENERAL ELECTION RUNOFF', state: state ] as ElectionType,
            [ code: '005', name: 'SPECIAL ELECTION', state: state ] as ElectionType,
            [ code: '006', name: 'SPECIAL ELECTION RUNOFF', state: state ] as ElectionType,
            [ code: '007', name: 'MUNICIPAL ELECTION', state: state ] as ElectionType,
            [ code: '008', name: 'RECALL', state: state ] as ElectionType,
            [ code: '009', name: 'PRESIDENTIAL PRIMARY', state: state ] as ElectionType,
            [ code: '010', name: 'MUNI ELECTION RUNOFF', state: state ] as ElectionType,
            [ code: '011', name: 'MUNI SPECIAL ELECTION', state: state ] as ElectionType,
            [ code: '012', name: 'MUNI SPECIAL ELEC RUNOFF', state: state ] as ElectionType,
            [ code: '013', name: 'MUNI GENERAL PRIMARY', state: state ] as ElectionType,
            [ code: '014', name: 'MUNI GENERAL PRIM RUNOFF', state: state ] as ElectionType,
            [ code: '015', name: 'MUNI SPECIAL PRIMARY', state: state ] as ElectionType,
            [ code: '016', name: 'MUNI SPECIAL PRIM RUNOFF', state: state ] as ElectionType,
            [ code: '017', name: 'SPECIAL PRIMARY', state: state ] as ElectionType,
            [ code: '018', name: 'SPECIAL PRIMARY RUNOFF', state: state ] as ElectionType,
            [ code: '019', name: 'MUNICIPAL RECALL', state: state ] as ElectionType            
        ].each { electionType ->
            if(!ElectionType.find(electionType)) {
                if(!electionType.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    electionType.errors.allErrors.each {
                        println it
                    }                
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(electionType.class)} ${electionType.code} - ${electionType.name}"                
                }                                                        
            }
        }
        [
            [ code: 'D', name: 'Georgia Democratic Party', simpleName: 'Georgia Democratic', state: state ] as Party,
            [ code: 'R', name: 'Georgia Republican Party', simpleName: 'Georgia Republican', state: state ] as Party,
            [ code: 'N', name: 'Georgia No Party', simpleName: 'Georgia None', state: state ] as Party
        ].each { party ->
            if(!party.find(party)) {
                if(!party.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    party.errors.allErrors.each {
                        println it
                    }                
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(party.class)} ${party.code} - ${party.name}"                
                }                                                        
            }
        }
    }	
}

