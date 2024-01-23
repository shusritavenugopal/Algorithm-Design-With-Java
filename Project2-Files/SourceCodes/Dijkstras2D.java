package com.assignment;
import com.opencsv.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Dijkstras2D {
    private static String INPUT_CSV_FILE = "/Users/shusritavenugopal/Desktop/Project2_Input_File/Project2_Input_File";
    private static final int INF = Integer.MAX_VALUE;

    public static int[] dijkstra(int[][] graph, int src, int dest) {
        int[] distances = new int[graph.length];
        boolean[] visited = new boolean[graph.length];
        int[] previous = new int[graph.length];

        for (int i = 0; i < distances.length; i++) {
            distances[i] = INF;
            previous[i] = -1;
        }

        distances[src] = 0;

        int currentNode = src;

        while (!visited[dest]) {
            visited[currentNode] = true;

            for (int neighbor = 0; neighbor < graph[currentNode].length; neighbor++) {
                int edgeWeight = graph[currentNode][neighbor];
                if (edgeWeight > 0 && !visited[neighbor] && distances[neighbor] > distances[currentNode] + edgeWeight) {
                    distances[neighbor] = distances[currentNode] + edgeWeight;
                    previous[neighbor] = currentNode;
                }
            }

            currentNode = findMinDistanceNode(distances, visited);

            if (currentNode == -1) {
                break;
            }
        }
        List<Integer> path = getPath(previous, src, dest);
        Collections.reverse(path); 
        System.out.println("Path is: " + path);
        return distances;
    }

    private static boolean allVisited(boolean[] visited) {
        for (boolean visit : visited) {
            if (!visit) {
                return false;
            }
        }

        return true;
    }

    private static int findMinDistanceNode(int[] distances, boolean[] visited) {
        int minDistance = INF;
        int minDistanceNode = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                minDistanceNode = i;
            }
        }

        return minDistanceNode;
    }

    public static List<Integer> getPath(int[] previous, int src, int dest) {
        List<Integer> path = new ArrayList<>();

        int currentNode = dest;
        while (currentNode != src) {
            path.add(currentNode);
            currentNode = previous[currentNode];
        }

        path.add(src);

        return path;
    }

    public static void main(String args[]) throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter The File Name To Read The Graph");
        String inputFileName = sc.nextLine();
        // important to keep the next line.
        INPUT_CSV_FILE = INPUT_CSV_FILE.concat(inputFileName).concat(".csv");
         
        try {
            long startTime = System.nanoTime(); // Record start time
            Runtime runtime = Runtime.getRuntime();
            String reqColumn = "NodeID";
            String connectedColumn = "ConnectedNodeID";
            String distanceColumn = "Distance";
            int nodeIDIndex = -1, connectedNodeIndex = -1, distanceIndex = -1;
            int i, j;
            FileReader filereader = new FileReader(INPUT_CSV_FILE);
            FileReader filereader1 = new FileReader(INPUT_CSV_FILE);
            String[] nextRecord; 
            String[] firstRecord;
            List<Integer> nodes = new ArrayList<Integer>();
            CSVReader csvReader = new CSVReader(filereader);
            CSVReader csvReader1 = new CSVReader(filereader1);

            // To get the index of NodeID column in the csv file.
            if ((firstRecord = csvReader.readNext()) != null) {
                for (int k = 0; k < firstRecord.length; k++) {
                    if (firstRecord[k].equals(reqColumn)) {
                        nodeIDIndex = k;
                    } else if (firstRecord[k].equals(connectedColumn)) {
                        connectedNodeIndex = k;
                    } else if (firstRecord[k].equals(distanceColumn)) {
                        distanceIndex = k;
                    }
                }
            }
            
            // add all nodes to nodes integer arraylist and read the max node.
            while ((nextRecord = csvReader.readNext()) != null) {
                nodes.add(Integer.parseInt(nextRecord[nodeIDIndex]));
            }
            int max = Collections.max(nodes);

            //construct a 2 dimensional array[][] of max x max to read the graph based on the distance - adjacency matrix
            int[][] W = new int[max+1][max+1];
            while ((nextRecord = csvReader1.readNext()) != null) {
                if (nextRecord[nodeIDIndex].equals(reqColumn)) continue;
                i = Integer.parseInt(nextRecord[nodeIDIndex]);
                j = Integer.parseInt(nextRecord[connectedNodeIndex]);
                if (W[i][j] == 0 && (Integer.parseInt(nextRecord[distanceIndex]) > 0)) {
                    W[i][j]= Integer.parseInt(nextRecord[distanceIndex]);
                }
            }
            System.out.println("Enter the source vertex: ");
            int source = sc.nextInt();
            System.out.println("Enter the destination vertex: ");
            int dest = sc.nextInt();

            int[] distancesResult = dijkstra(W, source, dest);
            long endTime = System.nanoTime(); // Record end time
            long elapsedTime = endTime - startTime; // Calculate elapsed time in nanoseconds
            System.out.println("Shortest distance from " + source + " to " + dest + " is " + distancesResult[dest]);
            System.out.println("elapsedTime:" + elapsedTime);
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Memory used: " + usedMemory + " bytes");

        } catch(Exception e) {
                e.printStackTrace();
        }
        
    }
}