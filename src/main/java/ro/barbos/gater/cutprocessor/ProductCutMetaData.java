package ro.barbos.gater.cutprocessor;

public class ProductCutMetaData {

	public double percentage;
	public long width = 0L;
	public long thick = 0L;
	public long length = 0L;
	public CutPriority priority;
	public long piecesCut = 0L;
	public long piecesTarget = 0L;
	public long volume = 0L;
	
	public double temporaryCutVolume = 0D;
	
	public boolean isCompleted() {
		return piecesCut >= piecesTarget;
	}
}
