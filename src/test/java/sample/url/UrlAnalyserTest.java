package sample.url;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import xingu.container.Binder;
import xingu.container.Inject;
import xingu.container.XinguTestCase;

public class UrlAnalyserTest
	extends XinguTestCase
{
	@Inject
	private UrlAnalyser analyser;

	@Override
	protected void rebind(Binder binder)
		throws Exception
	{
		super.rebind(binder);
	}

	@Test
	public void testEmptyDocument()
		throws Exception
	{
		final UrlData data = analyser.analyse("http://somedomain.com");
		assertEquals(Optional.empty(), data.getVersion());
		assertEquals(Optional.empty(), data.getTitle());
	}
}
