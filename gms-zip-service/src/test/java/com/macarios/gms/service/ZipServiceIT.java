package com.macarios.gms.service;

import com.macarios.gms.model.Zip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ZipServiceIT {
	@Autowired
	private ZipService zipService;

	@Test
	public void saveZip() {
		Zip zip1 = new Zip(
				"06184280",
				"R. Jambeiro",
				"",
				"Cidade das Flores",
				"Osasco",
				"SP",
				Instant.now(),
				Instant.now());

		Zip zip2 = new Zip(
				"15085210",
				"R. Gago Coutinho",
				"",
				"Pq. Estoril",
				"S J Rio Preto",
				"SP",
				Instant.now(),
				null);

		zipService.save(zip1);
		zipService.save(zip2);
		assertEquals(2, zipService.findAll().size());
	}
}
