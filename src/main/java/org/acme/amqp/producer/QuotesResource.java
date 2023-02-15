package org.acme.amqp.producer;

import io.smallrye.mutiny.Multi;
import org.acme.amqp.model.Quote;
import org.acme.amqp.model.QuoteRequest;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.logging.Logger;

@Path("/quotes")
public class QuotesResource {
    private final Logger logger = Logger.getLogger(QuotesResource.class.getName());

    @Channel("quote-requests")
    Emitter<QuoteRequest> quoteRequestEmitter; // <1>

    @Channel("quotes")
    Multi<Quote> quotes;

    /**
     * Endpoint retrieving the "quotes" queue and sending the items to a server sent event.
     */
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS) // <2>
    public Multi<Quote> stream() {
        return quotes; // <3>
    }

    /**
     * Endpoint to generate a new quote request id and send it to "quote-requests" AMQP queue using the emitter.
     */
    @POST
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    public String createRequest() {
        final UUID randomUUID = UUID.randomUUID();
        final String uuid = randomUUID.toString();
        final long startTime = System.currentTimeMillis();
        final QuoteRequest quoteRequest = new QuoteRequest(uuid, startTime);
        quoteRequestEmitter.send(quoteRequest);
        return uuid;
    }
}