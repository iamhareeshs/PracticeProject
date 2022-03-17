package scenario_3;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DeleteStation {
String deleteResponseCode="";	
@Test
@Parameters({"statusCode"})
public void validateDeleteAllStation(String statusCode) {
	deleteResponseCode=statusCode;
	for (String stationId : RegisterStation.stationResponseMap.keySet()) {
		String ID=RegisterStation.stationResponseMap.get(stationId).get("ID");
		System.out.println("ID"+ID);
		deleteStation(ID);
	}
}
public void deleteStation(String stationId) {
	String url=RegisterStation.deleteAPIUrl.replace("%stationId", stationId).replace("%apikey",RegisterStation.apikey);
	String payLoad="";
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.TEXT_PLAIN);
	RestTemplate rt = new RestTemplate();
	ResponseEntity<?> re=null;
	rt.setErrorHandler(new DefaultResponseErrorHandler() {
	    @Override
	    public boolean hasError(HttpStatus statusCode) {
	        return false;
	    }
	});
	HttpEntity<String> entity = new HttpEntity<String>(payLoad, headers);
	re = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
	Assert.assertEquals(re.getStatusCode().toString(), deleteResponseCode, "response is not as expected!");
}
}
