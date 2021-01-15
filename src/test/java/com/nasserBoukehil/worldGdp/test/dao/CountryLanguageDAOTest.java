package com.nasserBoukehil.worldGdp.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.nasserBoukehil.worldGdp.dao.CountryLanguageDAO;
import com.nasserBoukehil.worldGdp.model.CountryLanguage;
import com.nasserBoukehil.worldGdp.test.config.TestDBConfiguration;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(classes = { TestDBConfiguration.class, CountryLanguageDAO.class })
public class CountryLanguageDAOTest {

	@Autowired
	CountryLanguageDAO countryLanguageDAO;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Before
	public void before() {
		countryLanguageDAO.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
	}

	@Test
	public void testGetLanguages() {
		List<CountryLanguage> languages = countryLanguageDAO.getLanguanges("IND", 1);
		assertThat(languages).hasSize(10);
	}

	@Test
	public void testAddLanguage() {
		String countryCode = "IND";
		CountryLanguage cntrLang = createNewLanguage(countryCode);
		countryLanguageDAO.addLanguage(countryCode, cntrLang);
		List<CountryLanguage> languages = countryLanguageDAO.getLanguanges(countryCode, 2);
		assertThat(languages).hasSize(3);
	}
	
	@Test
	public void testLanguageExists() {
		String countryCode = "IND";
		CountryLanguage countryLang = createNewLanguage(countryCode);
		countryLanguageDAO.addLanguage(countryCode, countryLang);
		
		assertThat(countryLanguageDAO.languageExists(countryCode, countryLang.getLanguage())).isTrue();
		countryLanguageDAO.deleteLanguage(countryCode, countryLang.getLanguage());
	}
	
	@Test
	public void testDeleteLanguage() {
		String countryCode = "IND";
		CountryLanguage cl = createNewLanguage(countryCode);
		countryLanguageDAO.addLanguage(countryCode, cl);
		List<CountryLanguage> languages = countryLanguageDAO.getLanguanges(countryCode, 2);
		assertThat(languages).hasSize(3);
		
		countryLanguageDAO.deleteLanguage(countryCode, "Test");
		languages = countryLanguageDAO.getLanguanges(countryCode, 2);
		assertThat(languages).hasSize(2);
	}

	private CountryLanguage createNewLanguage(String countryCode) {
		CountryLanguage cl = new CountryLanguage();
		cl.setCountryCode(countryCode);
		cl.setIsOfficial("T");
		cl.setLanguage("Test");
		cl.setPercentage(12.3);
		return cl;
	}
}
