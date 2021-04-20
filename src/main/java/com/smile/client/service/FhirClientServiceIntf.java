package com.smile.client.service;

import java.util.List;

import com.smile.client.exception.PatientClientException;
import com.smile.client.model.PatientDetail;

public interface FhirClientServiceIntf {

	/**
	 * Search by Family Name
	 * @param familyName
	 * @return
	 */
	public List<PatientDetail> search(String familyName) throws PatientClientException;
	
	/**
	 * List the resource details;
	 *
	 * @param resourceName
	 * @return
	 */
	public void printPatientDetails(boolean sortByFirstName) throws PatientClientException;

	

	
	/**
	 * This enables to store the patient details in a given file name;
	 * 
	 * @param noOfPatients
	 * @param fileName
	 */
	public void storePatientDetailsToDisk() throws PatientClientException;

	/**
	 * This enables to store the patient details in a given file name;
	 * 
	 * @param noOfPatients
	 * @param fileName
	 * @return 
	 */
	public List<PatientDetail> loadPatientDetailsFromDisk() throws PatientClientException;

}
