package sample.http.commons;

import org.immutables.value.Value;

@Value.Immutable
public interface Link
{
	String getHref();
	
	String getText();
	
	default boolean isExternal()
	{
		String href = getHref();
		return HttpUtils.urlStartsWithHttpOrHttps(href);
	}
}
