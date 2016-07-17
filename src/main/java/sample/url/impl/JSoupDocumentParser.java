package sample.url.impl;

import java.io.InputStream;

import sample.url.DocumentParser;
import sample.url.ImmutableUrlData;
import sample.url.UrlData;

public class JSoupDocumentParser
	implements DocumentParser
{
	@Override
	public UrlData parse(InputStream input)
	{
		return ImmutableUrlData.builder().build();
	}
}
