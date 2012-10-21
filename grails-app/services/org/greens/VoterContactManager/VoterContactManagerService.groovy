package org.greens.VoterContactManager

class VoterContactManagerService {
    static transactional = true
    def contactService
    
    def getInit(stateCode = 'FL') {
        def state = State.findByCode(stateCode)
        return [
            races: Race.findAllByState(state),
            genders: Gender.findAllByState(state),
            counties: County.findAllByState(state),
            parties: Party.findAllByState(state),
            statuses: VoterStatus.findAllByState(state),
            importDates: ImportKey.findAllByState(state).collect { importKey ->
                return importKey.snapshotDate
            },
            electionTypes: ElectionType.findAllByState(state),
            historyVoteTypes: (stateCode in ['FL'])?HistoryVoteType.findAllByState(state):[],
            matchesHeaders: StateVoterFields.fields."${stateCode}",
            contactTypes: contactService.getContactTypes().contactTypes,
            contacts: contactService.getContactsByType().contacts
        ]
    }
}
