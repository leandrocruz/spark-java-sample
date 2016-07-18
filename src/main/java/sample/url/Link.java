package sample.url;

import org.apache.commons.lang3.StringUtils;
import org.immutables.value.Value;

@Value.Immutable
public interface Link
{
	String href();
	
	String text();
	
	default boolean isExternal()
	{
		String href = href();
		if(StringUtils.isEmpty(href))
		{
			return false;
		}
		return href.startsWith("http") || href.startsWith("https");
	}
}
