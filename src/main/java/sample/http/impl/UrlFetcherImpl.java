package sample.http.impl;

import java.io.InputStream;
import java.net.URL;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import sample.http.UrlFetcher;
import sample.http.UrlFetcherException;

/*
 * This is the default implementation of the UrlFetcher based on Unirest, a simple http client.
 * For production environments it's suggested to use other http client libraries, like Apache Http Client
 * or even libcurl (for simplified handling of ssl certificates)  
 */
public class UrlFetcherImpl
	implements UrlFetcher
{
	@Override
	public InputStream fetch(URL locator)
	{
		try
		{
			String url = locator.toExternalForm();
			return Unirest.get(url).asBinary().getBody();
		}
		catch(UnirestException e)
		{
			throw new UrlFetcherException(e.getMessage(), e);
		}
	}
}
