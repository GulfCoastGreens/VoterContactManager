package org.greens.VoterContactManager

class Phone {
    String areaCode = ''
    String number = ''
    String extension = ''
    static transients = ['phoneNumber']
    static constraints = {
        areaCode(unique: ['number','extension'],blank:true,nullable:false)
        number(unique: ['areaCode','extension'],blank:true,nullable:false)
        extension(unique: ['number','areaCode'],blank:true,nullable:false)
    }
    String getPhoneNumber() {
        return areaCode + number
    }
}
