#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.populators;

import java.util.List;

public class Converter<SOURCE, TARGET> {

    private List<Populator> populators;

    public TARGET convert(final SOURCE source, final TARGET target) {
        for (final Populator populator : populators) {
            populator.populate(source, target);
        }
        return target;
    }

    /**
     * @return the populators
     */
    public List<Populator> getPopulators() {
        return populators;
    }

    /**
     * @param populators
     *            the populators to set
     */
    public void setPopulators(final List<Populator> populators) {
        this.populators = populators;
    }

}
