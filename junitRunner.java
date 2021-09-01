package com.boi.grp.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/FinalFeatures/UXP_Banking_365"},
        //features = {"src/test/java/com/boi/grp/initialfeatures"},
        plugin = {"pretty","json:target/cucumber-parallel/cucumber.json","junit:target/cucumber-parallel/cucumber.xml","html:target/cucumber-parallel","rerun:target/cucumber-parallel/rerun.txt"},
        dryRun = false,
        tags={"@SMOPFormValidation"},
        glue = {"com.boi"}) 
public class junitRunner {
}