package sample;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import sample.Application;
import xingu.container.ContainerUtils;
import xingu.factory.Factory;

public class ApplicationTest
{
	private static Application app;
	
	@BeforeClass
	public static void setUp()
		throws Exception
	{
		app = ContainerUtils.getContainer().lookup(Factory.class).create(Application.class);	
	}
	
	@AfterClass
	public static void tearDown()
		throws Exception
	{
		app.shutdown();
	}
	
	@Test
	public void testError()
		throws Exception
	{
		final HttpResponse<String> resp = Unirest.get("http://localhost:8000/err").asString();
		assertEquals(500, resp.getStatus());
	}

	@Test
	public void testCustomer()
		throws Exception
	{
		final HttpResponse<JsonNode> resp = Unirest.get("http://localhost:8000/customer/leandro/40").asJson();
		assertEquals(200, resp.getStatus());
		
		//{"name":"leandro","age":1,"colors":["blue","gray","green"]}
		final JSONObject obj = resp.getBody().getObject();
		assertEquals("leandro", obj.getString("name"));
		assertEquals(40, obj.getInt("age"));
		assertEquals(String.join(",", new String[] {"\"blue\"", "\"gray\"", "\"green\""}), obj.getJSONArray("colors").join(","));
	}

	@Test
	public void testBadCustomerAge()
		throws Exception
	{
		final HttpResponse<String> resp = Unirest.get("http://localhost:8000/customer/leandro/a").asString();
		assertEquals(500, resp.getStatus());
	}
}
