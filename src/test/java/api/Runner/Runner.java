package api.Runner;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {".\\src\\test\\resources\\feature"},
		glue = {"api.Steps"},
		plugin = {"pretty",
				"html:target/API.html","json:target/API.json",
		
				"junit:target/cucumber-reports/cucumber.xml",
			"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
		dryRun=false
		//tags="@token and @Patient_Morbidity_Details"
		
		
		)
public class Runner {

} 