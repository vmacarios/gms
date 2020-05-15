package com.macarios.gms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macarios.gms.model.Zip;
import com.macarios.gms.service.ZipService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import org.junit.runner.RunWith;

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

	private static List<Zip> zipList = new ArrayList<>();
	private static Zip zip1;
	private static Zip zip2;

	@BeforeAll
	public static void initialSetup() {
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
		zipList.add(zip1);
		zipList.add(zip2);
	}

	@Test
	void getAllZips() throws Exception {
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
	void wrongUri() throws Exception {
		mockMvc.perform(get("/zip")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	void successfullySavedAZip() throws Exception {
		when(zipServiceMock.save(any(Zip.class))).thenReturn(zip1);
		String zipJSON = objectMapper.writeValueAsString(zip1);
		ResultActions result = mockMvc.perform(post("/zips")
				.contentType(MediaType.APPLICATION_JSON)
				.content(zipJSON)
		);
		result.andExpect(status().isCreated())
				.andExpect(jsonPath("$.zip").value(zip1.getZip()))
				.andExpect(jsonPath("$.address").value(zip1.getAddress()));
	}

	@Test
	void getOneZip() throws Exception {
		when(zipServiceMock.findById(any())).thenReturn(Optional.of(zipList.get(0)));
		mockMvc.perform(get("/zip/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.zip").value(zipList.get(0).getZip()))
				.andExpect(jsonPath("$.address").value(zipList.get(0).getAddress()))
				.andDo(print());
		verify(zipServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(zipServiceMock);
	}

	@Test
	void zipNotFound() throws Exception {
		when(zipServiceMock.findById(any())).thenReturn(Optional.empty());
		mockMvc.perform(get("/zip/{id}", 1))
				.andExpect(status().isNotFound())
				.andDo(print());
		verify(zipServiceMock, times(1)).findById(1);
		verifyNoMoreInteractions(zipServiceMock);
	}
}
