package R_Tree;

import Data.Post;

public class Region {
	Region subRegions[];
	boolean isSubRegion;
	Region superRegion;
	Region fatherNode;
	Boolean isLeaf;
	Post pointsLeaf[];

	Point max;
	Point min;
	int area;
	boolean isfull;

	public Region() {
		isLeaf = false;
		subRegions = new Region[2];
		isfull = false;
	}

	public Region(Post post){
		this.isLeaf = true;
		pointsLeaf = new Post[2];
		pointsLeaf[0] = post;
	}

	public Region(int dimension){
		this.isLeaf = false;
		subRegions = new Region[dimension];
	}
	
	public void add(Object o) {
		if (pointsLeaf[0] == null) {
			pointsLeaf[0] = (Post) o;
		} else {
			pointsLeaf[1] = (Post) o;
		}

	}

	//Todo implement newArea
	public int newArea(Object overflowR) {
		return(1);
	}
}
