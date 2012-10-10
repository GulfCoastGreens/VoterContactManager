package org.greens.VoterContactManager

class VoterContactManagerService {
    static transactional = true
    
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
            matchesHeaders: { code ->
                switch(code) {
                    case 'FL':
                        return [
                            "County Code",
                            "Voter ID",
                            "Name Last",
                            "Name Suffix",
                            "Name First",
                            "Name Middle",
                            "Suppress Address",
                            "Residence Address Line 1",
                            "Residence Address Line 2",
                            "Residence City USPS",
                            "Residence State",
                            "Residence Zipcode",
                            "Mailing Address Line 1",
                            "Mailing Address Line 2",
                            "Mailing Address Line 3",
                            "Mailing City",
                            "Mailing State",
                            "Mailing Zipcode",
                            "Mailing Country",
                            "Gender",
                            "Race",
                            "Birth Date",
                            "Registration Date",
                            "Party Affiliation",
                            "Precinct",
                            "Precinct Group",
                            "Precinct Split",
                            "Precinct Suffix",
                            "Voter Status",
                            "Congressional District",
                            "House District",
                            "Senate District",
                            "County Commission District",
                            "School Board District",
                            "Daytime Area Code",
                            "Daytime Phone Number",
                            "Daytime Phone Extension",
                            "Export Date"
                        ];
                        break;
                        
                }
            }.call(stateCode)
        ]
    }
}
