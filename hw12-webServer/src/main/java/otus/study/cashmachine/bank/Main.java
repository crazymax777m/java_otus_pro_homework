package otus.study.cashmachine.bank;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
        ResourceConfig config = new ResourceConfig().packages("otus.study.cashmachine.bank.resources");

        GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("Jersey app started. Available at " + baseUri);
    }
}
