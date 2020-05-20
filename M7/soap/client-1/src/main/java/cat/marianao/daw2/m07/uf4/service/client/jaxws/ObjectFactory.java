
package cat.marianao.daw2.m07.uf4.service.client.jaxws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cat.marianao.daw2.m07.uf4.service.client.jaxws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MakeReservationResponse_QNAME = new QName("http://service.uf4.m07.daw2.marianao.cat/", "makeReservationResponse");
    private final static QName _GetAllShowsResponse_QNAME = new QName("http://service.uf4.m07.daw2.marianao.cat/", "getAllShowsResponse");
    private final static QName _CancelReservationResponse_QNAME = new QName("http://service.uf4.m07.daw2.marianao.cat/", "cancelReservationResponse");
    private final static QName _CancelReservation_QNAME = new QName("http://service.uf4.m07.daw2.marianao.cat/", "cancelReservation");
    private final static QName _GetAllShows_QNAME = new QName("http://service.uf4.m07.daw2.marianao.cat/", "getAllShows");
    private final static QName _MakeReservation_QNAME = new QName("http://service.uf4.m07.daw2.marianao.cat/", "makeReservation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cat.marianao.daw2.m07.uf4.service.client.jaxws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllShows }
     * 
     */
    public GetAllShows createGetAllShows() {
        return new GetAllShows();
    }

    /**
     * Create an instance of {@link MakeReservation }
     * 
     */
    public MakeReservation createMakeReservation() {
        return new MakeReservation();
    }

    /**
     * Create an instance of {@link CancelReservation }
     * 
     */
    public CancelReservation createCancelReservation() {
        return new CancelReservation();
    }

    /**
     * Create an instance of {@link CancelReservationResponse }
     * 
     */
    public CancelReservationResponse createCancelReservationResponse() {
        return new CancelReservationResponse();
    }

    /**
     * Create an instance of {@link MakeReservationResponse }
     * 
     */
    public MakeReservationResponse createMakeReservationResponse() {
        return new MakeReservationResponse();
    }

    /**
     * Create an instance of {@link GetAllShowsResponse }
     * 
     */
    public GetAllShowsResponse createGetAllShowsResponse() {
        return new GetAllShowsResponse();
    }

    /**
     * Create an instance of {@link Show }
     * 
     */
    public Show createShow() {
        return new Show();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.uf4.m07.daw2.marianao.cat/", name = "makeReservationResponse")
    public JAXBElement<MakeReservationResponse> createMakeReservationResponse(MakeReservationResponse value) {
        return new JAXBElement<MakeReservationResponse>(_MakeReservationResponse_QNAME, MakeReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllShowsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.uf4.m07.daw2.marianao.cat/", name = "getAllShowsResponse")
    public JAXBElement<GetAllShowsResponse> createGetAllShowsResponse(GetAllShowsResponse value) {
        return new JAXBElement<GetAllShowsResponse>(_GetAllShowsResponse_QNAME, GetAllShowsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.uf4.m07.daw2.marianao.cat/", name = "cancelReservationResponse")
    public JAXBElement<CancelReservationResponse> createCancelReservationResponse(CancelReservationResponse value) {
        return new JAXBElement<CancelReservationResponse>(_CancelReservationResponse_QNAME, CancelReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.uf4.m07.daw2.marianao.cat/", name = "cancelReservation")
    public JAXBElement<CancelReservation> createCancelReservation(CancelReservation value) {
        return new JAXBElement<CancelReservation>(_CancelReservation_QNAME, CancelReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllShows }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.uf4.m07.daw2.marianao.cat/", name = "getAllShows")
    public JAXBElement<GetAllShows> createGetAllShows(GetAllShows value) {
        return new JAXBElement<GetAllShows>(_GetAllShows_QNAME, GetAllShows.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.uf4.m07.daw2.marianao.cat/", name = "makeReservation")
    public JAXBElement<MakeReservation> createMakeReservation(MakeReservation value) {
        return new JAXBElement<MakeReservation>(_MakeReservation_QNAME, MakeReservation.class, null, value);
    }

}
