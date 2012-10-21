package org.greens.VoterContactManager

class Address {
    String line1 = ""
    String line2 = ""
    String line3 = ""
    String city = ""
    State state
    String zip = ""
    static constraints = {
        line1(unique: ['line2','line3','city','state','zip'],blank:true,nullable:false)
        line2(unique: ['line1','line3','city','state','zip'],blank:true,nullable:false)
        line3(unique: ['line1','line2','city','state','zip'],blank:true,nullable:false)
        city(unique: ['line1','line2','line3','state','zip'],blank:true,nullable:false)
        state(unique: ['line1','line2','line3','city','zip'],nullable:true)
        zip(unique: ['line1','line2','line3','state','city'],blank:true,nullable:false)
    }
}
