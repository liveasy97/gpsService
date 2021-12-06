package com.spring.gpsApiData.model;

import lombok.Data;

@Data
public class DeviceTrackListModel {

	String gpsSpeed;
	double lat;
	double lng;
	String gpsTime;
	String satellite;
	String direction;
	String posType;

}
