package de.mobile.domain;

import java.util.List;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonProperty;

@Value.Immutable
public interface Sample
{
	String getName();
	
	int getAge();
	
	@JsonProperty("colors")
	List<String> getFavoriteColors();
}
