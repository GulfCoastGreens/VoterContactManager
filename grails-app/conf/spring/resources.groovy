// Place your Spring DSL code here
beans = {
   multipartResolver(org.springframework.web.multipart.commons.CommonsMultipartResolver){ 

        // Max in memory 100kbytes 
        maxInMemorySize=10240 

        //1Gb Max upload size 
        maxUploadSize=1024000000 
        
        //uploadTempDir="/tmp" 

    } 
}
