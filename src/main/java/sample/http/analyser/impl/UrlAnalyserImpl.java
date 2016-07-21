package sample.http.analyser.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javaslang.control.Either;
import sample.http.analyser.UrlAnalyser;
import sample.http.analyser.UrlAnalyserException;
import sample.http.commons.HttpUtils;
import sample.http.commons.UrlData;
import sample.http.fetcher.FetchResponse;
import sample.http.fetcher.UrlFetcher;
import sample.http.parser.DocumentParser;
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
	public Either<Integer, UrlData> analyse(String url)
		throws UrlAnalyserException
	{
		logger.info("Analysing url: '{}'", url);
		final URL           locator    = urlFrom(url);
		final FetchResponse response   = fetcher.fetch(locator);
		final int           statusCode = response.statusCode();
		if(HttpUtils.isOk(statusCode))
		{
			final Optional<InputStream> input = response.inputStream();
			final String                html  = toString(input.get());
			return Either.right(parser.parse(html));
		}
		else
		{
			return Either.left(statusCode);
		}
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
