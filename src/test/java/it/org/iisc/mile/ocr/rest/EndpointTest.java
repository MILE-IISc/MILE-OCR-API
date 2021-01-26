package it.org.iisc.mile.ocr.rest;

import static org.junit.Assert.assertEquals;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.Test;

public class EndpointTest {

    @Test
    public void testGetProperties() {
        String port = System.getProperty("liberty.test.port");
        String url = "http://localhost:" + port + "/";

        Client client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);

        WebTarget target = client.target(url + "/health");
        Response response = target.request().get();

        assertEquals("Incorrect response code from " + url, 
                     Response.Status.OK.getStatusCode(), response.getStatus());

        JsonObject obj = response.readEntity(JsonObject.class);

        assertEquals("The system property for the local and remote JVM should match",
                     "UP",
                     obj.getString("status"));
        response.close();
    }
}
