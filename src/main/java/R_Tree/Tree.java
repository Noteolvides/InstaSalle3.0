package R_Tree;

import Data.Post;

public class Tree {
	final static int BTREEDIMENSION = 2;
	Region root;


	public Tree() {
		this.root = new Region(BTREEDIMENSION);
	}


	//Todo Insercion
	public void insertion(Object o, Region actual){
		Region aux;

		//Búsqueda dle mejor nodo región en el que colocar el punto
		//Todo implementar la búsqueda del mejor nodo
		aux = bestNodeSearch(this, actual, root);

		//comprovación de si está o no llena la mejor región
		//Todo implementar isFull
		if(!aux.isfull){
			//si no lo está, añadimos el objeto
			//Todo implementar add
			aux.add(o);
		}else{
			//En caso que no lo esté, hacemos split
			//Todo Implementar regionSplit
			regionSplit(aux, this, (Post) o, new Region(), false);
		}
	}

	private Region bestNodeSearch(Tree tree, Region actual, Region root) {
		return(new Region());
	}

	private void regionSplit(Region split, Tree t, Post overflowP, Region overflowR, boolean regionsplit){
		Region region1 = new Region();
		Region region2 = new Region();
		long[] minpoint = new long[]{split.min.x, split.min.y};
		long[] maxpoint = new long[]{split.max.x, split.max.y};

		if(regionsplit){
			//Región más pequeña
			Region min = new Region();
			min.min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
			//Región más grande
			Region max = new Region();
			max.max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

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
			//TODO comprovar también max y min para OverflowR

			//Fin de este TODO
			region1.add(min);
			region2.add(max);
			//TODO añadir region1 y region2 a una región como subregiones.

			//Fin del TODO

			//TODO cambiar el condicional y subir el for
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
				//TODO comprovar si la región de overflow es max o min
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
			//TODO Hacer la comprovación con el punto que he de añadir (OverflowP)

			//Fin del TODO

			//En caso que haya sitio para las nuevas regiones, se ponen
			if(!split.superRegion.isfull){
				//Búsqueda e inserción de ambas regiones
				for(int i = 0; i < split.superRegion.subRegions.length; i++){
					if(split.superRegion.subRegions[i] == split){
						split.superRegion.subRegions[i] = region1;
					}
					if(split.superRegion.subRegions[i] == null){
						split.superRegion.subRegions[i] = region2;
					}
				}

				//Redistribución de los puntos en las nuevas regiones
				for(int i = 0; i < split.pointsLeaf.length; i++){
					if((!split.pointsLeaf[i].equals(region1.pointsLeaf[0]))||((!split.pointsLeaf[i].equals(region2.pointsLeaf[0])))){
						this.insertion(split.pointsLeaf[i], split.superRegion);
					}
				}

				//TODO añadir el punto OverflowP

				//Fin del TODO
			}else{
				//TODO asignar overflowR

				//Fin del TODO
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
