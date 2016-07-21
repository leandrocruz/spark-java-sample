package sample;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public interface RestResponse
{
	boolean isError();
	
	Optional<String> getMessage();
	
	Optional<Object> getPayload();
}
