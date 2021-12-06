package com.spring.gpsApiData.model;

import lombok.Data;

@Data
public class StoppagesListModel {
	private String truckStatus;
	private String duration;
	private String startTime;
	private String endTime;
	private double lat;
	private double lng;
}
