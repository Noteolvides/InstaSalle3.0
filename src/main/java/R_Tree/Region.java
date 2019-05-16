package R_Tree;

import Data.Post;

import static R_Tree.Tree.BTREEDIMENSION;

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
	int childPos;

	public Region() {
		isLeaf = false;
		subRegions = new Region[2];
		isfull = false;
		childPos = 0;
	}

	public Region(Post post){
		this.isLeaf = true;
		pointsLeaf = new Post[2];
		childPos = 0;
		pointsLeaf[childPos] = post;
		childPos++;
		this.min = new Point(post.location[0],post.location[1]);
		this.max = new Point(post.location[0],post.location[1]);
	}

	public Region(int dimension){
		this.isLeaf = false;
		subRegions = new Region[dimension];
	}
	
	public void add(Post post) {
		if (isLeaf) {
			pointsLeaf[childPos] = post;
			if (childPos == BTREEDIMENSION) {
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
			add(newSubRegion);
		}
	}

	public void add(Region region) {
		subRegions[childPos] = region;
		childPos++;
		if (childPos == BTREEDIMENSION) {
			isfull = true;
		}
	}

	//Todo implement newArea
	public int newArea(Object overflowR) {
		return(1);
	}
}
