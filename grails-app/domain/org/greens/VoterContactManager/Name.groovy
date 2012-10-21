package org.greens.VoterContactManager

class Name {
    String salutation = ""
    String prefix = ""
    String first = ""
    String middle = ""
    String last = ""
    String suffix = ""
    static constraints = {
        salutation(unique: ['prefix','first','middle','last','suffix'],blank:true,nullable:false)
        prefix(unique: ['salutation','first','middle','last','suffix'],blank:true,nullable:false)
        first(unique: ['salutation','prefix','middle','last','suffix'],blank:true,nullable:false)
        middle(unique: ['salutation','prefix','first','last','suffix'],blank:true,nullable:false)
        last(unique: ['salutation','prefix','middle','first','suffix'],blank:true,nullable:false)
        suffix(unique: ['salutation','prefix','middle','last','first'],blank:true,nullable:false)
    }
}
