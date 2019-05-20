package R_Tree;

import Data.Post;

import static R_Tree.Tree.BTREEDIMENSION;

public class Region implements Cloneable {
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
	boolean isRegionFull;
	int childPos;
	int childRegionPos;

	public Region(double height,double witdh){
		max = new Point(height,witdh);
		min = new Point(0d,0d);
		subRegions = new Region[BTREEDIMENSION];
		childPos = 0;
		childRegionPos = 0;
		isfull = false;
		isRegionFull = false;
		superRegion = null;
	}

	public Region() {
		isLeaf = false;
		isfull = false;
		childPos = 0;
		childRegionPos = 0;
		superRegion = this;
	}

	public Region(Post post){
		this.isLeaf = true;
		pointsLeaf = new Post[BTREEDIMENSION];
		childPos = 0;
		childRegionPos = 0;
		pointsLeaf[childPos] = post;
		childPos++;
		this.min = new Point(post.location[0],post.location[1]);
		this.max = new Point(post.location[0],post.location[1]);
		superRegion = this;
	}

	public Region(int dimension){
		this.isLeaf = false;
		subRegions = new Region[dimension];
		superRegion = this;
	}
	
	public void add(Post post) {
		if (isLeaf) {
			pointsLeaf[childPos] = post;
			childPos++;
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

		subRegions[childRegionPos] = region;
		subRegions[childRegionPos].superRegion = this;
		childRegionPos++;
		if (childRegionPos == BTREEDIMENSION) {
			isRegionFull = true;
		}
	}

	//Todo implement newArea
	public int newArea(Object overflowR) {
		return(1);
	}

	public Object clone(){
		try {
			return(super.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return(null);
		}
	}
}

