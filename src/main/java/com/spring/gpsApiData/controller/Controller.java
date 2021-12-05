package com.spring.gpsApiData.controller;

import java.util.List;

import com.spring.gpsApiData.entities.gpsData;
import com.spring.gpsApiData.entities.historyData;
import com.spring.gpsApiData.model.DeviceTrackListAndStoppagesListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.gpsApiData.service.GpsDataService;

@RestController
public class Controller {

	@Autowired
	private GpsDataService gpsdataService;

	@GetMapping("/locationbyimei/{imei}")
	private List<historyData> getGpsData(@PathVariable String imei) throws Exception {
		return gpsdataService.getgpsDataWithoutSaving(imei);
	}

//	@GetMapping("/locationbyimei")
//	public ResponseEntity<DeviceTrackListAndStoppagesListResponse> getHistoryData(
//			@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
//			@RequestParam(required = true) String imei) throws Exception {
//		return new ResponseEntity<>(gpsdataService.getHistoryDataDirect(imei, startTime, endTime),
//				HttpStatus.OK);
//	}

//	@PostMapping("/locationbyimei")
//	private String saveGpsData(@RequestBody gpsData data) {
//		gpsdataService.savegpsData(data);
//		return "done";
//	}
/*
	@PostMapping("/locationbyimei")
	private String saveHistoryData(@RequestBody historyData data) {
		gpsdataService.saveHistoryData(data);
		return "done";
	}

	@PostMapping("/addimei")
	private ResponseEntity<String> addImei(@RequestBody String imei) {
		try {

			gpsdataService.addImei(imei);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Added Imei Successfully", HttpStatus.OK);
	}

	@PostMapping("/ignitionoff")
	private ResponseEntity<RelaySendCommandResponse> sendInstructionToDevice(
			@RequestBody IgnitionOffPostRequest ignitionOffPostRequest) throws Exception {

		RelaySendCommandResponse responseFromJimi = gpsdataService.commandToDevice(ignitionOffPostRequest);
		if (responseFromJimi.getCode() == 12005 || responseFromJimi.getCode() == 0) {

			return new ResponseEntity<>(responseFromJimi, HttpStatus.OK);
		}

		return new ResponseEntity<>(responseFromJimi, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/creategeofence")
	private ResponseEntity<CreateGeoFenceResponse> createGeoFenceforImei(
			@RequestBody CreateGeoFencePostRequest createGeoFencePostRequest) throws Exception {

		CreateGeoFenceResponse responseFromJimi = gpsdataService.createGeoFence(createGeoFencePostRequest);
		if (responseFromJimi.getCode() == 12003 || responseFromJimi.getCode() == 0) {

			return new ResponseEntity<>(responseFromJimi, HttpStatus.OK);
		}

		return new ResponseEntity<>(responseFromJimi, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/routehistory")
	public ResponseEntity<RouteHistoryWithTotalDistanceModel> routeHistory(
			@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = true) String imei) throws Exception {

		return new ResponseEntity<>(gpsdataService.routeHistory(imei, startTime, endTime), HttpStatus.OK);
	}

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

 */
}