
package cat.marianao.daw2.m07.uf4.service.client.jaxws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "TicketServiceEndpoint", targetNamespace = "http://service.uf4.m07.daw2.marianao.cat/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface TicketServiceEndpoint {


    /**
     * 
     * @param arg0
     * @return
     *     returns cat.marianao.daw2.m07.uf4.service.client.jaxws.Show
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "cancelReservation", targetNamespace = "http://service.uf4.m07.daw2.marianao.cat/", className = "cat.marianao.daw2.m07.uf4.service.client.jaxws.CancelReservation")
    @ResponseWrapper(localName = "cancelReservationResponse", targetNamespace = "http://service.uf4.m07.daw2.marianao.cat/", className = "cat.marianao.daw2.m07.uf4.service.client.jaxws.CancelReservationResponse")
    @Action(input = "http://service.uf4.m07.daw2.marianao.cat/TicketServiceEndpoint/cancelReservationRequest", output = "http://service.uf4.m07.daw2.marianao.cat/TicketServiceEndpoint/cancelReservationResponse")
    public Show cancelReservation(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<cat.marianao.daw2.m07.uf4.service.client.jaxws.Show>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAllShows", targetNamespace = "http://service.uf4.m07.daw2.marianao.cat/", className = "cat.marianao.daw2.m07.uf4.service.client.jaxws.GetAllShows")
    @ResponseWrapper(localName = "getAllShowsResponse", targetNamespace = "http://service.uf4.m07.daw2.marianao.cat/", className = "cat.marianao.daw2.m07.uf4.service.client.jaxws.GetAllShowsResponse")
    @Action(input = "http://service.uf4.m07.daw2.marianao.cat/TicketServiceEndpoint/getAllShowsRequest", output = "http://service.uf4.m07.daw2.marianao.cat/TicketServiceEndpoint/getAllShowsResponse")
    public List<Show> getAllShows();

    /**
     * 
     * @param arg0
     * @return
     *     returns cat.marianao.daw2.m07.uf4.service.client.jaxws.Show
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "makeReservation", targetNamespace = "http://service.uf4.m07.daw2.marianao.cat/", className = "cat.marianao.daw2.m07.uf4.service.client.jaxws.MakeReservation")
    @ResponseWrapper(localName = "makeReservationResponse", targetNamespace = "http://service.uf4.m07.daw2.marianao.cat/", className = "cat.marianao.daw2.m07.uf4.service.client.jaxws.MakeReservationResponse")
    @Action(input = "http://service.uf4.m07.daw2.marianao.cat/TicketServiceEndpoint/makeReservationRequest", output = "http://service.uf4.m07.daw2.marianao.cat/TicketServiceEndpoint/makeReservationResponse")
    public Show makeReservation(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
