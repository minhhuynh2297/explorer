import java.util.*;

public class Explorer {

    public static void main(String[] args){
        int length = 10;
        int time = 0;
        int sum_times = 1;
        Random random = new Random();
        List<Integer> visited_points= new ArrayList<Integer>();
        for(int i=0; i<3; i++){
            visited_points.add(random.nextInt(length*length-1));
        }
        int minE = 5;
        int maxE = 10;
        int minT = 7;
        int maxT = 7;
        int energy_capacity = 100;
        int time_rest = 0;
        for(int i=0; i<sum_times;i++){
            Map map = new Map(length, minE, maxE , minT, maxT);
            RouteFinder dijkstra = new RouteFinder(map);
            dijkstra.explore(visited_points, energy_capacity, time_rest);    
        }
        System.out.println(time/sum_times);
    }

}
