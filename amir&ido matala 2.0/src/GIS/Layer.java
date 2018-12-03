package GIS;

import java.util.*;
import Geom.Point3D;


public class Layer implements GIS_layer {

	private HashSet<GIS_element> set;
	private Data data;
//	private long time_of_creation;
	
	/*
	 * construct a layer with a Data for timestamp and an HashSet<GIS_element>.
	 */
	public Layer(){
		this.set = new HashSet<GIS_element>();
		this.data = new Data();
	}
	
	
	@Override
	public boolean add(GIS_element e) {
		 if(e.getData() != null){
			 Element element = new Element(e.getData().toString().split(","));
			 set.add(element);
			 return true;
		 }
		else
			return false;
	}

	@Override
	public boolean addAll(Collection<? extends GIS_element> c) {
		return this.set.addAll(c);
	}

	@Override
	public void clear() {
		this.set.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.set.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.set.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return this.set.isEmpty();
	}

	@Override
	public Iterator<GIS_element> iterator() {
		return this.set.iterator();
	}

	@Override
	public boolean remove(Object o) {
		if(this.set.contains(o)){
			this.set.remove(o);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if(this.set.containsAll(c)){
			this.set.removeAll(c);
			return true;
		}
		else 
			return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.set.retainAll(c);
	}

	@Override
	public int size() {
		return this.set.size();
	}

	@Override
	public Object[] toArray() {
		return this.set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.set.toArray(a);
	}
	/*
	 * return UTC time of the layer.
	 */
	@Override
	public Meta_data get_Meta_data() {
		return data;
	}
	
	public String toString() {
		
		String header = "[MAC, SSID, AuthMode, FirstSeen, Channel, RSSI, Latitude, Longitude, Altitude, AccuracyMeters, Type],\n";
		String ans = "[" + header;
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			if(iterator.hasNext() != false)
				ans += element.toString() +",\n";
			else
				ans += element.toString() + "]\n";
		}
		return ans;
	}
}
