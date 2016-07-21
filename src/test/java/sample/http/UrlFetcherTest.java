package sample.http;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Optional;

import org.junit.Test;

import sample.http.fetcher.FetchResponse;
import sample.http.fetcher.UrlFetcher;
import xingu.container.Inject;
import xingu.container.XinguTestCase;

public class UrlFetcherTest
	extends XinguTestCase
{
	@Inject
	private UrlFetcher fetcher;
	
	@Test
	public void testUnknownHost()
		throws Exception
	{
		final FetchResponse result = fetcher.fetch(new URL("http://any.xyz"));
		assertEquals(-1, result.statusCode());
		assertEquals(Optional.empty(), result.inputStream());
	}
}
