#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import org.apache.commons.io.FileExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ${package}.model.Account;
import ${package}.model.File;

public interface FileService {

    void saveFile(CommonsMultipartFile commonsMultipartFile, String friendlyName) throws FileExistsException;

    Page<File> findFilesByAccount(Account account, Pageable pageable);

    int countFiles(Account account);

    void deleteFile(String name);

    File findFileByName(String name);

    String getFileLocation(File file);

}
