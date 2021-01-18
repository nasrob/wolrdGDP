package com.nasserBoukehil.worldGdp.test.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasserBoukehil.worldGdp.AppConfiguration;
import com.nasserBoukehil.worldGdp.dao.CountryDao;
import com.nasserBoukehil.worldGdp.model.Country;

@RunWith(SpringRunner.class)
@SpringJUnitWebConfig(classes = {AppConfiguration.class})
public class CountryAPIControllerTest {
	
	@Autowired
	private WebApplicationContext wAppContext;
	
	private MockMvc mockMvc;
	
	@Autowired
	CountryDao countryDao;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wAppContext).build();
		countryDao.setNamedParamJdbcTemplate(namedParameterJdbcTemplate);
	}
	
	@Test
	public void testGetCountries() throws Exception {
		mockMvc.perform(get("/api/countries")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
					.andExpect(status().isOk())
					.andExpect((ResultMatcher) content().contentType("application/json;charset=UTF-8"))
					.andExpect(jsonPath("$").isMap())
					.andExpect(jsonPath("$.list").isNotEmpty())
					.andExpect(jsonPath("$.count", is(239)));
	}
	
	@Test
	public void testEditCountry() throws Exception {
		String countryCode = "IND";
		Country country = countryDao.getCountryDetail(countryCode);
		country.setHeadOfState("Ram Stam");
		country.setLifeExpectancy(70d);
		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult result = mockMvc.perform(
					post("/api/countries/" + countryCode)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(country))
				).andExpect(status().isOk())
				.andReturn();
		country = objectMapper.readValue(result.getResponse().getContentAsString(), Country.class);
		assertThat(country.getHeadOfState()).isEqualTo("Ram Stam");
	}
	
	
}
