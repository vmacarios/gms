package com.macarios.gms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author victor macarios
 * @date 2020/05
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.macarios.gms"})
public class ZipMS {
	private static final Logger logger = LoggerFactory.getLogger(ZipMS.class);

	public static void main(String[] args) {
//		Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
//
//		logger.info("The value is: {}", "info test");
//		logger.debug(notifyAdmin, "The value is: {}", "debug test");

		SpringApplication.run(ZipMS.class, args);

	}
}
