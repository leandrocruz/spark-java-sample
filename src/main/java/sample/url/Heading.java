package sample.url;

import org.immutables.value.Value;

@Value.Immutable
public interface Heading
{
	int level();
	
	String text();
}
