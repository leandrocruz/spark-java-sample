package sample;

import static spark.Spark.get;

import java.io.InputStream;

import sample.url.UrlAnalyser;
import spark.Request;
import spark.Response;
import xingu.container.ContainerUtils;
import xingu.container.Inject;
import xingu.factory.Factory;

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
		final String url  = request.params(":url");
		return analyser.analyse(url);
	}
}