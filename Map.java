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
                    map[j][i] = new Terrain(j,(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                }
                else{
                    map[j][i] = new Terrain(j+(map.length * i),(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                }
            }
        }

        /* second quadrant */
        for (int i = 0; i < (x / 2); i++) {
            for (int j = (x / 2); j < x; j++) {
                if(i==0){
                    map[j][i] = new Terrain(j,(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                }
                else{
                    map[j][i] = new Terrain(j+(map.length * i),(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                }
            }
        }

        /* third quadrant */
        for (int i = (x / 2); i < x; i++) {
            for (int j = 0; j < (x / 2); j++) {
                map[j][i] = new Terrain(j+(map.length * i),(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
                //       System.out.println("2");
            }
        }

        /* fourth quadrant */
        for (int i = (x / 2); i < x; i++) {
            for (int j = (x / 2); j < x; j++) {
                map[j][i] = new Terrain(j+(map.length * i),(int) (Math.random() * ((maxE - minE) + 1)) + minE, (int) (Math.random() * ((maxT - minT) + 1)) + minT);
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

    public void explore(int src, int dest){
        costE[src] = 0;
        costT[src] = 0;
        pq = new PriorityQueue<Terrain>(map.length*map.length, new Terrain());
        pq.add(new Terrain(src, 0, 0));
        while(settled.size() != map.length*map.length){
            int u = pq.remove().id;
            settled.add(u);
            neighbors(u);
        }

            System.out.println("The shortest energy cost from source to dest is:" + costE[dest]);
            System.out.println("The shortest time cost from source to dest is:" + costT[dest]);
        }

    public void neighbors(int u){
        int edgeT = -1;
        int newT = -1;
        int edgeE = -1;
        int newE = -1;

        for(int i =0; i< adj.get(u).size(); i++){
            Terrain v = adj.get(u).get(i);

            if(!settled.contains(v.id)){
                edgeT = v.time;
                newT= costT[u] + edgeT;
                edgeE = v.energy;
                newE = costT[u] + edgeE;

                if(newT < costT[v.id]){
                    costT[v.id] = newT;
                }
                if(newE < costE[v.id]){
                    costE[v.id] = newE;
                }

                pq.add(new Terrain(v.id, costT[v.id], costE[v.id]));
            }

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
                System.out.println("[ID:" + map[j][i].id + " E:" + map[j][i].energy + " T:" + map[j][i].time + "]");
            }
        }
    }

}
