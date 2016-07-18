package sample.url.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import sample.url.DocumentParser;
import sample.url.UrlAnalyser;
import sample.url.UrlAnalyserException;
import sample.url.UrlData;
import sample.url.UrlFetcher;
import xingu.container.Inject;

public class UrlAnalyserImpl
	implements UrlAnalyser
{
	@Inject
	private UrlFetcher fetcher;
	
	@Inject
	private DocumentParser parser;
	
	@Override
	public UrlData analyse(String url)
		throws UrlAnalyserException
	{
		final URL         locator = urlFrom(url);
		final InputStream input   = fetcher.fetch(locator);
		final String      html    = toString(input);
		return parser.parse(html);
	}

	private String toString(InputStream input)
	{
		if(input == null)
		{
			return null;
		}

		try
		{
			return IOUtils.toString(input);
		}
		catch(IOException e)
		{
			throw new UrlAnalyserException("Error converting input to string");
		}
	}

	private URL urlFrom(String url)
	{
		try
		{
			return new URL(url);
		}
		catch(MalformedURLException e)
		{
			throw new UrlAnalyserException("Error parsing url", e);
		}
	}
}
