package com.macarios.gms.service;

import com.macarios.gms.model.Zip;
import com.macarios.gms.repository.ZipRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ZipServiceTest {
	@MockBean
	private ZipRepository zipRepository;

	private static List<Zip> zipList = new ArrayList<>();

	@BeforeAll
	public static void initialSetup() {
		zipList.add(new Zip(
				"06184280",
				"R. Jambeiro",
				"",
				"Cidade das Flores",
				"Osasco",
				"SP",
				Instant.now(),
				Instant.now()));
		zipList.add(new Zip(
				"15085210",
				"R. Gago Coutinho",
				"",
				"Pq. Estoril",
				"S J Rio Preto",
				"SP",
				Instant.now(),
				null));
	}

	@Test
	public void getAllZips() {
		ZipService zipService = new ZipService(zipRepository);
		when(zipRepository.findAll()).thenReturn(zipList);

		List<Zip> localZipList = zipService.findAll();
		Zip firstZip = localZipList.get(0);
		Zip lastZip = localZipList.get(localZipList.size() - 1);

		assertEquals(2, localZipList.size());
		assertEquals("R. Jambeiro", firstZip.getAddress());
		assertEquals("15085210", lastZip.getZip());
	}

}
