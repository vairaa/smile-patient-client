/**
 * 
 */
package com.smile.client.model;

import java.io.Serializable;

/**
 * @author vaira
 *
 */
public class PatientDetail implements Serializable{
    private static final long serialVersionUID = 1L;

	private int patientId;
	private String firstName="";
	private String lastName="";
	private String dob=""; // TODO set the date format;
	
	
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "PatientDetail "
				+ "[patientId=" + patientId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	
}
