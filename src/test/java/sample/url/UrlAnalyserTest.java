package sample.url;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

import org.junit.Test;

import sample.url.impl.JSoupDocumentParser;
import xingu.container.Binder;
import xingu.container.Inject;
import xingu.container.XinguTestCase;

import static org.mockito.Mockito.*;

public class UrlAnalyserTest
	extends XinguTestCase
{
	@Inject
	private UrlAnalyser analyser;

	@Inject
	private UrlFetcher fetcher;
	
	@Override
	protected void rebind(Binder binder)
		throws Exception
	{
		binder.bind(DocumentParser.class).to(JSoupDocumentParser.class);
		withMock(UrlFetcher.class);
	}

	@Test
	public void testEmptyDocument()
		throws Exception
	{
		final UrlData data = analyser.analyse("http://any.com");
		assertEquals(Optional.empty(), data.getVersion());
		assertEquals(Optional.empty(), data.getTitle());
	}

	@Test
	public void testSampleDocument()
		throws Exception
	{
		final String      url   = "https://www.kernel.org";
		final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("kernel.org");
		when(fetcher.fetch(new URL(url))).thenReturn(input);
		
		final UrlData data = analyser.analyse(url);
		assertEquals(Optional.of("The Linux Kernel Archives"), data.getTitle());
		assertEquals(Optional.empty(), data.getVersion());
	}

}
