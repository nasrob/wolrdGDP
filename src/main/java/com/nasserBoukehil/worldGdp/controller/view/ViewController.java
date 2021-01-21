package com.nasserBoukehil.worldGdp.controller.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nasserBoukehil.worldGdp.dao.CityDAO;
import com.nasserBoukehil.worldGdp.dao.CountryDao;
import com.nasserBoukehil.worldGdp.dao.LookupDAO;

@Controller
@RequestMapping("/")
public class ViewController {

	@Autowired CountryDao countryDao;
	@Autowired LookupDAO lookupDao;
	@Autowired CityDAO cityDao;
	
	@GetMapping({"/countries", "/"})
	public String countries(Model model, @RequestParam Map<String, Object> params) {
		model.addAttribute("continents", lookupDao.getContinents());
		model.addAttribute("regions", lookupDao.getRegions());
		model.addAttribute("countries", countryDao.getCountries(params));
		model.addAttribute("count", countryDao.getCountriesCount(params));
		
		return "countries";
	}
	
	@GetMapping("/countries/{code}")
	public String countryDetail(@PathVariable String code, Model model) {
		model.addAttribute("c", countryDao.getCountryDetail(code));		
		return "country";
	}
	
	@GetMapping("/countries/{code}/form")
	public String editCountry(@PathVariable String code, Model model) {
		model.addAttribute("c", countryDao.getCountryDetail(code));
		model.addAttribute("cities", cityDao.getCities(code));
		model.addAttribute("continents", lookupDao.getContinents());
		model.addAttribute("regions", lookupDao.getRegions());
		model.addAttribute("head", lookupDao.getHeadOfStates());
		model.addAttribute("govs", lookupDao.getGovernmentTypes());
		
		return "country-form";
	}
	
}
