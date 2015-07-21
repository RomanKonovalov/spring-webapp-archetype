#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.populators.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ${package}.dto.AccountForm;
import ${package}.dto.AddressForm;
import ${package}.model.Account;
import ${package}.model.Address;
import ${package}.populators.Converter;
import ${package}.populators.Populator;

@Component
public class AccountFormReversePopulator implements Populator<Account, AccountForm> {

    @Autowired
    @Qualifier("addressReverseConverter")
    private Converter<Address, AddressForm> addressReverseConverter;

    @Override
    public void populate(final Account source, final AccountForm target) {
        target.setEmail(source.getEmail());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setWebsite(source.getWebsite());
        final AddressForm address = new AddressForm();

        if (source.getAddress() != null) {
            addressReverseConverter.convert(source.getAddress(), address);
        }
        target.setAddress(address);

        target.setEnabled(source.isEnabled());
        target.setRoles(source.getRoles());
        target.setAccountExpired(!source.isAccountNonExpired());
        target.setAccountLocked(!source.isAccountNonLocked());
        target.setCredentialsExpired(!source.isCredentialsNonExpired());
    }

    /**
     * @return the addressReverseConverter
     */
    public Converter<Address, AddressForm> getAddressReverseConverter() {
        return addressReverseConverter;
    }

    /**
     * @param addressReverseConverter
     *            the addressReverseConverter to set
     */
    public void setAddressReverseConverter(final Converter<Address, AddressForm> addressReverseConverter) {
        this.addressReverseConverter = addressReverseConverter;
    }

}
