package com.smile.client.util;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.HumanName;

import com.smile.client.model.PatientDetail;

public class ResponseUtil {
	/**
	 * This is a utility method which transform the response into the custom model
	 * 
	 * @param response
	 * @return
	 */
	public static List<PatientDetail> transform(Bundle response) {
		List<PatientDetail> patients = new ArrayList<>();
		if(response!=null && response.getEntry()!=null) {
		response.getEntry().forEach(p -> {
			PatientDetail patient = new PatientDetail();
			if(p.getResource().getChildByName("name") !=null) {
				setNameEntity(p,patient);
				setDob(p,patient);
			}
			patients.add(patient);			
		});
		}
		return patients;
	}
	
	public static Map<String,PatientDetail> transformToMap(Bundle response) {
		Map<String,PatientDetail> patientMap = new HashMap();
		
		response.getEntry().forEach(p -> {
			PatientDetail patient = new PatientDetail();
			if(p.getResource().getChildByName("name") !=null) {
				setNameEntity(p,patient);
				setDob(p,patient);
			}
			if(!patient.getLastName().isEmpty() || !patient.getFirstName().isBlank())
				patientMap.putIfAbsent(patient.getLastName(), patient);
		});
		return patientMap;
	}
	
	public static List<PatientDetail> transformAndSortByFirstName(Bundle response) {
		List<PatientDetail> patients = transform(response);
		return patients.stream().sorted(Comparator.comparing(PatientDetail::getFirstName)).collect(Collectors.toList());
	}
	
	public static void storePatientDetails(List<PatientDetail> patients) throws IOException {

		FileOutputStream file = new FileOutputStream("C:\\Users\\vaira\\patients.out");
		final ObjectOutputStream writer = new ObjectOutputStream(file);
		patients.forEach(p -> {
			try {
				writer.writeObject(p);		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		writer.close();
		file.close();
	}

	public static List<PatientDetail> LoadPatientDetails() throws IOException, ClassNotFoundException {
		List<PatientDetail> patients = new ArrayList<>();

		FileInputStream file = new FileInputStream("C:\\Users\\vaira\\patients.out");
		ObjectInputStream reader = new ObjectInputStream(file);

		while (true) {

			try {
				patients.add((PatientDetail) reader.readObject());

			} catch (EOFException eof) {
				break;
			}
		}
		return patients;
	}
	
	
	private static void setNameEntity(BundleEntryComponent p,PatientDetail patient) {
		if(p.getResource().getChildByName("name") !=null && p.getResource().getChildByName("name").hasValues())  {
				p.getResource().getChildByName("name").getValues().forEach(n -> {
					HumanName obj = p.castToHumanName(n);
					patient.setFirstName(obj.getFamily());
					patient.setLastName(obj.getGivenAsSingleString());
				});
		}
	}
	
	private static void setDob(BundleEntryComponent p,PatientDetail patient) {
		if (p.getResource().getChildByName("birthDate")!=null && p.getResource().getChildByName("birthDate").hasValues()) {
			DateType dt = p.castToDate(p.getResource().getChildByName("birthDate").getValues().get(0));
			patient.setDob(dt.getValueAsString());
		}

	}

}
