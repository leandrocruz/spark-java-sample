package sample.http.fetcher;

import java.io.InputStream;
import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public interface FetchResponse
{
	Optional<InputStream> inputStream();
	
	int statusCode();
}
