package org.greens.VoterContactManager

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import org.greens.VoterContactManager.UploadDataService
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.apache.commons.fileupload.servlet.ServletFileUpload
import org.apache.commons.fileupload.disk.DiskFileItem
import org.apache.commons.fileupload.FileItem
import grails.converters.*
import org.quartz.*
import static org.quartz.JobBuilder.*
import static org.quartz.SimpleScheduleBuilder.*
import static org.quartz.TriggerBuilder.*
import static org.quartz.DateBuilder.*
import grails.plugin.quartz2.InvokeMethodJob
import grails.plugins.springsecurity.Secured

class UploadDataController {
    def uploadDataService
    def quartzScheduler
    def index = {
        
    }
    def uploadVoterData = { 
        def jobDataMap = new JobDataMap([targetObject:org.greens.VoterContactManager.UploadDataService,targetMethod:'uploadVoterData',arguments:[new ZipInputStream(request.getFile("contents").getInputStream()),params.stateCode]])
        JobDetail job2 = newJob(InvokeMethodJob.class).withIdentity("Import Florida Voters")
            .usingJobData(jobDataMap)
            .build()        
        Trigger trigger2 = newTrigger().withIdentity("Import Florida Voters Trigger")
            // .withSchedule(simpleSchedule().withIntervalInSeconds(4).repeatForever())  
            .startNow()
            .build()
        if(quartzScheduler == null ) {
            println "IS NULL"
        }
        quartzScheduler.scheduleJob(job2, trigger2)        
        // uploadDataService.uploadVoterData(new ZipInputStream(request.getFile("contents").getInputStream()))
        render request.getFileMap().collectEntries { m ->
            return [name: m.key]
        } as JSON
        println "Done"
    }
    def uploadHistoryData = { 
        def jobDataMap = new JobDataMap([targetObject:org.greens.VoterContactManager.UploadDataService,targetMethod:'uploadHistoryData',arguments:[new ZipInputStream(request.getFile("contents").getInputStream()),params.stateCode]])
        JobDetail job2 = newJob(InvokeMethodJob.class).withIdentity("Import Florida Histories")
            .usingJobData(jobDataMap)
            .build()        
        Trigger trigger2 = newTrigger().withIdentity("Import Florida Histories Trigger")
            // .withSchedule(simpleSchedule().withIntervalInSeconds(4).repeatForever())  
            .startNow()
            .build()

        quartzScheduler.scheduleJob(job2, trigger2)        
        // uploadDataService.uploadVoterData(new ZipInputStream(request.getFile("contents").getInputStream()))
        render request.getFileMap().collectEntries { m ->
            return [name: m.key]
        } as JSON
    }
}
