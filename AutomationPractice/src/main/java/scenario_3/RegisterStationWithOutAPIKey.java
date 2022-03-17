package scenario_3;

import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.Util;

public class RegisterStationWithOutAPIKey {
	Util util = new Util();

	/*
	 * Method name :validateRegisterStation
	 * Validate the response  of registration API without apikey 
	 */
	@Test
	@Parameters({ "statusCode" })
	private void validateRegisterStation(String statusCode) throws Exception {
		String url = "http://api.openweathermap.org/data/3.0/stations";
		String payload = "{\"external_id\": \"DEMO_TEST001\",\"name\": \"Interview Station"
				+ util.generateRandomNumber() + "\",\"latitude\": 33.33,\"longitude\": -111.43,\"altitude\": 444}";
		ResponseEntity<?> response = util.postRequest(url, payload);
		Assert.assertEquals(response.getStatusCode().toString(), statusCode, "invalid response !");
	}
}
