package com.nasserBoukehil.worldGdp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class City {
	private Long id;
	private String name;
	private Country country;
	private String district;
	private Long population;
}
