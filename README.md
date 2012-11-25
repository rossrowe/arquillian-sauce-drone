This project contains the source code for the Sauce extension for Arquillian

To use the library, add the following dependency to your Maven pom.xml file

    <dependency>
        <groupId>com.saucelabs</groupId>
        <artifactId>arquillian-sauce-drone</artifactId>
        <version>0.0.1-SNAPSHOT</version>
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
            <property name="implementationClass">com.saucelabs.drone.webdriver.SauceWebDriverFactory</property>
            <property name="browserCapabilities">sauce</property>
            <property name="userName">YOUR_SAUCE_USERNAME</property>
            <property name="accessKey">YOUR_SAUCE_ACCESS_KEY</property>
            <property name="browser">firefox</property>
            <property name="os">Windows 2008</property>
            <property name="version">4.</property>
    </extension>