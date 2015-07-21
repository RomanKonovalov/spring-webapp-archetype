#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUploadForm {

    private String name;

    private CommonsMultipartFile file;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the file
     */
    public CommonsMultipartFile getFile() {
        return file;
    }

    /**
     * @param file
     *            the file to set
     */
    public void setFile(final CommonsMultipartFile file) {
        this.file = file;
    }

}
