package GIS;

import Geom.Geom_element;
import Geom.Point3D;

public class Element implements GIS_element {

	private Data data;
	
	/*
	 * construct an element by constructing its Data.
	 */
	public Element(String[] res){
		this.data = new Data(res);
	}
	 
	@Override
	public Geom_element getGeom() {
		double x = Double.parseDouble(data.getValue("Longitude"));
		double y = Double.parseDouble(data.getValue("Latitude"));
		double z = Double.parseDouble(data.getValue("Altitude"));
		Point3D geom = new Point3D(x,y,z);
		return geom;
	}

	@Override
	public Meta_data getData() {
		return data;
	}

	@Override
	public void translate(Point3D vec) {
		data.setCoordinates(vec);
	}
		
	public String toString() {
		return "[" + this.data.toString() + "]";
	}
	
}
