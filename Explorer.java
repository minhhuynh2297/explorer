public class Explorer {

    public static void main(String[] args){
        Map map = new Map(4, 1, 5 , 1, 9);
        map.printMap();
        //map.printList();
        map.explore(1,2);
    }

}
