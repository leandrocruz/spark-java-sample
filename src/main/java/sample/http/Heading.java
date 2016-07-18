package sample.http;

import org.immutables.value.Value;

@Value.Immutable
public interface Heading
{
	int getLevel();
	
	String getText();
}
