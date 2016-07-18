package sample.url;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public interface UrlData
{
	Optional<String> version();
	
	Optional<String> title();

	List<Link> links();
	
	List<Heading> headings();
}
