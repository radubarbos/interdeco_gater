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
}
