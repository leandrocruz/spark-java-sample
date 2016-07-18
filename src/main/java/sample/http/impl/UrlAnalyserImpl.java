package sample.http.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sample.http.DocumentParser;
import sample.http.UrlAnalyser;
import sample.http.UrlAnalyserException;
import sample.http.UrlData;
import sample.http.UrlFetcher;
import xingu.container.Inject;

public class UrlAnalyserImpl
	implements UrlAnalyser
{
	@Inject
	private UrlFetcher fetcher;
	
	@Inject
	private DocumentParser parser;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public UrlData analyse(String url)
		throws UrlAnalyserException
	{
		logger.info("Analysing url: '{}'", url);
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
