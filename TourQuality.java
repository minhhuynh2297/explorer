import java.util.*;
public class TourQuality{
	private int[][] distance_matrix;
	public TourQuality(int[][] distance_matrix){
		this.distance_matrix = distance_matrix;
	}
	public int distanceOf(List<Integer> tour){
		int distance = 0;
		for(int i=0; i<tour.size()-1; i++){
			distance = distance+distance_matrix[tour.get(i)][tour.get(i+1)];
		}
		return distance;
	}
}