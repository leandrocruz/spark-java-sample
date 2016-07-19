package sample.http;

public interface UrlAnalyser
{
	UrlData analyse(String url)
		throws UrlAnalyserException;
}
