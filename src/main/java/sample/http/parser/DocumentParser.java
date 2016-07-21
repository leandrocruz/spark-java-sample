package sample.http.parser;

import sample.http.commons.UrlData;

public interface DocumentParser
{
	UrlData parse(String input);
}
