package com.nasserBoukehil.worldGdp.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nasserBoukehil.worldGdp.model.City;
import com.nasserBoukehil.worldGdp.model.Country;

public class CountryRowMapper implements RowMapper<Country>{
	
	public Country mapRow(ResultSet aResultSet, int rowNum) throws SQLException {
		
		Country country = new Country();
		country.setCode(aResultSet.getString("code"));
		country.setName(aResultSet.getString("name"));
		country.setContinent(aResultSet.getString("continent"));
		country.setRegion(aResultSet.getString("region"));
		country.setSurfaceArea(aResultSet.getDouble("surface_area"));
		country.setIndepYear(aResultSet.getShort("indep_year"));
		country.setPopulation(aResultSet.getLong("population"));
		country.setLifeExpectancy(aResultSet.getDouble("life_expectancy"));
		country.setGnp(aResultSet.getDouble("gnp"));
		country.setLocalName(aResultSet.getString("local_name"));
		country.setGovernmentForm(aResultSet.getString("government_form"));
		country.setHeadOfState(aResultSet.getString("head_of_state"));
		country.setCode2(aResultSet.getString("code2"));
		
		if (Long.valueOf(aResultSet.getLong("capital")) != null) {
			City city = new City();
			city.setId(aResultSet.getLong("capital"));
			city.setName(aResultSet.getString("capital_name"));
			country.setCapital(city);
		}
		
		return country;
	}

}
