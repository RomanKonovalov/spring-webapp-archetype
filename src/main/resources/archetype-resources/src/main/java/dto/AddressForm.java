#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dto;

public class AddressForm {

    private String address;
    private String city;
    private String province;
    private String country;
    private String postalCode;

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province
     *            the province to set
     */
    public void setProvince(final String province) {
        this.province = province;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode
     *            the postalCode to set
     */
    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object${symbol_pound}toString()
     */
    @Override
    public String toString() {
        return "AddressForm [address=" + address + ", city=" + city + ", province=" + province + ", country=" + country
                + ", postalCode=" + postalCode + "]";
    }

}
