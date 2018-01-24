
package server.datetype;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the server.datetype package. 
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

    private final static QName _GetStudentByPrice_QNAME = new QName("http://datetype.server/", "getStudentByPrice");
    private final static QName _GetStudentById_QNAME = new QName("http://datetype.server/", "getStudentById");
    private final static QName _AddStudent_QNAME = new QName("http://datetype.server/", "addStudent");
    private final static QName _GetAllStudentsMap_QNAME = new QName("http://datetype.server/", "getAllStudentsMap");
    private final static QName _GetAllStudentsMapResponse_QNAME = new QName("http://datetype.server/", "getAllStudentsMapResponse");
    private final static QName _GetStudentByPriceResponse_QNAME = new QName("http://datetype.server/", "getStudentByPriceResponse");
    private final static QName _AddStudentResponse_QNAME = new QName("http://datetype.server/", "addStudentResponse");
    private final static QName _GetStudentByIdResponse_QNAME = new QName("http://datetype.server/", "getStudentByIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: server.datetype
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllStudentsMapResponse }
     * 
     */
    public GetAllStudentsMapResponse createGetAllStudentsMapResponse() {
        return new GetAllStudentsMapResponse();
    }

    /**
     * Create an instance of {@link GetAllStudentsMapResponse.Return }
     * 
     */
    public GetAllStudentsMapResponse.Return createGetAllStudentsMapResponseReturn() {
        return new GetAllStudentsMapResponse.Return();
    }

    /**
     * Create an instance of {@link AddStudent }
     * 
     */
    public AddStudent createAddStudent() {
        return new AddStudent();
    }

    /**
     * Create an instance of {@link GetStudentById }
     * 
     */
    public GetStudentById createGetStudentById() {
        return new GetStudentById();
    }

    /**
     * Create an instance of {@link GetStudentByPrice }
     * 
     */
    public GetStudentByPrice createGetStudentByPrice() {
        return new GetStudentByPrice();
    }

    /**
     * Create an instance of {@link AddStudentResponse }
     * 
     */
    public AddStudentResponse createAddStudentResponse() {
        return new AddStudentResponse();
    }

    /**
     * Create an instance of {@link GetStudentByIdResponse }
     * 
     */
    public GetStudentByIdResponse createGetStudentByIdResponse() {
        return new GetStudentByIdResponse();
    }

    /**
     * Create an instance of {@link GetAllStudentsMap }
     * 
     */
    public GetAllStudentsMap createGetAllStudentsMap() {
        return new GetAllStudentsMap();
    }

    /**
     * Create an instance of {@link GetStudentByPriceResponse }
     * 
     */
    public GetStudentByPriceResponse createGetStudentByPriceResponse() {
        return new GetStudentByPriceResponse();
    }

    /**
     * Create an instance of {@link Student }
     * 
     */
    public Student createStudent() {
        return new Student();
    }

    /**
     * Create an instance of {@link GetAllStudentsMapResponse.Return.Entry }
     * 
     */
    public GetAllStudentsMapResponse.Return.Entry createGetAllStudentsMapResponseReturnEntry() {
        return new GetAllStudentsMapResponse.Return.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentByPrice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datetype.server/", name = "getStudentByPrice")
    public JAXBElement<GetStudentByPrice> createGetStudentByPrice(GetStudentByPrice value) {
        return new JAXBElement<GetStudentByPrice>(_GetStudentByPrice_QNAME, GetStudentByPrice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datetype.server/", name = "getStudentById")
    public JAXBElement<GetStudentById> createGetStudentById(GetStudentById value) {
        return new JAXBElement<GetStudentById>(_GetStudentById_QNAME, GetStudentById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddStudent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datetype.server/", name = "addStudent")
    public JAXBElement<AddStudent> createAddStudent(AddStudent value) {
        return new JAXBElement<AddStudent>(_AddStudent_QNAME, AddStudent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllStudentsMap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datetype.server/", name = "getAllStudentsMap")
    public JAXBElement<GetAllStudentsMap> createGetAllStudentsMap(GetAllStudentsMap value) {
        return new JAXBElement<GetAllStudentsMap>(_GetAllStudentsMap_QNAME, GetAllStudentsMap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllStudentsMapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datetype.server/", name = "getAllStudentsMapResponse")
    public JAXBElement<GetAllStudentsMapResponse> createGetAllStudentsMapResponse(GetAllStudentsMapResponse value) {
        return new JAXBElement<GetAllStudentsMapResponse>(_GetAllStudentsMapResponse_QNAME, GetAllStudentsMapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentByPriceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datetype.server/", name = "getStudentByPriceResponse")
    public JAXBElement<GetStudentByPriceResponse> createGetStudentByPriceResponse(GetStudentByPriceResponse value) {
        return new JAXBElement<GetStudentByPriceResponse>(_GetStudentByPriceResponse_QNAME, GetStudentByPriceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddStudentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datetype.server/", name = "addStudentResponse")
    public JAXBElement<AddStudentResponse> createAddStudentResponse(AddStudentResponse value) {
        return new JAXBElement<AddStudentResponse>(_AddStudentResponse_QNAME, AddStudentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://datetype.server/", name = "getStudentByIdResponse")
    public JAXBElement<GetStudentByIdResponse> createGetStudentByIdResponse(GetStudentByIdResponse value) {
        return new JAXBElement<GetStudentByIdResponse>(_GetStudentByIdResponse_QNAME, GetStudentByIdResponse.class, null, value);
    }

}
