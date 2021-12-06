package com.spring.gpsApiData.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.gpsApiData.model.DeviceTrackListAndStoppagesListResponse;
import com.spring.gpsApiData.model.JimiException;
import com.spring.gpsApiData.service.GpsDataServiceTraccar;

@RestController
public class Controller {

	@Autowired
	private GpsDataServiceTraccar gpsdataService;

	@GetMapping("/locationbyimei")
	public ResponseEntity<DeviceTrackListAndStoppagesListResponse> getHistoryData(
			@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = true) String imei) throws Exception {
		return new ResponseEntity<>(gpsdataService.getHistoryDataUsingTraccar(imei, startTime, endTime), HttpStatus.OK);
	}

	// Exception Handlers //

	@ExceptionHandler(JimiException.class)
	public ResponseEntity<String> handleJimiException(JimiException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
		String name = ex.getParameterName();
		System.out.println(name + " parameter is missing");
		return new ResponseEntity<>(name + " parameter is missing", HttpStatus.BAD_REQUEST);
	}
}