#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.populators.impl;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ${package}.dto.FileForm;
import ${package}.model.File;
import ${package}.populators.Populator;
import ${package}.services.AccountService;

@Component
public class FileFormPopulator implements Populator<File, FileForm> {

    @Value("${symbol_dollar}{fileupload.storage}")
    private java.io.File storage;

    @Autowired
    private AccountService accountService;

    @Override
    public void populate(final File source, final FileForm target) {

        final String location = storage.getAbsolutePath() + java.io.File.separator
                + accountService.getCurrentAccount().getEmail() + java.io.File.separator + source.getName();

        target.setFriendlyName(source.getFriendlyName());
        target.setName(source.getName());
        target.setLocation(location);
        target.setSize(FileUtils.byteCountToDisplaySize(source.getSize()));
        target.setContentType(source.getContentType());
    }
}
