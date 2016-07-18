package sample;

import static spark.Spark.get;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

import sample.url.UrlAnalyser;
import spark.Request;
import spark.Response;
import xingu.container.ContainerUtils;
import xingu.container.Inject;
import xingu.factory.Factory;
import xingu.lang.NotImplementedYet;

public class Application
	extends ApplicationSupport
{
	@Inject
	private UrlAnalyser analyser;
	
	public static void main(String[] args)
		throws Exception
	{
		final InputStream config = Thread
				.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("pulga.xml");

		ContainerUtils
			.getContainer(config)
			.lookup(Factory.class)
			.create(Application.class);
	}

	@Override
	protected void registerRoutes()
		throws Exception
	{
		get("/analyse/:url", handle((req, res) -> analyse(req, res)));
	}

	Object analyse(Request request, Response response)
		throws Exception
	{
		String url = request.params(":url");
		if(StringUtils.isEmpty(url))
		{
			throw new NotImplementedYet("Parameter 'url' is missing");
		}
		
		if(!url.startsWith("http") && !url.startsWith("https"))
		{
			int index = url.indexOf(":");
			if(index > 0)
			{
				throw new NotImplementedYet("Unknown protocol: " + url.substring(0, index));
			}
			url = "http://" + url;
		}
		return analyser.analyse(url);
	}
}