#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.impl;

import java.io.File;
import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ${package}.model.Account;
import ${package}.repository.FileRepository;
import ${package}.services.AccountService;
import ${package}.services.FileService;

@Service
@PreAuthorize("@securityService.hasPermissionForUtility('FileUtility')")
public class DefaultFileService implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultFileService.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private FileRepository fileRepository;

    @Value("${symbol_dollar}{fileupload.storage}")
    private File storage;

    @Override
    public void saveFile(final CommonsMultipartFile file, final String friendlyName) throws FileExistsException {

        LOG.debug("Saving file: {}, with 'friendlyName': {} for acoount: {}", file.getOriginalFilename(), friendlyName,
                accountService.getCurrentAccount().getEmail());

        final File destinationFolder = new File(storage, accountService.getCurrentAccount().getEmail());
        final File destinationFile = new File(destinationFolder, file.getOriginalFilename());

        if (destinationFile.exists()) {
            throw new FileExistsException(destinationFile);
        }

        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), destinationFile);
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }

        final ${package}.model.File dbFile = new ${package}.model.File();
        dbFile.setAccount(accountService.getCurrentAccount());
        dbFile.setName(file.getOriginalFilename());
        dbFile.setFriendlyName(StringUtils.isNotBlank(friendlyName) ? friendlyName : FilenameUtils.getBaseName(file
                .getOriginalFilename()));
        dbFile.setContentType(file.getContentType());
        dbFile.setSize(file.getSize());

        fileRepository.save(dbFile);
    }

    @Override
    public Page<${package}.model.File> findFilesByAccount(final Account account, final Pageable pageable) {
        return fileRepository.findByAccount(account, pageable);
    }

    @Override
    public int countFiles(final Account account) {
        return fileRepository.countByAccount(account);
    }

    @Override
    public void deleteFile(final String name) {
        LOG.debug("Deleting file with name: {} for acoount: {}", name, accountService.getCurrentAccount().getEmail());

        final ${package}.model.File dbFile = findFileByName(name);

        final File file = new File(getFileLocation(dbFile));

        try {
            FileUtils.forceDelete(file);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        fileRepository.delete(dbFile);
    }

    @Override
    public ${package}.model.File findFileByName(final String name) {
        return fileRepository.findByNameAndAccount(name, accountService.getCurrentAccount()).orElseThrow(
                () -> new EntityNotFoundException("Can't find file by name: '" + name + "'"));
    }

    @Override
    public String getFileLocation(final ${package}.model.File dbFile) {
        return storage.getAbsolutePath() + File.separator + accountService.getCurrentAccount().getEmail()
                + File.separator + dbFile.getName();
    }

}
