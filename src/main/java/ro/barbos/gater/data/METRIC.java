package ro.barbos.gater.data;

public enum METRIC {
  MILIMETER, CENTIMETER, DECIMETER, METER;
  
  public static METRIC getById(int id) {
	  for(METRIC metric: METRIC.values()) {
		  if(metric.ordinal() == (id-1)) {
			  return metric;
		  }
	  }
	  return null;
  }

    public static long toMilimeter(long value, int metricIndex) {
        switch(metricIndex) {
            case 0: return value;
            case 1: return value * 10;
            case 2: return value * 100;
            case 3: return value * 1000;
        }
        return value;
    }
}
