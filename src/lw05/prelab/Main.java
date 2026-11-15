package lw05.prelab;

import java.util.*;
import java.io.*;

public class Main {

static void addEdge(int[][] graph, int v1, int v2, int weight) {
graph[v1][v2] = weight;
graph[v2][v1] = weight;
}

  static void printPath(List<String> path) {
    for(int i=0; i<path.size(); i++){
        System.out.print(path.get(i));
        if(i < path.size()-1) System.out.print(" -> ");
    }
System.out.println();
  }

    static List<String> bfs(int start, int size, int[][] graph, String[] names) {
List<String> order = new ArrayList<>();
  Set<Integer> visited = new HashSet<>();
Queue<Integer> q = new LinkedList<>();
q.add(start);
visited.add(start);
while(!q.isEmpty()) {
  int curr = q.poll();
  order.add(names[curr]);
  for(int i=0; i<size; i++) {
if(graph[curr][i] > 0 && !visited.contains(i)) {
    visited.add(i);
    q.add(i);
}
  }
}
return order;
    }

static void dfs(int current, int size, int[][] graph, String[] names, Set<Integer> visited, List<String> dfsOrder) {
visited.add(current);
dfsOrder.add(names[current]);
  for(int i=0; i<size; i++) {
    if(graph[current][i] > 0 && !visited.contains(i)) {
      dfs(i, size, graph, names, visited, dfsOrder);
    }
  }
}

  static int findTheNextNode(int[] dist, boolean[] visited, int size) {
int min = Integer.MAX_VALUE;
int minIndex = -1;
for(int i=0; i<size; i++) {
  if(!visited[i] && dist[i] < min) {
    min = dist[i];
    minIndex = i;
  }
}
    return minIndex;
  }

static void route(int[] prev, int current, int start, String[] names, List<String> path) {
if(current == start) {
    path.add(names[start]);
return;
}
  route(prev, prev[current], start, names, path);
  path.add(names[current]);
}

    static void dijkstraAlg(int start, int dest, int size, int[][] graph, String[] names) {
int[] dist = new int[size];
  boolean[] visited = new boolean[size];
int[] prev = new int[size];
  Arrays.fill(dist, Integer.MAX_VALUE);
Arrays.fill(prev, -1);
dist[start] = 0;

for(int i=0; i<size-1; i++) {
    int u = findTheNextNode(dist, visited, size);
    if(u == -1) break;
    visited[u] = true;
    for(int v=0; v<size; v++) {
      if(!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
dist[v] = dist[u] + graph[u][v];
prev[v] = u;
      }
    }
}

System.out.println("=== Shortest Time from " + names[start] + " ===");
  for(int i=0; i<size; i++) {
      System.out.println(names[i] + " : " + dist[i] + " min");
  }

System.out.println("=== Shortest Route from " + names[start] + " to " + names[dest] + " ===");
List<String> path = new ArrayList<>();
  route(prev, dest, start, names, path);
printPath(path);
System.out.println("Total time: " + dist[dest] + " min");
    }

public static void main(String[] args) {
try {
  Scanner sc = new Scanner(new File("src/lw05/prelab/campus.txt"));
  int n = sc.nextInt();
  String[] names = new String[n];
  for(int i=0; i<n; i++) names[i] = sc.next();

  int e = sc.nextInt();
  int[][] graph = new int[n][n];
  for(int i=0; i<e; i++) {
int v1 = sc.nextInt();
int v2 = sc.nextInt();
int weight = sc.nextInt();
addEdge(graph, v1, v2, weight);
  }

  int start = sc.nextInt();
  int dest = sc.nextInt();
  sc.close();

  System.out.println("=== BFS from " + names[start] + " ===");
  List<String> bfsOrder = bfs(start, n, graph, names);
  printPath(bfsOrder);

  System.out.println("=== DFS from " + names[start] + " ===");
  List<String> dfsOrder = new ArrayList<>();
  Set<Integer> dfsVisited = new HashSet<>();
  dfs(start, n, graph, names, dfsVisited, dfsOrder);
  printPath(dfsOrder);

  dijkstraAlg(start, dest, n, graph, names);

} catch (Exception ex) {
  ex.printStackTrace();
}
}
}