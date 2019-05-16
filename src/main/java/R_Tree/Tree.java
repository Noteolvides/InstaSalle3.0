package R_Tree;

import Data.Post;

public class Tree {
	final static int BTREEDIMENSION = 2;
	Region root;


	public Tree() {
	}


	//Todo Insercion
	public Region insertion(Object o, Region actual){
		Region aux;

		if (actual == null) {
			actual = new Region((Post) o);
		} else {
			//Búsqueda dle mejor nodo región en el que colocar el punto
			//Todo implementar la búsqueda del mejor nodo
			aux = bestNodeSearch(root, o);

			//comprovación de si está o no llena la mejor región
			if (!aux.isfull) {
				//si no lo está, añadimos el objeto
				//Todo implementar add
				aux.add((Post) o);
			} else {
				//En caso que no lo esté, hacemos split
				//Todo Implementar regionSplit
				regionSplit(aux, this, (Post) o, new Region(), false);
			}
		}
		return actual;
	}

	private Region bestNodeSearch(Region actual, Object o) {
		Region regions[] = actual.subRegions;
		Region best = actual;
		Double diff = Double.MAX_VALUE;
		Post newNode = (Post) o;
		if (!actual.isLeaf) {
			for (Region region : regions) {
				if (region.min.x <= newNode.location[0] && region.min.y <= newNode.location[1]
						&& region.max.x >= newNode.location[0] && region.max.y >= newNode.location[1]) {
					best = region;
					diff = 0D;
				} else {
					if (diff != 0D) {
						Point auxmax = new Point(region.max.x, region.max.y);
						Point auxmin = new Point(region.min.x, region.min.y);
						if (newNode.location[0] < auxmin.x) {
							auxmin.x = newNode.location[0];
						}
						if (newNode.location[1] < auxmin.y) {
							auxmin.y = newNode.location[1];
						}
						if (newNode.location[0] > auxmax.x) {
							auxmax.x = newNode.location[0];
						}
						if (newNode.location[1] > auxmax.y) {
							auxmax.y = newNode.location[1];
						}
						Double newdiff = (auxmax.x * auxmax.y) - (auxmin.x * auxmin.y);
						if (newdiff < diff) {
							diff = newdiff;
							best = region;
						}
					}
				}
			}
			if (best.subRegions != null) {
				best = bestNodeSearch(best, o);
			}
		}
		return best;
	}

	private void regionSplit(Region split, Tree t, Post overflowP, Region overflowR, boolean regionsplit){
		Region region1 = new Region();
		Region region2 = new Region();
		double[] minpoint = new double[]{split.min.x, split.min.y};
		double[] maxpoint = new double[]{split.max.x, split.max.y};

		if(regionsplit){
			//Región más pequeña
			Region min = new Region();
			min.min = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
			//Región más grande
			Region max = new Region();
			max.max = new Point(Double.MIN_VALUE, Double.MIN_VALUE);

			//Se busca la región más cercana y lejana desde el punto de vista del 0,0
			//Para encontrar las regiones más alejadas entre ellas
			for(int	i = 0; i < split.subRegions.length; i++){
				//Búsqueda de la región más cercana
				if(split.subRegions[i].min.x < min.min.x){
					if(split.subRegions[i].min.y < min.min.y){
						min = split.subRegions[i];
					}
				}

				//Búsqueda de la región más lejana
				if(split.subRegions[i].max.x > max.max.x){
					if(split.subRegions[i].max.y > max.max.y){
						max = split.subRegions[i];
					}
				}
			}
			region1.add(min);
			region2.add(max);
			if(!split.superRegion.isfull){
				//bucle para encontrar dónde poner cada región
				for(int i = 0; i < split.superRegion.subRegions.length; i++){
					if((split.superRegion.subRegions[i] != max)||(split.superRegion.subRegions[i] != min)){
						if(region1.newArea(split.superRegion.subRegions[i]) < region2.newArea(split.superRegion.subRegions[i])){
							region1.add(split.superRegion.subRegions[i]);
						}else{
							region2.add(split.superRegion.subRegions[i]);
						}
					}
				}

				//Insertamos la región de overflow
				if(region1.newArea(overflowR) < region2.newArea(overflowR)){
					region1.add(overflowR);
				}else{
					region2.add(overflowR);
				}

			}else{
				regionSplit(split.fatherNode.superRegion, this, overflowP, overflowR, true);
			}
		}else{
			//Búsqueda de los dos posts más distanciados, es decir, aquellos que forman la MBR
			//max y min dentro de la región.
			for (int i = 0; i < split.pointsLeaf.length; i++){
				//Comprovación para el punto mínimo
				if(split.pointsLeaf[i].location[0].equals(minpoint[0])){
					if (split.pointsLeaf[i].location[1].equals(minpoint[1])){
						Post postadd = split.pointsLeaf[i];
						region1.add(postadd);
					}
				}

				//Comprovación para el punto máximo
				if(split.pointsLeaf[i].location[0].equals(maxpoint[0])){
					if (split.pointsLeaf[i].location[1].equals(maxpoint[1])){
						Post postadd = split.pointsLeaf[i];
						region2.add(postadd);
					}
				}
			}

			//En caso que haya sitio para las nuevas regiones, se ponen
			if(!split.superRegion.isfull){
				//Búsqueda e inserción de ambas regiones
				for(int i = 0; i < split.superRegion.subRegions.length; i++){
					if(split.superRegion.subRegions[i] == split){
						split.superRegion.subRegions[i] = region1;
					}
					if(split.superRegion.subRegions[i] == new Region()){
						split.superRegion.subRegions[i] = region2;
					}
				}

				//Redistribución de los puntos en las nuevas regiones
				for(int i = 0; i < split.pointsLeaf.length; i++){
					if((!split.pointsLeaf[i].equals(region1.pointsLeaf[0]))||((!split.pointsLeaf[i].equals(region2.pointsLeaf[0])))){
						this.insertion(split.pointsLeaf[i], split.superRegion);
					}
				}
			}else{
				regionSplit(split.superRegion, this, overflowP, overflowR, true);
			}

			//Redistribución de los puntos en las nuevas regiones
			for(int i = 0; i < split.pointsLeaf.length; i++){
				if((!split.pointsLeaf[i].equals(region1.pointsLeaf[0]))||((!split.pointsLeaf[i].equals(region2.pointsLeaf[0])))){
					this.insertion(split.pointsLeaf[i], split.superRegion);
				}
			}
		}
	}

	//Todo Busqueda

	//Todo Eliminacion
}
