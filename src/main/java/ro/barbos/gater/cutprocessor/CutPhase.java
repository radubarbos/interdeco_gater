package ro.barbos.gater.cutprocessor;

public enum CutPhase {
   FIRST(1), SECOND(2), THIRD(3), FOURTH(4);
   
   private int code;
   
   CutPhase(int code) {
	   this.code = code;
   }
   
   public int getCode() {
	  return code; 
   }
}
