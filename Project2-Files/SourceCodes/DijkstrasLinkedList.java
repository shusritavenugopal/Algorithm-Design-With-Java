package com.assignment;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;

class Node {
    int data;
    Node next;
    Node prev;

    public Node(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

class DoublyLinkedList {
    Node head;
    Node tail;

    public void append(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public void remove(Node node) {
        if (node == head) {
            head = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
    }
}

class Graph {
    int V; // Number of vertices
    int[][] adjMatrix;
    DoublyLinkedList[] adjacencyList;

    public Graph(int vertices) {
        this.V = vertices;
        this.adjMatrix = new int[vertices][vertices];
        this.adjacencyList = new DoublyLinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new DoublyLinkedList();
        }
    }

    public void addEdge(int src, int dest, int weight) {
        adjMatrix[src][dest] = weight;
        adjMatrix[dest][src] = weight; // Undirected graph
        adjacencyList[src].append(dest);
        adjacencyList[dest].append(src); // Adding both directions to adjacency list
    }

    public List<Integer> dijkstra(int start, int end) {
        int[] distance = new int[V];
        int[] parent = new int[V];
        boolean[] visited = new boolean[V];

        for (int i = 0; i < V; i++) {
            distance[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }
        distance[start] = 0;

        for (int i = 0; i < V - 1; i++) {
            int u = minDistance(distance, visited);
            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visited[v] && distance[u] != Integer.MAX_VALUE
                        && distance[u] + adjMatrix[u][v] < distance[v]) {
                    distance[v] = distance[u] + adjMatrix[u][v];
                    parent[v] = u;
                }
            }
        }

        return getPath(parent, start, end);
    }

    private int minDistance(int[] distance, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!visited[v] && distance[v] <= min) {
                min = distance[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    private List<Integer> getPath(int[] parent, int start, int end) {
        List<Integer> path = new ArrayList<>();
        int current = end;
        while (current != -1) {
            path.add(current);
            current = parent[current];
        }
        if (path.get(path.size() - 1) != start) {
            path.clear();
        }
        return path;
    }
}

public class DijkstrasLinkedList {
    private static String INPUT_CSV_FILE = "/Users/shusritavenugopal/Desktop/Project2_Input_File/Project2_Input_File";
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
            int vertices = max+1;
            Graph graph = new Graph(vertices);

            while ((nextRecord = csvReader1.readNext()) != null) {
                if (nextRecord[nodeIDIndex].equals(reqColumn)) continue;
                graph.addEdge(Integer.parseInt(nextRecord[nodeIDIndex]), Integer.parseInt(nextRecord[connectedNodeIndex]), Integer.parseInt(nextRecord[distanceIndex]));
            }
            System.out.println("Enter the source vertex: ");
            int source = sc.nextInt();
            System.out.println("Enter the destination vertex: ");
            int dest = sc.nextInt();
            List<Integer> shortestPath = graph.dijkstra(source, dest);
            if (shortestPath.isEmpty()) {
                System.out.println("No path found.");
            } else {
                System.out.print("Shortest path from " + source + " to " + dest + ": ");
                for (int p = 0; p < shortestPath.size(); p++) {
                    System.out.print(shortestPath.get(p));
                    if (p < shortestPath.size() - 1) {
                        System.out.print(", ");
                    }
                }
            }
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
