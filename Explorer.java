import java.util.*;

public class Explorer {

    public static void main(String[] args){
        int length = 10;
        int time = 0;
        int sum_times = 1;
        Random random = new Random();
        List<Integer> visited_points= new ArrayList<Integer>();
        for(int i=0; i<4; i++){
            visited_points.add(random.nextInt(length*length-1));
            System.out.println(visited_points.get(i));
        }
        System.out.println("*******");
        int minE = 5;
        int maxE = 5;
        int minT = 1;
        int maxT = 7;
        int energy_capacity = 100;
        int time_rest = 0;
        for(int i=0; i<sum_times;i++){
            Map map = new Map(length, minE, maxE , minT, maxT);
            RouteFinder dijkstra = new RouteFinder(map);
            int distance_matrix[][] = dijkstra.explore(visited_points, energy_capacity, time_rest);    
            // int distance_matrix[][] = { { 0, 2, 9, 10 }, { 1, 0, 6, 4 }, { 15, 7, 0, 8 }, { 6, 3, 12, 0 } };
            System.out.println(Arrays.deepToString(distance_matrix));
            TourFinder held_karp = new TourFinder(distance_matrix);
            held_karp.travel();
        }
    }

}