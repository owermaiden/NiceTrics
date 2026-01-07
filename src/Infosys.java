import java.util.*;

/**
 * Infosys - small demo of finding the shortest route between two cities in a directed flight graph.
 *
 * Improvements made:
 * - Method and variable names clarified for readability (e.g. shortestPath -> findShortestPath)
 * - Added JavaDoc and inline comments explaining the BFS and reconstruction steps
 * - Added a USE_COLOR toggle so ANSI coloring can be disabled in environments that don't support it
 */
public class Infosys {

    /* Toggle to enable/disable ANSI coloring in the terminal output. Set to false if your terminal
       doesn't support ANSI escape codes or when writing logs where color codes are undesired. */
    private static final boolean USE_COLOR = true;

    // ANSI color codes for terminal-highlighted path output (used only when USE_COLOR == true)
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_CYAN = "\u001B[36m"; // color used for the printed path

    public static void main(String[] args) {
        // Build a small directed flight graph (from -> list of destinations)
        Map<String, List<String>> flights = new HashMap<>();
        addDirectedFlight(flights, "Mumbai", "Istanbul");
        addDirectedFlight(flights, "Istanbul", "Frankfurt");
        addDirectedFlight(flights, "Frankfurt", "London");
        addDirectedFlight(flights, "Frankfurt", "Edinburgh");
        addDirectedFlight(flights, "Frankfurt", "New-York");

        // Examples: print the minimum path (colored if enabled) between pairs
        printColoredPath(flights, "Mumbai", "Edinburgh"); // expected path: Mumbai -> Istanbul -> Frankfurt -> Edinburgh
        printColoredPath(flights, "New-York", "Istanbul"); // expected: no path
        printColoredPath(flights, "Mumbai", "London"); // expected: Mumbai -> Istanbul -> Frankfurt -> London
        printColoredPath(flights, "Mumbai", "Mumbai"); // expected: Mumbai (0 flights)
    }

    /**
     * Add a directed flight edge to the graph.
     * @param graph the map storing adjacency lists
     * @param from starting city
     * @param to destination city
     */
    private static void addDirectedFlight(Map<String, List<String>> graph, String from, String to) {
        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    /**
     * Print the shortest path from start to end. The path string itself is colored when
     * coloring is enabled. Also prints the number of flights (edges) in the path.
     */
    private static void printColoredPath(Map<String, List<String>> flights, String start, String end) {
        List<String> path = findShortestPath(flights, start, end);
        if (path == null) {
            System.out.println("No route from " + start + " to " + end);
            return;
        }

        int flightsCount = Math.max(0, path.size() - 1);
        String pathText = String.join(" -> ", path);

        if (USE_COLOR) {
            System.out.println("Minimum path from " + start + " to " + end + ": "
                    + ANSI_CYAN + pathText + ANSI_RESET + " (flights: " + flightsCount + ")");
        } else {
            System.out.println("Minimum path from " + start + " to " + end + ": "
                    + pathText + " (flights: " + flightsCount + ")");
        }
    }

    /**
     * Find the shortest path (fewest flights / edges) between start and end using BFS.
     * If a path exists, returns a list of city names from start to end (inclusive).
     * If no path exists, returns null.
     *
     * Algorithm summary:
     * - Standard BFS from the start node.
     * - Maintain a predecessor map so when we discover 'end' we can reconstruct the path
     *   by following predecessors back to 'start'. This guarantees a shortest (by edges) path
     *   because BFS explores nodes in increasing order of distance.
     */
    private static List<String> findShortestPath(Map<String, List<String>> flights, String start, String end) {
        // Trivial case: start == end -> path is just the single node
        if (start.equals(end)) return Collections.singletonList(start);

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visitedSet = new HashSet<>();
        Map<String, String> predecessorMap = new HashMap<>();

        // Initialize BFS
        queue.add(start);
        visitedSet.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            // Iterate neighbors (outgoing flights from 'current')
            for (String neighbor : flights.getOrDefault(current, Collections.emptyList())) {
                if (visitedSet.contains(neighbor)) continue; // already explored

                // Mark neighbor visited and record predecessor to enable path reconstruction
                visitedSet.add(neighbor);
                predecessorMap.put(neighbor, current);

                // If we've reached the destination, reconstruct the path immediately
                if (neighbor.equals(end)) {
                    LinkedList<String> path = new LinkedList<>();
                    String p = end;
                    while (p != null) {
                        path.addFirst(p);
                        p = predecessorMap.get(p); // follow predecessors back toward start
                    }
                    // Safety check: ensure path starts with 'start'
                    if (!path.isEmpty() && path.getFirst().equals(start)) return path;
                    else return null; // something unexpected: incomplete predecessor chain
                }

                // Otherwise continue BFS
                queue.add(neighbor);
            }
        }

        // BFS exhausted and 'end' not reached
        return null;
    }
}
