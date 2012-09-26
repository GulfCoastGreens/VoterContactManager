package org.greens.VoterContactManager

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.text.SimpleDateFormat
import org.greens.VoterContactManager.MergeDataService
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import grails.plugin.quartz2.InvokeMethodJob
import org.springframework.web.multipart.commons.CommonsMultipartFile
import grails.util.GrailsNameUtils
import org.greens.VoterContactManager.*

class UploadDataService {
    static transactional = true
    def mergeDataService
    def dataMap
    static uploadVoterData(ZipInputStream zin,String stateCode) {
        MergeDataService mergeDataService = new MergeDataService()
        def importKey
        // wrap the ZipInputStream with an InputStreamReader 
        InputStreamReader isr = new InputStreamReader(zin);
        BufferedReader br = new BufferedReader(isr);        
        ZipEntry ze = null
        String line
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        while ((ze = zin.getNextEntry()) != null) {
            System.out.println("Unzipping " + ze.getName())
            Date modificationTime = sdf.parse(sdf.format(new Date(ze.getTime())))
            System.out.println("Modification Date "+modificationTime)
            // ALA_20120806.txt
            if(importKey == null) {
                Date snapshotDate
                switch(stateCode) {
                    case "FL":
                        snapshotDate = new SimpleDateFormat("yyyyMMdd").parse(ze.getName()[17..24])
                        break
                    case "GA":
                        snapshotDate = sdf.parse(sdf.format(new Date(ze.getTime())))
                        break
                    default:
                        snapshotDate = sdf.parse(sdf.format(new Date(ze.getTime())))
                        break
                }
                importKey = ImportKey.findBySnapshotDateAndState(snapshotDate,State.findByCode(stateCode))
                if(importKey == null) {
                    importKey = new ImportKey([state: State.findByCode(stateCode),snapshotDate: snapshotDate])
                    if(!importKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        importKey.errors.allErrors.each {
                            println it
                        }                
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(importKey.class)} ${importKey.state.name}"                
                    }                    
                }                
            }            
            System.out.println("Date " + importKey.snapshotDate)
            while ((line = br.readLine()) != null) {
                switch(stateCode) {
                    case "FL":
                        mergeDataService.mergeFloridaVoterRecord(line,importKey)
                        break
                    case "GA":
                        mergeDataService.mergeGeorgiaVoterRecord(line,importKey)
                        break
                }
                // println(line)
            }
        }
        br.close()
        zin.close()        
    }
    static uploadFloridaVoterData(ZipInputStream zin) {
        // CommonsMultipartFile contents
        // ZipInputStream zin = new ZipInputStream(contents.getInputStream())
        MergeDataService mergeDataService = new MergeDataService()
        def importKey
        // wrap the ZipInputStream with an InputStreamReader 
        InputStreamReader isr = new InputStreamReader(zin);
        BufferedReader br = new BufferedReader(isr);        
        ZipEntry ze = null
        String line
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        // ZipEntry ze gives you access to the filename etc of the entry in the zipfile you are currently handling
        while ((ze = zin.getNextEntry()) != null) {
            System.out.println("Unzipping " + ze.getName())
            Date modificationTime = sdf.parse(sdf.format(new Date(ze.getTime())))
            System.out.println("Modification Date "+modificationTime)
            // ALA_20120806.txt
            if(importKey == null) {
                Date snapshotDate = new SimpleDateFormat("yyyyMMdd").parse(ze.getName()[17..24])
                importKey = ImportKey.findBySnapshotDateAndState(snapshotDate,State.findByCode('FL'))
                if(importKey == null) {
                    importKey = new ImportKey([state: State.findByCode('FL'),snapshotDate: snapshotDate])
                    if(!importKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        importKey.errors.allErrors.each {
                            println it
                        }                
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(importKey.class)} ${importKey.state.name}"                
                    }                    
                }
            }            
            System.out.println("Date " + importKey.snapshotDate)
            while ((line = br.readLine()) != null) {
                mergeDataService.mergeFloridaVoterRecord(line,importKey)
                // println(line)
            }
        }
        br.close()
        zin.close()
    }
    static uploadHistoryData(ZipInputStream zin,String stateCode) {
        // CommonsMultipartFile contents
        // ZipInputStream zin = new ZipInputStream(contents.getInputStream())
        MergeDataService mergeDataService = new MergeDataService()
        def importKey
        // wrap the ZipInputStream with an InputStreamReader 
        InputStreamReader isr = new InputStreamReader(zin);
        BufferedReader br = new BufferedReader(isr);        
        ZipEntry ze = null
        String line
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        // ZipEntry ze gives you access to the filename etc of the entry in the zipfile you are currently handling
        while ((ze = zin.getNextEntry()) != null) {
            System.out.println("Unzipping " + ze.getName())
            Date modificationTime = sdf.parse(sdf.format(new Date(ze.getTime())))
            System.out.println("Modification Date "+modificationTime)
            // ALA_20120806.txt
            if(importKey == null) {
                Date snapshotDate
                switch(stateCode) {
                    case "FL":
                        snapshotDate = new SimpleDateFormat("yyyyMMdd").parse(ze.getName()[19..26])
                        break
                    case "GA":
                        snapshotDate = sdf.parse(sdf.format(new Date(ze.getTime())))
                        break
                    default:
                        snapshotDate = sdf.parse(sdf.format(new Date(ze.getTime())))
                        break
                }
                importKey = ImportKey.findBySnapshotDateAndState(snapshotDate,State.findByCode(stateCode))
                if(importKey == null) {
                    importKey = new ImportKey([state: State.findByCode(stateCode),snapshotDate: snapshotDate])
                    if(!importKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        importKey.errors.allErrors.each {
                            println it
                        }                
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(importKey.class)} ${importKey.state.name}"                
                    }                    
                }                
            }            
            System.out.println("Date " + importKey.snapshotDate)
            while ((line = br.readLine()) != null) {
                switch(stateCode) {
                    case "FL":
                        mergeDataService.mergeFloridaHistoryRecord(line,importKey)
                        break
                    case "GA":
                        mergeDataService.mergeGeorgiaHistoryRecord(line,importKey)
                        break
                }
                // println(line)
            }
        }
        br.close()
        zin.close()        
    }
    static uploadFloridaHistoryData(ZipInputStream zin) {
        // CommonsMultipartFile contents
        // ZipInputStream zin = new ZipInputStream(contents.getInputStream())
        MergeDataService mergeDataService = new MergeDataService()
        def importKey
        // wrap the ZipInputStream with an InputStreamReader 
        InputStreamReader isr = new InputStreamReader(zin);
        BufferedReader br = new BufferedReader(isr);        
        ZipEntry ze = null
        String line
        // ZipEntry ze gives you access to the filename etc of the entry in the zipfile you are currently handling
        while ((ze = zin.getNextEntry()) != null) {
            System.out.println("Unzipping " + ze.getName())
            // ALA_H_20120806.txt
            if(importKey == null) {
                Date snapshotDate = new SimpleDateFormat("yyyyMMdd").parse(ze.getName()[19..26])
                importKey = ImportKey.findBySnapshotDateAndState(snapshotDate,State.findByCode('FL'))
                if(importKey == null) {
                    importKey = new ImportKey([state: State.findByCode('FL'),snapshotDate: snapshotDate])
                    if(!importKey.save(failOnError:true, flush: true, insert: true, validate: true)) {
                        importKey.errors.allErrors.each {
                            println it
                        }                
                    } else {
                        println "Created new ${GrailsNameUtils.getShortName(importKey.class)} ${importKey.state.name}"                
                    }                    
                }
            }            
            System.out.println("Date " + importKey.snapshotDate)
            while ((line = br.readLine()) != null) {
                mergeDataService.mergeFloridaHistoryRecord(line,importKey)
                // println(line)
            }
        }
        br.close()
        zin.close()
    }
    
    def mergeFloridaVoterRecord(String row,Date snapshotDate) {
        row.eachCsvLine { tokens ->
            
        }
    }
    
}
