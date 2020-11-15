package com.nasserBoukehil.worldGdp.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CountryLanguage {
	
	private Country country;
	@NotNull @Size(max = 30) private String language;
	@NotNull @Size(max = 1, min = 1) private String isOfficial;
	@NotNull private Double percentage;
}

