package sample;

import static spark.Spark.get;

import java.io.InputStream;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import javaslang.control.Either;
import sample.http.analyser.UrlAnalyser;
import sample.http.commons.HttpUtils;
import sample.http.commons.UrlData;
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
		get("/analyse", handle((req, res) -> analyse(req, res)));
	}

	private RestResponse analyse(Request request, Response response)
		throws Exception
	{
		final String url = validUrlOrError(request);		
		final Either<Integer, UrlData> either = analyser.analyse(url);
		
		final boolean isError = either.isLeft();
		final Optional<Integer> code    = isError ? Optional.of(either.getLeft()) : Optional.empty();
		final Optional<String>  message = isError ? Optional.of("Error loading url: " + url) : Optional.empty();
		final Optional<UrlData> payload = isError ? Optional.empty() : Optional.of(either.get());

		return ImmutableRestResponse
				.builder()				
				.isError(isError)
				.code(code)
				.message(message)
				.payload(payload)
				.build();
	}

	private String validUrlOrError(Request request)
	{
		String url = request.queryMap().get("u").value();
		if(StringUtils.isEmpty(url))
		{
			throw new NotImplementedYet("Parameter 'url' is missing");
		}

		if(HttpUtils.urlStartsWithHttpOrHttps(url))
		{
			return url;
		}

		int index = url.indexOf(":");
		if(index > 0)
		{
			throw new NotImplementedYet("Unknown protocol: " + url.substring(0, index));
		}
		return "http://" + url;
	}
}