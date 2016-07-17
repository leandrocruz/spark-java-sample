package sample.url;

public class UrlAnalyserException
	extends RuntimeException
{

	public UrlAnalyserException(String message)
	{
		super(message);
	}

	public UrlAnalyserException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
