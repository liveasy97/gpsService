
package com.spring.gpsApiData.entities;

import lombok.Data;


@Data
public class gpsData {
	
	private String imei;
	private String lat;
	private String lng;
	private String speed;
	private String deviceName;
	private String powerValue;
	private String direction;
	private String gpsTime;

}
