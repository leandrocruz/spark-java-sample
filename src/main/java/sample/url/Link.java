package sample.url;

import org.apache.commons.lang3.StringUtils;
import org.immutables.value.Value;

@Value.Immutable
public interface Link
{
	String getHref();
	
	String getText();
	
	default boolean isExternal()
	{
		String href = getHref();
		if(StringUtils.isEmpty(href))
		{
			return false;
		}
		return href.startsWith("http") || href.startsWith("https");
	}
}
