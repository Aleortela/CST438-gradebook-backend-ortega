package com.cst438.services;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;



import com.cst438.domain.CourseDTOG;

public class RegistrationServiceREST extends RegistrationService {

	
	RestTemplate restTemplate = new RestTemplate();
	
	@Value("${registration.url}") 
	String registration_url;
	
	public RegistrationServiceREST() {
		System.out.println("REST registration service ");
	}
	
	@Override
	public void sendFinalGrades(int course_id , CourseDTOG courseDTO) { 
		
		//TODO  complete this method in homework 4
		System.out.println("Service Rest: Sending final grades for " + course_id+" "+courseDTO);
		restTemplate.put(registration_url+"/course/"+course_id, courseDTO);	
		System.out.println("Sent final grades "+courseDTO.grades);
		
		
		
	}
}

