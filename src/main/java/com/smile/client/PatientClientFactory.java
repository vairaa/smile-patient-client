package com.smile.client;

public class PatientClientFactory {

	private static PatientRestClient client;
	
	public static PatientRestClient buildPatientRestClient() {
		if(client==null) {
			client= new PatientRestClient();
		}
		return client;
	}

}
