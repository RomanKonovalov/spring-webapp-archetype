#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.populators;

import org.springframework.core.convert.ConversionException;

public interface Populator<SOURCE, TARGET> {
    /**
     * Populate the target instance with values from the source instance.
     *
     * @param source
     *            the source object
     * @param target
     *            the target to fill
     * @throws ConversionException
     *             if an error occurs
     */
    void populate(SOURCE source, TARGET target);
}