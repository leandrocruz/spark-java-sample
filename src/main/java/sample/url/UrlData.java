package sample.url;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public interface UrlData
{
	Optional<String> getVersion();
	
	Optional<String> getTitle();
}
