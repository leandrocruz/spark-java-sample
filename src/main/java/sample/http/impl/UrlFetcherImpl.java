package sample.http.impl;

import java.io.InputStream;
import java.net.URL;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import sample.http.UrlFetcher;
import sample.http.UrlFetcherException;

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
