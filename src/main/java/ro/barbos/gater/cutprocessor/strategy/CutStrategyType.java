package ro.barbos.gater.cutprocessor.strategy;

import java.util.HashMap;
import java.util.Map;

public enum CutStrategyType {
  SIMPLE_VERTICAL, BEST_MATCH_VERTICAL, ROTATE_ONE, BEST_MULTIBLADE_MATCH, NO_MULTI_BLADE, ROTATE_ONE_180;

  private boolean enabled = false;

  public void setEnabled(boolean enabled) {
      this.enabled = enabled;
  }

    public boolean isEnabled() {
        return enabled;
    }

    public static Map<String, Boolean> getActiveStrategies() {
        Map<String, Boolean> cutStrategies = new HashMap<>();
        for(CutStrategyType strategy: CutStrategyType.values()) {
            if(strategy.isEnabled()) {
                cutStrategies.put(strategy.name(), true);
            }
        }
        return cutStrategies;
    }

}
