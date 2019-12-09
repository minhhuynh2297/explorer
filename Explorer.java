import java.util.*;

public class Explorer {

    public static void main(String[] args){
        int length = 100;
        int time = 0;
        int sum_times = 1;
        Random random = new Random();
        List<Integer> visited_points= new ArrayList<Integer>();
        // Held_Karp got a limit: in my computer: 16 is the allowed biggest number
        int num_planned_points = 4;
        for(int i=0; i<num_planned_points; i++){
            visited_points.add(random.nextInt(length*length-1));
            // System.out.println(visited_points.get(i));
        }
        System.out.println("*******");
        int minE = 1;
        int maxE = 5;
        int minT = 1;
        int maxT = 7;
        int energy_capacity = 100;
        int time_rest = 10;
        for(int i=0; i<sum_times;i++){
            Map map = new Map(length, minE, maxE , minT, maxT);
            RouteFinder astar_low = new RouteFinder(map);
            RouteFinder dijkstra_low = new RouteFinder(map);
            RouteFinder astar = new RouteFinder(map);
            RouteFinder dijkstra = new RouteFinder(map);
            // dijkstra.explore(1, 1000, energy_capacity, time_rest); 
            // int distance_matrix[][] = astar.explore(1,20, energy_capacity, time_rest);    
            int astar_distance_matrix_low[][] = astar_low.explore(visited_points, energy_capacity, time_rest, true, true); 
            int dij_distance_matrix_low[][] = dijkstra_low.explore(visited_points, energy_capacity, time_rest, false, true);
            int astar_distance_matrix[][] = astar.explore(visited_points, energy_capacity, time_rest, true, false); 
            int dij_distance_matrix[][] = dijkstra.explore(visited_points, energy_capacity, time_rest, false, false);
            System.out.println(Arrays.deepToString(astar_distance_matrix_low));
            System.out.println(Arrays.deepToString(dij_distance_matrix_low));
            System.out.println(Arrays.deepToString(astar_distance_matrix));
            System.out.println(Arrays.deepToString(dij_distance_matrix));

        }






        // int length = 10;
        // int time = 0;
        // int sum_times = 1;
        // Random random = new Random();
        // List<Integer> visited_points= new ArrayList<Integer>();
        // // Held_Karp got a limit: in my computer: 16 is the allowed biggest number
        // int num_planned_points = 16;
        // for(int i=0; i<num_planned_points; i++){
        //     visited_points.add(random.nextInt(length*length-1));
        //     System.out.println(visited_points.get(i));
        // }
        // System.out.println("*******");
        // int minE = 1;
        // int maxE = 5;
        // int minT = 1;
        // int maxT = 7;
        // int energy_capacity = 100;
        // int time_rest = 0;
        // for(int i=0; i<sum_times;i++){
        //     Map map = new Map(length, minE, maxE , minT, maxT);
        //     RouteFinder dijkstra = new RouteFinder(map);
        //     int distance_matrix[][] = dijkstra.explore(visited_points, energy_capacity, time_rest);    
        //     // int distance_matrix[][] = { { 0, 2, 9, 10 }, { 1, 0, 6, 4 }, { 15, 7, 0, 8 }, { 6, 3, 12, 0 } };
        //     TourQuality tour_q = new TourQuality(distance_matrix);
        //     System.out.println(Arrays.deepToString(distance_matrix));
        //     KarpSteelCyclePatching ks_cycle_patching = new KarpSteelCyclePatching(distance_matrix);
        //     System.out.println(tour_q.distanceOf(ks_cycle_patching.travel()));
        //     HeldKarp held_karp = new HeldKarp(distance_matrix);
        //     System.out.println(tour_q.distanceOf(held_karp.travel()));
        // }





      // the problem is written in the form of a matrix
        // int[][] dataMatrix = {
        // //col0  col1  col2  col3
        // {1500, 4000, 4500},  //row0
        // {2000, 6000, 3500},  //row1
        // {2000, 4000, 2500},  //row2
        // };
        // int distance_matrix[][] = { { 0, 2, 9, 10, 100, 1000 }, { 1, 0, 6, 4, 100, 1000 }, { 15, 7, 0, 8, 100, 1000 }, { 6, 3, 12, 0, 100, 1000 }, { 100, 100, 100, 100, 0, 1000 } ,{ 1000, 1000, 1000, 1000, 0 }};
        // //find optimal assignment
        // KarpSteelCyclePatching ks_cycle_patching = new KarpSteelCyclePatching(distance_matrix);
        // ks_cycle_patching.travel();
        // HeldKarp held_karp = new HeldKarp(distance_matrix);
        // held_karp.travel();
        
        // int[] assignment_array = {3,0,4,1,2,6,5};
        // List<List<Integer>> cycle_list = new ArrayList<List<Integer>>();
        // List<Integer> cycle = new ArrayList<Integer>();
        
        // boolean[] visit = new boolean[assignment_array.length]; 
        // Arrays.fill(visit, false);
        // List<Integer> non_assigned_ids = new ArrayList<Integer>();
        // for(int i=0; i<assignment_array.length; i++){
        //     non_assigned_ids.add(i);
        // }
        // int current_node_id = 0;
        // while(true){
        //     if(visit[current_node_id]){
        //         List<Integer> clone_cycle = new ArrayList<>();
        //         clone_cycle.addAll(cycle);
        //         cycle_list.add(clone_cycle);
        //         if(non_assigned_ids.size()>0){
        //             cycle = new ArrayList<>();
        //             current_node_id = non_assigned_ids.get(0);
        //         }
        //         else{
        //             break;
        //         }
                
        //     }
        //     else{ 
        //         visit[current_node_id] = true;
        //         cycle.add(current_node_id);
        //         non_assigned_ids.remove(new Integer(current_node_id));
        //         current_node_id = assignment_array[current_node_id];
        //     }
        // }
        // for(int id=0; id<cycle_list.size(); id++){
        //     StringBuilder sb = new StringBuilder();
        //     for (int i = cycle_list.get(id).size() - 1; i >= 0; i--) {
        //       int num = cycle_list.get(id).get(i);
        //       sb.append(num);
        //     }
        //     String result = sb.toString();
        //     System.out.println(result);            
        // }


    }

}