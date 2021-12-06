package com.spring.gpsApiData.service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.spring.gpsApiData.dao.DeviceDataDao;
import com.spring.gpsApiData.dao.TraccarDataDao;
import com.spring.gpsApiData.entities.DeviceData;
import com.spring.gpsApiData.entities.TraccarData;
import com.spring.gpsApiData.entities.gpsData;
import com.spring.gpsApiData.model.DeviceTrackListAndStoppagesListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.spring.gpsApiData.entities.historyData;

@Service
public class GpsDataServiceImpl implements GpsDataService {

	private static final Logger log = LoggerFactory.getLogger(GpsDataServiceImpl.class);

	@Autowired
	private TraccarDataDao dao;

	@Autowired
	private DeviceDataDao deviceDataDao;

/*
	@Override
	public List<historyData> getgpsDataWithSaving(String imei) throws Exception {
		if (imei.contains("transporter") || imei.contains("shipper")) {
			List<historyData> dataList = dao.findByImei(imei);
			List<historyData> result = new ArrayList<>(Collections.emptyList());
			historyData lastEntry = dataList.get(dataList.size() - 1);
			result.add(lastEntry);
			return result;
		} else {
			List<historyData> result = new ArrayList<>(Collections.emptyList());
			historyData gpsData = getDataFromJimi.getGpsApiDataUsingImei(imei);
			result.add(gpsData);
//            gpsData.setId(UUID.randomUUID());
//            dao.save(gpsData);
			return result;
		}
	}
*/

	@Override
	public List<historyData> getgpsDataWithoutSaving(String imei) throws Exception {

		DeviceData device = deviceDataDao.findByImei(imei);

		List<TraccarData> result = dao.findByNId(device.getId());

			TraccarData tData = result.get(0);
			historyData d = new historyData();
			d.setId(UUID.randomUUID());
			d.setImei(String.valueOf(device.getUniqueid()));
			d.setLat(String.valueOf(tData.getLatitude()));
			d.setLng(String.valueOf(tData.getLongitude()));
			d.setDeviceName(device.getName());
			d.setDirection(String.valueOf(tData.getCourse()));
			d.setHbTime(tData.getFixtime().toString());
			d.setSpeed(String.valueOf(tData.getSpeed()));
			d.setGpsTime(tData.getDevicetime().toString());
			d.setPowerValue(null);
			d.setTimeStamp(tData.getServertime().toString());

		List<historyData> resultF = new ArrayList<>();
		resultF.add(d);
		return resultF;


	}

//
//	@Override
//	public String savegpsData(gpsData data) {
//		TraccarData tData = new TraccarData();
//
//		DeviceData dData = tData.getDeviceData();
//		dData.setUniqueid(data.getImei());
//		dData.setName(data.getDeviceName());
//		tData.setLatitude(Double.valueOf(data.getLat()));
//		tData.setLongitude(Double.valueOf(data.getLng()));
//		tData.setSpeed(Float.valueOf(data.getSpeed()));
//		tData.setCourse(Float.valueOf(data.getDirection()));
//		tData.setDevicetime(Date.from(Instant.parse(data.getGpsTime())));
//		return data.toString();
//	}
/*
	@Override
	public List<historyData> getHistoryDataWithSaving(String imei, String startTime, String endTime) {
		if (imei != null) {
			List<historyData> historyDataList = dao.findByImeiBetweenTimeRange(imei, startTime, endTime);

			return historyDataList;
		} else {
			return dao.findHistoryDataBetweenTimeRange(startTime, endTime);
		}
	}

	// fetching data directly from jimi //
	@Override
	public DeviceTrackListAndStoppagesListResponse getHistoryDataDirect(String imei, String startTime,
																		String endTime) throws Exception {

		DeviceTrackListAndStoppagesListResponse deviceTrackList = getDataFromJimi
				.getGpsApiDataUsingImeiStartTimeEndTime(imei, startTime, endTime);
		return deviceTrackList;
	}
/*
	@Override
	public void addImei(String imei) throws Exception {

		RegisteredImeiData registeredimeidata = new RegisteredImeiData();
		registeredimeidata.setImei(imei);
		registeredimeidata.setStatus(Status.active.toString());
		;

		if (rdao.existsByImei(imei)) {
			throw new Exception("Imei Already exists");
		}

		rdao.save(registeredimeidata);
	}

	@Override
	public String saveHistoryData(historyData data) {
		data.setId(UUID.randomUUID());
		dao.save(data);
		return "done";
	}

	@Override
	public RelaySendCommandResponse commandToDevice(IgnitionOffPostRequest ignitionOffPostRequest) throws Exception {

		RelaySendCommandResponse relayInfo = getDataFromJimi.getGpsApiDeviceRelayData(ignitionOffPostRequest);

		RelaySendCommandResponse response = new RelaySendCommandResponse();
		response.setCode(relayInfo.getCode());
		response.setResult(relayInfo.getResult());

		if (relayInfo.getCode() == 12005) {
			if (relayInfo.getMessage().contains("225")) {
				response.setMessage("TimeOut");
			} else if (relayInfo.getMessage().contains("226")) {
				response.setMessage("Parameter error");
			} else if (relayInfo.getMessage().contains("227")) {
				response.setMessage("The command is not executed correctly");
			} else if (relayInfo.getMessage().contains("228")) {
				response.setMessage("The device is not online");
			} else if (relayInfo.getMessage().contains("229")) {
				response.setMessage("Network error, connection error, etc.");
			} else if (relayInfo.getMessage().contains("238")) {
				response.setMessage("Device interrupted");
			} else if (relayInfo.getMessage().contains("240")) {
				response.setMessage("Data format error");
			} else if (relayInfo.getMessage().contains("243")) {
				response.setMessage("Not supported by device");
			} else if (relayInfo.getMessage().contains("252")) {
				response.setMessage("The device is busy");
			}

		} else if (relayInfo.getCode() == 0) {
			response.setMessage("Send command successful");
		}
		return response;
	}

	@Override
	public CreateGeoFenceResponse createGeoFence(CreateGeoFencePostRequest createGeoFencePostRequest) throws Exception {

		CreateGeoFenceResponse createGeoFenceResponse = getDataFromJimi.CreatingGeoFence(createGeoFencePostRequest);
		CreateGeoFenceResponse response = new CreateGeoFenceResponse();
		response.setCode(createGeoFenceResponse.getCode());
		response.setResult(createGeoFenceResponse.getResult());
		if (createGeoFenceResponse.getCode() == 12003) {
			if (createGeoFenceResponse.getMessage().contains("41001")) {
				response.setMessage("Exceed max number of Geo-fences supported");
			} else if (createGeoFenceResponse.getMessage().contains("41002")) {
				response.setMessage("Fence name is already exists");
			} else if (createGeoFenceResponse.getMessage().contains("41003")) {
				response.setMessage("The device is not online");
			} else if (createGeoFenceResponse.getMessage().contains("41004")) {
				response.setMessage("Geo-fence operation failed");
			}
		} else if (createGeoFenceResponse.getCode() == 0) {
			response.setMessage("Create an electronic fence successfully");
		}
		return response;
	}

	@Override
	public RouteHistoryWithTotalDistanceModel routeHistory(String imei, String startTime, String endTime)
			throws Exception {

		return getDataFromJimi.routeHistory(imei, startTime, endTime);
	}

 */
}
