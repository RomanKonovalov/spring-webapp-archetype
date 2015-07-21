#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controllers;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import ${package}.services.message.MessageService;

@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @Value(value = "${symbol_pound}{${symbol_dollar}{fileupload.max.size}}")
    private long maxFileSize;

    @Autowired
    private MessageService messageService;

    public static final String ACCESS_DENIED_ERROR_VIEW = "403";

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ModelAndView entityNotFoundExceptionHandler(final EntityNotFoundException e) {
        final Model model = new ExtendedModelMap();
        messageService.addErrorAttribute(model, e.getLocalizedMessage());

        final ModelAndView modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
        modelAndView.addAllObjects(model.asMap());

        LOG.debug(e.getLocalizedMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public String accessDeniedExceptionHandler(final AccessDeniedException e) {
        LOG.debug("AccessDeniedException", e);
        return ACCESS_DENIED_ERROR_VIEW;
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ModelAndView maxUploadSizeExceededExceptionHandler(final MaxUploadSizeExceededException e) {
        final Model model = new ExtendedModelMap();
        messageService.addErrorAttribute(model, "{uploadForm.file.size.error}",
                FileUtils.byteCountToDisplaySize(maxFileSize));

        final ModelAndView modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
        modelAndView.addAllObjects(model.asMap());

        LOG.debug(e.getLocalizedMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultExceptionHandler(final Exception e) {
        final Model model = new ExtendedModelMap();
        messageService.addErrorAttribute(model, "{errors.general}", e.getLocalizedMessage());

        final ModelAndView modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
        modelAndView.addAllObjects(model.asMap());

        LOG.error("Global error occurred", e);

        return modelAndView;
    }
}
