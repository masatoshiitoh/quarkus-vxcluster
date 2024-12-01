package org.acme;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;

import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/vertx")
public class VertxResource {

    private final Vertx vertx;

    @Inject
    public VertxResource(Vertx vertx) {
        System.out.println(vertx.isClustered() ? "Clustered vertx" : "Standalone vertx");
        
        this.vertx = vertx;
    }

    @GET
    @Path("/lorem")
    public Uni<String> readShortFile() {
        vertx.eventBus().publish("quarkus.test", "Hello from Vert.x!");
        return vertx.fileSystem().readFile("lorem.txt")
                .onItem().transform(content -> content.toString(StandardCharsets.UTF_8));
    }
}