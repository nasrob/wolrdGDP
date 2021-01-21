package com.nasserBoukehil.worldGdp.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.nasserBoukehil.worldGdp.dao.LookupDAO;
import com.nasserBoukehil.worldGdp.test.config.TestDBConfiguration;

@RunWith(SpringRunner.class)
@SpringJUnitWebConfig( classes = {TestDBConfiguration.class, LookupDAO.class})
public class LookupDAOTest {

	@Autowired LookupDAO lookupDao;
	
	@Test
	public void testGetContinents() {
		List<String> continents = lookupDao.getContinents();
		assertThat(continents).hasSize(7);
		assertThat(continents.get(0)).isEqualTo("Africa");
	}
	
	@Test
	public void testGetRegions() {
		List<String> regions = lookupDao.getRegions();
		assertThat(regions).hasSize(25);
		assertThat(regions.get(0)).isEqualTo("Antarctica");
	}
	
}
