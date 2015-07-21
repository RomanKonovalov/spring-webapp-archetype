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
import ${package}.services.AccountService;

@Component
public class AccountFormPopulator implements Populator<AccountForm, Account> {

    @Autowired
    @Qualifier("addressConverter")
    private Converter<AddressForm, Address> addressConverter;

    @Autowired
    private AccountService accountService;

    @Override
    public void populate(final AccountForm source, final Account target) {
        target.setEmail(source.getEmail());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setWebsite(source.getWebsite());

        final Address persistedAddress = accountService.findAccountByEmail(source.getEmail()).getAddress();
        Address address;
        if (persistedAddress != null) {
            address = addressConverter.convert(source.getAddress(), persistedAddress);
        } else {
            address = addressConverter.convert(source.getAddress(), new Address());
        }
        target.setAddress(address);

        if (accountService.isAdmin()) {
            target.setRoles(source.getRoles());
            target.setAccountNonExpired(!source.isAccountExpired());
            target.setAccountNonLocked(!source.isAccountLocked());
            target.setCredentialsNonExpired(!source.isCredentialsExpired());
            target.setEnabled(source.isEnabled());
        }

    }

}
