package com.assignment;
import com.opencsv.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Floyds2D {
    private static String INPUT_CSV_FILE = "/Users/shusritavenugopal/Desktop/Project2_Input_File/Project2_Input_File";

    public static void floyds2D(int[][] graph, int source, int destination) {
        int V = graph.length;
        int dist[][] = new int[V][V];
        int path[][] = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
                if (i != j && graph[i][j] != 0) {
                    path[i][j] = i;
                } else {
                    path[i][j] = -1;
                }
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != 0 && dist[k][j] != 0 &&
                        (dist[i][k] + dist[k][j] < dist[i][j] || dist[i][j] == 0)) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = path[k][j];
                    }
                }
            }
        }

        int shortestDistance = dist[source][destination];
        System.out.println("Shortest Distance from Node " + source + " to Node " + destination + ": " + shortestDistance);

        System.out.println("Shortest Path from Node " + source + " to Node " + destination + ": " + reconstructPath(source, destination, path));
    }

    private static String reconstructPath(int source, int destination, int[][] path) {
        if (path[source][destination] == -1) {
            return "No path exists";
        }

        StringBuilder pathBuilder = new StringBuilder();
        int viaNode = path[source][destination];
        while (viaNode != source) {
            pathBuilder.insert(0, "," + viaNode);
            viaNode = path[source][viaNode];
        }
        pathBuilder.insert(0, source);
        pathBuilder.append("," + destination);

        return pathBuilder.toString();
    }
    public static void main(String args[]) throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter The File Name To Read The Graph");
        String inputFileName = sc.nextLine();
        // important to keep the next line.
        if (inputFileName != null) {
            INPUT_CSV_FILE = INPUT_CSV_FILE.concat(inputFileName).concat(".csv");
        }
         
        try {
            long startTime = System.nanoTime(); // Record start time
            Runtime runtime = Runtime.getRuntime();
            String reqColumn = "NodeID";
            String connectedColumn = "ConnectedNodeID";
            String distanceColumn = "Distance";
            int nodeIDIndex = -1;
            int connectedNodeIndex = -1;
            int distanceIndex = -1;
            int i;
            int j;
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

            floyds2D(W, source, dest);
            long endTime = System.nanoTime(); // Record end time
            long elapsedTime = endTime - startTime; // Calculate elapsed time in nanoseconds
            System.out.println("elapsedTime:" + elapsedTime);
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Memory used: " + usedMemory + " bytes");
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
