package sample.http.fetcher;

public class UrlFetcherException
	extends RuntimeException
{

	public UrlFetcherException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public UrlFetcherException(String message)
	{
		super(message);
	}

}
