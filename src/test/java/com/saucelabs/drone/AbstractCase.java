package com.saucelabs.drone;

import com.saucelabs.drone.webapp.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.io.File;
import java.net.URL;

/**
 * @author Ross Rowe
 */
public abstract class AbstractCase {
    @ArquillianResource
    protected URL contextPath;

    /**
     * Creates a WAR of a Weld based application using ShrinkWrap
     *
     * @return WebArchive to be tested
     */
    @Deployment(testable = false)
    public static WebArchive createDeployment() {

        boolean isRunningOnAS7 = System.getProperty("jbossHome", "target/jboss-as-7.0.2.Final").contains("7.0.2.Final");

        WebArchive war = ShrinkWrap
                .create(WebArchive.class, "weld-login.war")
                .addClasses(Credentials.class, LoggedIn.class, Login.class, User.class, Users.class)
                .addAsResource(new File("src/test/resources/import.sql"))
                .addAsWebInfResource(new File("src/test/webapp/WEB-INF/beans.xml"))
                .addAsWebInfResource(new File("src/test/webapp/WEB-INF/faces-config.xml"))
                .addAsWebResource(new File("src/test/webapp/index.html"))
                .addAsWebResource(new File("src/test/webapp/home.xhtml"))
                .addAsWebResource(new File("src/test/webapp/template.xhtml"))
                .addAsWebResource(new File("src/test/webapp/users.xhtml"))
                .addAsResource(
                        isRunningOnAS7 ? new File("src/test/resources/META-INF/persistence.xml") : new File(
                                "src/test/resources/META-INF/persistence-as6.xml"),
                        ArchivePaths.create("META-INF/persistence.xml")).setWebXML(new File("src/test/webapp/WEB-INF/web.xml"));

        return war;
    }


}
