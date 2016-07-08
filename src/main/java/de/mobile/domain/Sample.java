package de.mobile.domain;

import java.util.List;

import org.immutables.value.Value;

@Value.Immutable
public interface Sample
{
	String getName();
	
	int getAge();
	
	List<String> getFavoriteColors();
}
