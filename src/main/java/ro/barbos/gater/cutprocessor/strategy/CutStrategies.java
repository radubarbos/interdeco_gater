package ro.barbos.gater.cutprocessor.strategy;

import java.util.HashMap;
import java.util.Map;

import static ro.barbos.gater.cutprocessor.strategy.CutStrategyType.*;

/**
 * Created by radu on 11/26/2015.
 */
public class CutStrategies {

    public static Map<String, Boolean> getActiveStrategies() {
        Map<String, Boolean> cutStrategies = new HashMap<String, Boolean>();
        if(SIMPLE_VERTICAL.isEnabled()) {
            cutStrategies.put(SIMPLE_VERTICAL.name(), true);
        }
        if(BEST_MATCH_VERTICAL.isEnabled()) {
            cutStrategies.put(BEST_MATCH_VERTICAL.name(), true);
        }
        if(ROTATE_ONE.isEnabled()) {
            cutStrategies.put(ROTATE_ONE.name(), true);
        }
        if(BEST_MULTIBLADE_MATCH.isEnabled()) {
            cutStrategies.put(BEST_MULTIBLADE_MATCH.name(), true);
        }
        if(NO_MULTI_BLADE.isEnabled()) {
            cutStrategies.put(NO_MULTI_BLADE.name(), true);
        }
        return cutStrategies;
    }
}
