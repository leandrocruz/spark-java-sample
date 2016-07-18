package sample.url;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import sample.url.impl.JSoupDocumentParser;
import xingu.container.Binder;
import xingu.container.Inject;
import xingu.container.XinguTestCase;
import xingu.lang.NotImplementedYet;

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
	@Ignore
	public void testEmptyDocument()
		throws Exception
	{
		final UrlData data = analyser.analyse("http://any.com");
		assertEquals(Optional.empty(), data.version());
		assertEquals(Optional.empty(), data.title());
	}

	@Test(expected=NotImplementedYet.class)
	public void testOlderDoctypes()
		throws Exception
	{
		final String doctype = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n\"http://www.w3.org/TR/html4/strict.dtd\">";
		when(fetcher.fetch(any(URL.class))).thenReturn(IOUtils.toInputStream(doctype));
		analyser.analyse("http://any.com");
	}
	
	@Test
	public void testSampleDocument()
		throws Exception
	{
		final String      url   = "https://www.kernel.org";
		final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("kernel.org");
		when(fetcher.fetch(new URL(url))).thenReturn(input);
		
		final UrlData data = analyser.analyse(url);
		assertEquals(Optional.of("The Linux Kernel Archives"), data.title());
		assertEquals("5+", data.version().get());

		/* Links */
		final List<Link> links = data.links();
		assertEquals(117, links.size());
		
		final List<Link> external = links.stream().filter(link -> link.isExternal()).collect(Collectors.toList());
		assertEquals(109, external.size());

		final List<Link> relative = links.stream().filter(link -> !link.isExternal()).collect(Collectors.toList());
		assertEquals(8, relative.size());

		/* Headings */
		final List<Heading> headings = data.headings();
		assertEquals(3, headings.size());
		assertEquals(1, headings.get(0).level());
		assertEquals(2, headings.get(1).level());
		assertEquals(2, headings.get(2).level());
	}
}
