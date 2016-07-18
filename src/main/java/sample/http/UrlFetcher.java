package sample.http;

import java.io.InputStream;
import java.net.URL;

public interface UrlFetcher
{
	InputStream fetch(URL locator)
		throws UrlFetcherException;
}
