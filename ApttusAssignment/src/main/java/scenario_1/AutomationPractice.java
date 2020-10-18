package scenario_1;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import common.Start;
import common.Util;

public class AutomationPractice {
	Util util = new Util();
	static HashMap<String, String> prductDetails = new HashMap<String, String>();
	/*
	 * Method name :loginToApplication Purpose : Log into application portal
	 */
	@Test(priority = 1)
	@Parameters({ "email", "password", "username" })
	public void loginToApplication(String email, String password, String username) throws Exception {
		System.out.println("Inside loginToApplication");
		Start.driver.get("http://Automationpractice.com/");
		Thread.sleep(8000);
		String login_css = ".login";
		String email_css = "#email";
		String password_css = "#passwd";
		String signInButton = "#SubmitLogin";
		util.clickElement(login_css);
		util.sendKeyCssSelector(email_css, email);
		util.sendKeyCssSelector(password_css, password);
		util.clickElement(signInButton);
		System.out.println("Signin");
		Assert.assertEquals(util.getTextvalue(".account"), username,
				"Failed to login to the application portal for the user " + username);
		Thread.sleep(2000);
	}

	/*
	 * Method name :validateAddToCart Purpose :Validate Add to cart functionality
	 */
	@Test(priority = 2)
	private void validateAddToCart() throws Exception {

		String tshirtMenu_css = "#block_top_menu>ul>li>a[title='T-shirts']";
		String product_css = ".right-block a[title='Faded Short Sleeve T-shirts']";
		String successMessage_css = ".cross+h2";
		String productTitle_css = "#layer_cart_product_title";
		String specification_css = "#layer_cart_product_attributes";
		String quantity_css = "#layer_cart_product_quantity";
		String total_css = "#layer_cart_product_price";
		String addToCart_css = "#add_to_cart button";
		util.clickElement(tshirtMenu_css);
		util.clickElement(product_css);
		util.clickElement(addToCart_css);
		Assert.assertEquals(prductDetails.get("name"), util.getTextvalue(productTitle_css), "Displayed value is wrong");
		Assert.assertEquals(prductDetails.get("message"), util.getTextvalue(successMessage_css),
				"Displayed value is wrong");
		Assert.assertEquals(prductDetails.get("Quantity"), util.getTextvalue(quantity_css) + "Quantity",
				"Displayed value is wrong");
		Assert.assertEquals(prductDetails.get("Total"), util.getTextvalue(total_css), "Displayed value is wrong");
		Assert.assertEquals(prductDetails.get("specification"), util.getTextvalue(specification_css),
				"Displayed value is wrong");

	}
	/*
	 * Method name :readPrductDetails Purpose : Read expected Product Details from
	 * test data excel sheet testData is stored in test_data folder
	 */
	@BeforeClass
	private void readPrductDetails() throws Exception {
		FileInputStream testDataFile = new FileInputStream(new File(".//test_data//productTestData.xlsx"));
		Workbook testdataWB = new XSSFWorkbook(testDataFile);
		Sheet testDataSheet = testdataWB.getSheet("product");
		Iterator<Row> iterator = testDataSheet.iterator();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			String key = currentRow.getCell(0).getStringCellValue();
			String value = currentRow.getCell(1).getStringCellValue();
			prductDetails.put(key, value);
		}
		testdataWB.close();
	}
}
