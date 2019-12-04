import java.util.*;

public class Map {

    //2D Array representation for the map perhaps for later use for visualization
    //currently used for quick access of terrain information
    private Terrain[][] map;
    private int costT[];
    private int costE[];
    private Set<Integer> settled = new HashSet<Integer>();
    private PriorityQueue<Terrain> pq;
    //list of adjacent edges - current state of map is that it is FULLY connected / DENSE
    private List<List<Terrain>> adj = new ArrayList<List<Terrain>>();
    /**
     * Initializes the map with varying terrain of varying costs
     * @param x is the dimension of the n x n map
     */
    public Map(int x, int minE, int maxE, int minT, int maxT) {
        map = new Terrain[x][x];

        /* first quadrant */
        for (int i = 0; i < (x / 2); i++) {
            for (int j = 0; j < (x / 2); j++) {
                if(i==0){
                    map[i][j] = new Terrain(j,(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                }
                else{
                    map[i][j] = new Terrain(j+(map.length * i),(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                }
            }
        }

        /* second quadrant */
        for (int i = 0; i < (x / 2); i++) {
            for (int j = (x / 2); j < x; j++) {
                if(i==0){
                    map[i][j] = new Terrain(j,(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                }
                else{
                    map[i][j] = new Terrain(j+(map.length * i),(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                }
            }
        }

        /* third quadrant */
        for (int i = (x / 2); i < x; i++) {
            for (int j = 0; j < (x / 2); j++) {
                map[i][j] = new Terrain(j+(map.length * i),(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                //       System.out.println("2");
            }
        }

        /* fourth quadrant */
        for (int i = (x / 2); i < x; i++) {
            for (int j = (x / 2); j < x; j++) {
                map[i][j] = new Terrain(j+(map.length * i),(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                //      System.out.println("4");
            }
        }

        /* initializes lists in ArrayList */
        for(int i = 0; i < map.length; i++){
            for(int j=0; j<map.length; j++){
                List<Terrain> item = new ArrayList<Terrain>();
                adj.add(item);
            }
        }
        /* adds in adjacent terrains */
        for(int i = 0; i< map.length; i++){
            for (int j=0; j<map.length; j++){
                if(i!=0 && map[j][i-1]!=null){
                    adj.get(map[j][i].id).add(map[j][i-1]);
                }
                if(j!=map.length-1 && map[j+1][i]!=null){
                    adj.get(map[j][i].id).add(map[j+1][i]);
                }
                if(j!=0 && map[j-1][i]!=null){
                    adj.get(map[j][i].id).add(map[j-1][i]);
                }
                if(i!=map.length-1 && map[j][i+1]!=null){
                    adj.get(map[j][i].id).add(map[j][i+1]);
                }
            }

        }

        costE = new int[map.length*map.length];
        costT = new int[map.length*map.length];

        for(int i = 0; i<map.length*map.length; i++){
            costE[i] = Integer.MAX_VALUE;
            costT[i] = Integer.MAX_VALUE;
        }

    }

    public int energyExplore(int src, int dest){
        return 0;
    }

    public int timeExplore(int startX, int startY, int destX, int destY){
        return 0;
    }

    public void test(){
        System.out.println(adj.get(0));
    }

    public void printList(){
        for(int i = 0; i<map.length * map.length; i++){
          Iterator<Terrain> iterator = adj.get(i).iterator();
            while(iterator.hasNext()){
                System.out.print(iterator.next().id+ ", ");
            }
            System.out.println();
        }
    }

    public void printMap(){
        for(int i=0; i<map.length; i++){
            System.out.println();
            for (int j=0; j<map.length; j++){
                System.out.println(i + ", " + j);
                System.out.println("[ID:" + map[i][j].id + " E:" + map[i][j].energy + " T:" + map[i][j].time + "]");
            }
        }
    }
    public void printMapTime(){
        for(int i=0; i<map.length; i++){
            for (int j=0; j<map.length; j++){
                System.out.print((int)map[i][j].time + " ");
            }
            System.out.println();
        }
    }
    public void printMapEnergy(){
        for(int i=0; i<map.length; i++){
            for (int j=0; j<map.length; j++){
                System.out.print((int)map[i][j].time + " ");
            }
            System.out.println();
        }
    }
    public Terrain TheCell(int i, int j){
        return map[i][j];
    }
    public Terrain TheCell(int id){
        int i = id/map.length;
        int j = id%map.length;
        return map[i][j];
    }
    public Terrain GetTheqQuadrantInfo(int pre_unit , int current_unit){
        int unit_length = map.length/2;
        int id_x = current_unit/2;
        int id_y= current_unit%2;
        int i_start = id_x*unit_length;
        int i_end = i_start+unit_length;
        int j_start = id_y*unit_length;
        int j_end = j_start+unit_length;
        float sum_time_cost = 0;
        float sum_energy_cost = 0;
        int order = 0;
        if(pre_unit>current_unit){
            order = 1;
        }
        else if(pre_unit<=current_unit){
            order = -1;
        }

        for (int i = i_start; i < i_end; i++) {
            for (int j = j_start; j < j_end; j++) {
                sum_time_cost = sum_time_cost + map[i][j].time(map[i][j].id+order);
                sum_energy_cost = sum_energy_cost + map[i][j].energy(map[i][j].id+order);
            }
        }
        return new Terrain((int) Math.random(), (int)(sum_time_cost/(unit_length*unit_length)), (int)(sum_energy_cost/(unit_length*unit_length)));
    }
    public int Length(){
        return map.length;
    }

}
