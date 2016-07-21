package sample;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public interface RestResponse
{
	boolean isError();
	
	Optional<Integer> getCode();
	
	Optional<String> getMessage();
	
	Optional<Object> getPayload();
}
