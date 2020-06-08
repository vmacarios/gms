package com.macarios.gms.service;

import com.macarios.gms.model.Zip;
import com.macarios.gms.repository.ZipRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Victor Macarios
 */
@SpringBootTest
public class ZipServiceTest {
	@MockBean
	private ZipRepository zipRepository;

	@Autowired
	private ZipService zipService;

	private static final List<Zip> zipList = new ArrayList<>();
	private static Zip zip, zip2;

	@BeforeAll
	public static void initialSetup() {
		zip = new Zip(
				1,
				"06184280",
				"R. Jambeiro",
				"",
				"Cidade das Flores",
				"Osasco",
				"SP",
				Instant.now(),
				Instant.now());

		zip2 = new Zip(
				2,
				"15085210",
				"R. Gago Coutinho",
				"",
				"Pq. Estoril",
				"S J Rio Preto",
				"SP",
				Instant.now(),
				null);

		zipList.add(zip);
		zipList.add(zip2);
	}

	@Test
	public void testGetAllZips() {
		when(zipRepository.findAll()).thenReturn(zipList);

		final List<Zip> localZipList = zipService.findAll();
		final Zip firstZip = localZipList.get(0);
		final Zip lastZip = localZipList.get(localZipList.size() - 1);

		assertEquals(2, localZipList.size());

		assertEquals(localZipList.get(0).getZip(), firstZip.getZip());
		assertEquals(localZipList.get(0).getAddress(), firstZip.getAddress());
		assertEquals(localZipList.get(0).getComp(), firstZip.getComp());
		assertEquals(localZipList.get(0).getNeighborhood(), firstZip.getNeighborhood());
		assertEquals(localZipList.get(0).getCity(), firstZip.getCity());
		assertEquals(localZipList.get(0).getState(), firstZip.getState());

		assertEquals(localZipList.get(1).getZip(), lastZip.getZip());
		assertEquals(localZipList.get(1).getAddress(), lastZip.getAddress());
		assertEquals(localZipList.get(1).getComp(), lastZip.getComp());
		assertEquals(localZipList.get(1).getNeighborhood(), lastZip.getNeighborhood());
		assertEquals(localZipList.get(1).getCity(), lastZip.getCity());
		assertEquals(localZipList.get(1).getState(), lastZip.getState());
	}

	@Test
	void testSaveZip() {
		zipService.save(zip);

		verify(zipRepository, times(1)).save(zip);
		verifyNoMoreInteractions(zipRepository);
	}


	@Test
	void testZipNotFound() {
		when(zipRepository.findById(any())).thenReturn(Optional.empty());

		final Optional<Zip> zip = zipService.findById(1);

		assertFalse(zip.isPresent());
		verify(zipRepository, times(1)).findById(1);
		verifyNoMoreInteractions(zipRepository);
	}

	@Test
	void testGetOneZip() {
		when(zipRepository.findById(any())).thenReturn(Optional.ofNullable(zip));

		final Optional<Zip> selectedZip = zipService.findById(1);

		assertTrue(selectedZip.isPresent());
		assertEquals(selectedZip.get(), zip);
		verify(zipRepository, times(1)).findById(1);
		verifyNoMoreInteractions(zipRepository);
	}

	@Test
	void testDeleteZip() {
		zipService.deleteById(1);

		verify(zipRepository, times(1)).deleteById(1);
		verifyNoMoreInteractions(zipRepository);
	}

	@Test
	void testUpdateZip() {
		zipService.update(zip);

		verify(zipRepository, times(1)).save(any(Zip.class));
		verifyNoMoreInteractions(zipRepository);
	}
}
