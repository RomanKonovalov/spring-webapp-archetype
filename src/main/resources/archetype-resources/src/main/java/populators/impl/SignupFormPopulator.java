#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.populators.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ${package}.dto.AddressForm;
import ${package}.dto.SignupForm;
import ${package}.model.Account;
import ${package}.model.Address;
import ${package}.populators.Converter;
import ${package}.populators.Populator;

@Component
public class SignupFormPopulator implements Populator<SignupForm, Account> {

    @Autowired
    @Qualifier("addressConverter")
    private Converter<AddressForm, Address> addressConverter;

    @Override
    public void populate(final SignupForm source, final Account target) {
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setWebsite(source.getWebsite());
        final Address address = new Address();
        addressConverter.convert(source.getAddress(), address);
        target.setAddress(address);

        target.setEnabled(true);
    }

}
