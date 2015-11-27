package ro.barbos.gater.cutprocessor.strategy;

public enum CutStrategyType {
  SIMPLE_VERTICAL, BEST_MATCH_VERTICAL, ROTATE_ONE, BEST_MULTIBLADE_MATCH, NO_MULTI_BLADE;

  private boolean enabled = false;

  public void setEnabled(boolean enabled) {
      this.enabled = enabled;
  }

    public boolean isEnabled() {
        return enabled;
    }

}
