package com.nasserBoukehil.worldGdp.test.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasserBoukehil.worldGdp.AppConfiguration;
import com.nasserBoukehil.worldGdp.dao.CountryLanguageDAO;
import com.nasserBoukehil.worldGdp.model.CountryLanguage;

@RunWith(SpringRunner.class)
@SpringJUnitWebConfig(classes = {AppConfiguration.class})
public class CountryLanguageAPIControllerTest {

	@Autowired
	private WebApplicationContext waContext;
	private MockMvc mockMvc;
	
	@Autowired
	CountryLanguageDAO cLanguageDAO;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(waContext).build();
		cLanguageDAO.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
	}
	
	@Test
	public void testGetLanguage() throws Exception {
		String countryCode = "FRA";
		mockMvc.perform(get("/api/languages/{countryCode}", countryCode)
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect((ResultMatcher) content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()", is(10)))
				.andExpect(jsonPath("$[0].language", is("French")));
	}
	
	@Test
	public void testAddLanguage() throws Exception {
		String countryCode = "FRA";
		
		CountryLanguage cl = new CountryLanguage();
		cl.setCountryCode(countryCode);
		cl.setIsOfficial("1");
		cl.setLanguage("TEST");
		cl.setPercentage(100d);
		
		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult result = (MvcResult) mockMvc.perform(
					post("/api/languages/{countryCode}", countryCode)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(cl))
				).andExpect(status().isOk()).andReturn();
		
		List<CountryLanguage> langs = cLanguageDAO.getLanguanges(countryCode, 1);
		CountryLanguage first = langs.get(0);
		assertThat(first.getLanguage()).isEqualTo("TEST");
		cLanguageDAO.deleteLanguage(countryCode, first.getLanguage());
	}
	
	@Test
	public void testAddLanguage_DuplicateLang() throws Exception {
		String countryCode = "FRA";
		
		CountryLanguage cl = new CountryLanguage();
		cl.setCountryCode(countryCode);
		cl.setIsOfficial("1");
		cl.setLanguage("TEST");
		cl.setPercentage(100d);
		
		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult result = (MvcResult) mockMvc.perform(
					post("/api/languages/{countryCode}", countryCode)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(cl))
				).andExpect(status().isBadRequest()).andReturn();
		
		cLanguageDAO.deleteLanguage(countryCode, cl.getLanguage());
	}
	
	public void testDeleteCity() throws Exception {
		String countryCode = "FRA";
		
		CountryLanguage cl = new CountryLanguage();
		cl.setCountryCode(countryCode);
		cl.setIsOfficial("1");
		cl.setLanguage("TEST");
		cl.setPercentage(100d);
		
		cLanguageDAO.addLanguage(countryCode, cl);
		mockMvc.perform(
					delete("/api/languages/{countryCode}/language/language",
							countryCode, cl.getLanguage()))
					.andDo(MockMvcResultHandlers.print())
					.andExpect(status().isOk());
		
		List<CountryLanguage> langs = cLanguageDAO.getLanguanges(countryCode, 1);
		CountryLanguage first = langs.get(0);
		assertThat(first.getLanguage()).isEqualTo("French");
	}
}
