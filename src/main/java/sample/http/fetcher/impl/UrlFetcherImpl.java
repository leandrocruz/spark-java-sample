package sample.http.fetcher.impl;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.avalon.framework.activity.Initializable;
import org.apache.avalon.framework.activity.Startable;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import sample.http.commons.HttpUtils;
import sample.http.fetcher.FetchResponse;
import sample.http.fetcher.ImmutableFetchResponse;
import sample.http.fetcher.UrlFetcher;
import sample.http.fetcher.UrlFetcherException;

public class UrlFetcherImpl
	implements UrlFetcher, Initializable, Startable
{
	private ResponseHandler<FetchResponse> handler;
	
	private CloseableHttpClient client;

	@Override
	public void start()
		throws Exception
	{
		this.client = HttpClients.createDefault();
	}

	@Override
	public void stop()
		throws Exception
	{
		IOUtils.closeQuietly(this.client);
	}

	@Override
	public void initialize()
		throws Exception
	{
		handler = response -> {
			final int status = response.getStatusLine().getStatusCode();
			if(HttpUtils.isOk(status))
			{
				final HttpEntity entity = response.getEntity();
				final byte[]     array  = EntityUtils.toByteArray(entity);
				return ImmutableFetchResponse
						.builder()
						.inputStream(new ByteArrayInputStream(array))
						.statusCode(status)
						.build();
			}
			else
			{
				return ImmutableFetchResponse
						.builder()
						.statusCode(status)
						.build();
			}
		};
	}

	@Override
	public FetchResponse fetch(URL locator)
		throws UrlFetcherException
	{
		final String url = locator.toExternalForm();
		try
		{
			/*
			 * TODO: add network timeout
			 */
			return client.execute(new HttpGet(url), handler);
		}
		catch(UnknownHostException e)
		{
			return ImmutableFetchResponse.builder().statusCode(-1).build();
		}
		catch(Exception e)
		{
			throw new UrlFetcherException("Error fetching url: " + url, e);
		}
	}
}
