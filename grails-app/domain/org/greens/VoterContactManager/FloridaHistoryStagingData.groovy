package org.greens.VoterContactManager

import java.text.SimpleDateFormat
class FloridaHistoryStagingData {
    ImportKey importKey
    String countyCode
    String voterId
    String electionTypeCode
    Date electionDate
    String historyVoteTypeCode

    static constraints = {
        countyCode(nullable: true, blank:true)
        voterId(nullable: true, blank:true)
        electionTypeCode(nullable: true, blank:true)
        electionDate(nullable: true)
        historyVoteTypeCode(nullable: true, blank:true)
    }
    static def createFromList(File f,def importKeyId) {
        FloridaHistory.withSession { s ->
            def insertQuery = s.createSQLQuery(""" 
                INSERT INTO florida_history_staging_data (
                    id,
                    version,
                    import_key_id,
                    county_code,
                    voter_id,
                    election_date,
                    election_type_code,
                    history_vote_type_code
                ) VALUES (
                    (select nextval ('hibernate_sequence')),
                    0,
                    :importKeyId,
                    :countyCode,
                    :voterId,
                    :electionDate,
                    :electionTypeCode,
                    :historyVoteTypeCode
                )
            """)            
            f.withReader { r ->
                r.toCsvReader('separatorChar':"\t").eachLine { row ->
                    if(row.size() > 2) {
                        insertQuery.setProperties([
                            importKeyId: importKeyId,
                            countyCode: row[0],
                            voterId: row[1],
                            electionDate: (!!!row[2].toString().trim())?null:new SimpleDateFormat("MM/dd/yyyy").parse(row[2].toString().trim()),                
                            electionTypeCode: row[3],
                            historyVoteTypeCode: row[4]
                        ]).executeUpdate()
                        s.flush()
                        s.clear()
                    }
                }
            }
            f.delete()
        }
    }
}
