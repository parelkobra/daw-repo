package cat.institutmarianao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.net.URI;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.junit.Test;

import cat.institutmarianao.domain.Book;

public class BookRestServiceTest {
    private static final Client CLIENT = ClientBuilder.newClient();

    // Get All Books
    @Test
    public void shouldReturnAllBooks() {
	URI uri = UriBuilder.fromUri("http://localhost/books/rest/books").port(8080).build();
	WebTarget target = CLIENT.target(uri);
	Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();

	Book book1 = new Book("9788425343537", "Ildefonso Falcones", "La catedral del mar");
	Book book2 = new Book("9788467009477", "Jose Maria Peridis Perez", "La luz y el misterio de las catedrales");

	Response res = invocation.invoke();
	List<Book> returnedBooks = res.readEntity(new GenericType<List<Book>>() {
	});

	assertTrue(returnedBooks.contains(book1));
	assertTrue(returnedBooks.contains(book2));
    }

    // Find existing book
    @Test
    public void existingBookShouldBeReturned() {
	URI uri = UriBuilder.fromUri("http://localhost/books/rest/books").port(8080).build();
	WebTarget target = CLIENT.target(uri).path("9788467009477");
	Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();

	Book expectedBook = new Book("9788467009477", "La luz y el misterio de las catedrales",
		"Jose Maria Peridis Perez");

	Response res = invocation.invoke();
	Book responseBook = res.readEntity(Book.class);

	assertEquals(expectedBook, responseBook);
    }

    // Find non exixstent book
    @Test
    public void nonExistentBookShouldReturn404() {
	URI uri = UriBuilder.fromUri("http://localhost/books/rest/books").port(8080).build();
	WebTarget target = CLIENT.target(uri).path("unknownISBN");
	Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();

	Response res = invocation.invoke();

	assertEquals(Response.Status.NOT_FOUND.toString(), res.getStatusInfo().toString());
    }

    // Create null book
    @Test
    public void attemptsToCreateNullBooksShouldReturn400() {
	URI uri = UriBuilder.fromUri("http://localhost/books/rest/books").port(8080).build();
	WebTarget target = CLIENT.target(uri);
	Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildPost(null);

	Response res = invocation.invoke();

	assertEquals(Response.Status.BAD_REQUEST.toString(), res.getStatusInfo().toString());
    }

    // Create new book
    @Test
    public void createBookShouldReturnTheURLToGetTheBook() {
	Book book = new Book("9788423342518", "Clara Sanchez", "Lo que esconde tu nombre");

	URI uri = UriBuilder.fromUri("http://localhost/books/rest/books").port(8080).build();
	WebTarget target = CLIENT.target(uri);
	Invocation invocation = target.request(MediaType.APPLICATION_JSON)
		.buildPost(Entity.entity(book, MediaType.APPLICATION_JSON));

	Response res = invocation.invoke();

	// Throws javax.ws.rs.ProcessingException: Error reading entity from input stream
	// URI returnedUri = res.readEntity(URI.class);

	String returnedUri = getResponseURI(res);

	String expectedUri = UriBuilder.fromUri("http://localhost/books/rest/books").port(8080).path("9788423342518")
		.build().toString();

	assertEquals(expectedUri, returnedUri);
    }

    // Edit non existent book
    @Test
    public void editNonExistentBookShouldReturn404() {
	Book bookToEdit = new Book("", "", "");

	URI uri = UriBuilder.fromUri("http://localhost/books/rest/books").port(8080).build();
	WebTarget target = CLIENT.target(uri);
	Invocation invocation = target.request(MediaType.APPLICATION_JSON)
		.buildPut(Entity.entity(bookToEdit, MediaType.APPLICATION_JSON));

	Response res = invocation.invoke();

	assertEquals(Response.Status.NOT_FOUND.toString(), res.getStatusInfo().toString());
    }

    // Edit existing book
    @Test
    public void editExistingBookShouldReturnItEdited() {
	Book book = new Book("9788467009477", "Fiodor Dostoievski", "El idiota");

	URI uri = UriBuilder.fromUri("http://localhost/books/rest/books").port(8080).build();
	WebTarget target = CLIENT.target(uri);
	Invocation invocation = target.request(MediaType.APPLICATION_JSON)
		.buildPut(Entity.entity(book, MediaType.APPLICATION_JSON));

	Response res = invocation.invoke();
	Book responseBook = res.readEntity(Book.class);

	assertEquals(book, responseBook);

	// Edit it back
	book = new Book("9788467009477", "Jose Maria Peridis Perez", "La luz y el misterio de las catedrales");
	invocation = target.request(MediaType.APPLICATION_JSON)
		.buildPut(Entity.entity(book, MediaType.APPLICATION_JSON));
	res = invocation.invoke();
    }

    // Remove existing book
    @Test
    public void deleteExistingBookShouldReturn200() {
	URI uri = UriBuilder.fromUri("http://localhost/books/rest/books").port(8080).build();
	WebTarget target = CLIENT.target(uri).path("9788425343537");
	Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildDelete();

	Response res = invocation.invoke();

	assertEquals(Response.Status.OK.toString(), res.getStatusInfo().toString());

	// Add the deleted book back
	Book book = new Book("9788425343537", "Ildefonso Falcones", "La catedral del mar");

	target = CLIENT.target(uri);
	invocation = target.request(MediaType.APPLICATION_JSON)
		.buildPost(Entity.entity(book, MediaType.APPLICATION_JSON));
	invocation.invoke();
    }

    // Remove non existent book
    @Test
    public void deleteNonExistingBookShouldReturn404() {
	URI uri = UriBuilder.fromUri("http://localhost/books/rest/books").port(8080).build();
	WebTarget target = CLIENT.target(uri).path("unknownISBN");
	Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildDelete();

	Response res = invocation.invoke();

	assertEquals(Response.Status.NOT_FOUND.toString(), res.getStatusInfo().toString());
    }

    // Find books by title
    @Test
    public void shouldReturnListOfBooksContainingTitle() {
	String titleString = "catedral";

	URI uri = UriBuilder.fromUri("http://localhost/books/rest/books/findByTitle").port(8080).build();
	WebTarget target = CLIENT.target(uri).path(titleString);
	Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();

	Response res = invocation.invoke();
	List<Book> returnedBooks = res.readEntity(new GenericType<List<Book>>() {
	});

	assertTrue("List size should be 2 but it's " + returnedBooks.size(), returnedBooks.size() == 2);
    }

    /**
     * Extracts the URI from the response's entity
     *
     * @param response The response object
     * @return The string value of the json object
     */
    private String getResponseURI(Response response) {
	String jsonResponse = response.readEntity(String.class);
	JsonObject jsonObj = Json.createReader(new StringReader(jsonResponse)).readObject();
	return jsonObj.getString("value");
    }
}
