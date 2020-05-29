package com.macarios.gms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macarios.gms.model.Zip;
import com.macarios.gms.service.ZipService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import org.junit.runner.RunWith;

/**
 * @author Victor Macarios
 */
//@ExtendWith(SpringExtension.class)
@WebMvcTest
//@SpringBootTest
//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
public class ZipControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ZipService zipServiceMock;

	@Autowired
	private ObjectMapper objectMapper;

	private static final List<Zip> zipList = new ArrayList<>();
	private static Zip zip1;
	private static Zip zip2;

	@BeforeAll
	static void initialSetup() {
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
				1,
				"15085210",
				"R. Gago Coutinho",
				"",
				"Pq. Estoril",
				"S J Rio Preto",
				"SP",
				Instant.now(),
				null);
		zipList.add(zip1);
		zipList.add(zip2);
	}

	@Test
	void testGetAllZips() throws Exception {
		when(zipServiceMock.findAll()).thenReturn(zipList);
		mockMvc.perform(get("/zips")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].zip", is(zipList.get(0).getZip())))
				.andExpect(jsonPath("$[1].zip", is(zipList.get(1).getZip())))
				.andDo(print());
		verify(zipServiceMock, times(1)).findAll();
		verifyNoMoreInteractions(zipServiceMock);
	}

	@Test
	void testWrongUri() throws Exception {
		mockMvc.perform(get("/zip")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(print());

		verify(zipServiceMock, times(0)).findAll();
		verifyNoMoreInteractions(zipServiceMock);
	}

	@Test
	void testSuccessfullySavedAZip() throws Exception {
		when(zipServiceMock.save(any(Zip.class))).thenReturn(zipList.get(0));
		String zipJSON = objectMapper.writeValueAsString(zip1);
		ResultActions result = mockMvc.perform(post("/zips")
				.contentType(MediaType.APPLICATION_JSON)
				.content(zipJSON)
		);
		result.andExpect(status().isCreated())
				.andExpect(jsonPath("$.zip").value(zipList.get(0).getZip()))
				.andExpect(jsonPath("$.address").value(zipList.get(0).getAddress()));
		verify(zipServiceMock, times(1)).save(any(Zip.class));
		verifyNoMoreInteractions(zipServiceMock);
	}

	@Test
	void testGetOneZip() throws Exception {
		when(zipServiceMock.findById(any())).thenReturn(Optional.of(zipList.get(0)));
		mockMvc.perform(get("/zips/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.zip").value(zipList.get(0).getZip()))
				.andExpect(jsonPath("$.address").value(zipList.get(0).getAddress()))
				.andDo(print());
		verify(zipServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(zipServiceMock);
	}

	@Test
	void testZipNotFound() throws Exception {
		when(zipServiceMock.findById(any())).thenReturn(Optional.empty());
		mockMvc.perform(get("/zips/{id}", 1))
				.andExpect(status().isNotFound())
				.andDo(print());
		verify(zipServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(zipServiceMock);
	}

	@Test
	void testeDeleteZip() throws Exception {
		doNothing().when(zipServiceMock).deleteById(any());
		mockMvc.perform(delete("/zips/{id}", 1))
				.andExpect(status().isNoContent())
				.andDo(print());
		verify(zipServiceMock, times(1)).deleteById(1);
		verifyNoMoreInteractions(zipServiceMock);
	}

	@Test
	void testDeleteANotFoundZip() throws Exception {
		doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(zipServiceMock).deleteById(any());
		mockMvc.perform(delete("/zips/{id}", 1))
				.andExpect(status().isNotFound())
				.andDo(print());
		verify(zipServiceMock, times(1)).deleteById(1);
		verifyNoMoreInteractions(zipServiceMock);
	}

	@Test
	void testUpdateZip() throws Exception {
		when(zipServiceMock.findById(any())).thenReturn(Optional.ofNullable(zipList.get(1)));
		when(zipServiceMock.update(any(Zip.class))).thenReturn(zipList.get(1));
		String zipJson = objectMapper.writeValueAsString(zip2);
		ResultActions result = mockMvc.perform(put("/zips/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(zipJson));
		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.zip").value(zipList.get(1).getZip()))
				.andExpect(jsonPath("$.address").value(zipList.get(1).getAddress()))
				.andDo(print());
		verify(zipServiceMock, times(1)).findById(any());
		verify(zipServiceMock, times(1)).update(any(Zip.class));
		verifyNoMoreInteractions(zipServiceMock);
	}

	@Test
	void testUpdateANotFoundZip() throws Exception {
//		when(zipServiceMock.update(any(Zip.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
		String zipJson = objectMapper.writeValueAsString(zip2);
		mockMvc.perform(put("/zips/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(zipJson))
				.andExpect(status().isNotFound());
//		verify(zipServiceMock, times(1)).update(any(Zip.class));
//		verifyNoMoreInteractions(zipServiceMock);

	}
}
