### Kusajaru's Algorithm

Computes strongly connected components in a directed graph.

- Step 1 - Let Grev = Graph G with all arcs reversed
- Step 2 - Run Algorithm on Grev, compute the times
- Step 3 - Run Algoirhtm on G, compute the leaders

##### Pseudocode

    time = 0;
    leader = null;

    Algorithm(Graph)
      for each node in Graph
        if node unexplored
          leader = node
          DFS(Graph, node)
        
    DFS(Graph, Node)
      mark Node explored
      set Node leader to leader
      for each arc(i,j)
        if j unexplored
          DFS(G, j)
          
      time++
      set node time to time
  
