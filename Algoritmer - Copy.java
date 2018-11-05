import static java.lang.Math.random;
import java.util.Random;

public class Algoritmer {
    public static void main(String[] args) {

        // Bygger et nettverk av byer
        int size = 10; //Antall byer
        int cost = 100; //Kostnad � dra mellom byer (Mellom 1 og cost)
        int[][] cities = new int[size][size]; // Tabellen for � holde byer
        int[][] oldSolution = new int[size][size];
        int[][] bestSolution = new int[size][size];
        boolean[][] visited = new boolean[size][size]; // Tabellem for bes�kte byer

 		//Trekker tilfeldig kostnad mellom byer
        for (int i = 0; i < cities.length; i++) {
            for (int j = 0; j < cities.length; j++) {
                cities[i][j] = randomNumber(cost);
                visited[i][j] = false;
            }
        }

        //Eliminerer link til seg selv (diagonalen)
        for (int i = 0; i < cities.length; i++) {
            for (int j = 0; j < cities.length; j++) {
                if (i == j) {
                    cities[i][j] = 0;
                }
            }
        }

        // Forskjellige variabler for � holde resultater
        int randomResultat;
        int iterativeResultat;
        int greedyResultat;
        int greedyOptResultat;
        int greedyRandomOptResultat;

        // TESTING av forskjellige algoritmer


        randomResultat = randomAlg(cities, visited, oldSolution, size);
        System.out.print("Random resultat: " + randomResultat + "\n");

        for (int i = 0; i < cities.length; i++) {
				            for (int j = 0; j < cities.length; j++) {
				               System.out.print("" +oldSolution[i][j]+" -> ");
				            }
				            System.out.println("");
        }

 		greedyOptResultat = greedyOpt(cities, visited, size, randomResultat, oldSolution, bestSolution, 1);
		System.out.print("Greedy optimalisert p� random: " + greedyOptResultat + "\n");

		for (int i = 0; i < cities.length; i++) {
				            for (int j = 0; j < cities.length; j++) {
				               System.out.print("" +oldSolution[i][j]+" -> ");
				            }
				            System.out.println("");
        }

		greedyRandomOptResultat = greedyRandomOpt(cities, visited, size, randomResultat, oldSolution, bestSolution, 1);
        System.out.println("Greedy random optimalisert p� random: " + greedyRandomOptResultat);
		System.out.println("");

		for (int i = 0; i < cities.length; i++) {
		            for (int j = 0; j < cities.length; j++) {
		               System.out.print("" +oldSolution[i][j]+" -> ");
		            }
		            System.out.println("");
        }

        iterativeResultat = iterativeRandomAlg(cities, visited, oldSolution, size);
        System.out.print("Iterativ Random resultat p� iterativ random: " + iterativeResultat + "\n");
        greedyOptResultat = greedyOpt(cities, visited, size, iterativeResultat, oldSolution, bestSolution, 2);
        System.out.print("Greedy optimalisert: " + greedyOptResultat + "\n");
        greedyRandomOptResultat = greedyRandomOpt(cities, visited, size, iterativeResultat, oldSolution, bestSolution, 2);
        System.out.println("Greedy random optimalisert p� iterativ random: " + greedyRandomOptResultat);
		System.out.println("");

        greedyResultat = greedyAlg(cities, visited, oldSolution ,size);
        System.out.print("Greedy resultat: " + greedyResultat + "\n");
        greedyOptResultat = greedyOpt(cities, visited, size, greedyResultat, oldSolution, bestSolution, 3);
        System.out.print("Greedy optimalisert p� greedy: " + greedyOptResultat + "\n");
        greedyRandomOptResultat = greedyRandomOpt(cities, visited, size, greedyResultat, oldSolution, bestSolution, 3);
        System.out.println("Greedy random optimalisert p� greedy: " + greedyRandomOptResultat);

    } // Main END

    // Trekker et tilfeldig tall og returnerer det (Ferdig)
    public static int randomNumber(int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max) + 1;
        return randomNum;
    }

    // Resetter bes�kte byer (Ferdig)
    public static boolean[][] resetVisited(boolean[][] visited) {
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited.length; j++) {
                visited[i][j] = false;
            }
        }
        return visited;
    }

    // Random algorithm (Ferdig)
    public static int randomAlg(int[][] cities, boolean[][] visited, int[][] oldSolution, int size) {
        visited = resetVisited(visited);
        int a = randomNumber(size) - 1;
        int b = randomNumber(size) - 1;
        int total = 0;
        for (int i = 0; i <= cities.length - 1; i++) {
            if (a != b && !visited[a][b] && !visited[b][a]) {
                visited[a][b] = true;
                visited[b][a] = true;
                oldSolution[a][b] = cities[a][b];
                //System.out.print(a + "-" + b + " cost: "+ cities[a][b] + " -> \n");
                total += cities[a][b];
            }
            a = b;
            b = randomNumber(size) - 1;
        }
        //System.out.println("total cost random algorithm: " + total + "\n");
        return total;
    }

    // Iterative Random Algorithm
    public static int iterativeRandomAlg(int[][] cities, boolean[][] visited, int[][] oldSolution, int size){
        visited = resetVisited(visited);
        int iterativeAmount = 100;
        int currentRoute;
        int bestRoute = Integer.MAX_VALUE;
        int [][] tempSolution = new int[size][size];

        for(int i = 0; i < iterativeAmount; i++){
            currentRoute = randomAlg(cities, visited, oldSolution ,size);
            if(currentRoute < bestRoute){
              bestRoute = currentRoute;
              tempSolution = oldSolution;
            }
            resetVisited(visited);
        }
        //System.out.println("total cost iterative random algorithm: " + bestRoute + "\n");
        oldSolution = tempSolution;
        return bestRoute;
    }

    // Greedy Algorithm (Ferdig)
    public static int greedyAlg(int[][] cities, boolean[][] visited, int[][] oldSolution, int size) {
        visited = resetVisited(visited);
        int a = randomNumber(size) - 1;
        int total = 0;
        int cost = 0;
        int best;
        int current = 0;
        int previous;
        int counter = 0;
        for (int i = 0; i <= cities.length - 1; i++) {
            best = Integer.MAX_VALUE;
            for (int j = 0; j < cities.length - 1; j++) {
                if (cities[a][j] < best && a != j && !visited[a][j] && !visited[j][a]) {
                    best = cities[a][j];
                    current = j;
                }
            }
            visited[a][current] = true;
            visited[current][a] = true;
            oldSolution[a][current] = cities[a][current];
            total += cities[a][current];
            cost = cities[a][current];
            previous = a;
            a = current;
            //System.out.print("cost: " + cost + " city " + previous + " -> " + current);
        }
        //System.out.println("total cost greedy: " + total + "\n");
        return total;
    }

    public static int greedyOpt(int[][] cities, boolean[][] visited, int size, int oldCost, int[][] oldSolution, int[][] bestSolution, int command){
        int newCost = 0;
        int bestCost = oldCost;
        int a = randomNumber(size) - 1; // Trekker tilfeldig by som skal
        int b = randomNumber(size) - 1; // Byttes med en annen tilfeldig by
        int[][] newSolution = new int[size][size];
        bestSolution = oldSolution;
        int tempCities[][] = cities;
        int temp[][] = new int[size][size]; // Holder verdier mens vi bytter byer;
        int maxTries = 10; // Bestemmer hvor lenge vi skal holde p�

		for(int i = 0; i < maxTries; i++){
			// Bytter byer
			for(int j = 0; j < tempCities.length; j++){
				tempCities[a][j] = temp[a][j];
				tempCities[a][j] = tempCities[b][j];
				tempCities[b][j] = temp[a][j];
			}
			// Velger initiall l�sning
			if(command == 1){
				newCost = randomAlg(cities, visited, oldSolution ,size);
				newSolution = oldSolution;
			}
			else if(command == 2){
				newCost = iterativeRandomAlg(cities, visited, oldSolution ,size);
				newSolution = oldSolution;
			}
			else if(command == 3){
				newCost = greedyAlg(cities, visited, oldSolution ,size);
				newSolution = oldSolution;
			}

			if(newCost < oldCost){
				oldCost = newCost;
				oldSolution = newSolution;
				if(newCost < bestCost){
					bestCost = newCost;
					bestSolution = newSolution;
				}
			}
		}
    return bestCost;
    }

    public static int greedyRandomOpt(int[][] cities, boolean[][] visited, int size, int oldCost, int[][] oldSolution, int[][] bestSolution, int command){
        int newCost = 0;
        int bestCost = oldCost;
        int a = randomNumber(size) - 1; // Trekker tilfeldig by som skal
        int b = randomNumber(size) - 1; // Byttes med en annen tilfeldig by
		int[][] newSolution = new int[size][size];
        bestSolution = oldSolution;
        int tempCities[][] = cities;
        int temp[][] = new int[size][size]; // Holder verdier mens vi bytter byer;
        int maxTries = 10; // Bestemmer hvor lenge vi skal holde p�
        double rnd;
        double probability = 0.9; // Bestemmer hvor lenge vi skal holde p�
        do{
            for(int i = 0; i < maxTries; i++){
                // Bytter byer
                for(int j = 0; j < tempCities.length; j++){
                    tempCities[a][j] = temp[a][j];
                    tempCities[a][j] = tempCities[b][j];
                    tempCities[b][j] = temp[a][j];
                }
                // Velger initiall l�sning
			    if(command == 1){
					newCost = randomAlg(cities, visited, oldSolution ,size);
					newSolution = oldSolution;
				}
				else if(command == 2){
					newCost = iterativeRandomAlg(cities, visited, oldSolution ,size);
					newSolution = oldSolution;
				}
				else if(command == 3){
					newCost = greedyAlg(cities, visited, oldSolution ,size);
					newSolution = oldSolution;
				}

                if(newCost < oldCost){
                    oldCost = newCost;
                    oldSolution = newSolution;
                    if(newCost < bestCost){
                        bestCost = newCost;
                    	bestSolution = newSolution;
                    }
                }
                else{
                    rnd = ((random() % 100)/100);
                    if(rnd < probability){
                        oldCost = newCost;
                        bestSolution = newSolution;
                    }
                }
            }
            probability = probability * 0.9;
        }while(probability > 0.0000001);
    return bestCost;
    }
} // Class END