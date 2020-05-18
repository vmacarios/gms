package com.macarios.gms.service;

import com.macarios.gms.model.Zip;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author victor macarios
 */
@SpringBootTest
public class ZipServiceIT {
	@Autowired
	private ZipService zipService;

	private static Zip zip1, zip2;

	@BeforeAll
	public static void createZips() {
		zip1 = new Zip(
				"06184280",
				"R. Jambeiro",
				"",
				"Cidade das Flores",
				"Osasco",
				"SP",
				Instant.now(),
				Instant.now());
		zip2 = new Zip(
				"15085210",
				"R. Gago Coutinho",
				"",
				"Pq. Estoril",
				"S J Rio Preto",
				"SP",
				Instant.now(),
				null);
	}

	@BeforeEach
	public void saveZip() {
		zipService.save(zip1);
	}

	@Test
	@Transactional // Roll back DB to previously state
	public void testSaveZip() {
		final Zip savedZip = zipService.save(zip2);
		final Integer id = savedZip.getId();
		final Optional<Zip> zip = zipService.findById(id);
		assertTrue(zip.isPresent());
		assertEquals(zip.get().getZip(), zip2.getZip());
	}

	@Test
	void testFindAll() {
		assertEquals(1, zipService.findAll().size());
	}

	@Test
	void testFindOne() {
		final Optional<Zip> zip = zipService.findById(1);
		assertTrue(zip.isPresent());
		assertEquals(zip1.getZip(), zip.get().getZip());
	}

	@Test
	@Transactional // Roll back DB to previously state
	void testDelete() {
		final Optional<Zip> zip = zipService.findById(1);
		assertTrue(zip.isPresent());
		zipService.deleteById(1);
		assertFalse(zipService.findById(1).isPresent());
	}
}
