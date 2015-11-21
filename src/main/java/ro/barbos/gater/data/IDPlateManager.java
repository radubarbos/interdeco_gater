package ro.barbos.gater.data;

import java.util.ArrayList;
import java.util.List;

import ro.barbos.gater.model.IDPlate;

public class IDPlateManager {
	
	private static List<IDPlate> available = new ArrayList<IDPlate>();
	
	public static IDPlate getAvailableIDPlate() {
		if(available.size() > 0) {
			return available.get(0);
		}
		return null;
	}
	
	public static void setAvailableIDPlate(List<IDPlate> plates) {
		IDPlateManager.available = plates;
	}
	
	public static List<IDPlate> getAvailableIDPlates() {
		return available;
	}
	
	public static void removeAvailablePlate(IDPlate idPlate) {
		available.remove(idPlate);
	}
	
	public static void addAvailablePlate(IDPlate idPlate) {
		int index = -1;
		for(int i =0; i< available.size(); i++) {
			if(available.get(i).getId() > idPlate.getId()) {
				index = i;
				break;
			}
		}
		if(index == -1) {
			available.add(idPlate);
		}
		else {
			available.add(index, idPlate);
		}
	}

}
