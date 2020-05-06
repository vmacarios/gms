package com.macarios.gms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author victor macarios
 * @date 2020/05
 */

@SpringBootApplication
public class Zip {
	private static final Logger logger = LoggerFactory.getLogger(Zip.class);

	public static void main(String[] args) {
//		Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
//
//		logger.info("The value is: {}", "info test");
//		logger.debug(notifyAdmin, "The value is: {}", "debug test");

		SpringApplication.run(Zip.class, args);

	}
}
