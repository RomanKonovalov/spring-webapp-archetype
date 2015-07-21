#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ${package}.dto.FileForm;
import ${package}.dto.FileUploadForm;
import ${package}.model.File;
import ${package}.populators.Converter;
import ${package}.services.AccountService;
import ${package}.services.FileService;
import ${package}.services.message.MessageService;

@Controller
@RequestMapping("/file")
public class FilesController {

    private static final Logger LOG = LoggerFactory.getLogger(FilesController.class);

    private static final String MY_FILES_VIEW_NAME = "file/myFiles";

    private static final String UPLOAD_VIEW_NAME = "file/upload";

    private static final int PAGE_SIZE = 25;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FileService fileService;

    @Autowired
    @Qualifier(value = "fileFormConverter")
    private Converter<File, FileForm> fileFormConverter;

    @Autowired
    private MessageService messageService;

    @ModelAttribute("maxFileSize")
    public String getMaxFileSize(@Value(value = "${symbol_pound}{${symbol_dollar}{fileupload.max.size}}") final long maxFileSize) {
        return FileUtils.byteCountToDisplaySize(maxFileSize);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public FileUploadForm showForm() {
        return new FileUploadForm();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@Valid @ModelAttribute final FileUploadForm fileUploadForm, final BindingResult errors,
            final RedirectAttributes ra, final HttpServletRequest request) {

        if (fileUploadForm.getFile().getSize() == 0) {
            errors.rejectValue("file", "uploadForm.file.required.error", "File required");
        }

        if ((errors.hasErrors())) {
            return UPLOAD_VIEW_NAME;
        }

        try {
            fileService.saveFile(fileUploadForm.getFile(), fileUploadForm.getName());
        } catch (final FileExistsException e) {
            errors.rejectValue("file", "uploadForm.file.exist.error", "File exist");
            return UPLOAD_VIEW_NAME;
        }

        messageService.addSuccessAttribute(ra, "{uploadForm.success}", fileUploadForm.getFile().getOriginalFilename());

        return "redirect:/" + MY_FILES_VIEW_NAME;
    }

    @RequestMapping(value = "/myFiles", method = RequestMethod.GET)
    public String getMyFiles(final Model model, final HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "") final String searchCriteria) {

        final Pageable pageable = new PageRequest(getPage(request), PAGE_SIZE, getSort(request));

        final List<FileForm> fileForms = new ArrayList<>();

        for (final File file : fileService.findFilesByAccount(accountService.getCurrentAccount(), pageable)) {
            fileForms.add(fileFormConverter.convert(file, new FileForm()));
        }

        model.addAttribute("files", fileForms);
        model.addAttribute("filesCount", fileService.countFiles(accountService.getCurrentAccount()));

        return MY_FILES_VIEW_NAME;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteFile(@RequestParam(required = true) final String name, final RedirectAttributes ra)
            throws UnsupportedEncodingException {

        fileService.deleteFile(name);

        messageService.addSuccessAttribute(ra, "{myfiles.delete.success}", name);

        return "redirect:/" + MY_FILES_VIEW_NAME;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public HttpEntity<FileSystemResource> getFile(@RequestParam(required = true) final String name,
            final HttpServletResponse response) throws UnsupportedEncodingException {

        final File file = fileService.findFileByName(name);

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_TYPE, file.getContentType());
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

        return new HttpEntity<FileSystemResource>(new FileSystemResource(fileService.getFileLocation(file)),
                responseHeaders);
    }

    private int getPage(final HttpServletRequest request) {
        final String pageNumber = request.getParameter(new ParamEncoder("files")
                .encodeParameterName(TableTagParameters.PARAMETER_PAGE));

        return pageNumber != null ? Integer.parseInt(pageNumber) - 1 : 0;
    }

    private Sort getSort(final HttpServletRequest request) {
        final String property = request.getParameter(new ParamEncoder("files")
                .encodeParameterName(TableTagParameters.PARAMETER_SORT));

        final String order = request.getParameter(new ParamEncoder("files")
                .encodeParameterName(TableTagParameters.PARAMETER_ORDER));

        final Direction direction = order == "2" ? Direction.DESC : Direction.ASC;

        return StringUtils.isNotEmpty(property) ? new Sort(direction, property.split(" ")) : null;
    }

}
