package sample.url.impl;

import sample.url.ImmutableUrlData;
import sample.url.UrlAnalyser;
import sample.url.UrlData;

public class UrlAnalyserImpl
	implements UrlAnalyser
{
	@Override
	public UrlData analyse(String url)
	{
		return ImmutableUrlData.builder().build();
	}
}
