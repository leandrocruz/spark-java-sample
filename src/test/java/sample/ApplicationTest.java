package sample;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import xingu.container.ContainerUtils;
import xingu.factory.Factory;

/*
 * This test only works if the test machine has network access to google.com 
 */
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
	public void testAnalyse()
		throws Exception
	{
		final HttpResponse<JsonNode> resp = Unirest.get("http://localhost:8000/analyse?u=google.com").asJson();
		assertEquals(200, resp.getStatus());
		
		final JSONObject obj = resp.getBody().getObject().getJSONObject("payload");
		assertEquals("Google", obj.getString("title"));
		assertEquals("5+", obj.getString("version"));
		assertEquals(20, obj.getJSONArray("links").length());
		assertEquals(0, obj.getJSONArray("headings").length());

	}
}
