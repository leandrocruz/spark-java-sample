package sample.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import javaslang.control.Either;
import sample.http.analyser.UrlAnalyser;
import sample.http.commons.Heading;
import sample.http.commons.Link;
import sample.http.commons.UrlData;
import sample.http.fetcher.FetchResponse;
import sample.http.fetcher.ImmutableFetchResponse;
import sample.http.fetcher.UrlFetcher;
import sample.http.parser.DocumentParser;
import sample.http.parser.impl.JSoupDocumentParser;
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
		final Either<Integer, UrlData> data = analyser.analyse("http://any.com");
	}

	private FetchResponse toFetchResponse(int code, InputStream is)
	{
		return ImmutableFetchResponse.builder().inputStream(is).statusCode(code).build();
	}
	
	@Test(expected=NotImplementedYet.class)
	public void testOlderDoctypes()
		throws Exception
	{
		final String doctype = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n\"http://www.w3.org/TR/html4/strict.dtd\">";
		when(fetcher.fetch(any(URL.class))).thenReturn(toFetchResponse(200, IOUtils.toInputStream(doctype)));
		analyser.analyse("http://any.com");
	}
	
	@Test
	public void testSampleDocument()
		throws Exception
	{
		final String      url   = "https://www.kernel.org";
		final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("kernel.org");
		when(fetcher.fetch(new URL(url))).thenReturn(toFetchResponse(200, input));
		
		final Either<Integer, UrlData> data = analyser.analyse(url);
		assertTrue(data.isRight());
		assertEquals(Optional.of("The Linux Kernel Archives"), data.get().getTitle());
		assertEquals("5+", data.get().getVersion().get());

		/* Links */
		final List<Link> links = data.get().getLinks();
		assertEquals(117, links.size());
		
		final List<Link> external = links.stream().filter(link -> link.isExternal()).collect(Collectors.toList());
		assertEquals(109, external.size());

		final List<Link> relative = links.stream().filter(link -> !link.isExternal()).collect(Collectors.toList());
		assertEquals(8, relative.size());

		/* Headings */
		final List<Heading> headings = data.get().getHeadings();
		assertEquals(3, headings.size());
		assertEquals(1, headings.get(0).getLevel());
		assertEquals(2, headings.get(1).getLevel());
		assertEquals(2, headings.get(2).getLevel());
	}
}
