package sample;

import static spark.Spark.after;
import static spark.Spark.*;

import org.apache.avalon.framework.activity.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sample.codec.Codec;
import spark.Request;
import spark.Response;
import spark.Route;
import xingu.container.Inject;

public abstract class ApplicationSupport
	implements Initializable
{
	@Inject
	protected Codec codec;
	
	private final Logger logger = LoggerFactory.getLogger(Application.class);

	@Override
	public void initialize()
		throws Exception
	{
		port(8000);
		after((request, response) -> response.header("Content-Encoding", "gzip"));
		registerRoutes();
	}
	
	public void shutdown()
	{
		stop();
	}

	protected abstract void registerRoutes()
		throws Exception;

	protected Route handle(Route route)
	{
		return new RouteHandler(route);
	}

	private class RouteHandler
		implements Route
	{
		private final Route route;
	
		public RouteHandler(Route route)
		{
			this.route = route;
		}
		
		@Override
		public Object handle(Request request, Response response)
			throws Exception
		{
			final boolean allowed = true; //authManager.isAllowed(request, response);
			if(!allowed)
			{
				response.status(401);
				return "Request rejected";
			}
			try
			{
				final Object result = route.handle(request, response);
				return codec.encode(result);
			}
			catch(Exception e)
			{
				logger.error("Error handling request", e);
				response.status(500);
				return "Error handling request: " + e.getMessage(); //maybe report the exception to the client?
			}
		}
	}
}
