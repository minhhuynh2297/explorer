import java.util.*;
import java.util.List;

public class Explorer {

    public static void main(String[] args){
        // Three inputs
        // int map_length = 10;
        // int num_planned_points=16;
        // int time_rest = 100;
        // int energy_capacity = 10;
        int map_length = Integer.parseInt(args[0]);
        int num_planned_points = Integer.parseInt(args[1]);
        int time_rest = Integer.parseInt(args[2]);
        int energy_capacity = Integer.parseInt(args[3]);
        if(map_length<1 && map_length>100){
            System.out.println("map_length is out of range");
        }
        else if(num_planned_points>16){
            System.out.println("num_planned_points is out of range");
        }
        else if(time_rest>100 && time_rest<0){
            System.out.println("time_rest is out of range");
        }
        else if(energy_capacity>100 && energy_capacity<0){
            System.out.println("energy_capacity is out of range");
        }
        else{
            int time = 0;
            int sum_times = 1;
            int minE = 1;
            int maxE = 7;
            int minT = 1;
            int maxT = 8;

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
            System.out.println("Time Info of Map");
            map.printMapTime();
            System.out.println("Energy Info of Map");
            map.printMapEnergy();
            System.out.println("Wind Cost Feature: 10");
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
            System.out.println("");
            System.out.println("Approx tour: ");
            tq_approx.printRoutes(approx_tour_routes);
            System.out.println("approx_tour_distance: "+ tour_distance_patch/sum_times);
            System.out.println("");

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
            System.out.println("optimal_tour_distance: "+ tour_distance_hk/sum_times);  
            System.out.println("");


            System.out.println("approx tour quality: " +  (tour_distance_patch/tour_distance_hk-1.0));
            System.out.println("");
            System.out.println("");
        }
        

    }


}