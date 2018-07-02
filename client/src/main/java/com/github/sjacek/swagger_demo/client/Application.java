package com.github.sjacek.swagger_demo.client;

import com.github.sjacek.swagger_demo.client.api.HelloControllerApi;
import com.github.sjacek.swagger_demo.client.model.NowOutput;
import com.github.sjacek.swagger_demo.client.model.SendDateOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.OffsetDateTime;

import java.lang.invoke.MethodHandles;

import static com.github.sjacek.swagger_demo.client.config.ApiClientConfig.MINE;
import static java.lang.System.exit;
import static org.springframework.boot.Banner.Mode.OFF;
import static java.time.OffsetDateTime.now;
import static java.time.ZoneOffset.ofHours;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final HelloControllerApi helloController;

    public static final String ZONE_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    @Autowired
    public Application(@Qualifier(MINE) HelloControllerApi helloController) {
        this.helloController = helloController;
    }

    public static void main(String[] args) {
        logger.info("Start... JVM version: " + System.getProperty("java.version"));
        //disabled banner, don't want to see the spring logo
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        String s1 = helloController.sendTextUsingGET1("Hello server");
        logger.debug(s1);

//        OffsetDateTime dt = now();
//        SendDateOutput s2 = helloController.sendDate(dt);
//        logger.debug(s2.getRet());
//
//        dt = OffsetDateTime.of(2017, 12, 20,
//                23, 50, 10, 123000000,
//                ofHours(2));
//        SendDateOutput s3 = helloController.sendDate(dt);
//        logger.debug(s3.getRet());

        NowOutput no = helloController.now();
        logger.debug(no.getDt().toString());
        exit(0);
    }
}
