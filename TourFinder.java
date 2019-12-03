import java.util.*;
import java.util.Map;
public class TourFinder{
    private int distance_matrix[][];
    public TourFinder(int[][] distance_matrix){
       this.distance_matrix = distance_matrix;
    }
    public int[] travel(){
        Set<Integer> g_set;
        Pair<Integer, Set<Integer>> pair;
        Map<String, Integer> cost = new HashMap<String, Integer>();
        for(int k=1; k<this.distance_matrix.length; k++){
            g_set = new HashSet<Integer>();
            g_set.add(10);
            g_set.add(2);
            g_set.add(2000);
            pair = new Pair<>(k,g_set);
            cost.put(pair.toString(), this.distance_matrix[k][0]);
        }
        // for (Map.Entry<Pair<Integer, Set<Integer>>, Integer> entry : cost.entrySet()) {
        //     System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        // }
        // for(int k=1; k<this.distance_matrix.length; k++){
        //     g_set = new HashSet<Integer>();
        //     g_set.add(10);
        //     g_set.add(2);   
        //     g_set.add(2000);  
        //     pair = new Pair<>(k,g_set);
        //     pair = new Pair<>(pair.toString());
        //     System.out.println(cost.get(pair.toString()));
        // }
        return null;
    }
    public class Pair<A, B> {
        public Integer first;
        public Set<Integer> second;
        public Pair(Integer first, Set<Integer> second) {
            super();
            this.first = first;
            this.second = second;
        }
        public Pair(String str){
            String[] arrOfStr = str.split("-");
            this.first = Integer.parseInt(arrOfStr[0]);
            this.second = new HashSet<Integer>();
            arrOfStr[1] = arrOfStr[1].replace("[", "");
            arrOfStr[1] = arrOfStr[1].replace("]", "");
            if(arrOfStr[1].length()>0){
                arrOfStr = arrOfStr[1].split(",");
                for(int i=0;i<arrOfStr.length;i++){
                    this.second.add(Integer.valueOf(arrOfStr[i].replace(" ", "")));
                }                
            }
        }
        public String toString(){
            return this.first.toString()+"-"+this.second.toString();
        }

    }
}

