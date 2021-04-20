package com.smile.client;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import com.smile.client.exception.PatientClientException;
import com.smile.client.interceptor.PatientClientInterceptor;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import jdk.internal.org.jline.utils.Log;

public class PatientRestClient {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PatientRestClient.class);

	private static String RESTURL =  "http://hapi.fhir.org/baseR4";
	private static String PATIENT_RESOURCE="Patient";
	
    private IGenericClient client;
    
    private IGenericClient getPatientClient() {
    	if(client==null) {
    		 client= FhirContext.forR4().
    				 newRestfulGenericClient(RESTURL);
    		 client.registerInterceptor(new PatientClientInterceptor());
    	}
    	return client;
    }
    
	public  Bundle search(String familyName) throws PatientClientException {
		//log.info("Inside Rest Client {}",familyName);
		Bundle response = null;
		try {
			getPatientClient().
				search().forResource(PATIENT_RESOURCE)
				.where(Patient.FAMILY.matches().value(familyName))
				.returnBundle(Bundle.class).execute();
				return response;
		}catch(Exception e) {
			throw new PatientClientException("XXX-001", e.getMessage());
		}
	}


	public Bundle listAllPatients(String resourceName) throws PatientClientException {
    	Bundle response = null;
		try {
			getPatientClient().
				search().forResource(resourceName)
				.returnBundle(Bundle.class).execute();
				return response;
		}catch(Exception e) {
			throw new PatientClientException("XXX-001", e.getMessage());
		}
	}

    
    
}
