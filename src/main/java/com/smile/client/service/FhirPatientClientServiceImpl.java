package com.smile.client.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import com.smile.client.PatientClientFactory;
import com.smile.client.PatientRestClient;
import com.smile.client.exception.PatientClientException;
import com.smile.client.model.PatientDetail;
import com.smile.client.util.ResponseUtil;

public class FhirPatientClientServiceImpl implements FhirClientServiceIntf {

	
	private static String RESOURCE_PATIENT = "Patient";

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FhirPatientClientServiceImpl.class);


	@Override
	public void printPatientDetails(boolean sortByFirstName) throws PatientClientException{
		Bundle response = null;
		try {
			response = PatientClientFactory.buildPatientRestClient().
					listAllPatients("Patient");
			if (sortByFirstName) {
				ResponseUtil.transformAndSortByFirstName(response).forEach(patient -> log.info("{},{},{}",
						patient.getFirstName(), patient.getLastName(), patient.getDob()));
			} else {
				ResponseUtil.transform(response).forEach(patient ->
				log.info("{},{},{}", patient.getFirstName(),
						patient.getLastName(), patient.getDob()));
			}
		} catch (Exception e) {
			log.error("Exception occured {}", e.getMessage());
		}
	}

	@Override
	public void storePatientDetailsToDisk() throws PatientClientException{
		// Search for Patient resources
		Bundle response = null;
		try {
			response = PatientClientFactory.buildPatientRestClient().search("Patient");			
			List<PatientDetail> patients = ResponseUtil.transform(response);
			ResponseUtil.storePatientDetails(patients);
		} catch (Exception e) {
			log.error("Exception occured {}", e.getMessage());
		}
	}

	@Override
	public List<PatientDetail> loadPatientDetailsFromDisk() throws PatientClientException{
		 try {
			 List<PatientDetail> patients= ResponseUtil.LoadPatientDetails();
					 patients.forEach(p-> {
				log.info("Loading from File {},{},{}",p.getFirstName(),
						p.getLastName(), p.getDob());
			});
			 return patients;

		} catch (ClassNotFoundException | IOException e) {
			throw new PatientClientException("YYY","Exception while loading file from disk "+e.getMessage());
		}
	}

	@Override
	public List<PatientDetail> search(String familyName) throws PatientClientException {
		if(familyName!=null && ! familyName.isBlank() && !familyName.isEmpty() ) {
			return ResponseUtil.transform(
				PatientClientFactory.buildPatientRestClient().search(familyName));
		}else
			throw new PatientClientException("ZZZ","Invalid Input");
	}

	
	 
}
