package org.greens.VoterContactManager

import java.text.SimpleDateFormat
import org.hibernate.Criteria
import grails.util.GrailsNameUtils
import grails.converters.*
import org.hibernate.Hibernate

class FloridaStagingData {
    FloridaVoter floridaVoter
    String countyCode
    County county
    VoterKey voterKey
    String voterId
    Name name
    String lastName = ""
    String suffixName = ""
    String firstName = ""
    String middleName = ""
    String suppressAddress = ""
    Address residenceAddress
    String residenceAddressLine1 = ""
    String residenceAddressLine2 = ""
    String residenceCity = ""
    String residenceState = ""
    String residenceZipcode = ""
    Address mailingAddress
    String mailingAddressLine1 = ""
    String mailingAddressLine2 = ""
    String mailingAddressLine3 = ""
    String mailingCity = ""
    String mailingState = ""
    String mailingZipcode = ""
    String mailingCountry = ""
    String gender = ""
    Gender genderMatch
    String race = ""
    Race raceMatch
    Date birthDate
    Date registrationDate
    String partyAffiliation = ""
    Party party
    String precinct = ""
    String precinctGroup = ""
    String precinctSplit = ""
    String precinctSuffix = ""
    Precinct precinctMatch
    String voterStatus = ""
    VoterStatus voterStatusMatch
    String congressionalDistrict = ""
    String houseDistrict = ""
    String senateDistrict = ""
    String countyCommissionDistrict = ""
    String schoolBoardDistrict = ""
    String daytimeAreaCode = ""
    String daytimePhoneNumber = ""
    String daytimePhoneExtension = ""
    Phone phone
    static constraints = {
        floridaVoter(nullable:true)
        countyCode(unique: ['voterId'],blank:false,nullable:false)
        voterId(unique: ['countyCode'],blank:false,nullable:false)
        birthDate(nullable:true)
        registrationDate(nullable:true)
        mailingAddress(nullable:true)
        residenceAddress(nullable:true)
        party(nullable:true)
        precinctMatch(nullable:true)
        raceMatch(nullable:true)
        genderMatch(nullable:true)
        voterStatusMatch(nullable:true)
        name(nullable:true)
        county(nullable:true)
        phone(nullable:true)
        voterKey(nullable:true)
    }
    
    static def createFromList(def row) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery(""" 
                INSERT INTO florida_staging_data (
                    id,
                    version,
                    county_code,
                    voter_id,
                    last_name,
                    suffix_name,
                    first_name,
                    middle_name,
                    suppress_address,
                    residence_address_line1,
                    residence_address_line2,
                    residence_city,
                    residence_state,
                    residence_zipcode,
                    mailing_address_line1,
                    mailing_address_line2,
                    mailing_address_line3,
                    mailing_city,
                    mailing_state,
                    mailing_zipcode,
                    mailing_country,
                    gender,
                    race,
                    birth_date,
                    registration_date,
                    party_affiliation,
                    precinct,
                    precinct_group,
                    precinct_split,
                    precinct_suffix,
                    voter_status,
                    congressional_district,
                    house_district,
                    senate_district,
                    county_commission_district,
                    school_board_district,
                    daytime_area_code,
                    daytime_phone_number,
                    daytime_phone_extension
                ) VALUES (
                    (select nextval ('hibernate_sequence')),
                    0,
                    :county_code,
                    :voter_id,
                    :last_name,
                    :suffix_name,
                    :first_name,
                    :middle_name,
                    :suppress_address,
                    :residence_address_line1,
                    :residence_address_line2,
                    :residence_city,
                    :residence_state,
                    :residence_zipcode,
                    :mailing_address_line1,
                    :mailing_address_line2,
                    :mailing_address_line3,
                    :mailing_city,
                    :mailing_state,
                    :mailing_zipcode,
                    :mailing_country,
                    :gender,
                    :race,
                    :birth_date,
                    :registration_date,
                    :party_affiliation,
                    :precinct,
                    :precinct_group,
                    :precinct_split,
                    :precinct_suffix,
                    :voter_status,
                    :congressional_district,
                    :house_district,
                    :senate_district,
                    :county_commission_district,
                    :school_board_district,
                    :daytime_area_code,
                    :daytime_phone_number,
                    :daytime_phone_extension
                )
            """).setProperties([
                county_code : row[0].toString().trim(),
                voter_id : row[1].toString().trim(),
                last_name : row[2].toString().trim(),
                suffix_name : row[3].toString().trim(),
                first_name : row[4].toString().trim(),
                middle_name : row[5].toString().trim(),
                suppress_address : row[6].toString().trim(),
                residence_address_line1 : row[7].toString().trim(),
                residence_address_line2 : row[8].toString().trim(),
                residence_city : row[9].toString().trim(),
                residence_state : row[10].toString().trim(),
                residence_zipcode : row[11].toString().trim(),
                mailing_address_line1 : row[12].toString().trim(),
                mailing_address_line2 : row[13].toString().trim(),
                mailing_address_line3 : row[14].toString().trim(),
                mailing_city : row[15].toString().trim(),
                mailing_state : row[16].toString().trim(),
                mailing_zipcode : row[17].toString().trim(),
                mailing_country : row[18].toString().trim(),
                gender : row[19].toString().trim(),
                race : row[20].toString().trim(),
                birth_date : (!!!row[21].toString().trim())?null:new SimpleDateFormat("MM/dd/yyyy").parse(row[21].toString().trim()),
                registration_date : (!!!row[22].toString().trim())?null:new SimpleDateFormat("MM/dd/yyyy").parse(row[22].toString().trim()),
                party_affiliation : row[23].toString().trim(),
                precinct : row[24].toString().trim(),
                precinct_group : row[25].toString().trim(),
                precinct_split : row[26].toString().trim(),
                precinct_suffix : row[27].toString().trim(),
                voter_status : row[28].toString().trim(),
                congressional_district : row[29].toString().trim(),
                house_district : row[30].toString().trim(),
                senate_district : row[31].toString().trim(),
                county_commission_district : row[32].toString().trim(),
                school_board_district : row[33].toString().trim(),
                daytime_area_code : row[34].toString().trim(),
                daytime_phone_number : row[35].toString().trim(),
                daytime_phone_extension : row[36].toString().trim()                            
            ]).executeUpdate()
        }
    }
    static def createFromListOld(def row) {
        def fsd = [
            countyCode : row[0].toString().trim(),
            voterId : row[1].toString().trim(),
            lastName : row[2].toString().trim(),
            suffixName : row[3].toString().trim(),
            firstName : row[4].toString().trim(),
            middleName : row[5].toString().trim(),
            suppressAddress : row[6].toString().trim(),
            residenceAddressLine1 : row[7].toString().trim(),
            residenceAddressLine2 : row[8].toString().trim(),
            residenceCity : row[9].toString().trim(),
            residenceState : row[10].toString().trim(),
            residenceZipcode : row[11].toString().trim(),
            mailingAddressLine1 : row[12].toString().trim(),
            mailingAddressLine2 : row[13].toString().trim(),
            mailingAddressLine3 : row[14].toString().trim(),
            mailingCity : row[15].toString().trim(),
            mailingState : row[16].toString().trim(),
            mailingZipcode : row[17].toString().trim(),
            mailingCountry : row[18].toString().trim(),
            gender : row[19].toString().trim(),
            race : row[20].toString().trim(),
            birthDate : (!!!row[21].toString().trim())?null:new SimpleDateFormat("MM/dd/yyyy").parse(row[21].toString().trim()),
            registrationDate : (!!!row[22].toString().trim())?null:new SimpleDateFormat("MM/dd/yyyy").parse(row[22].toString().trim()),
            partyAffiliation : row[23].toString().trim(),
            precinct : row[24].toString().trim(),
            precinctGroup : row[25].toString().trim(),
            precinctSplit : row[26].toString().trim(),
            precinctSuffix : row[27].toString().trim(),
            voterStatus : row[28].toString().trim(),
            congressionalDistrict : row[29].toString().trim(),
            houseDistrict : row[30].toString().trim(),
            senateDistrict : row[31].toString().trim(),
            countyCommissionDistrict : row[32].toString().trim(),
            schoolBoardDistrict : row[33].toString().trim(),
            daytimeAreaCode : row[34].toString().trim(),
            daytimePhoneNumber : row[35].toString().trim(),
            daytimePhoneExtension : row[36].toString().trim()            
        ] as FloridaStagingData
        if(!fsd.save(failOnError:true, flush: true, insert: true, validate: true)) {
            fsd.errors.allErrors.each {
                println it
            }
        } else {
            println "Created new ${GrailsNameUtils.getShortName(fsd.class)} ${fsd.voterId}"
        }
        FloridaStagingData.withSession { s ->
            s.flush()
            s.clear()
        }
    }
    static def appendVotersDialectIssue(ImportKey importKey) {
        FloridaStagingData.withSession { s ->
            def insertQuery = s.createSQLQuery(""" 
                INSERT INTO voter
                    ("county_id",
                    "voter_key_voter_id",
                    "voter_key_import_key_id",
                    "name_id",
                    "suppress_address",
                    "resident_address_id",
                    "mailing_address_id",
                    "mailing_country",
                    "gender_id",
                    "race_id",
                    "birth_date",
                    "registration_date",
                    "party_id",
                    "precinct_id",
                    "voter_status_id",
                    "congressional_district",
                    "house_district",
                    "senate_district",
                    "county_commission_district",
                    "school_board_district",
                    "phone_id",
                    "class",
                    "id")
                    VALUES
                    (:countyId,
                    :voterId,
                    ${importKey.id},
                    :nameId,
                    :suppressAddress,
                    :residentAddressId,
                    :mailingAddressId,
                    :mailingCountry,
                    :genderId,
                    :raceId,
                    :birthDate,
                    :registrationDate,
                    :partyId,
                    :precinctId,
                    :voterStatusId,
                    :congressionalDistrict,
                    :houseDistrict,
                    :senateDistrict,
                    :countyCommissionDistrict,
                    :schoolBoardDistrict,
                    :phoneId,
                    :class,
                    (select nextval ('hibernate_sequence')))
            """)
            def updateQuery = s.createSQLQuery(""" 
                UPDATE voter SET
                    "county_id"=:countyId,
                    "voter_key_voter_id"=:voterId,
                    "voter_key_import_key_id"=${importKey.id},
                    "name_id"=:nameId,
                    "suppress_address"=:suppressAddress,
                    "resident_address_id"=:residentAddressId,
                    "mailing_address_id"=:mailingAddressId,
                    "mailing_country"=:mailingCountry,
                    "gender_id"=:genderId,
                    "race_id"=:raceId,
                    "birth_date"=:birthDate,
                    "registration_date"=:registrationDate,
                    "party_id"=:partyId,
                    "precinct_id"=:precinctId,
                    "voter_status_id"=:voterStatusId,
                    "congressional_district"=:congressionalDistrict,
                    "house_district"=:houseDistrict,
                    "senate_district"=:senateDistrict,
                    "county_commission_district"=:countyCommissionDistrict,
                    "school_board_district"=:schoolBoardDistrict,
                    "phone_id"=:phoneId,
                    "class"=:class
                WHERE id=:id
            """)
            s.createSQLQuery("""
                SELECT
                    (SELECT c.id FROM county c WHERE c.code=trim(both from tv.county_code)) as "countyId",
                    trim(both from tv.voter_id) as "voterId", 
                    (
                        SELECT 
                            n.id 
                        FROM name n 
                        WHERE n.salutation='' AND
                        n.prefix = '' AND
                        n.last = initcap(trim(both from tv.last_name)) AND 
                        n.suffix = initcap(trim(both from tv.suffix_name)) AND 
                        n.first = initcap(trim(both from tv.first_name)) AND 
                        n.middle = initcap(trim(both from tv.middle_name)) 
                    ) as nameId,
                    tv.suppress_address as "suppressAddress",
                    (
                        SELECT a.id 
                        FROM address a
                        WHERE
                        tv.suppress_address = 'N' AND
                        a.line1 = initcap(trim(both from tv.residence_address_line1)) AND
                        a.line2 = initcap(trim(both from tv.residence_address_line2)) AND
                        a.line3 = '' AND
                        a.city = initcap(trim(both from tv.residence_city)) AND
                        a.state_id = (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.residence_state)) > 0 THEN tv.residence_state
                            ELSE '${importKey.state.code}'
                        END) AND
                        a.zip = trim(both from tv.residence_zipcode)
                    ) as "residentAddressId",
                    (
                        SELECT a.id 
                        FROM address a
                        WHERE
                        tv.suppress_address = 'N' AND
                        a.line1 = initcap(trim(both from tv.mailing_address_line1)) AND
                        a.line2 = initcap(trim(both from tv.mailing_address_line2)) AND
                        a.line3 = initcap(trim(both from tv.mailing_address_line3)) AND
                        a.city = initcap(trim(both from tv.mailing_city)) AND
                        a.state_id = (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.mailing_state)) > 0 THEN tv.mailing_state
                            ELSE '${importKey.state.code}'
                        END) AND
                        a.zip = trim(both from tv.mailing_zipcode)
                    ) as "mailingAddressId",
                    trim(both from tv.mailing_country) as "mailingCountry",
                    (SELECT g.id FROM gender g WHERE g.code = trim(both from tv.gender) AND g.state_id=${importKey.state.id}) as "genderId",
                    (SELECT r.id FROM race r WHERE r.code = trim(both from tv.race) AND r.state_id=${importKey.state.id}) as "raceId",
                    tv.birth_date as "birthDate",
                    tv.registration_date as "registrationDate",
                    (SELECT p.id FROM party p WHERE p.code=trim(both from tv.party_affiliation) AND p.state_id=${importKey.state.id}) as "partyId",
                    (
                        SELECT pr.id 
                        FROM precinct pr
                        WHERE pr.precinct = trim(both from tv.precinct) AND
                        pr.precinct_group = trim(both from tv.precinct_group) AND
                        pr.precinct_split = trim(both from tv.precinct_split) AND
                        pr.precinct_suffix = trim(both from tv.precinct_suffix) AND
                        pr.state_id = '${importKey.state.id}'
                    ) as "precinctId",
                    (SELECT vs.id FROM voter_status vs WHERE vs.code = trim(both from tv.voter_status) AND vs.state_id=${importKey.state.id}) as "voterStatusId",
                    trim(both from tv.congressional_district) as "congressionalDistrict",
                    trim(both from tv.house_district) as "houseDistrict",
                    trim(both from tv.senate_district) as "senateDistrict",
                    trim(both from tv.county_commission_district) as "countyCommissionDistrict",
                    trim(both from tv.school_board_district) as "schoolBoardDistrict",
                    (
                        SELECT ph.id FROM phone ph
                        WHERE ph.area_code = trim(both from tv.daytime_area_code) AND
                        ph.number = trim(both from tv.daytime_phone_number) AND
                        ph.extension = trim(both from tv.daytime_phone_extension)
                    ) as "phoneId",
                    'org.greens.VoterContactManager.FloridaVoter' as "class"
                FROM "florida_staging_data" tv
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->       
                def idObj = FloridaVoter.findByCountyAndVoterKey(County.get(raw.countyId),VoterKey.findByImportKeyAndVoterId(importKey,raw.voterId))
                if(!!!idObj) {
                    // Insert
                    insertQuery.setProperties(raw).executeUpdate()
                } else {
                    // Update
                    raw.id = idObj.id
                    updateQuery.setProperties(raw).executeUpdate()
                }
            }
        }
    }
    static def appendVoters(ImportKey importKey) {
        /**
         *
         *                    (
         *               SELECT 
         *                   fv.id 
         *               FROM voter fv 
         *               WHERE fv.voter_key_voter_id=trim(both from tv.voter_id) AND 
         *               fv.voter_key_import_key_id=${importKey.id} AND 
         *               fv.county_id=(
         *                   SELECT c.id FROM county c WHERE c.code=trim(both from tv.county_code)
         *               )
         *           ) as "id",
         *
         *  select nextval ('hibernate_sequence')
         *
        **/
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT
                    (SELECT c.id FROM county c WHERE c.code=trim(both from tv.county_code)) as "countyId",
                    trim(both from tv.voter_id) as "voterId", 
                    (
                        SELECT 
                            n.id 
                        FROM name n 
                        WHERE n.salutation='' AND
                        n.prefix = '' AND
                        n.last = initcap(trim(both from tv.last_name)) AND 
                        n.suffix = initcap(trim(both from tv.suffix_name)) AND 
                        n.first = initcap(trim(both from tv.first_name)) AND 
                        n.middle = initcap(trim(both from tv.middle_name)) 
                    ) as nameId,
                    tv.suppress_address as "suppressAddress",
                    (
                        SELECT a.id 
                        FROM address a
                        WHERE
                        tv.suppress_address = 'N' AND
                        a.line1 = initcap(trim(both from tv.residence_address_line1)) AND
                        a.line2 = initcap(trim(both from tv.residence_address_line2)) AND
                        a.line3 = '' AND
                        a.city = initcap(trim(both from tv.residence_city)) AND
                        a.state_id = (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.residence_state)) > 0 THEN tv.residence_state
                            ELSE '${importKey.state.code}'
                        END) AND
                        a.zip = trim(both from tv.residence_zipcode)
                    ) as "residentAddressId",
                    (
                        SELECT a.id 
                        FROM address a
                        WHERE
                        tv.suppress_address = 'N' AND
                        a.line1 = initcap(trim(both from tv.mailing_address_line1)) AND
                        a.line2 = initcap(trim(both from tv.mailing_address_line2)) AND
                        a.line3 = initcap(trim(both from tv.mailing_address_line3)) AND
                        a.city = initcap(trim(both from tv.mailing_city)) AND
                        a.state_id = (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.mailing_state)) > 0 THEN tv.mailing_state
                            ELSE '${importKey.state.code}'
                        END) AND
                        a.zip = trim(both from tv.mailing_zipcode)
                    ) as "mailingAddressId",
                    trim(both from tv.mailing_country) as "mailingCountry",
                    (SELECT g.id FROM gender g WHERE g.code = trim(both from tv.gender) AND g.state_id=${importKey.state.id}) as "genderId",
                    (SELECT r.id FROM race r WHERE r.code = trim(both from tv.race) AND r.state_id=${importKey.state.id}) as "raceId",
                    tv.birth_date as "birthDate",
                    tv.registration_date as "registrationDate",
                    (SELECT p.id FROM party p WHERE p.code=trim(both from tv.party_affiliation) AND p.state_id=${importKey.state.id}) as "partyId",
                    (
                        SELECT pr.id 
                        FROM precinct pr
                        WHERE pr.precinct = trim(both from tv.precinct) AND
                        pr.precinct_group = trim(both from tv.precinct_group) AND
                        pr.precinct_split = trim(both from tv.precinct_split) AND
                        pr.precinct_suffix = trim(both from tv.precinct_suffix) AND
                        pr.state_id = '${importKey.state.id}'
                    ) as "precinctId",
                    (SELECT vs.id FROM voter_status vs WHERE vs.code = trim(both from tv.voter_status) AND vs.state_id=${importKey.state.id}) as "voterStatusId",
                    trim(both from tv.congressional_district) as "congressionalDistrict",
                    trim(both from tv.house_district) as "houseDistrict",
                    trim(both from tv.senate_district) as "senateDistrict",
                    trim(both from tv.county_commission_district) as "countyCommissionDistrict",
                    trim(both from tv.school_board_district) as "schoolBoardDistrict",
                    (
                        SELECT ph.id FROM phone ph
                        WHERE ph.area_code = trim(both from tv.daytime_area_code) AND
                        ph.number = trim(both from tv.daytime_phone_number) AND
                        ph.extension = trim(both from tv.daytime_phone_extension)
                    ) as "phoneId"
                FROM "florida_staging_data" tv
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->                
                def voterKey = VoterKey.findByImportKeyAndVoterId(importKey,raw.voterId)
                def county = County.get(raw.countyId)
                def fv = FloridaVoter.findWhere([ voterKey: voterKey, county: county ])
                def floridaVoter = [
                    id: (fv)?fv.id:null,
                    county: county,
                    voterKey: voterKey,
                    name: (!!raw.nameId)?Name.get(raw.nameId):null,
                    suppressAddress: raw.suppressAddress,
                    residentAddress: (!!raw.residentAddressId)?Address.get(raw.residentAddressId):null,
                    mailingAddress: (!!raw.mailingAddressId)?Address.get(raw.mailingAddressId):null,
                    mailingCountry: raw.mailingCountry,
                    gender: Gender.get(raw.genderId),
                    race: Race.get(raw.raceId),
                    birthDate: raw.birthDate,
                    registrationDate: raw.registrationDate,
                    party: Party.get(raw.partyId),
                    precinct: Precinct.get(raw.precinctId),
                    voterStatus: VoterStatus.get(raw.voterStatusId),
                    congressionalDistrict: raw.congressionalDistrict,
                    houseDistrict: raw.houseDistrict,
                    senateDistrict: raw.senateDistrict,
                    countyCommissionDistrict: raw.countyCommissionDistrict,
                    schoolBoardDistrict: raw.schoolBoardDistrict,
                    phone: (!!raw.phoneId)?Phone.get(raw.phoneId):null
                ] as FloridaVoter
//                if(!floridaVoter.save(failOnError:true, flush: false, insert: (fv)?false:true,validate: true)) {
//                    floridaVoter.errors.allErrors.each {
//                        println it
//                    }
//                    println floridaVoter.id
//                } else {
//                    println "Merged ${GrailsNameUtils.getShortName(floridaVoter.class)} ${floridaVoter.id}"                
//                }
                if(!!!fv) {
                    fv = floridaVoter as FloridaVoter
                    if(!fv.save(failOnError:true, flush: false, insert: true, validate: true)) {
                        fv.errors.allErrors.each {
                            println it
                        }
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(fv.class)} ${fv.id}"                
                    }
                } else {
                    fv.properties = floridaVoter
                    if(!fv.save(failOnError:true, flush: false, validate: true)) {
                        fv.errors.allErrors.each {
                            println it
                        }
                    } else {
                        println "Updated ${GrailsNameUtils.getShortName(fv.class)} ${fv.id}"                
                    }
                }
                s.flush()
                s.clear()
            }
        }        
    }
    static def appendUniqueVoterKeys(ImportKey importKey) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT DISTINCT
                    trim(both from tv.voter_id) as "voterId"
                FROM "florida_staging_data" tv
                WHERE NOT EXISTS (
                    SELECT
                        vk.voter_id
                    FROM voter_key vk
                    WHERE vk.voter_id = trim(both from tv.voter_id) AND
                    vk.import_key_id = ${importKey.id}
                )
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def voterKey = [
                    voterId: raw.voterId,
                    importKey: importKey
                ] as VoterKey
                if(!voterKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    voterKey.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(voterKey.class)} ${voterKey.voterId}"                
                }                                        
            }                
            s.flush()
            s.clear()
        }        
    }
    static def appendUniquePhoneNumbers() {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT DISTINCT
                    trim(both from tv.daytime_area_code) as "areaCode",
                    trim(both from tv.daytime_phone_number) as "number",
                    trim(both from tv.daytime_phone_extension) as "extension"
                FROM "florida_staging_data" tv
                WHERE (length(trim(both from tv.daytime_area_code)) > 0 OR
                length(trim(both from tv.daytime_phone_number)) > 0 OR
                length(trim(both from tv.daytime_phone_extension)) > 0) AND
                NOT EXISTS (
                    SELECT
                        p.area_code,
                        p.extension,
                        p.number
                    FROM phone p
                    WHERE p.area_code = trim(both from tv.daytime_area_code) AND
                    p.number = trim(both from tv.daytime_phone_number) AND
                    p.extension = trim(both from tv.daytime_phone_extension)
                )
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                System.out.println(raw.toString())
                def phone = raw as Phone
//                def phone = [
//                    areaCode: raw.areaCode,
//                    number: raw.phone,
//                    extension: raw.extension
//                ] as Phone
                System.out.println(phone.toString())
                if(!phone.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    phone.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(phone.class)} ${phone.areaCode} ${phone.number}"                
                }                                        
            }
            s.flush()
            s.clear()
        }        
    }
    static def appendUniquePrecincts(State state) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT DISTINCT
                    trim(both from tv.precinct) as "precinct",
                    trim(both from tv.precinct_group) as "precinctGroup",
                    trim(both from tv.precinct_split) as "precinctSplit",
                    trim(both from tv.precinct_suffix) as "precinctSuffix"
                FROM "florida_staging_data" tv
                WHERE NOT EXISTS (
                    SELECT 
                        p.precinct,
                        p.precinct_group,
                        p.precinct_split,
                        p.precinct_suffix
                    FROM precinct p
                    WHERE p.precinct = trim(both from tv.precinct) AND
                    p.precinct_group = trim(both from tv.precinct_group) AND
                    p.precinct_split = trim(both from tv.precinct_split) AND
                    p.precinct_suffix = trim(both from tv.precinct_suffix) AND
                    p.state_id = '${state.id}'
                )
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw -> 
                def precinct = [
                    precinct: raw.precinct,
                    precinctGroup: raw.precinctGroup,
                    precinctSplit: raw.precinctSplit,
                    precinctSuffix: raw.precinctSuffix,
                    state: state
                ] as Precinct
                if(!precinct.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    precinct.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(precinct.class)} ${precinct.precinct}"                
                }                                        
            }
            s.flush()
            s.clear()
        }
    }
    static def updateAddresses(State state) {
        FloridaStagingData.withSession { s ->
            def mailingUpdateQuery = s.createSQLQuery("update florida_staging_data set mailing_address_id=:id WHERE voter_id=:voter_id")
            def residenceUpdateQuery = s.createSQLQuery("update florida_staging_data set residence_address_id=:id WHERE voter_id=:voter_id")
            s.createSQLQuery(""" 
                SELECT 
                        ad.line1,
                        ad.line2,
                        ad.line3,
                        ad.city,
                        ad.state_id,
                        ad.zip
                FROM (
                        SELECT 
                                tv.mailing_address_line1 as "line1",
                                tv.mailing_address_line2 as "line2", 
                                tv.mailing_address_line3 as "line3", 
                                tv.mailing_city as "city",
                                (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.mailing_state)) > 0 THEN trim(both from tv.mailing_state)
                                    ELSE 'FL'
                                END) as "state_id",
                                tv.mailing_zipcode as "zip"
                        FROM florida_staging_data "tv"
                        WHERE tv.suppress_address='N'
                        UNION
                        SELECT 
                                tv.residence_address_line1 as "line1",
                                tv.residence_address_line2 as "line2", 
                                '' as "line3", 
                                tv.residence_city as "city",
                                (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.residence_state)) > 0 THEN trim(both from tv.residence_state)
                                    ELSE 'FL'
                                END) as "state_id",
                                tv.residence_zipcode as "zip"
                        FROM florida_staging_data "tv"
                        WHERE tv.suppress_address='N'
                ) as ad
                EXCEPT
                SELECT
                        line1,
                        line2,
                        line3,
                        city,
                        state_id,
                        zip
                FROM address
            """).addEntity(Address.class).list().each { raw ->
                def address = raw as Address
                if(!address.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    address.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"  
                    
                }                                                        
            }
        }
        
    }
    static def updateAddressesOld(State state) {
        FloridaStagingData.withSession { s ->
            def mailingUpdateQuery = s.createSQLQuery("update florida_staging_data set mailing_address_id=:id WHERE voter_id=:voter_id")
            def residenceUpdateQuery = s.createSQLQuery("update florida_staging_data set residence_address_id=:id WHERE voter_id=:voter_id")
            s.createSQLQuery("""
                SELECT 
                        tv.mailing_address_line1 as "line1",
                        tv.mailing_address_line2 as "line2", 
                        tv.mailing_address_line3 as "line3", 
                        tv.mailing_city as "city",
                        (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.mailing_state)) > 0 THEN (SELECT s.id FROM state s WHERE s.code=trim(both from tv.mailing_state))
                            ELSE (SELECT s.id FROM state s WHERE s.code='${state.code}')
                        END) as "state.id",
                        tv.mailing_zipcode as "zip"
                FROM florida_staging_data "tv"
                LEFT JOIN address a ON
                        tv.mailing_address_line1=a.line1 AND
                        tv.mailing_address_line2=a.line2 AND 
                        tv.mailing_address_line3=a.line3 AND 
                        tv.mailing_city=a.city AND
                        a.state_id = (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.mailing_state)) > 0 THEN (SELECT s.id FROM state s WHERE s.code=trim(both from tv.mailing_state))
                            ELSE (SELECT s.id FROM state s WHERE s.code='${state.code}')
                        END) AND	
                        tv.mailing_zipcode=a.zip
                WHERE a.id IS NULL and tv.suppress_address='N'
            """).addEntity(Address.class).list().each { raw ->
                def address = raw as Address
                if(!address.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    address.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"  
                    
                }                                                        
            }
            s.createSQLQuery("""
                SELECT 
                        tv.residence_address_line1 as "line1",
                        tv.residence_address_line2 as "line2", 
                        '' as "line3", 
                        tv.residence_city as "city",
                        (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.residence_state)) > 0 THEN tv.residence_state
                            ELSE '${state.code}'
                        END) as "state.id",
                        tv.residence_zipcode as "zip"
                FROM florida_staging_data "tv"
                LEFT JOIN address a ON
                        tv.residence_address_line1=a.line1 AND
                        tv.residence_address_line2=a.line2 AND 
                        ''=a.line3 AND 
                        tv.residence_city=a.city AND
                        a.state_id = (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.residence_state)) > 0 THEN tv.residence_state
                            ELSE '${state.code}'
                        END) AND	
                        tv.residence_zipcode=a.zip
                WHERE a.id IS NULL and tv.suppress_address='N'
            """).addEntity(Address.class).list().each { raw ->
                def address = raw as Address
                if(!address.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    address.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"  
                    
                }                                                        
            }
            s.createSQLQuery(""" 
                SELECT 
                    tv.voter_id,
                    a.id
                FROM florida_staging_data "tv"
                LEFT JOIN address a ON
                    tv.mailing_address_line1=a.line1 AND
                    tv.mailing_address_line2=a.line2 AND 
                    tv.mailing_address_line3=a.line3 AND 
                    tv.mailing_city=a.city AND
                    a.state_id = (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.mailing_state)) > 0 THEN tv.mailing_state
                        ELSE '${state.code}'
                    END) AND	
                    tv.mailing_zipcode=a.zip
                WHERE a.id IS NOT NULL AND tv.mailing_address_id IS NULL
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                mailingUpdateQuery.setParameter("id", raw.id).setParameter("voter_id", raw.voter_id).executeUpdate()                
            }
            s.createSQLQuery(""" 
                SELECT 
                    tv.voter_id,
                    a.id
                FROM florida_staging_data "tv"
                LEFT JOIN address a ON
                    tv.residence_address_line1=a.line1 AND
                    tv.residence_address_line2=a.line2 AND 
                    ''=a.line3 AND 
                    tv.residence_city=a.city AND
                    a.state_id = (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.residence_state)) > 0 THEN tv.residence_state
                        ELSE '${state.code}'
                    END) AND	
                    tv.residence_zipcode=a.zip
                WHERE a.id IS NOT NULL AND tv.residence_address_id IS NULL
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                residenceUpdateQuery.setParameter("id", raw.id).setParameter("voter_id", raw.voter_id).executeUpdate()                
            }
        }
    }
    static def appendUniqueAddresses(State state) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT DISTINCT
                    initcap(trim(both from tv.residence_address_line1)) as "line1",
                    initcap(trim(both from tv.residence_address_line2)) as "line2",
                    initcap(trim(both from tv.residence_city)) as "city",
                    CASE WHEN length(trim(both from tv.residence_state)) > 0 THEN tv.residence_state
                        ELSE '${state.code}'
                    END as "state",
                    trim(both from tv.residence_zipcode) as "zip"
                FROM "florida_staging_data" tv
                WHERE
                tv.suppress_address = 'N' AND
                NOT EXISTS (
                    SELECT 
                        a.line1,
                        a.line2,
                        a.city,
                        a.state_id,
                        a.zip
                    FROM address a
                    WHERE
                    a.line1 = initcap(trim(both from tv.residence_address_line1)) AND
                    a.line2 = initcap(trim(both from tv.residence_address_line2)) AND
                    a.line3 = '' AND
                    a.city = initcap(trim(both from tv.residence_city)) AND
                    a.state_id = (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.residence_state)) > 0 THEN tv.residence_state
                        ELSE '${state.code}'
                    END) AND
                    a.zip = trim(both from tv.residence_zipcode)
                )
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def address = [
                    line1: raw.line1,
                    line2: raw.line2,
                    line3: "",
                    city: raw.city,
                    state: State.findByCode(raw.state),
                    zip: raw.zip
                ] as Address
                if(!address.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    address.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"                
                }                                        
            }
            s.createSQLQuery("""
                SELECT DISTINCT
                    initcap(trim(both from tv.mailing_address_line1)) as "line1",
                    initcap(trim(both from tv.mailing_address_line2)) as "line2",
                    initcap(trim(both from tv.mailing_address_line3)) as "line3",
                    initcap(trim(both from tv.mailing_city)) as "city",
                    CASE WHEN length(trim(both from tv.mailing_state)) > 0 THEN tv.mailing_state
                        ELSE '${state.code}'
                    END as "state",
                    trim(both from tv.mailing_zipcode) as "zip"
                FROM "florida_staging_data" tv
                WHERE
                tv.suppress_address = 'N' AND
                NOT EXISTS (
                    SELECT 
                        a.line1,
                        a.line2,
                        a.line3,
                        a.city,
                        a.state_id,
                        a.zip
                    FROM address a
                    WHERE
                    a.line1 = initcap(trim(both from tv.mailing_address_line1)) AND
                    a.line2 = initcap(trim(both from tv.mailing_address_line2)) AND
                    a.line3 = initcap(trim(both from tv.mailing_address_line3)) AND
                    a.city = initcap(trim(both from tv.mailing_city)) AND
                    a.state_id = (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.mailing_state)) > 0 THEN tv.mailing_state
                        ELSE '${state.code}'
                    END) AND
                    a.zip = trim(both from tv.mailing_zipcode)
                )
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def address = [
                    line1: raw.line1,
                    line2: raw.line2,
                    line3: raw.line3,
                    city: raw.city,
                    state: State.findByCode(raw.state),
                    zip: raw.zip
                ] as Address
                if(!address.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    address.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(address.class)} ${address.zip}"                
                }                                        
            }
            s.flush()
            s.clear()
        }
    }
    
    
    static def updateNames() {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT DISTINCT
                    '' as "salutation",
                    initcap(trim(both from tv.last_name)) as "last",
                    initcap(trim(both from tv.suffix_name)) as "suffix",
                    '' as "prefix",
                    initcap(trim(both from tv.first_name)) as "first",
                    initcap(trim(both from tv.middle_name)) as "middle"
                FROM "florida_staging_data" tv
                EXCEPT
                SELECT 
                    salutation,
                    last,
                    suffix,
                    prefix,
                    first,
                    middle
                FROM name
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def name = [
                    salutation: raw.salutation,
                    prefix: raw.prefix,
                    last: raw.last,
                    suffix: raw.suffix,
                    first: raw.first,
                    middle: raw.middle
                ] as Name
                if(!name.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    name.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(name.class)} ${name.last}, ${name.first}"                
                }                                        
            }
            s.flush()
            s.clear()
        }
    }
    static def appendUniqueNames() {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT DISTINCT
                    '' as "salutation",
                    initcap(trim(both from tv.last_name)) as "last",
                    initcap(trim(both from tv.suffix_name)) as "suffix",
                    '' as "prefix",
                    initcap(trim(both from tv.first_name)) as "first",
                    initcap(trim(both from tv.middle_name)) as "middle"
                FROM "florida_staging_data" tv
                WHERE NOT EXISTS (
                    SELECT 
                        n.salutation,
                        n.last,
                        n.suffix,
                        n.prefix,
                        n.first,
                        n.middle
                    FROM name n
                    WHERE n.salutation='' AND
                    n.prefix = '' AND
                    n.last = initcap(trim(both from tv.last_name)) AND 
                    n.suffix = initcap(trim(both from tv.suffix_name)) AND 
                    n.first = initcap(trim(both from tv.first_name)) AND 
                    n.middle = initcap(trim(both from tv.middle_name)) 
                )
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def name = [
                    salutation: raw.salutation,
                    prefix: raw.prefix,
                    last: raw.last,
                    suffix: raw.suffix,
                    first: raw.first,
                    middle: raw.middle
                ] as Name
                if(!name.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    name.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(name.class)} ${name.last}, ${name.first}"                
                }                                        
            }
            s.flush()
            s.clear()
        }        
    }
    static def updateVotersPrevious(ImportKey importKey) {
        FloridaStagingData.withSession { s ->
            def updateQuery = s.createSQLQuery("""
                UPDATE voter SET
                    name_id = :name_id,
                    resident_address_id = :resident_address_id,
                    mailing_address_id = :mailing_address_id,
                    mailing_country = :mailing_country,
                    voter_status_id = :voter_status_id,
                    birth_date = :birth_date,
                    registration_date = :registration_date,
                    gender_id = :gender_id,
                    race_id = :race_id,
                    congressional_district = :congressional_district,
                    senate_district = :senate_district,
                    house_district = :house_district,
                    county_commission_district = :county_commission_district,
                    suppress_address = :suppress_address,
                    party_id = :party_id,
                    precinct_id = :precinct_id,
                    school_board_district = :school_board_district,
                    daytime_phone_id = :phone_id                
                WHERE id=:id
            """)
            def insertQuery = s.createSQLQuery("""
                INSERT INTO voter (
                    id,
                    version,
                    class
                    voter_key_voter_id,
                    voter_key_import_key_id,
                    county_id,
                    name_id,
                    resident_address_id,
                    mailing_address_id,
                    mailing_country,
                    voter_status_id,
                    birth_date,
                    registration_date,
                    gender_id,
                    race_id,
                    congressional_district,
                    senate_district,
                    house_district,
                    county_commission_district,
                    suppress_address,
                    party_id,
                    precinct_id,
                    school_board_district,
                    daytime_phone_id                
                ) VALUES (
                    (select nextval ('hibernate_sequence')),
                    0,
                    'org.greens.VoterContactManager.FloridaVoter',
                    :voter_id,
                    ${importKey.id},
                    :county_id,
                    :name_id,
                    :resident_address_id,
                    :mailing_address_id,
                    :mailing_country,
                    :voter_status_id,
                    :birth_date,
                    :registration_date,
                    :gender_id,
                    :race_id,
                    :congressional_district,
                    :senate_district,
                    :house_district,
                    :county_commission_district,
                    :suppress_address,
                    :party_id,
                    :precinct_id,
                    :school_board_district,
                    :phone_id                
                )
            """)
            s.createSQLQuery("""
                SELECT 
                        florida_voter_id as id,
                        voter_id,
                        county_id,
                        name_id,
                        residence_address_id as resident_address_id,
                        mailing_address_id,
                        mailing_country,
                        voter_status_match_id as voter_status_id,
                        birth_date,
                        registration_date,
                        gender_match_id as gender_id,
                        race_match_id as race_id,
                        congressional_district,
                        senate_district,
                        house_district,
                        county_commission_district,
                        suppress_address,
                        party_id,
                        precinct_match_id as precinct_id,
                        school_board_district,
                        phone_id                	
                FROM "florida_staging_data" tv 
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                if(!!raw.id) {
                    updateQuery.setProperties([
                        id: raw.id,
                        name_id: raw.name_id,
                        resident_address_id: raw.resident_address_id,
                        mailing_address_id: raw.mailing_address_id,
                        mailing_country: raw.mailing_country,
                        voter_status_id: raw.voter_status_id,
                        birth_date: raw.birth_date,
                        registration_date: raw.registration_date,
                        gender_id: raw.gender_id,
                        race_id: raw.race_id,
                        congressional_district: raw.congressional_district,
                        senate_district: raw.senate_district,
                        house_district: raw.house_district,
                        county_commission_district: raw.county_commission_district,
                        suppress_address: raw.suppress_address,
                        party_id: raw.party_id,
                        precinct_id: raw.precinct_id,
                        school_board_district: raw.school_board_district,
                        phone_id: raw.phone_id                                        
                    ]).executeUpdate() 
                } else {
                    insertQuery.setProperties([
                        voter_id: raw.voter_id,
                        county_id: raw.county_id,
                        name_id: raw.name_id,
                        resident_address_id: raw.resident_address_id,
                        mailing_address_id: raw.mailing_address_id,
                        mailing_country: raw.mailing_country,
                        voter_status_id: raw.voter_status_id,
                        birth_date: raw.birth_date,
                        registration_date: raw.registration_date,
                        gender_id: raw.gender_id,
                        race_id: raw.race_id,
                        congressional_district: raw.congressional_district,
                        senate_district: raw.senate_district,
                        house_district: raw.house_district,
                        county_commission_district: raw.county_commission_district,
                        suppress_address: raw.suppress_address,
                        party_id: raw.party_id,
                        precinct_id: raw.precinct_id,
                        school_board_district: raw.school_board_district,
                        phone_id: raw.phone_id                
                    ]).executeUpdate()
                }
            }
            s.flush()
            s.clear()            
        }
    }
    static def updateVoters(ImportKey importKey) {
        FloridaStagingData.withSession { s ->
            def updateQuery = s.createSQLQuery("""
                UPDATE voter SET
                    name_id = :name_id,
                    resident_address_id = :resident_address_id,
                    mailing_address_id = :mailing_address_id,
                    mailing_country = :mailing_country,
                    voter_status_id = :voter_status_id,
                    birth_date = :birth_date,
                    registration_date = :registration_date,
                    gender_id = :gender_id,
                    race_id = :race_id,
                    congressional_district = :congressional_district,
                    senate_district = :senate_district,
                    house_district = :house_district,
                    county_commission_district = :county_commission_district,
                    suppress_address = :suppress_address,
                    party_id = :party_id,
                    precinct_id = :precinct_id,
                    school_board_district = :school_board_district,
                    daytime_phone_id = :phone_id                
                WHERE id=:id
            """)
            def insertQuery = s.createSQLQuery("""
                INSERT INTO voter (
                    id,
                    version,
                    class
                    voter_key_voter_id,
                    voter_key_import_key_id,
                    county_id,
                    name_id,
                    resident_address_id,
                    mailing_address_id,
                    mailing_country,
                    voter_status_id,
                    birth_date,
                    registration_date,
                    gender_id,
                    race_id,
                    congressional_district,
                    senate_district,
                    house_district,
                    county_commission_district,
                    suppress_address,
                    party_id,
                    precinct_id,
                    school_board_district,
                    daytime_phone_id                
                ) VALUES (
                    (select nextval ('hibernate_sequence')),
                    0,
                    'org.greens.VoterContactManager.FloridaVoter',
                    :voter_id,
                    ${importKey.id},
                    :county_id,
                    :name_id,
                    :resident_address_id,
                    :mailing_address_id,
                    :mailing_country,
                    :voter_status_id,
                    :birth_date,
                    :registration_date,
                    :gender_id,
                    :race_id,
                    :congressional_district,
                    :senate_district,
                    :house_district,
                    :county_commission_district,
                    :suppress_address,
                    :party_id,
                    :precinct_id,
                    :school_board_district,
                    :phone_id                
                )
            """)            
            s.createSQLQuery("""
                SELECT DISTINCT
                        tvm.florida_voter_id as id,
                        tv.voter_id,
                        ${importKey.id} as "import_key_id",
                        tvm.county_id,
                        tvm.name_id,
                        tvm.resident_address_id,
                        tvm.mailing_address_id,
                        tv.mailing_country,
                        tvm.voter_status_id,
                        tv.birth_date,
                        tv.registration_date,
                        tvm.gender_id,
                        tvm.race_id,
                        tv.congressional_district,
                        tv.senate_district,
                        tv.house_district,
                        tv.county_commission_district,
                        tv.suppress_address,
                        tvm.party_id,
                        tvm.precinct_id,
                        tv.school_board_district,
                        tvm.phone_id                	
                FROM "florida_staging_data" tv 
                LEFT JOIN (
                       SELECT DISTINCT
                                tv.id,
                                tv.voter_id,
                                v.id AS "florida_voter_id",
                                c.id AS "county_id",
                                n.id as "name_id",
                                ma.id as "mailing_address_id",
                                ra.id as "resident_address_id",
                                p.id as "party_id",
                                pr.id as "precinct_id",
                                ph.id as "phone_id",
                                r.id as "race_id",
                                g.id as "gender_id",
                                vs.id as "voter_status_id"
                        FROM florida_staging_data "tv"
                        LEFT JOIN address ma ON
                                trim(both from tv.mailing_address_line1) = ma.line1 AND
                                trim(both from tv.mailing_address_line2) = ma.line2 AND 
                                trim(both from tv.mailing_address_line3) = ma.line3 AND 
                                trim(both from tv.mailing_city) = ma.city AND
                                (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.mailing_state)) > 0 THEN trim(both from tv.mailing_state)
                                    ELSE '${importKey.state.code}'
                                END) = ma.state_id AND
                                trim(both from tv.mailing_zipcode) = ma.zip AND
                                tv.suppress_address='N'
                        LEFT JOIN address ra ON
                                trim(both from tv.residence_address_line1) = ra.line1 AND
                                trim(both from tv.residence_address_line2) = ra.line2 AND 
                                '' = ra.line3 AND 
                                trim(both from tv.residence_city) = ra.city AND
                                (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.residence_state)) > 0 THEN trim(both from tv.residence_state)
                                    ELSE '${importKey.state.code}'
                                END) = ra.state_id AND
                                trim(both from tv.residence_zipcode) = ra.zip AND
                                tv.suppress_address='N'
                        LEFT JOIN name n ON
                                '' = n.salutation AND
                                initcap(trim(both from tv.last_name)) = n.last AND
                                initcap(trim(both from tv.suffix_name)) = n.suffix AND
                                '' = n.prefix AND
                                initcap(trim(both from tv.first_name)) = n.first AND
                                initcap(trim(both from tv.middle_name)) = n.middle
                        LEFT JOIN precinct pr ON
                                trim(both from tv.precinct) = pr.precinct AND
                                trim(both from tv.precinct_group) = pr.precinct_group AND
                                trim(both from tv.precinct_split) = pr.precinct_split AND
                                trim(both from tv.precinct_suffix) = pr.precinct_suffix AND
                                pr.state_id = ${importKey.state.id}
                        LEFT JOIN phone ph ON
                                trim(both from tv.daytime_area_code) = ph.area_code AND
                                trim(both from tv.daytime_phone_number) = ph.number AND
                                trim(both from tv.daytime_phone_extension) = ph.extension
                        LEFT JOIN race r ON
                                trim(both from tv.race) = r.code AND
                                r.state_id = ${importKey.state.id}
                        LEFT JOIN gender g ON
                                trim(both from tv.gender) = g.code AND
                                g.state_id = ${importKey.state.id}
                        LEFT JOIN voter_status vs ON
                                trim(both from tv.voter_status) = vs.code AND
                                vs.state_id = ${importKey.state.id}
                        LEFT JOIN party p ON
                                p.code = trim(both from tv.party_affiliation) AND
                                p.state_id = ${importKey.state.id}
                        LEFT JOIN county c ON
                                c.code = trim(both from tv.county_code) AND
                                c.state_id = ${importKey.state.id}
                        LEFT JOIN voter v ON
                                v.voter_key_voter_id=tv.voter_id AND
                                v.county_id=(SELECT c.id FROM county c WHERE c.code=tv.county_code AND c.state_id = ${importKey.state.id}) AND
                                v.voter_key_import_key_id=${importKey.id}
                ) as tvm ON tv.id=tvm.id
                WHERE tvm.florida_voter_id IS NULL
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                if(!!raw.id) {
                    updateQuery.setProperties([
                        id: raw.id,
                        name_id: raw.name_id,
                        resident_address_id: raw.resident_address_id,
                        mailing_address_id: raw.mailing_address_id,
                        mailing_country: raw.mailing_country,
                        voter_status_id: raw.voter_status_id,
                        birth_date: raw.birth_date,
                        registration_date: raw.registration_date,
                        gender_id: raw.gender_id,
                        race_id: raw.race_id,
                        congressional_district: raw.congressional_district,
                        senate_district: raw.senate_district,
                        house_district: raw.house_district,
                        county_commission_district: raw.county_commission_district,
                        suppress_address: raw.suppress_address,
                        party_id: raw.party_id,
                        precinct_id: raw.precinct_id,
                        school_board_district: raw.school_board_district,
                        phone_id: raw.phone_id                                        
                    ]).executeUpdate() 
                } else {
                    insertQuery.setProperties([
                        voter_id: raw.voter_id,
                        county_id: raw.county_id,
                        name_id: raw.name_id,
                        resident_address_id: raw.resident_address_id,
                        mailing_address_id: raw.mailing_address_id,
                        mailing_country: raw.mailing_country,
                        voter_status_id: raw.voter_status_id,
                        birth_date: raw.birth_date,
                        registration_date: raw.registration_date,
                        gender_id: raw.gender_id,
                        race_id: raw.race_id,
                        congressional_district: raw.congressional_district,
                        senate_district: raw.senate_district,
                        house_district: raw.house_district,
                        county_commission_district: raw.county_commission_district,
                        suppress_address: raw.suppress_address,
                        party_id: raw.party_id,
                        precinct_id: raw.precinct_id,
                        school_board_district: raw.school_board_district,
                        phone_id: raw.phone_id                
                    ]).executeUpdate()
                }
            }
            s.flush()
            s.clear()            
        }
    }
    static def updateVotersOLD(ImportKey importKey) {
        FloridaStagingData.findAll().each { fsd ->
            def fv = [
                id: (!!fsd.floridaVoter)?fsd.floridaVoter.id:null,
                voterKey: fsd.voterKey,
                county: fsd.county,
                name: fsd.name,
                residentAddress: fsd.residenceAddress,
                mailingAddress: fsd.mailingAddress,
                mailingCountry: fsd.mailingCountry,
                voterStatus: fsd.voterStatusMatch,
                birthDate: fsd.birthDate,
                registrationDate: fsd.registrationDate,
                gender: fsd.genderMatch,
                race: fsd.raceMatch,
                congressionalDistrict: fsd.congressionalDistrict,
                senateDistrict: fsd.senateDistrict,
                houseDistrict: fsd.houseDistrict,
                countyCommissionDistrict: fsd.countyCommissionDistrict,
                suppressAddress: fsd.suppressAddress,
                party: fsd.party,
                precinct: fsd.precinctMatch,
                schoolBoardDistrict: fsd.schoolBoardDistrict,
                daytimePhone: fsd.phone                
            ]
            if(!!fsd.floridaVoter) {
                fsd.floridaVoter.properties = fv
                if(!fsd.floridaVoter.save(failOnError:true, flush: true, validate: true)) {
                    fsd.floridaVoter.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Updated ${GrailsNameUtils.getShortName(fsd.floridaVoter.class)} ${fsd.floridaVoter.id}"                
                }
            } else {
                def floridaVoter = fv as FloridaVoter
                if(!floridaVoter.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    floridaVoter.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(floridaVoter.class)} ${floridaVoter.id}"                
                }                                    
            }
        }
    }
    static def updateIndexes(ImportKey importKey) {
        FloridaStagingData.withSession { s ->
            def updateQuery = s.createSQLQuery("""UPDATE  florida_staging_data SET
                    florida_voter_id = :florida_voter_id,
                    county_id = :county_id,
                    name_id = :name_id,
                    mailing_address_id = :mailing_address_id,
                    residence_address_id = :residence_address_id,
                    party_id = :party_id,
                    precinct_match_id = :precinct_id,
                    phone_id = :phone_id,
                    race_match_id = :race_id,
                    gender_match_id = :gender_id,
                    voter_status_match_id = :voter_status_id,
                    voter_key_voter_id = :voter_id,
                    voter_key_import_key_id = ${importKey.id}
                WHERE voter_id = :voter_id
            """)
            s.createSQLQuery("""
                SELECT DISTINCT
                        tv.voter_id,
                        v.id AS "florida_voter_id",
                        c.id AS "county_id",
                        n.id as "name_id",
                        ma.id as "mailing_address_id",
                        ra.id as "residence_address_id",
                        p.id as "party_id",
                        pr.id as "precinct_id",
                        ph.id as "phone_id",
                        r.id as "race_id",
                        g.id as "gender_id",
                        vs.id as "voter_status_id"
                FROM florida_staging_data "tv"
                LEFT JOIN address ma ON
                        trim(both from tv.mailing_address_line1) = ma.line1 AND
                        trim(both from tv.mailing_address_line2) = ma.line2 AND 
                        trim(both from tv.mailing_address_line3) = ma.line3 AND 
                        trim(both from tv.mailing_city) = ma.city AND
                        (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.mailing_state)) > 0 THEN trim(both from tv.mailing_state)
                            ELSE '${importKey.state.code}'
                        END) = ma.state_id AND
                        trim(both from tv.mailing_zipcode) = ma.zip AND
                        tv.suppress_address='N'
                LEFT JOIN address ra ON
                        trim(both from tv.residence_address_line1) = ra.line1 AND
                        trim(both from tv.residence_address_line2) = ra.line2 AND 
                        '' = ra.line3 AND 
                        trim(both from tv.residence_city) = ra.city AND
                        (SELECT s.id FROM state s WHERE s.code=CASE WHEN length(trim(both from tv.residence_state)) > 0 THEN trim(both from tv.residence_state)
                            ELSE '${importKey.state.code}'
                        END) = ra.state_id AND
                        trim(both from tv.residence_zipcode) = ra.zip AND
                        tv.suppress_address='N'
                LEFT JOIN name n ON
                        '' = n.salutation AND
                        initcap(trim(both from tv.last_name)) = n.last AND
                        initcap(trim(both from tv.suffix_name)) = n.suffix AND
                        '' = n.prefix AND
                        initcap(trim(both from tv.first_name)) = n.first AND
                        initcap(trim(both from tv.middle_name)) = n.middle
                LEFT JOIN precinct pr ON
                        trim(both from tv.precinct) = pr.precinct AND
                        trim(both from tv.precinct_group) = pr.precinct_group AND
                        trim(both from tv.precinct_split) = pr.precinct_split AND
                        trim(both from tv.precinct_suffix) = pr.precinct_suffix AND
                        pr.state_id = ${importKey.state.id}
                LEFT JOIN phone ph ON
                        trim(both from tv.daytime_area_code) = ph.area_code AND
                        trim(both from tv.daytime_phone_number) = ph.number AND
                        trim(both from tv.daytime_phone_extension) = ph.extension
                LEFT JOIN race r ON
                        trim(both from tv.race) = r.code AND
                        r.state_id = ${importKey.state.id}
                LEFT JOIN gender g ON
                        trim(both from tv.gender) = g.code AND
                        g.state_id = ${importKey.state.id}
                LEFT JOIN voter_status vs ON
                        trim(both from tv.voter_status) = vs.code AND
                        vs.state_id = ${importKey.state.id}
                LEFT JOIN party p ON
                        p.code = trim(both from tv.party_affiliation) AND
                        p.state_id = ${importKey.state.id}
                LEFT JOIN county c ON
                        c.code = trim(both from tv.county_code) AND
                        c.state_id = ${importKey.state.id}
                LEFT JOIN voter v ON
                        v.voter_key_voter_id=tv.voter_id AND
                        v.county_id=(SELECT c.id FROM county c WHERE c.code=tv.county_code AND c.state_id = ${importKey.state.id}) AND
                        v.voter_key_import_key_id=${importKey.id}
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                // Transformers.aliasToBean(PubDocumentDTO.class)
                updateQuery.setProperties(raw).executeUpdate()
            }
            
            s.flush()
            s.clear()
        }
    }
    static def updateVoterStatuses(State state) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT 
                        trim(both from tv.voter_status) as "code" 
                FROM "florida_staging_data" tv 
                WHERE
                        length(trim(both from tv.voter_status)) > 0
                EXCEPT
                SELECT
                        code
                FROM voter_status
                WHERE state_id = ${state.id}
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->                        
                def voterStatus = [code: raw.code.trim(), name: "",state: state] as VoterStatus
                if(!voterStatus.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    voterStatus.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(voterStatus.class)} ${voterStatus.code}"                
                }                    
            }
            s.flush()
            s.clear()
        }
    }
    static def updateGenders(State state) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT 
                        trim(both from tv.gender) as "code" 
                FROM "florida_staging_data" tv 
                WHERE
                        length(trim(both from tv.gender)) > 0
                EXCEPT
                SELECT
                        code
                FROM gender
                WHERE state_id = ${state.id}
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->                        
                def gender = [code: raw.code.trim(), name: "",state: state] as Gender
                if(!gender.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    gender.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(gender.class)} ${gender.code}"                
                }                    
            }
            s.flush()
            s.clear()
        }
    }
    static def updateRaces(State state) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT 
                        trim(both from race) as "code" 
                FROM "florida_staging_data" tv 
                WHERE
                        length(trim(both from tv.race)) > 0
                EXCEPT
                SELECT
                        code
                FROM race
                WHERE state_id = ${state.id}
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->                        
                def race = [code: raw.code.trim(), name: "",state: state] as Race
                if(!race.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    race.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(race.class)} ${race.code}"                
                }                    
            }
            s.flush()
            s.clear()
        }
    }
    static def updatePhones() {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT DISTINCT
                    trim(both from tv.daytime_area_code) as "areaCode",
                    trim(both from tv.daytime_phone_number) as "number",
                    trim(both from tv.daytime_phone_extension) as "extension"
                FROM "florida_staging_data" tv
                EXCEPT
                SELECT
                        area_code as "areaCode",
                        number as "number",
                        extension as "extension"
                FROM phone
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def phone = [
                    areaCode: raw.areaCode.trim(), 
                    number: raw.number.trim(), 
                    extension: raw.extension.trim()
                ] as Phone
                if(!phone.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    phone.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(phone.class)} ${phone.number}"                
                }                    
            }
            s.flush()
            s.clear()        
        }
    }
    static def updatePrecincts(State state) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT DISTINCT
                    trim(both from tv.precinct) as "precinct",
                    trim(both from tv.precinct_group) as "precinctGroup",
                    trim(both from tv.precinct_split) as "precinctSplit",
                    trim(both from tv.precinct_suffix) as "precinctSuffix"
                FROM "florida_staging_data" tv
                EXCEPT
                SELECT
                        precinct,
                        precinct_group as "precinctGroup",
                        precinct_split as "precinctSplit",
                        precinct_suffix as "precinctSuffix"
                FROM precinct p
                WHERE
                        state_id = ${state.id}
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def precinct = [
                    precinct: raw.precinct.trim(), 
                    precinctGroup: raw.precinctGroup.trim(), 
                    precinctSplit: raw.precinctSplit.trim(), 
                    precinctSuffix: raw.precinctSuffix.trim(), 
                    state: state
                ] as Precinct
                if(!precinct.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    precinct.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(precinct.class)} ${precinct.precinct}"                
                }                    
            }
            s.flush()
            s.clear()
        }
    }
    static def updateParties(State state) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
            SELECT DISTINCT
                trim(both from tv.party_affiliation) as "code"
            FROM "florida_staging_data" tv 
            LEFT JOIN party p ON p.code = trim(both from tv.party_affiliation) AND p.state_id = ${state.id}
            WHERE length(trim(both from tv.party_affiliation)) > 0 AND p.code IS NULL 
        """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def party = [code: raw.code.trim(), name: ""] as Party
                if(!party.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    party.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(party.class)} ${party.code}"                
                }                    
            }
            s.flush()
            s.clear()
        }
    }
    
    static def appendUniqueParties(State state) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT DISTINCT 
                    trim(both from tv.party_affiliation) as "code" 
                FROM "florida_staging_data" tv 
                WHERE length(trim(both from tv.party_affiliation)) > 0 AND 
                NOT EXISTS (
                    SELECT p.code
                    FROM party p
                    WHERE p.code = trim(both from tv.party_affiliation) AND
                    state_id = ${state.id}
                )
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def party = [code: raw.code.trim()] as Party
                if(!party.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    party.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(party.class)} ${party.code}"                
                }                    
            }
            s.flush()
            s.clear()
        }        
    }
    static def updateStates() {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT rs.code FROM (
                        SELECT 
                                trim(both from tv.residence_state) as "code" 
                        FROM "florida_staging_data" tv 
                        WHERE tv.suppress_address = 'N' AND 
                        length(trim(both from tv.residence_state)) > 0
                        UNION
                        SELECT  
                            trim(both from tv.mailing_state) as "code" 
                        FROM "florida_staging_data" tv 
                        WHERE tv.suppress_address = 'N' AND 
                        length(trim(both from tv.mailing_state)) > 0	
                ) rs
                EXCEPT
                SELECT
                        s.code
                FROM state s
        """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->                        
                def state = [code: raw.code.trim(), name: ""] as State
                if(!state.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    state.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(state.class)} ${state.name}"                
                }                    
            }

            s.flush()
            s.clear()
        }
    }
    static def updateVoterKeys(ImportKey importKey) {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT tv.voter_id 
                FROM florida_staging_data tv
                EXCEPT
                SELECT
                    vk.voter_id as "voter_id"
                FROM voter_key vk
                WHERE
                    vk.import_key_id = ${importKey.id}
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def voterKey = [voterId: raw.voter_id.trim(),importKey: importKey] as VoterKey
                if(!voterKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    voterKey.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(voterKey.class)} ${voterKey.voterId}"                
                }                    
            }    
        }
    }
    
    static def appendUniqueStates() {
        FloridaStagingData.withSession { s ->
            s.createSQLQuery("""
                SELECT DISTINCT 
                    trim(both from tv.residence_state) as "code" 
                FROM "florida_staging_data" tv 
                WHERE tv.suppress_address = 'N' AND 
                length(trim(both from tv.residence_state)) > 0 AND 
                NOT EXISTS (
                    SELECT
                        s.code
                    FROM state s
                    WHERE s.code=trim(both from tv.residence_state)
                )
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->
                def state = [code: raw.code.trim()] as State
                if(!state.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    state.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(state.class)} ${state.name}"                
                }                    
            }            
            s.createSQLQuery("""
                SELECT DISTINCT 
                    trim(both from tv.mailing_state) as "code" 
                FROM "florida_staging_data" tv 
                WHERE tv.suppress_address = 'N' AND 
                length(trim(both from tv.mailing_state)) > 0 AND 
                NOT EXISTS (
                    SELECT
                        s.code
                    FROM state s
                    WHERE s.code = trim(both from tv.mailing_state)
                )
            """).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list().each { raw ->                        
                def state = [code: raw.code.trim()] as State
                if(!state.save(failOnError:true, flush: true, insert: true, validate: true)) {
                    state.errors.allErrors.each {
                        println it
                    }
                } else {
                    println "Created new ${GrailsNameUtils.getShortName(state.class)} ${state.name}"                
                }                    
            }
            s.flush()
            s.clear()
        }
    }
}
