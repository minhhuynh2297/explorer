import java.util.*;
public class TourQuality{
	private int[][] distance_matrix;
	private List<List<Integer>> routes;
	public TourQuality(int[][] distance_matrix, List<List<Integer>> routes){
		this.distance_matrix = distance_matrix;
		this.routes = routes;
	}
	public float distanceOf(List<Integer> tour){
		float distance = 0;
		for(int i=0; i<tour.size()-1; i++){
			distance = (float)distance+(float)distance_matrix[tour.get(i)][tour.get(i+1)];
		}
		return distance;
	}
	public List<Integer> getTourRoutes(List<Integer> tour){
		List<Integer> tour_routes = new ArrayList<Integer>();
		for(int i=0; i<tour.size()-1; i++){
			int x = tour.get(i);
			int next_x = tour.get(i+1);
			int the_r = x*distance_matrix.length+next_x;
        	List<Integer> r = this.routes.get(the_r);
        	for(int r_id=0; r_id<r.size()-1; r_id++){
                tour_routes.add(r.get(r_id));
            } 
		}
		tour_routes.add(this.routes.get(0).get(0));
		return tour_routes;
	}
	public void printTour(List<Integer> tour){
		for(int i=0; i<tour.size()-1; i++){
			int x = tour.get(i);
			int next_x = tour.get(i+1);
			int the_r = x*distance_matrix.length+next_x;
        	List<Integer> r = this.routes.get(the_r);
            for(int r_id=0; r_id<r.size(); r_id++){
                System.out.print(r.get(r_id)+" ");
            } 
            System.out.println("");
		}
		System.out.println("");
	}
	public void printSimpleTour(List<Integer> tour){
		for(int i=0; i<tour.size(); i++){
			int x = tour.get(i);
			System.out.print(x+" ");
		}
		System.out.println("");
	}
	public float sumOfAllPossibleRoutesIn(){
		float distance = 0;
		for(int i=0; i<this.distance_matrix.length; i++){
			for( int j=0; j<this.distance_matrix.length; j++){
				distance = (float)distance+(float)distance_matrix[i][j];
			}	
		}
		return distance;
	}
	public int sumOfAllPossibleRoutesIn(int [][] distance_matrix){
		this.distance_matrix = distance_matrix;
		int distance = 0;
		for(int i=0; i<this.distance_matrix.length; i++){
			for( int j=0; j<this.distance_matrix.length; j++){
				distance = distance+distance_matrix[i][j];
			}	
		}
		return distance;
	}
    public void printRoutes(){
        for(int i=0; i<this.routes.size(); i++){
            List<Integer> r = this.routes.get(i);
            for(int r_id=0; r_id<r.size(); r_id++){
                System.out.print(r.get(r_id)+" ");
            } 
            System.out.println("");
        }
    }
    public void printRoutes(List<Integer> r){
        for(int r_id=0; r_id<r.size(); r_id++){
            System.out.print(r.get(r_id)+" ");
        } 
        System.out.println("");
        System.out.println("");
    }
}