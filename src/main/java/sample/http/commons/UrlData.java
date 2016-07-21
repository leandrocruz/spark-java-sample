package sample.http.commons;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public interface UrlData
{
	Optional<String> getVersion();
	
	Optional<String> getTitle();

	List<Link> getLinks();
	
	List<Heading> getHeadings();
}
