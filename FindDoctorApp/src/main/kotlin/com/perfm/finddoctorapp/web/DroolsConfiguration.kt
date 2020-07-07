package com.perfm.finddoctorapp.web

import org.kie.api.KieServices
import org.kie.api.builder.Message
import org.kie.api.runtime.KieContainer
import org.kie.internal.io.ResourceFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
@ComponentScan("com.perfm.finddoctorapp")
class DroolsConfiguration {

//    val hospitalValidationDrlFile = "HospitalValidation.drl"
//    val doctorValidationDrlFile = "DoctorValidation.drl"

    @Bean
    fun kieContainer(): KieContainer? {
        val kieServices = KieServices.Factory.get()
        val kieFileSystem = kieServices.newKieFileSystem()
        val dir = File("src/main/resources")
        val directoryListing: Array<File> = dir.listFiles()
        if (directoryListing != null) {
            for(child in directoryListing)
                kieFileSystem.write(ResourceFactory.newClassPathResource(child.name));
        }
        //kieFileSystem.write(ResourceFactory.newClassPathResource(doctorValidationDrlFile)).write(ResourceFactory.newClassPathResource(hospitalValidationDrlFile))
        val kieBuilder = kieServices.newKieBuilder(kieFileSystem)
        kieBuilder.buildAll()
//        val errorSize =  kieBuilder.getResults().getMessages(Message.Level.ERROR).size
        val kieModule = kieBuilder.kieModule
        return kieServices.newKieContainer(kieModule.releaseId)
    }
}