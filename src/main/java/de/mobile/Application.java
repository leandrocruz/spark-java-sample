package de.mobile;

import static spark.Spark.get;

import org.apache.avalon.framework.activity.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mobile.codec.Codec;
import spark.Request;
import spark.Response;
import spark.Route;
import xingu.container.Container;
import xingu.container.ContainerUtils;
import xingu.container.Inject;
import xingu.factory.Factory;
import xingu.lang.NotImplementedYet;

public class Application
	implements Initializable
{
	@Inject
	private Codec codec;
	
	private final Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args)
		throws Exception
	{
		final Container container = ContainerUtils.getContainer();
		final Factory factory = container.lookup(Factory.class);
		factory.create(Application.class);
	}

	@Override
	public void initialize()
		throws Exception
	{
		get("/ok/:id", 	new RouteHandler((req, res) -> onOk(req, res)));
		get("/err", 	new RouteHandler((req, res) -> onError(req, res)));

	}

	private Object onError(Request req, Response res)
		throws Exception
	{
		throw new NotImplementedYet("sorry!");
	}

	private Object onOk(Request request, Response response)
		throws Exception
	{
		final String id = request.params(":id");
		return "ok: " + id;
	}

	class RouteHandler
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