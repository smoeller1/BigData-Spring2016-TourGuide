
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoeller on 3/5/2016.
 */

public class TourGuide {
    public static void main(String[] args) {
        System.out.println("main: Starting");

        List<String> imgList = new ArrayList<String>();
        imgList.add("IMG_5480.JPG");
        imgList.add("IMG_5481.JPG");
        imgList.add("IMG_5482.JPG");
        imgList.add("IMG_5483.JPG");
        Stitch myStitcher = new Stitch(imgList, "c:\\img\\");
        String panoImage = "";
        try {
            panoImage = myStitcher.OutputImage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("main: Finished stitching, output image: " + panoImage);


        YelpRecommend recommender = new YelpRecommend();

        String searchItem = "belt";
        JSONObject results = recommender.searchForBusinessesByLocation(searchItem, "39.04229,-94.59155");
        System.out.println("Best match business for " + searchItem + ": " + results.get("name") + ", " + results.get("distance") + " meters away at (" + results.get("latitude") + "," + results.get("longitude") + ")");
        try {
            SocketClient.sendToServer("Best match business for " + searchItem + ": " + results.get("name") + ", " + results.get("distance") + " meters away at (" + results.get("latitude") + "," + results.get("longitude") + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
