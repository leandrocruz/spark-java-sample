package sample.url.impl;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import sample.url.DocumentParser;
import sample.url.ImmutableUrlData;
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
		final URL locator = urlFrom(url);
		final String protocol = locator.getProtocol();
		if("http".equals(protocol) || "https".equals(protocol))
		{
			final InputStream input = fetcher.fetch(locator);
			return parser.parse(input);
		}
		else
		{
			throw new UrlAnalyserException("Bad protocol: " + protocol);
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
