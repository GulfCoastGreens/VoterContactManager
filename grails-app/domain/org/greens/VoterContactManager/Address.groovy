package org.greens.VoterContactManager

class Address {
    String line1 = ""
    String line2 = ""
    String line3 = ""
    String city = ""
    State state
    String zip = ""
    static constraints = {
        state(nullable:true)
    }
}
