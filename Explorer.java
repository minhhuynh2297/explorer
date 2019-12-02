public class Explorer {

    public static void main(String[] args){
        int length = 4;
        for(int i=0; i<1;i++){
            Map map = new Map(length, 1, 5 , 1, 1);
            map.printMapTime();
            RouteFinder dijkstra = new RouteFinder(map);
            dijkstra.explore(0, 8, 2, 5);    
        }
    }

}
