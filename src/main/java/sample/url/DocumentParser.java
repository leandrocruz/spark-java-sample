package sample.url;

import java.io.InputStream;

public interface DocumentParser
{
	UrlData parse(InputStream input);
}
