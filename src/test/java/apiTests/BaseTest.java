package apiTests;

import configuration.Configuration;
import org.testng.annotations.BeforeTest;
import utils.Utils;

public class BaseTest extends Utils {
    @BeforeTest(alwaysRun = true)
    public void initAll() {
        Configuration.load();
    }
}
