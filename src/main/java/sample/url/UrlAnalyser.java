package sample.url;

public interface UrlAnalyser
{
	UrlData analyse(String url)
		throws UrlAnalyserException;

}
