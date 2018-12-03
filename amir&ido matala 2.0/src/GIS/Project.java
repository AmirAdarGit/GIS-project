package GIS;

import java.util.*;


public class Project implements GIS_project{
	
	private HashSet<GIS_layer> set;
	private Data data;

	/*
	 * construct a Project with Data (for timestamp) and an HashSet<GIS_layer>.
	 */
	public Project () {
		this.data = new Data();
		this.set = new HashSet<GIS_layer>();
	}
	

	
	@Override
	public boolean add(GIS_layer arg0) {
		if(arg0.get_Meta_data() != null){
			Layer layer = new Layer();
		Iterator<GIS_element> iter = arg0.iterator();
		while(iter.hasNext()){
			Element element = (Element) iter.next();
			layer.add(element);
		}
		this.set.add(layer);
		return true;
	}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends GIS_layer> arg0) {
		return this.set.addAll(arg0);
	}

	@Override
	public void clear() {
		this.set.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return this.set.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return this.set.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return this.set.isEmpty();
	}

	@Override
	public Iterator<GIS_layer> iterator() {
		return this.set.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		if(this.set.contains(arg0)) {
			this.set.remove(arg0);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		if(this.set.containsAll(arg0)){
			this.set.removeAll(arg0);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return this.set.retainAll(arg0);
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
	public <T> T[] toArray(T[] arg0) {
		return this.set.toArray(arg0);
	}

	@Override
	public Meta_data get_Meta_data() {
		return data;
	}
	
	public String toString() {
		int counter = 1;
		String layer_count = "<layer" + counter +">\n";
		String ans = "[" + layer_count;
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Layer layer = (Layer) iterator.next();
			if(iterator.hasNext() != false){
				counter++;
				layer_count = "<layer" + counter +">\n";
				ans += layer.toString() + ",\n" + layer_count;
			}
			else
				ans += layer.toString() + "]\n"; //
		}
		return ans;
	}

}
