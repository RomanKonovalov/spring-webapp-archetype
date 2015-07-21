#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class MessageService {

    @Autowired
    private MessageSource messageSource;

    public void addSuccessAttribute(final RedirectAttributes ra, final String message, final Object... args) {
        addAttribute(ra, message, Message.Type.SUCCESS, args);
    }

    public void addErrorAttribute(final RedirectAttributes ra, final String message, final Object... args) {
        addAttribute(ra, message, Message.Type.DANGER, args);
    }

    public void addInfoAttribute(final RedirectAttributes ra, final String message, final Object... args) {
        addAttribute(ra, message, Message.Type.INFO, args);
    }

    public void addWarningAttribute(final RedirectAttributes ra, final String message, final Object... args) {
        addAttribute(ra, message, Message.Type.WARNING, args);
    }

    private void addAttribute(final RedirectAttributes ra, String message, final Message.Type type,
            final Object... args) {
        message = proceedMessage(message, args);
        ra.addFlashAttribute(Message.MESSAGE_ATTRIBUTE, new Message(message, type, args));
    }

    public void addSuccessAttribute(final Model model, final String message, final Object... args) {
        addAttribute(model, message, Message.Type.SUCCESS, args);
    }

    public void addErrorAttribute(final Model model, final String message, final Object... args) {
        addAttribute(model, message, Message.Type.DANGER, args);
    }

    public void addInfoAttribute(final Model model, final String message, final Object... args) {
        addAttribute(model, message, Message.Type.INFO, args);
    }

    public void addWarningAttribute(final Model model, final String message, final Object... args) {
        addAttribute(model, message, Message.Type.WARNING, args);
    }

    private void addAttribute(final Model model, String message, final Message.Type type, final Object... args) {
        message = proceedMessage(message, args);
        model.addAttribute(Message.MESSAGE_ATTRIBUTE, new Message(message, type, args));
    }

    public String convertMessage(final String message, final Object... args) {
        return messageSource.getMessage(message, args, message, LocaleContextHolder.getLocale());
    }

    private String proceedMessage(String message, final Object... args) {
        if (message.startsWith("{") && message.endsWith("}")) {
            message = convertMessage(message.substring(1, message.length() - 1), args);
        }

        return message;
    }

    /**
     * A message to be displayed in web context. Depending on the type,
     * different style will be applied.
     */
    public static class Message implements java.io.Serializable {
        /**
         * Name of the flash attribute.
         */
        public static final String MESSAGE_ATTRIBUTE = "message";

        /**
         * The type of the message to be displayed. The type is used to show
         * message in a different style.
         */
        public static enum Type {
            DANGER, WARNING, INFO, SUCCESS;
        }

        private final String message;
        private final Type type;
        private final Object[] args;

        public Message(final String message, final Type type) {
            this.message = message;
            this.type = type;
            this.args = null;
        }

        public Message(final String message, final Type type, final Object... args) {
            this.message = message;
            this.type = type;
            this.args = args;
        }

        public String getMessage() {
            return message;
        }

        public String getType() {
            return type.toString().toLowerCase();
        }

        public Object[] getArgs() {
            return args;
        }
    }
}
