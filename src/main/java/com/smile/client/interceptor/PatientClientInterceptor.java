package com.smile.client.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import ca.uhn.fhir.util.StopWatch;

public class PatientClientInterceptor 
		implements ca.uhn.fhir.rest.client.api.IClientInterceptor {

	private static int noOfRequest=0;
	private static long totalTime;
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PatientClientInterceptor.class);

	private StopWatch watch = new StopWatch();

	@Override
	@Hook(Pointcut.CLIENT_REQUEST)
	public void interceptRequest(IHttpRequest theRequest) {
		++noOfRequest;
		watch.startTask("Task:"+theRequest.getUri());
	}

	@Override
	@Hook(Pointcut.CLIENT_RESPONSE)
	public void interceptResponse(IHttpResponse theResponse) throws IOException {
		theResponse.getRequestStopWatch().endCurrentTask();
		long finishtime = theResponse.getRequestStopWatch().getMillis();
		totalTime = totalTime+finishtime;
		
		//log.info("Response Time for this request: {}", finishtime);
		if(noOfRequest%20 == 0) {
			log.info("Through Response Time of total request {} and total Time: {} ,avg time :{}  System Calculated throughput: {} , System Calculated Avg resp :{} ",noOfRequest,totalTime, totalTime/20, 
					theResponse.getRequestStopWatch().getThroughput(noOfRequest,TimeUnit.SECONDS),theResponse.getRequestStopWatch().getMillisAndRestart());
			noOfRequest=0;
			totalTime=0;
		}
	}
}
