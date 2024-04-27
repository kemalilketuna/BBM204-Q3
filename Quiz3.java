import java.io.*;
import java.util.*;

public class Quiz3 {
    public static void main(String[] args) throws IOException {
        String inputFile = args[0];

        BufferedReader reader = new BufferedReader(new FileReader(inputFile)); 
        int N = Integer.parseInt(reader.readLine());

        for (int i = 0; i < N; i++) {
            String[] input = reader.readLine().split(" ");
            int S = Integer.parseInt(input[0]);
            int P = Integer.parseInt(input[1]);

            List<int[]> stations = new ArrayList<>();
            for (int j = 0; j < P; j++) {
                String[] coordinates = reader.readLine().split(" ");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                stations.add(new int[]{x, y});
            }

            double minT = prims(S, P, stations);
            System.out.println(String.format("%.2f", minT));
        }
        reader.close();
    }

    // Prim's algorithm with priority queue
    private static double prims(int S, int P, List<int[]> stations) {
        // list of double
        List<Double> ways = new ArrayList<>();
        
        boolean[] visited = new boolean[P];
        // distance is double because we need to calculate the square root
        // use priority queue to get the minimum distance
        PriorityQueue<double[]> pq = new PriorityQueue<>(new Comparator<double[]>() {
            @Override
            public int compare(double[] a, double[] b) {
                return Double.compare(a[1], b[1]);
            }
        });

        // start from the first station
        pq.add(new double[]{0, 0});

        while (!pq.isEmpty()) {
            double[] curr = pq.poll();
            int index = (int) curr[0];
            if (visited[index]) {
                continue;
            }
            visited[index] = true;
            ways.add(curr[1]);

            for (int i = 0; i < P; i++) {
                if (!visited[i]) {
                    double dist = calculateDistance(stations.get(index)[0], stations.get(index)[1], stations.get(i)[0], stations.get(i)[1]);
                    pq.add(new double[]{i, dist});
                }
            }
        }

        // sort the ways list
        Collections.sort(ways);

        // return the last element of the list
        return ways.get(ways.size() -S);
    }

    private static double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
