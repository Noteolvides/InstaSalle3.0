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
		this.min = new Point(post.location[0],post.location[1]);
		this.max = new Point(post.location[0],post.location[1]);
	}

	public Region(int dimension){
		this.isLeaf = false;
		subRegions = new Region[dimension];
	}
	
	public void add(Post post) {
		if (isLeaf) {
			if (pointsLeaf[0] == null) {
				pointsLeaf[0] = post;
			} else {
				pointsLeaf[1] = post;
				this.isfull = true;
			}
			if (post.location[0] < min.x) {
				min.x = post.location[0];
			}
			if (post.location[1] < min.y) {
				min.y = post.location[1];
			}
			if (post.location[0] > max.x) {
				max.x = post.location[0];
			}
			if (post.location[1] > max.y) {
				max.y = post.location[1];
			}
		} else {
			Region newSubRegion = new Region(post);
			if (subRegions[0] == null) {
				subRegions[0] = newSubRegion;
			} else {
				subRegions[1] = newSubRegion;
			}
		}
	}

	//Todo implement newArea
	public int newArea(Object overflowR) {
		return(1);
	}
}
