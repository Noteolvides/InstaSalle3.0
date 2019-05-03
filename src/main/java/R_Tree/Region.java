package R_Tree;

import java.lang.reflect.Array;

public class Region <T extends Comparable> {
	Region subRegions [];
	Boolean isLeaf;
	T pointsLeaf[];

	public Region(T data) {
		isLeaf = false;
		subRegions = new Region[2];
	}

	public Region(T data,int dimension,Class<T> c){
		this.isLeaf = true;
		@SuppressWarnings("unchecked")
		final T[] a = (T[]) Array.newInstance(c, dimension);
	}

	public Region(T data,int dimension){
		this.isLeaf = false;
		subRegions = new Region[dimension];
	}
}
