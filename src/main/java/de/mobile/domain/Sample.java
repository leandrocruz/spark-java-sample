package de.mobile.domain;

import org.immutables.value.Value;

@Value.Immutable
public interface Sample
{
	String getName();
	
	int getAge();
}
