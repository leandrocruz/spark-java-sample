package de.mobile;

import static spark.Spark.get;

import de.mobile.domain.ImmutableSample;
import spark.Request;
import spark.Response;
import xingu.container.ContainerUtils;
import xingu.factory.Factory;
import xingu.lang.NotImplementedYet;

public class Application
	extends ApplicationSupport
{
	public static void main(String[] args)
		throws Exception
	{
		ContainerUtils
			.getContainer()
			.lookup(Factory.class)
			.create(Application.class);
	}

	@Override
	protected void registerRoutes()
		throws Exception
	{
		get("/customer/:name/:age", handle((req, res) -> onOk(req, res)));
		get("/err", 				handle((req, res) -> onError(req, res)));	
	}

	Object onError(Request req, Response res)
		throws Exception
	{
		throw new NotImplementedYet("sorry!");
	}

	Object onOk(Request request, Response response)
		throws Exception
	{
		final String age  = request.params(":age");
		final String name = request.params(":name");

		return ImmutableSample
				.builder()
				.name(name)
				.age(Integer.parseInt(age))
				.addFavoriteColors("blue", "gray", "green")
				.build();
	}
}