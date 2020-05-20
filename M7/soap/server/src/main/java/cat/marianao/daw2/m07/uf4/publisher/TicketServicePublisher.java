package cat.marianao.daw2.m07.uf4.publisher;

import javax.xml.ws.Endpoint;

import cat.marianao.daw2.m07.uf4.service.impl.TicketServiceEndpointImpl;

public class TicketServicePublisher {
    public static void main(String[] args) {
	Endpoint.publish("http://localhost:9999/publisher/TicketService", new TicketServiceEndpointImpl());
    }

}
