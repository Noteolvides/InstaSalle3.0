package R_Tree;

import Data.Post;

public class Tree {
    final static int BTREEDIMENSION = 2;
    Region root = new Region();
    Region aux = null;
    Post pintsAux[] = new Post[3];
    Region regionsAux[] = new Region[3];
    int regionPosAux = 0;
    int postionAux = 0;

    public Tree() {
    }


    //Todo Insercion
    public Region insertion(Object o, Region actual) {

        if (actual.childRegionPos == 0) {
            actual.add(new Region((Post) o));
        } else {
            //Búsqueda dle mejor nodo región en el que colocar el punto
            //Todo implementar la búsqueda del mejor nodo
            bestNodeSearch(root, o);

            //comprovación de si está o no llena la mejor región
            if (!aux.isfull) {
                //si no lo está, añadimos el objeto
                //Todo implementar add
                aux.add((Post) o);
            } else {
                //En caso que no lo esté, hacemos split
                //Todo Implementar regionSplit
                Region sutituirParametros = regionSplit(null, this, (Post) o, new Region(), false);
                aux.childRegionPos = sutituirParametros.childRegionPos;
                aux.superRegion =sutituirParametros.superRegion;
                aux.pointsLeaf = sutituirParametros.pointsLeaf;
                aux.subRegions = sutituirParametros.subRegions;
                aux.childRegionPos = sutituirParametros.childPos;
                aux.isfull = sutituirParametros.isfull;
                aux.isRegionFull = sutituirParametros.isRegionFull;
                aux.childPos = sutituirParametros.childPos;
                aux.max = sutituirParametros.max;
                aux.min  = sutituirParametros.min;
                aux.fatherNode = sutituirParametros.fatherNode;

                for (int i = 0; i < postionAux; i++) {
                    aux = insertion(pintsAux[i], aux.superRegion);
                }
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
                if (region != null) {
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
            }
            if (best.subRegions != null) {
                aux = bestNodeSearch(best, o);
            }
        }
        aux = best;
        return best;
    }

    private Region regionSplit(Region split, Tree t, Post overflowP, Region overflowR, boolean regionsplit) {
        split = aux;
        Region region1 = new Region();
        Region region2 = new Region();
        Double[] minpoint = new Double[]{split.min.x, split.min.y};
        Double[] maxpoint = new Double[]{split.max.x, split.max.y};

        if (regionsplit) {
            //Región más pequeña
            Region min = new Region();
            min.min = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
            //Región más grande
            Region max = new Region();
            max.max = new Point(Double.MIN_VALUE, Double.MIN_VALUE);

            //Se busca la región más cercana y lejana desde el punto de vista del 0,0
            //Para encontrar las regiones más alejadas entre ellas
            for (int i = 0; i < split.subRegions.length; i++) {
                //Búsqueda de la región más cercana
                if (split.subRegions[i].min.x < min.min.x) {
                    if (split.subRegions[i].min.y < min.min.y) {
                        min = split.subRegions[i];
                    }
                }

                //Búsqueda de la región más lejana
                if (split.subRegions[i].max.x > max.max.x) {
                    if (split.subRegions[i].max.y > max.max.y) {
                        max = split.subRegions[i];
                    }
                }
            }
            //Comprovación de max y min para OverflowR
			//Búsqueda de la región más cercana
			if (overflowR.min.x < min.min.x) {
				if (overflowR.min.y < min.min.y) {
					min = overflowR;
				}
			}

			//Búsqueda de la región más lejana
			if (overflowR.max.x > max.max.x) {
				if (overflowR.max.y > max.max.y) {
					max = overflowR;
				}
			}

			//region1.add(min);
            //region2.add(max);
            //añadir region1 y region2 a una región como subregiones.
			Region regionA = new Region();
			Region regionB = new Region();
			//regionA.add(region1);
			//regionB.add(region2);
			regionA.add(min);
			regionB.add(max);

            if (!split.isRegionFull) {
            	//Booleanos para saber qué posiciones hemos cambiado
            	boolean position0 = false;
            	boolean position1 = false;

            	//Bucle que posiciona las regiones en el lugar donde hacer el split
            	for(int i = 0; i < split.subRegions.length; i++){
            		//en caso que la región primera sea min, cambiamos ésta región por la que contiene min
            		if(split.subRegions[i] == min){
						split.subRegions[i] = regionA;
						if(i==0){ position0 = true; }else{ position1=true; }
					}else{
						//en caso que la región primera sea max, cambiamos ésta región por la que contiene max
						if(split.subRegions[i] == max){
							split.subRegions[i] = regionB;
							if(i==0){ position0 = true; }else{ position1=true; }
						}else{
							//en caso que la región en cuestión no sea min ni max, lo metemos en regionsAux
							regionsAux[regionPosAux] = split.subRegions[i];
							regionPosAux++;
						}
					}
				}

				//Si sólo se ha usado position0, meteremos overflowR en posición 1, pues significará que será max
				if(position0 && !position1){
						split.subRegions[0] = overflowR;
				}else{
					//Si sólo se ha usado position1, meteremos overflowR en posición 0, pues significará que será max
					if(!position0 && position1){
						split.subRegions[1] = overflowR;
					}else{
						//En caso que tango position0 y position1 sean true (nunca se dará el caso en que sean false), metemos overflowR en RegionsAux
						regionsAux[regionPosAux] = overflowR;
						regionPosAux++;
					}
				}

				//Bucle para posicionar la/s region/es sobrante/s
                for (int i = 0; i < regionsAux.length; i++) {
					if (split.subRegions[0].newArea(regionsAux[i]) < split.subRegions[1].newArea(regionsAux[i])) {
						split.subRegions[0].add(regionsAux[i]);
					} else {
						split.subRegions[0].add(regionsAux[i]);
					}
				}

            } else {
                regionSplit(split.fatherNode.superRegion, this, overflowP, overflowR, true);
            }
        } else {
            //Búsqueda de los dos posts más distanciados, es decir, aquellos que forman la MBR
            //max y min dentro de la región.
            Post min = null;
            Post max = null;
            for (int i = 0; i < split.childPos; i++) {
                //Comprovación para el punto mínimo
                if (split.pointsLeaf[i].location[0] <= minpoint[0]) {
                    if (split.pointsLeaf[i].location[1] <= minpoint[1]) {
                        minpoint = split.pointsLeaf[i].location;
                        min = split.pointsLeaf[i];
                    }
                }

                //Comprovación para el punto máximo
                if (split.pointsLeaf[i].location[0] >= maxpoint[0]) {
                    if (split.pointsLeaf[i].location[1] >= maxpoint[1]) {
                        maxpoint = split.pointsLeaf[i].location;
                        max = split.pointsLeaf[i];
                    }
                }
            }

            //Comprovación con el punto que he de añadir (OverflowP)

            //Comprovación para el punto mínimo con overflowP
            if (overflowP.location[0] <= minpoint[0]) {
                if (overflowP.location[1] <= minpoint[1]) {
                    min = overflowP;
                }
            }

            //Comprovación para el punto máximo con OverflowP
            if (overflowP.location[0] >= maxpoint[0]) {
                if (overflowP.location[1] >= maxpoint[1]) {
                    max = overflowP;
                }
            }

            region1 = new Region(min);
            region2 = new Region(max);
            region1.superRegion = split.superRegion;
            region2.superRegion = split.superRegion;

            Region aux2 = (Region) split.clone();
            //En caso que haya sitio para las nuevas regiones, se ponen
            if (!(split.isRegionFull)) {
                //Búsqueda e inserción de ambas regiones
                split.superRegion.add(region2);
                split = region1;
                postionAux = 0;
                //Redistribución de los puntos en las nuevas regiones
                for (int i = 0; i < aux2.pointsLeaf.length; i++) {
                    if (!((double)aux2.pointsLeaf[i].location[0] == (double)region1.pointsLeaf[0].location[0] && (double)aux2.pointsLeaf[i].location[1] == (double)region1.pointsLeaf[0].location[1])){
                        if (!((double)aux2.pointsLeaf[i].location[0] == (double)region2.pointsLeaf[0].location[0] && (double)aux2.pointsLeaf[i].location[1]  == (double)region2.pointsLeaf[0].location[1]))
                        {
                            pintsAux[postionAux] = aux2.pointsLeaf[i];
                            postionAux++;
                        }
                    }
                }

                //Se añade el punto OverflowP
                if (!((double)overflowP.location[0] == (double)region1.pointsLeaf[0].location[0] && (double)overflowP.location[1] == (double)region1.pointsLeaf[0].location[1])) {
                    if (!((double)overflowP.location[0] == (double)region2.pointsLeaf[0].location[0] && (double)overflowP.location[1] == (double)region2.pointsLeaf[0].location[1])){
                        pintsAux[postionAux] = overflowP;
                        postionAux++;
                    }
                }

                return split;
            } else {
                //Se asigna overflowR
				regionSplit(split.superRegion, this, overflowP, overflowR, true);

                //Redistribución de los puntos en las nuevas regiones
                for (int i = 0; i < split.pointsLeaf.length; i++) {
                    if ((!split.pointsLeaf[i].equals(region1.pointsLeaf[0])) || ((!split.pointsLeaf[i].equals(region2.pointsLeaf[0])))) {
                        this.insertion(split.pointsLeaf[i], split.superRegion);
                    }
                }
            }

        }
        return split;
    }

    //Todo Busqueda

    //Todo Eliminacion
}
