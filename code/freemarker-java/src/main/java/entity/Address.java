package entity;

/**
 * @author caojx
 * Created on 2018/3/9 下午下午6:19
 */
public class Address {

    /**
     * 国家
     */
    private String country;

    /**
     * 城市
     */
    private String city;

    public Address() {
    }

    public Address(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
