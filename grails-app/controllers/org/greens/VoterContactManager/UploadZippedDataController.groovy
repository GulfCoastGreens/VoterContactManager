package org.greens.VoterContactManager
import grails.converters.*
import org.quartz.*
import static org.quartz.JobBuilder.*
import static org.quartz.SimpleScheduleBuilder.*
import static org.quartz.TriggerBuilder.*
import static org.quartz.DateBuilder.*
import grails.plugin.quartz2.InvokeMethodJob
import java.util.zip.ZipInputStream

class UploadZippedDataController {
    def quartzScheduler

    def index() { }
    
    def uploadZip = {
        switch(params.stateCode) {
            case 'FL':
                switch(params.zipContentType) {
                    case 'voter':
                        def jobDataMap = new JobDataMap([targetObject:org.greens.VoterContactManager.FloridaService,targetMethod:'processVoters',arguments:[new ZipInputStream(request.getFile("contents").getInputStream())]])
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
                        break
                    case 'history':
                        def jobDataMap = new JobDataMap([targetObject:org.greens.VoterContactManager.FloridaService,targetMethod:'processHistories',arguments:[new ZipInputStream(request.getFile("contents").getInputStream())]])
                        JobDetail job2 = newJob(InvokeMethodJob.class).withIdentity("Import Florida Histories")
                            .usingJobData(jobDataMap)
                            .build()        
                        Trigger trigger2 = newTrigger().withIdentity("Import Florida Histories Trigger")
                            // .withSchedule(simpleSchedule().withIntervalInSeconds(4).repeatForever())  
                            .startNow()
                            .build()
                        if(quartzScheduler == null ) {
                            println "IS NULL"
                        }
                        quartzScheduler.scheduleJob(job2, trigger2)        
                        break
                }
                break;
            case 'GA':
                switch(params.zipContentType) {
                    case 'voter':
                        def jobDataMap = new JobDataMap([targetObject:org.greens.VoterContactManager.GeorgiaService,targetMethod:'processVoters',arguments:[new ZipInputStream(request.getFile("contents").getInputStream())]])
                        JobDetail job2 = newJob(InvokeMethodJob.class).withIdentity("Import Georgia Voters")
                            .usingJobData(jobDataMap)
                            .build()        
                        Trigger trigger2 = newTrigger().withIdentity("Import Georgia Voters Trigger")
                            // .withSchedule(simpleSchedule().withIntervalInSeconds(4).repeatForever())  
                            .startNow()
                            .build()
                        if(quartzScheduler == null ) {
                            println "IS NULL"
                        }
                        quartzScheduler.scheduleJob(job2, trigger2)        
                        break
                    case 'history':
                        def jobDataMap = new JobDataMap([targetObject:org.greens.VoterContactManager.GeorgiaService,targetMethod:'processHistories',arguments:[new ZipInputStream(request.getFile("contents").getInputStream())]])
                        JobDetail job2 = newJob(InvokeMethodJob.class).withIdentity("Import Georgia Histories")
                            .usingJobData(jobDataMap)
                            .build()        
                        Trigger trigger2 = newTrigger().withIdentity("Import Georgia Histories Trigger")
                            // .withSchedule(simpleSchedule().withIntervalInSeconds(4).repeatForever())  
                            .startNow()
                            .build()
                        if(quartzScheduler == null ) {
                            println "IS NULL"
                        }
                        quartzScheduler.scheduleJob(job2, trigger2)        
                        break
                }
                break                
        }
        render request.getFileMap().collectEntries { m ->
            return [name: m.key]
        } as JSON        
    }
}
