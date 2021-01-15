package com.nasserBoukehil.worldGdp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.nasserBoukehil.worldGdp.model.CountryLanguage;
import com.nasserBoukehil.worldGdp.rowMapper.CountryLanguageRowMapper;

import lombok.Setter;

@Service
@Setter
public class CountryLanguageDAO {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static final Integer PAGE_SIZE = 10;
	
	public List<CountryLanguage> getLanguanges(String countryCode, Integer pageNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", countryCode);
		
		Integer offset = (pageNo - 1) * PAGE_SIZE;
		params.put("offset", offset);
		params.put("size", PAGE_SIZE);
		
		return namedParameterJdbcTemplate.query("SELECT * FROM countrylanguage"
				+ " WHERE countrycode = :code"
				+ " ORDER BY percentage DESC"
				+ " LIMIT :size OFFSET :offset", 
				params, new CountryLanguageRowMapper());
	}
	
	public void addLanguage(String countryCode, CountryLanguage cl) {
		namedParameterJdbcTemplate.update("INSERT INTO countrylanguage ( "
				+ " countrycode, language, isoffical, percentage ) "
				+ " VALUES ( :country_code, :language, "
				+ " :is_official, :percentage ) ",
				getAsMap(countryCode, cl));
	}
	
	public boolean languageExists(String countryCode, String language) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", countryCode);
		params.put("lang", language);
		
		Integer langCount = namedParameterJdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM countrylanguage"
				+ " WHERE countrycode = :code"
				+ " AND language = :lang", params, Integer.class);
		
		return langCount > 0;
	}
	
	public void deleteLanguage(String countryCode, String language) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", countryCode);
		params.put("lang", language);
		
		namedParameterJdbcTemplate.update("DELETE FROM countrylanguage "
				+ " WHERE countrycode = :code AND "
				+ " language = :lang ", params);
	}

	private Map<String, Object> getAsMap(String countryCode, CountryLanguage cl) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("countryCode", countryCode);
		map.put("language", cl.getLanguage());
		map.put("is_official", cl.getIsOfficial());
		map.put("percentage", cl.getPercentage());
 		return map;
	}
}
