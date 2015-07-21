#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.populators.impl;

import org.springframework.stereotype.Component;

import ${package}.dto.AddressForm;
import ${package}.model.Address;
import ${package}.populators.Populator;

@Component
public class AddressPopulator implements Populator<AddressForm, Address> {

    @Override
    public void populate(final AddressForm source, final Address target) {
        target.setAddress(source.getAddress());
        target.setCity(source.getCity());
        target.setCountry(source.getCountry());
        target.setPostalCode(source.getPostalCode());
        target.setProvince(source.getProvince());
    }
}
