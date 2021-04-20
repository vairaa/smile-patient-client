package com.smile.entry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.smile.client.exception.PatientClientException;
import com.smile.client.model.PatientDetail;
import com.smile.client.service.FhirClientServiceIntf;
import com.smile.client.service.FhirPatientClientServiceImpl;


public class SampleClient {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SampleClient.class);
    public static void main(String[] theArgs) {

    	FhirClientServiceIntf service = new FhirPatientClientServiceImpl();
    	
    	
		try {
			/**Basic Task - START for self Understanding
			service.printPatientDetails(false); // List all patient details with no sorting
	    	service.printPatientDetails(true); // List all patient details with soring
	    	service.storePatientDetailsToDisk(); // Store in local disk
	    	List<PatientDetail> patients = service.loadPatientDetailsFromDisk();
			/**Basic Task - End for self Understanding **/
	    	Path file =  Paths.get("C:\\Users\\vaira\\patientsinfo.out");
			
			int noOfReruns = 1;
			do {
				log.info("Run Attemppt #{}",noOfReruns);
				Stream<String> lines  = Files.lines(file);
				lines.forEach(n-> {
				try {
					service.search(n);
				}catch(Exception  pce) {
					// Swallow Exception and keep looping
					log.error("Error while searching name {} exception is {}",
							n,pce.getMessage());
				}
				});
			}while(noOfReruns++!=3);
			
		} catch ( Exception e) {
			log.error("Error while searching  exception = {} ",e.getMessage() );
		}
    	
    }

}
