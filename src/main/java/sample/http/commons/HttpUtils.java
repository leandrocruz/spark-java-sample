package sample.http.commons;

import org.apache.commons.lang3.StringUtils;

public class HttpUtils
{
	public static boolean urlStartsWithHttpOrHttps(String url)
	{
		if(StringUtils.isEmpty(url))
		{
			return false;
		}
		return url.startsWith("http") || url.startsWith("https");
	}

	public static boolean isOk(int status)
	{
		return status >= 200 && status < 300;
	}
}
