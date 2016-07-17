package sample.url;

import java.io.InputStream;
import java.net.URL;

public interface UrlFetcher
{
	InputStream fetch(URL locator);
}
