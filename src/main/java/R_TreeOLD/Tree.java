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
    boolean pass = false;

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
                if (pass) {
                    aux.childRegionPos = sutituirParametros.childRegionPos;
                    aux.superRegion = sutituirParametros.superRegion;
                    aux.pointsLeaf = sutituirParametros.pointsLeaf;
                    aux.subRegions = sutituirParametros.subRegions;
                    aux.childRegionPos = sutituirParametros.childPos;
                    aux.isfull = sutituirParametros.isfull;
                    aux.isRegionFull = sutituirParametros.isRegionFull;
                    aux.childPos = sutituirParametros.childPos;
                    aux.max = sutituirParametros.max;
                    aux.min = sutituirParametros.min;
                    aux.fatherNode = sutituirParametros.fatherNode;

                    for (int i = 0; i < postionAux; i++) {
                        aux = insertion(pintsAux[i], aux.superRegion);
                    }
                    pass = false;
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
                best = bestNodeSearch(best, o);
                aux = best;
            }
        }
        aux = best;
        return best;
    }

    private Region[] bestNodeSearch(Object o,Region regions[]) {
        Double diff = Double.MAX_VALUE;
        Region best = regions[0];
        Post newNode = (Post) o;
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
        best.add((Post)o);
        return regions;
    }

    private Region regionSplit(Region split, Tree t, Post overflowP, Region overflowR, boolean regionsplit) {



        if (regionsplit) {
            //Región más pequeña
            Region min = new Region();
            min.min = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
            //Región más grande
            Region max = new Region();
            max.max = new Point(Double.MIN_VALUE, Double.MIN_VALUE);
            Double distanciaMin = Double.MAX_VALUE;
            Double distanciaMax = Double.MIN_VALUE;
            Double distanciaMaxBusqueda = null;
            Double distanciaMinBusqueda = null;

            //Se busca la región más cercana y lejana desde el punto de vista del 0,0
            //Para encontrar las regiones más alejadas entre ellas
            for (int i = 0; i < split.subRegions.length; i++) {
                //Búsqueda de la región más cercana
                distanciaMaxBusqueda = calcularDistanciaDesde(split.subRegions[i].max);
                distanciaMinBusqueda = calcularDistanciaDesde(split.subRegions[i].min);
                if (distanciaMaxBusqueda > distanciaMax) {
                    distanciaMax = distanciaMaxBusqueda;
                    max = split.subRegions[i];
                }

                if (distanciaMinBusqueda < distanciaMin) {
                    distanciaMin = distanciaMinBusqueda;
                    min = split.subRegions[i];
                }
            }
            distanciaMaxBusqueda = calcularDistanciaDesde(overflowR.max);
            distanciaMinBusqueda = calcularDistanciaDesde(overflowR.min);
            if (distanciaMaxBusqueda > distanciaMax) {
                max = overflowR;
            }

            if (distanciaMinBusqueda < distanciaMin) {
                min = overflowR;
            }

			//region1.add(min);
            //region2.add(max);
            //añadir region1 y region2 a una región como subregiones.
			Region regionA = new Region();
            regionA.min = min.min;
            regionA.max = min.max;
			Region regionB = new Region();
			regionB.min = max.min;
            regionB.max = max.max;

			regionA.add(min);
			regionB.add(max);


            if (!split.superRegion.isRegionFull || split.superRegion == root) {
            	//Booleanos para saber qué posiciones hemos cambiado
            	boolean position0 = false;
            	boolean position1 = false;

            	Region aux_3 = null;
            	//Bucle que posiciona las regiones en el lugar donde hacer el split
            	for(int i = 0; i < split.subRegions.length; i++){
            		//en caso que la región primera sea min, cambiamos ésta región por la que contiene min
            		if (!(split.subRegions[i] == min || split.subRegions[i] == max)){
                        aux_3 = split.subRegions[i];
                    }
				}
                if (!(overflowR == min || overflowR == max)){
                    aux_3 = overflowR;
                }
                regionPosAux = 0;
                regionsAux[0] = aux_3;
                regionPosAux++;

                //Bucle para posicionar la/s region/es sobrante/s
                for (int i = 0; i < regionPosAux; i++) {
					if (regionA.newArea(regionsAux[i]) < split.subRegions[1].newArea(regionsAux[i])) {
						regionA.add(regionsAux[i]);
					} else {
						regionB.add(regionsAux[i]);
					}
				}
                split.subRegions[0] = regionA;
                split.subRegions[1] = regionB;
            } else {
                regionSplit(split.superRegion, this, overflowP, regionsAux[0], true);
            }
        } else {
            Region region1 = new Region();
            Region region2 = new Region();
            split = aux;
            Double distanciaMin = Double.MAX_VALUE;
            Double distanciaMax = Double.MIN_VALUE;
            Double distancia = null;
            //Búsqueda de los dos posts más distanciados, es decir, aquellos que forman la MBR
            //max y min dentro de la región.
            Post min = null;
            Post max = null;
            for (int i = 0; i < split.childPos; i++) {
                //Comprovación para el punto mínimo
                //Calculo de distancias
                distancia = calcularDistanciaDesde(split.pointsLeaf[i].location);
                if (distancia <= distanciaMin) {
                    distanciaMin = distancia;
                    min = split.pointsLeaf[i];
                }

                //Comprovación para el punto máximo
                if (distancia >= distanciaMax) {
                    distanciaMax = distancia;
                    max = split.pointsLeaf[i];
                }
            }

            distancia = calcularDistanciaDesde(overflowP.location);
            if (distancia <= distanciaMin) {
                min = overflowP;
            }

            //Comprovación para el punto máximo
            if (distancia >= distanciaMax) {
                max = overflowP;
            }

            //Comprovación con el punto que he de añadir (OverflowP)

            region1 = new Region(min);
            region2 = new Region(max);
            region1.superRegion = split.superRegion;
            region2.superRegion = split.superRegion;

            Region aux2 = (Region) split.clone();
            //En caso que haya sitio para las nuevas regiones, se ponen
            if (!(split.superRegion.isRegionFull)) {
                //Búsqueda e inserción de ambas regiones
                split.superRegion.add(region2);
                split = region1;
                postionAux = 0;
                //Redistribución de los puntos en las nuevas regiones
                for (int i = 0; i < aux2.pointsLeaf.length; i++) {
                    if (!((double) aux2.pointsLeaf[i].location[0] == (double) region1.pointsLeaf[0].location[0] && (double) aux2.pointsLeaf[i].location[1] == (double) region1.pointsLeaf[0].location[1])) {
                        if (!((double) aux2.pointsLeaf[i].location[0] == (double) region2.pointsLeaf[0].location[0] && (double) aux2.pointsLeaf[i].location[1] == (double) region2.pointsLeaf[0].location[1])) {
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
                pass = true;
                return split;
            } else {
                //Se asigna overflowR

                //Fin del TODO

                postionAux = 0;
                //Redistribución de los puntos en las nuevas regiones
                for (int i = 0; i < aux2.pointsLeaf.length; i++) {
                    if (!((double) aux2.pointsLeaf[i].location[0] == (double) region1.pointsLeaf[0].location[0] && (double) aux2.pointsLeaf[i].location[1] == (double) region1.pointsLeaf[0].location[1])) {
                        if (!((double) aux2.pointsLeaf[i].location[0] == (double) region2.pointsLeaf[0].location[0] && (double) aux2.pointsLeaf[i].location[1] == (double) region2.pointsLeaf[0].location[1])) {
                            pintsAux[postionAux] = aux2.pointsLeaf[i];
                            postionAux++;
                        }
                    }
                }

                //TODO añadir el punto OverflowP
                if (!((double) overflowP.location[0] == (double) region1.pointsLeaf[0].location[0] && (double) overflowP.location[1] == (double) region1.pointsLeaf[0].location[1])) {
                    if (!((double) overflowP.location[0] == (double) region2.pointsLeaf[0].location[0] && (double) overflowP.location[1] == (double) region2.pointsLeaf[0].location[1])) {
                        pintsAux[postionAux] = overflowP;
                        postionAux++;
                    }
                }

                Region[] auxiliar =  new Region[]{region1,region2};
                auxiliar = bestNodeSearch(pintsAux[postionAux-1],auxiliar);
                aux.childRegionPos = auxiliar[0].childRegionPos;
                aux.superRegion = auxiliar[0].superRegion;
                aux.pointsLeaf = auxiliar[0].pointsLeaf;
                aux.subRegions = auxiliar[0].subRegions;
                aux.childRegionPos = auxiliar[0].childPos;
                aux.isfull = auxiliar[0].isfull;
                aux.isRegionFull = auxiliar[0].isRegionFull;
                aux.childPos = auxiliar[0].childPos;
                aux.max = auxiliar[0].max;
                aux.min = auxiliar[0].min;
                aux.fatherNode = auxiliar[0].fatherNode;
                if (auxiliar[1].min == null || auxiliar[1].max == null){
                    Point subsMinMax = new Point(auxiliar[1].pointsLeaf[0].location[0],auxiliar[1].pointsLeaf[0].location[1]);
                    auxiliar[1].min = subsMinMax;
                    auxiliar[1].max = subsMinMax;
                }
                regionSplit(split.superRegion, this, overflowP, auxiliar[1], true);

                //Fin del TODO

            }

        }
        return split;
    }

    public double calcularDistanciaDesde(Double[] parametros) {
        double cateto1 = parametros[0];
        double cateto2 = parametros[1];
        double hipotenusa = Math.sqrt(cateto1 * cateto1 + cateto2 * cateto2);
        return hipotenusa;
    }

    public double calcularDistanciaDesde(Point parametros) {
        double cateto1 = parametros.x;
        double cateto2 = parametros.y;
        double hipotenusa = Math.sqrt(cateto1 * cateto1 + cateto2 * cateto2);
        return hipotenusa;
    }
}
