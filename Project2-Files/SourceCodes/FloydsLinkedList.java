package com.assignment;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;

class Vertex {
    int label;
    int weight;
    Vertex next;

    Vertex(int label, int weight) {
        this.label = label;
        this.weight = weight;
        this.next = null;
    }
}

class AdjacencyList {
    Vertex head;
}

public class FloydsLinkedList {
    private static String INPUT_CSV_FILE = "/Users/shusritavenugopal/Desktop/Project2_Input_File/Project2_Input_File";
    static int sourceVertex, destinationVertex;
    public static AdjacencyList[] createGraph(int numVertices) {
        AdjacencyList[] graph = new AdjacencyList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            graph[i] = new AdjacencyList();
        }
        return graph;
    }

    public static void addEdge(AdjacencyList[] graph, int src, int dest, int weight) {
        Vertex newNode = new Vertex(dest, weight);
        newNode.next = graph[src].head;
        graph[src].head = newNode;
    }

    public static void floydWarshall(AdjacencyList[] graph, int numVertices) {
        int[][] distances = new int[numVertices][numVertices];
        int[][] nextVertex = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                distances[i][j] = (i == j) ? 0 : Integer.MAX_VALUE / 2;
                nextVertex[i][j] = -1;
            }
        }

        for (int src = 0; src < numVertices; src++) {
            Vertex node = graph[src].head;
            while (node != null) {
                int dest = node.label;
                int weight = node.weight;
                distances[src][dest] = weight;
                nextVertex[src][dest] = dest;
                node = node.next;
            }
        }

        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (distances[i][k] + distances[k][j] < distances[i][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                        nextVertex[i][j] = nextVertex[i][k];
                    }
                }
            }
        }

        int shortestDistance = distances[sourceVertex][destinationVertex];
        if (shortestDistance == Integer.MAX_VALUE / 2) {
            System.out.println("No path from source to destination.");
        } else {
            System.out.println("Shortest Distance from Vertex " + sourceVertex + " to Vertex " + destinationVertex + ": " + shortestDistance);
            System.out.print("Shortest Path: Vertex " + sourceVertex);
            traceShortestPath(nextVertex, sourceVertex, destinationVertex);
        }
    }

    public static void traceShortestPath(int[][] nextVertex, int source, int destination) {
        while (source != destination) {
            System.out.print(" -> Vertex " + nextVertex[source][destination]);
            source = nextVertex[source][destination];
        }
        System.out.println();
    }

    public static void main(String[] args) {
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
            int numVertices = max+1;

            AdjacencyList[] graph = createGraph(numVertices);

            // Manually add edges and weights
            while ((nextRecord = csvReader1.readNext()) != null) {
                if (nextRecord[nodeIDIndex].equals(reqColumn)) continue;
                addEdge(graph, Integer.parseInt(nextRecord[nodeIDIndex]), Integer.parseInt(nextRecord[connectedNodeIndex]), Integer.parseInt(nextRecord[distanceIndex]));
            }
            System.out.println("Enter the source vertex: ");
            sourceVertex = sc.nextInt();
            System.out.println("Enter the destination vertex: ");
            destinationVertex = sc.nextInt();

            floydWarshall(graph, numVertices);
            
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