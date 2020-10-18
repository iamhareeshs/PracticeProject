package common;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class Util {
	static int waitFactor = 1;

	public void clickElement(String element) throws Exception {
		try {
			WebElement actual_element = Start.driver.findElement(By.cssSelector(element));
			(new WebDriverWait(Start.driver, 60 * 3)).until(ExpectedConditions.elementToBeClickable(actual_element));
			actual_element.click();
		} catch (Exception e) {

		}
	}

	public String getTextvalue(String element) throws Exception {
		try {
			WebElement actual_element = Start.driver.findElement(By.cssSelector(element));
			(new WebDriverWait(Start.driver, 60 * waitFactor)).until(ExpectedConditions.visibilityOf(actual_element));
			System.out.println(actual_element.getText().trim());
			return actual_element.getText().trim();
		} catch (Exception e) {

		}
		return null;

	}

	public void sendKeyCssSelector(String element, String value) throws Exception {
		try {
			WebElement actual_element = Start.driver.findElement(By.cssSelector(element));
			(new WebDriverWait(Start.driver, 60 * waitFactor))
					.until(ExpectedConditions.elementToBeClickable(actual_element));
			actual_element.sendKeys(value);
		} catch (Exception e) {

		}
	}

	public String generateRandomNumber() {
		return String.valueOf((int) (Math.random() * 1000));
	}

	public ResponseEntity<?> postRequest(String url, String payLoads) throws Exception {
		ResponseEntity<?> re=null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate rt = new RestTemplate();
		rt.setErrorHandler(new DefaultResponseErrorHandler() {
		    @Override
		    public boolean hasError(HttpStatus statusCode) {
		        return false;
		    }
		});
		HttpEntity<String> entity = new HttpEntity<String>(payLoads, headers);
		try {
				re = rt.exchange(url, HttpMethod.POST, entity, String.class);
		}catch(Exception e) {
				re=rt.getForEntity(url, String.class);
		}
		return re;
	}

	public HashMap<String, String> buildPayLoadMap(String payloadString) {
		payloadString = payloadString.replaceAll("\"", "").replaceAll("}", "");
		HashMap<String, String> payloadSubMap = new HashMap<String, String>();
		for (String eachAttribute : payloadString.split(",")) {
			payloadSubMap.put(eachAttribute.split(":")[0].trim(), eachAttribute.split(":")[1].trim());
		}
		System.out.println("payloadSubMap>>" + payloadSubMap);
		return payloadSubMap;
	}
}
