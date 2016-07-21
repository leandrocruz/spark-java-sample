package sample.http.fetcher;

import java.net.URL;

public interface UrlFetcher
{
	FetchResponse fetch(URL locator)
		throws UrlFetcherException;
}
