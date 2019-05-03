package R_Tree;

import java.lang.reflect.Array;

public class Region <T extends Comparable> {
	Region subRegions [];
	Boolean isLeaf;
	T pointsLeaf[];

	int max;
	int min;
	int area;

	public Region() {
		isLeaf = false;
		subRegions = new Region[2];
	}

	public Region(int dimension,Class<T> c){
		this.isLeaf = true;
		@SuppressWarnings("unchecked")
		final T[] a = (T[]) Array.newInstance(c, dimension);
	}

	public Region(int dimension){
		this.isLeaf = false;
		subRegions = new Region[dimension];
	}
}
