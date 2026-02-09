import java.util.*;


public class GraphProject {

 
    static class Graph {
        private final boolean directed;
        private final Map<String, LinkedList<String>> adj;

        Graph(boolean directed) {
            this.directed = directed;
            this.adj = new HashMap<>();
        }

        public boolean isDirected() {
            return directed;
        }

        public boolean addVertex(String v) {
            if (v == null || v.trim().isEmpty()) return false;
            v = v.trim();
            if (adj.containsKey(v)) return false;
            adj.put(v, new LinkedList<>());
            return true;
        }

        public boolean containsVertex(String v) {
            if (v == null) return false;
            return adj.containsKey(v.trim());
        }

        public boolean addEdge(String from, String to) {
            if (from == null || to == null) return false;
            from = from.trim();
            to = to.trim();
            if (from.isEmpty() || to.isEmpty()) return false;

            if (!adj.containsKey(from)) addVertex(from);
            if (!adj.containsKey(to)) addVertex(to);

            // No duplicates
            if (adj.get(from).contains(to)) return false;

            adj.get(from).add(to);
            if (!directed) {
                if (!adj.get(to).contains(from)) adj.get(to).add(from);
            }
            return true;
        }

        public boolean removeEdge(String from, String to) {
            if (from == null || to == null) return false;
            from = from.trim();
            to = to.trim();
            if (!adj.containsKey(from) || !adj.containsKey(to)) return false;

            boolean removed = adj.get(from).remove(to);
            if (!directed) {
                adj.get(to).remove(from);
            }
            return removed;
        }

        public boolean removeVertex(String v) {
            if (v == null) return false;
            v = v.trim();
            if (!adj.containsKey(v)) return false;

            // Remove the vertex list
            adj.remove(v);

            // Remove edges pointing to v
            for (String key : adj.keySet()) {
                adj.get(key).remove(v);
            }
            return true;
        }

        public Set<String> getVertices() {
            return adj.keySet();
        }

        public List<String> neighbors(String v) {
            if (v == null) return Collections.emptyList();
            v = v.trim();
            if (!adj.containsKey(v)) return Collections.emptyList();
            return adj.get(v);
        }

        public int vertexCount() {
            return adj.size();
        }

        public int edgeCount() {
            int count = 0;
            for (String v : adj.keySet()) count += adj.get(v).size();
            return directed ? count : count / 2;
        }

        public String bfs(String start) {
            if (!containsVertex(start)) return "";
            start = start.trim();

            StringBuilder sb = new StringBuilder();
            Set<String> visited = new HashSet<>();
            Queue<String> q = new LinkedList<>();

            visited.add(start);
            q.add(start);

            while (!q.isEmpty()) {
                String cur = q.remove();
                if (sb.length() > 0) sb.append(" -> ");
                sb.append(cur);

                for (String nxt : adj.get(cur)) {
                    if (!visited.contains(nxt)) {
                        visited.add(nxt);
                        q.add(nxt);
                    }
                }
            }
            return sb.toString();
        }

        public String dfs(String start) {
            if (!containsVertex(start)) return "";
            start = start.trim();

            StringBuilder sb = new StringBuilder();
            Set<String> visited = new HashSet<>();
            dfsHelper(start, visited, sb);
            return sb.toString();
        }

        private void dfsHelper(String v, Set<String> visited, StringBuilder sb) {
            visited.add(v);
            if (sb.length() > 0) sb.append(" -> ");
            sb.append(v);

            for (String nxt : adj.get(v)) {
                if (!visited.contains(nxt)) {
                    dfsHelper(nxt, visited, sb);
                }
            }
        }

        /**
         * Shortest path in an unweighted graph using BFS.
         * Returns path like [A, B, C]. If no path, returns empty list.
         */
        public List<String> shortestPath(String start, String end) {
            if (!containsVertex(start) || !containsVertex(end)) return Collections.emptyList();
            start = start.trim();
            end = end.trim();

            Queue<String> q = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            Map<String, String> parent = new HashMap<>();

            visited.add(start);
            q.add(start);
            parent.put(start, null);

            while (!q.isEmpty()) {
                String cur = q.remove();
                if (cur.equals(end)) break;

                for (String nxt : adj.get(cur)) {
                    if (!visited.contains(nxt)) {
                        visited.add(nxt);
                        parent.put(nxt, cur);
                        q.add(nxt);
                    }
                }
            }

            if (!parent.containsKey(end)) return Collections.emptyList();

            LinkedList<String> path = new LinkedList<>();
            String cur = end;
            while (cur != null) {
                path.addFirst(cur);
                cur = parent.get(cur);
            }
            return path;
        }

       
        public boolean hasDirectedCycle() {
            if (!directed) return false;


            Map<String, Integer> state = new HashMap<>();
            for (String v : adj.keySet()) state.put(v, 0);

            for (String v : adj.keySet()) {
                if (state.get(v) == 0) {
                    if (cycleDfs(v, state)) return true;
                }
            }
            return false;
        }

        private boolean cycleDfs(String v, Map<String, Integer> state) {
            state.put(v, 1); // visiting
            for (String nxt : adj.get(v)) {
                if (!state.containsKey(nxt)) continue;

                int st = state.get(nxt);
                if (st == 1) return true;      // back edge
                if (st == 0 && cycleDfs(nxt, state)) return true;
            }
            state.put(v, 2); // done
            return false;
        }

        public void printGraph() {
            System.out.println("\n--- Graph (" + (directed ? "Directed" : "Undirected") + ") ---");
            System.out.println("Vertices: " + vertexCount() + " | Edges: " + edgeCount());
            for (String v : adj.keySet()) {
                System.out.print(v + " : ");
                System.out.println(adj.get(v));
            }
            System.out.println("-----------------------------------\n");
        }
    }

   
    private static void menu() {
        System.out.println("Graph Project Menu");
        System.out.println("1) Add vertex");
        System.out.println("2) Add edge");
        System.out.println("3) Remove vertex");
        System.out.println("4) Remove edge");
        System.out.println("5) Print graph");
        System.out.println("6) BFS traversal");
        System.out.println("7) DFS traversal");
        System.out.println("8) Shortest path (unweighted)");
        System.out.println("9) Check directed cycle");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    private static String readLine(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Create directed graph? (y/n): ");
        String ans = sc.nextLine().trim().toLowerCase();
        boolean directed = ans.startsWith("y");

        Graph g = new Graph(directed);

        // Starter data so it’s not empty
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("C", "D");
        if (directed) g.addEdge("D", "E");

        while (true) {
            menu();
            String choice = sc.nextLine().trim();

            if (choice.equals("0")) {
                System.out.println("Goodbye!");
                break;
            } else if (choice.equals("1")) {
                String v = readLine(sc, "Vertex name: ");
                System.out.println(g.addVertex(v) ? "Added." : "Not added (maybe exists?).");
            } else if (choice.equals("2")) {
                String a = readLine(sc, "From: ");
                String b = readLine(sc, "To: ");
                System.out.println(g.addEdge(a, b) ? "Edge added." : "Edge not added.");
            } else if (choice.equals("3")) {
                String v = readLine(sc, "Vertex to remove: ");
                System.out.println(g.removeVertex(v) ? "Removed." : "Not removed.");
            } else if (choice.equals("4")) {
                String a = readLine(sc, "From: ");
                String b = readLine(sc, "To: ");
                System.out.println(g.removeEdge(a, b) ? "Removed." : "Not removed.");
            } else if (choice.equals("5")) {
                g.printGraph();
            } else if (choice.equals("6")) {
                String start = readLine(sc, "Start vertex: ");
                String out = g.bfs(start);
                System.out.println(out.isEmpty() ? "Vertex not found." : ("BFS: " + out));
            } else if (choice.equals("7")) {
                String start = readLine(sc, "Start vertex: ");
                String out = g.dfs(start);
                System.out.println(out.isEmpty() ? "Vertex not found." : ("DFS: " + out));
            } else if (choice.equals("8")) {
                String start = readLine(sc, "Start: ");
                String end = readLine(sc, "End: ");
                List<String> path = g.shortestPath(start, end);
                if (path.isEmpty()) {
                    System.out.println("No path found (or vertices missing).");
                } else {
                    System.out.println("Shortest path: " + path);
                    System.out.println("Distance (edges): " + (path.size() - 1));
                }
            } else if (choice.equals("9")) {
                if (!g.isDirected()) {
                    System.out.println("Cycle check is enabled only for directed graphs in this project.");
                } else {
                    System.out.println(g.hasDirectedCycle() ? "Graph HAS a directed cycle." : "Graph has NO directed cycle.");
                }
            } else {
                System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}
