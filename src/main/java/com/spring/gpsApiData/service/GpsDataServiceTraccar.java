package com.spring.gpsApiData.service;

import com.spring.gpsApiData.model.DeviceTrackListAndStoppagesListResponse;

public interface GpsDataServiceTraccar {

	public DeviceTrackListAndStoppagesListResponse getHistoryDataUsingTraccar(String imei, String startTime,
			String endTime) throws Exception;

}
