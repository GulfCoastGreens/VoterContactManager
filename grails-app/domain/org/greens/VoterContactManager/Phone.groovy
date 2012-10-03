package org.greens.VoterContactManager

class Phone {
    String areaCode
    String number
    String extension
    static transients = ['phoneNumber']
    static constraints = {
    }
    String getPhoneNumber() {
        return areaCode + number
    }
}
