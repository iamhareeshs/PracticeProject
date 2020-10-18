package scenario_3;

import java.util.HashMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetStationDetails {
	/*
	 *Method name :validateStationDetails
	 *Purpose :Validate whether station registered is saved successfully and 
	 *validating the response data against the expected data
	 */
	String getStationDetailsUrl = "http://api.openweathermap.org/data/3.0/stations/%stationID?APPID=%apikey";
	@Test
	private void validateStationDetails() throws Exception {
		System.out.println("Inside validateStationDetails");
		for (String stationId: RegisterStation.stationResponseMap.keySet()) {
			String ID=RegisterStation.stationResponseMap.get(stationId).get("ID");
			System.out.println("ID >>"+ID);
			HashMap<String, String> responseMap=getStationDetails(ID);
			validateStationData(responseMap,RegisterStation.stationPayLoadmap.get(stationId));
		}	
	}
	/*
	 *Method name :getStationDetails
	 *Purpose : return the get station details api response
	 */
	private HashMap<String, String> getStationDetails(String ID) throws Exception {
		String payLoad = "";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		RestTemplate rt = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>(payLoad, headers);
		ResponseEntity<?> re = rt.exchange(getStationDetailsUrl.replace("%apikey", RegisterStation.apikey).replace("%stationID", ID), HttpMethod.GET, entity, String.class);
		@SuppressWarnings("unchecked")
		HashMap<String, String> response = new ObjectMapper().readValue(re.getBody().toString(), HashMap.class);
		return response;
	}
	/*
	 *Method name :validateStationData
	 *Purpose : Validating the registered station details against the get api response
	 */
	private  void validateStationData(HashMap<String, String> responseMap,HashMap<String, String> expectedMap) {
		for(String key:expectedMap.keySet()) {
			System.out.println("key>>"+key);
			if(responseMap.containsKey(key)) {
				Assert.assertEquals(String.valueOf(responseMap.get(key)), String.valueOf(expectedMap.get(key)),"Value is not saved properly in DB for the field "+key);
			}
		}
	}
}
