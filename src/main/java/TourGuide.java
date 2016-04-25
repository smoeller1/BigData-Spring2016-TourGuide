
import org.json.JSONObject;

import java.io.FileNotFoundException;

/**
 * Created by smoeller on 3/5/2016.
 */

public class TourGuide {
    public static void main(String[] args) {
        System.out.println("main: Starting");
        
        Stitch myStitcher = new Stitch("IMG_5455.JPG", "IMG_5456.JPG", "c:\\img\\");
        String panoImage = "";
        try {
            panoImage = myStitcher.OutputImage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("main: Finished stitching, output image: " + panoImage);

        YelpRecommend recommender = new YelpRecommend();

        String searchItem = "jeans";
        org.json.simple.JSONObject results = recommender.searchForBusinessesByLocation(searchItem, "38.952508,-94.719309");
        System.out.println("Best match business for " + searchItem + ": " + results.toString());

        searchItem = "Zoom";
        results = recommender.searchForBusinessesByLocation(searchItem, "39.04229,-94.59155");
        System.out.println("Best match business for " + searchItem + ": " + results.toString());
    }
}