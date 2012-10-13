package org.greens.VoterContactManager

class PhoneType {
    String name
    static constraints = {
        name(unique:true,nullable:false,blank:false,notEqual:"Voter Data Provided")
    }
}
