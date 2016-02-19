package ImageId;

import java.util.ListIterator;
import java.util.Vector;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class IdentifyImage {
	private Vector<Image> imageCompareSet;
	static int comparisonMethod = Imgproc.TM_CCOEFF_NORMED;
	
	public IdentifyImage() {
		this.imageCompareSet = new Vector<Image>();
		LoadCompareSet();
	}
	
	private void LoadCompareSet() {
		imageCompareSet.add(new Image("c:\\stick-figure1.jpg", "Tall Stick Person"));
		imageCompareSet.add(new Image("c:\\stick-figure2.jpg", "Wide Stick Person"));
		/*
		 * Add lots of images however we decide to do it. Could be by reading a file
		 * of image locations & descriptions, or pulling the info from a database,
		 * or just listing a bunch here (for smaller scale tests)
		 */
	}
	private float compareImagePair(Image img1, Image img2) {
		float bestMatch = (float) 0.0;
		int result_cols = img1.getImageCols() - img2.getImageCols() + 1;
		if (result_cols < 1)
			result_cols = 1;
		int result_rows = img1.getImageRows() - img2.getImageRows() + 1;
		if (result_rows < 1)
			result_rows = 1;
		Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
		System.out.println("Comparitor is " + result.size() + ": " + result_cols + " x " + result_rows);
		Imgproc.matchTemplate(img1.getImageDescriptor(), img2.getImageDescriptor(), result, comparisonMethod);
		System.out.println("Comparitor2 is " + result.size());
		for (int i = 0; i < result_rows; i++)
			for (int j = 0; j < result_cols; j++) 
			  if(result.get(i, j)[0] > bestMatch) {
				  bestMatch = (float) result.get(i, j)[0];
			  }
		return bestMatch;
	}
	
	public String IdImage(String imgSrc) {
		Image img = new Image(imgSrc);
		return this.IdImage(img);
	}
	public String IdImage(Image img) {
		ListIterator<Image> iter = imageCompareSet.listIterator();
		float bestMatchRate = (float) 0.0;
		String bestMatchDesc = "Unknown";
		while (iter.hasNext()) {
			Image tmpImg = iter.next();
			float thisMatchRate = compareImagePair(img, tmpImg);
			if (thisMatchRate > bestMatchRate) {
				//Found a new best batch to record
				bestMatchRate = thisMatchRate;
				bestMatchDesc = tmpImg.getImageDesc();
				System.out.println("Found a new best match: " + bestMatchDesc + " (" + bestMatchRate + ")");
			}
		}
		return bestMatchDesc;
	}
}
