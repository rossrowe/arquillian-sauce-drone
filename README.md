This project contains the source code for the [Sauce](http://saucelabs.com) extension for [Arquillian Drone](https://docs.jboss.org/author/display/ARQ/Drone)

To use the library, add the following dependency to your Maven pom.xml file

    <dependency>
        <groupId>com.saucelabs</groupId>
        <artifactId>arquillian-sauce-drone</artifactId>
        <version>0.0.4</version>
        <scope>test</scope>
    </dependency>

    <repositories>
        <repository>
             <id>saucelabs-repository</id>
             <url>https://repository-saucelabs.forge.cloudbees.com/release</url>
             <releases>
                 <enabled>true</enabled>
             </releases>
             <snapshots>
                 <enabled>true</enabled>
             </snapshots>
        </repository>
    </repositories>

Then add the following in your arquillian.xml file:

    <extension qualifier="sauce-webdriver">
            <property name="userName">YOUR_SAUCE_USERNAME</property>
            <property name="accessKey">YOUR_SAUCE_ACCESS_KEY</property>
            <property name="browser">firefox</property>
            <property name="os">Windows 2008</property>
            <property name="version">4.</property>
    </extension>

If you are running builds using the Sauce CI plugins, then the extension will read the appropriate environment variables, so you can just add this to your arquillian.xml file:

	<extension qualifier="sauce-webdriver"/>
	
Then just reference the WebDriver instance in your test as follows:

```java
@RunWith(Arquillian.class)
public class AmazonTest {

    @Drone
    WebDriver driver;

    @Test
    public void amazonTitle() {
        driver.get("http://www.amazon.com/");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }
}
```