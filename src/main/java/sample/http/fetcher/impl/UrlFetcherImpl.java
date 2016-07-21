package sample.http.fetcher.impl;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.avalon.framework.activity.Initializable;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import sample.http.fetcher.FetchResponse;
import sample.http.fetcher.ImmutableFetchResponse;
import sample.http.fetcher.UrlFetcher;
import sample.http.fetcher.UrlFetcherException;

public class UrlFetcherImpl
	implements UrlFetcher, Initializable
{
	private ResponseHandler<FetchResponse> handler;
	
	@Override
	public void initialize()
		throws Exception
	{
		handler = response -> {
			final int status = response.getStatusLine().getStatusCode();
			if(status >= 200 && status < 300)
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
		final CloseableHttpClient client = getHttpClient();
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
		finally
		{
			IOUtils.closeQuietly(client);
		}
	}

	private CloseableHttpClient getHttpClient()
	{
		/*
		 * TODO: reuse http client
		 */
		return HttpClients.createDefault();
	}
}
