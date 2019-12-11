import java.util.*;
import java.util.List;

public class Explorer {

    public static void main(String[] args){
        // Three inputs
        int energy_capacity = 10;
        int time_rest = 100;
        int map_length = 10;
        int num_planned_points=16;

        int time = 0;
        int sum_times = 1;
        int minE = 1;
        int maxE = 10;
        int minT = 1;
        int maxT = 10;

        List<Integer> visited_points= new ArrayList<Integer>();
        // Held_Karp got a limit: in my computer: 16 is the allowed biggest number
        Random random = new Random(0);
        for(int i=0; i<num_planned_points; i++){
            int visited_point = random.nextInt(map_length*map_length-1);
            visited_points.add(visited_point);
        }
        System.out.println("*******");

        float tour_distance_patch = 0; 
        float tour_distance_hk = 0;
        long startTime = 0;

        Map map = new Map(map_length, minE, maxE , minT, maxT);
        RouteFinder astar_time_with_rest = new RouteFinder(map);

        int[][] distance_matrix_approx = astar_time_with_rest.explore(visited_points, energy_capacity, time_rest, 3);
        List<List<Integer>> route_list = astar_time_with_rest.getRoutes();
        TourQuality tq_approx  = new TourQuality(distance_matrix_approx, route_list);
        // tq_approx.printRoutes();
        KarpSteelCyclePatching patch = new KarpSteelCyclePatching(distance_matrix_approx);
        List<Integer> approx_tour = patch.travel();
        tour_distance_patch+=tq_approx.distanceOf(approx_tour);
        // tq_approx.printSimpleTour(approx_tour);
        // tq_approx.printTour(approx_tour);
        List<Integer> approx_tour_routes = tq_approx.getTourRoutes(approx_tour);
        System.out.println("Approx tour: ");
        tq_approx.printRoutes(approx_tour_routes);

        int[][] distance_matrix_optimal = astar_time_with_rest.explore(visited_points, energy_capacity, time_rest, 2);
        route_list = astar_time_with_rest.getRoutes();
        TourQuality tq_optimal  = new TourQuality(distance_matrix_optimal, route_list);
        // tq_optimal.printRoutes();
        HeldKarp hk = new HeldKarp(distance_matrix_optimal);
        List<Integer> optimal_tour = hk.travel();
        tour_distance_hk+=tq_optimal.distanceOf(optimal_tour);
        // tq_optimal.printSimpleTour(optimal_tour);
        // tq_optimal.printTour(optimal_tour);
        List<Integer> optimal_tour_routes = tq_optimal.getTourRoutes(optimal_tour);
        System.out.println("Optimal tour: ");
        tq_optimal.printRoutes(optimal_tour_routes);

        System.out.println("tour_distance_hk: "+ tour_distance_hk/sum_times);  
        System.out.println("tour_distance_patch: "+ tour_distance_patch/sum_times);
        System.out.println("patched tour quality: " +  (tour_distance_patch/tour_distance_hk-1.0));
        System.out.println("");
        System.out.println("");

    }


}