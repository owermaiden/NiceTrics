import java.util.*;

public class TwoWayGraph {

       /*
    [8:26 PM] Sudhir Wagh
Consider the following flight routes –

1.	Mumbai to Istanbul
2.	Istanbul to Frankfurt
3.	Frankfurt to London
4.	Frankfurt to Edinburgh
5.	Frankfurt to New-York

Given a “start-point” and an “end-point” determine the route or connecting flights that one would need to take to reach the destination.
Example –

1.	If the start-point is set to “Mumbai”; and end-point is set to “Edinburgh”. Then the expected output is-
	Mumbai -> Istanbul -> Frankfurt -> Edinburgh
2.	If the start-point is set to “New-York”; and end-point is set to “Istanbul”. Then the expected output is-
	New-York -> Frankfurt -> Istanbul
     */

    private static Map<String, List<String>> flightMap = new HashMap<>();

    public static void main(String[] args) {
        // Initialize the flight routes
        initializeFlightRoutes();

        String startPoint = "New-York";
        String endPoint = "Istanbul";

        List<String> route = findRoute(startPoint, endPoint);
        System.out.println(route);
    }

    private static List<String> findRoute(String startPoint, String endPoint) {

        Set<String> visited = new HashSet<>();
        List<String> routes = new ArrayList<>();

        if (dfs(startPoint, endPoint, visited, routes)){
            routes.add(0, startPoint);
            return routes;
        }

        return null;
    }

    private static boolean dfs(String startPoint, String endPoint, Set<String> visited, List<String> routes) {
        // Recursive function terminal statement
        if (startPoint.equals(endPoint)) return true;

        visited.add(startPoint);
        List<String> neighbours = flightMap.get(startPoint);
        if (neighbours != null){
            for(String n : neighbours){
                // For each Vertex, if they are not visited - perform recursive operation
                if (!visited.contains(n)){
                    // Add each Vertex to routes
                    routes.add(n);
                    // If we can reach to destination - terminate
                    if (dfs(n, endPoint, visited, routes)){
                        return true;
                    }
                    // If we still cant find destination - remove the added item from routes
                    routes.remove(routes.size() - 1);
                }

            }
        }
        return false;
    }


    private static void initializeFlightRoutes() {
        // Add flight routes
        addFlight("Mumbai", "Istanbul");
        addFlight("Istanbul", "Frankfurt");
        addFlight("Frankfurt", "London");
        addFlight("Frankfurt", "Edinburgh");
        addFlight("Frankfurt", "New-York");
    }

    private static void addFlight(String from, String to) {
        if (!flightMap.containsKey(from)) {
            flightMap.put(from, new ArrayList<>());
        }
        flightMap.get(from).add(to);
        if (!flightMap.containsKey(to)) {
            flightMap.put(to, new ArrayList<>());
        }
        flightMap.get(to).add(from);
    }
}
