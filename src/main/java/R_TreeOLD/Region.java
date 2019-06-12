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

		if(subRegions == null){
			subRegions = new Region[BTREEDIMENSION];
		}
		subRegions[childRegionPos] = region;
		subRegions[childRegionPos].superRegion = this;
		childRegionPos++;
		if (childRegionPos == BTREEDIMENSION) {
			isRegionFull = true;
		}
	}

	//Todo implement newArea
	public double newArea(Region overflowR) {
		double distanciaO1 = calcularDistanciaDesde(this.min);
		double distanciaO2 = calcularDistanciaDesde(overflowR.min);
		Point min;
		Point max;
		if (distanciaO1 < distanciaO2){
			min = this.min;
		}else{
			min = overflowR.min;
		}
		distanciaO1 = calcularDistanciaDesde(this.max);
		distanciaO2 = calcularDistanciaDesde(overflowR.max);

		if (distanciaO1 < distanciaO2){
			max = this.max;
		}else{
			max = overflowR.max;
		}


		return (max.x - min.x)*(max.y - min.y);
	}

	public Object clone(){
		try {
			return(super.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return(null);
		}
	}

	public double calcularDistanciaDesde(Point parametros) {
		double cateto1 = parametros.x;
		double cateto2 = parametros.y;
		double hipotenusa = Math.sqrt(cateto1 * cateto1 + cateto2 * cateto2);
		return hipotenusa;
	}
}

