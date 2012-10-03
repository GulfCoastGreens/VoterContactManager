package org.greens.VoterContactManager

class Precinct {
    String precinct = ""
    String precinctGroup = ""
    String precinctSplit = ""
    String precinctSuffix = ""
    State state
    static constraints = {
        precinct(unique: ['state','precinctGroup','precinctSplit','precinctSuffix'],blank:true,nullable:false)
        precinctGroup(unique: ['state','precinct','precinctSplit','precinctSuffix'],blank:true,nullable:false)
        precinctSplit(unique: ['state','precinctGroup','precinct','precinctSuffix'],blank:true,nullable:false)
        precinctSuffix(unique: ['state','precinctGroup','precinctSplit','precinct'],blank:true,nullable:false)
        state(unique: ['precinct','precinctGroup','precinctSplit','precinctSuffix'],blank:false,nullable:false)                
    }
}
