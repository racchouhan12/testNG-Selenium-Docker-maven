1. Drivers is getting initialized in BaseTest
2. All Pages lies in com.main.tests.pages
3. Extent Reporter, Listeners, ExcelUtils, JSONUtils, Command etc lies in com.main.tests.utilities package.
4. For test to run on different Environment I have separated folder in test.resources.TestSuite.
5. Env specific folder will be created inside TestSuite and all testng.xml, properties etc should be laced inside it.
6. Command To Run: mvn test -DsuiteXmlFile=dev_env/testng.xml