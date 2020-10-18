package scenario_3;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Util;

public class RegisterStation {
	Util util = new Util();
	GetStationDetails getStationObj = new GetStationDetails();
	public static String deleteAPIUrl = "http://api.openweathermap.org/data/3.0/stations/%stationId?APPID=%apikey";
	public static String apikey = "c903f393e271c8b14fdacde45024c554";
	String successCode = "";
	static HashMap<String, HashMap<String, String>> stationPayLoadmap = new HashMap<String, HashMap<String, String>>();
	static HashMap<String, HashMap<String, String>> stationResponseMap = new HashMap<String, HashMap<String, String>>();
	/*
	 *Method name :validateRegisterStation
	 *Purpose :Validate whether the weather station registration API is success
	 */
	@Test
	@Parameters({ "statusCode" })
	public void validateRegisterStation(String statusCode) throws Exception {
		successCode = statusCode;
		String url = "http://api.openweathermap.org/data/3.0/stations?APPID=" + apikey;
		ArrayList<String> payloads = new ArrayList<String>();
		payloads.add("{\"external_id\": \"DEMO_TEST001\",\"name\": \"Interview Station" + util.generateRandomNumber()
				+ "\",\"latitude\": 33.33,\"longitude\": -111.43,\"altitude\": 444}");
		payloads.add("{\"external_id\": \"SF_TEST001\",\"name\": \"Interview Station" + util.generateRandomNumber()
				+ "\",\"latitude\": 33.44,\"longitude\": -12.43,\"altitude\": 444}");
		int stationId = 1;
		for (String payload : payloads) {
			ResponseEntity<?> re = util.postRequest(url, payload);
			Assert.assertEquals(re.getStatusCode().toString(), successCode, "Failed to register station !");
			stationPayLoadmap.put(String.valueOf(stationId), util.buildPayLoadMap(payload));
			@SuppressWarnings("unchecked")
			HashMap<String, String> response = new ObjectMapper().readValue(re.getBody().toString(), HashMap.class);
			stationResponseMap.put(String.valueOf(stationId), response);
			stationId++;
		}
	}

}
