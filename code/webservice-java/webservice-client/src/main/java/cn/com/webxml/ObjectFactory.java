
package cn.com.webxml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.com.webxml package. 
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

    private final static QName _DataSet_QNAME = new QName("http://WebXml.com.cn/", "DataSet");
    private final static QName _ArrayOfString_QNAME = new QName("http://WebXml.com.cn/", "ArrayOfString");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.com.webxml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSupportCityDatasetResponse }
     * 
     */
    public GetSupportCityDatasetResponse createGetSupportCityDatasetResponse() {
        return new GetSupportCityDatasetResponse();
    }

    /**
     * Create an instance of {@link GetRegionDatasetResponse }
     * 
     */
    public GetRegionDatasetResponse createGetRegionDatasetResponse() {
        return new GetRegionDatasetResponse();
    }

    /**
     * Create an instance of {@link GetRegionCountry }
     * 
     */
    public GetRegionCountry createGetRegionCountry() {
        return new GetRegionCountry();
    }

    /**
     * Create an instance of {@link DataSet }
     * 
     */
    public DataSet createDataSet() {
        return new DataSet();
    }

    /**
     * Create an instance of {@link GetSupportCityDatasetResponse.GetSupportCityDatasetResult }
     * 
     */
    public GetSupportCityDatasetResponse.GetSupportCityDatasetResult createGetSupportCityDatasetResponseGetSupportCityDatasetResult() {
        return new GetSupportCityDatasetResponse.GetSupportCityDatasetResult();
    }

    /**
     * Create an instance of {@link GetWeather }
     * 
     */
    public GetWeather createGetWeather() {
        return new GetWeather();
    }

    /**
     * Create an instance of {@link GetRegionProvince }
     * 
     */
    public GetRegionProvince createGetRegionProvince() {
        return new GetRegionProvince();
    }

    /**
     * Create an instance of {@link GetRegionCountryResponse }
     * 
     */
    public GetRegionCountryResponse createGetRegionCountryResponse() {
        return new GetRegionCountryResponse();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link GetSupportCityString }
     * 
     */
    public GetSupportCityString createGetSupportCityString() {
        return new GetSupportCityString();
    }

    /**
     * Create an instance of {@link GetSupportCityStringResponse }
     * 
     */
    public GetSupportCityStringResponse createGetSupportCityStringResponse() {
        return new GetSupportCityStringResponse();
    }

    /**
     * Create an instance of {@link GetRegionDatasetResponse.GetRegionDatasetResult }
     * 
     */
    public GetRegionDatasetResponse.GetRegionDatasetResult createGetRegionDatasetResponseGetRegionDatasetResult() {
        return new GetRegionDatasetResponse.GetRegionDatasetResult();
    }

    /**
     * Create an instance of {@link GetWeatherResponse }
     * 
     */
    public GetWeatherResponse createGetWeatherResponse() {
        return new GetWeatherResponse();
    }

    /**
     * Create an instance of {@link GetRegionDataset }
     * 
     */
    public GetRegionDataset createGetRegionDataset() {
        return new GetRegionDataset();
    }

    /**
     * Create an instance of {@link GetSupportCityDataset }
     * 
     */
    public GetSupportCityDataset createGetSupportCityDataset() {
        return new GetSupportCityDataset();
    }

    /**
     * Create an instance of {@link GetRegionProvinceResponse }
     * 
     */
    public GetRegionProvinceResponse createGetRegionProvinceResponse() {
        return new GetRegionProvinceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebXml.com.cn/", name = "DataSet")
    public JAXBElement<DataSet> createDataSet(DataSet value) {
        return new JAXBElement<DataSet>(_DataSet_QNAME, DataSet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebXml.com.cn/", name = "ArrayOfString")
    public JAXBElement<ArrayOfString> createArrayOfString(ArrayOfString value) {
        return new JAXBElement<ArrayOfString>(_ArrayOfString_QNAME, ArrayOfString.class, null, value);
    }

}
