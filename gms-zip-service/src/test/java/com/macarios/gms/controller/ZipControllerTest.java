package com.macarios.gms.controller;

import com.macarios.gms.model.Zip;
import com.macarios.gms.service.ZipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
	private ZipService zipService;

	@Test
	public void getAllZips() throws Exception {
		List<Zip> zipList = new ArrayList<>();
		zipList.add(new Zip("06184280", "R. Jambeiro", "", "Cidade das Flores", "Osasco", "SP", Instant.now(), Instant.now()));
		zipList.add(new Zip("1508210", "R. Gago Coutinho", "", "Pq. Estoril", "S J Rio Preto", "SP", Instant.now(), null));
		when(zipService.findAll()).thenReturn(zipList);
		mockMvc.perform(get("/zips")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].zip", is("06184280")))
				.andDo(print());
		verify(zipService, times(1)).findAll();
		verifyNoMoreInteractions(zipService);
	}

	@Test
	public void wrongUri() throws Exception {
		mockMvc.perform(get("/zip")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

}
