import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {
	
	public static void main(String[] args) {
		new FaceDetector();
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		VideoCapture camera = new VideoCapture(0);
		
		if(!camera.isOpened())
			System.out.println("Error: Initializing camera failed!");
		else
		{
			CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
			
			if(faceDetector.empty()) {
				System.out.println("\nError: faceDetector");
				return;
			} else {
				System.out.println("\nFaceCascade loaded!!!");
			}

			Mat frame = new Mat();
			
			while(true) {
				if(camera.read(frame)) {
					System.out.println("Frame Obtained");
					System.out.println("Captured Frame Width " +
					frame.width() + " Height " + frame.height());
					Imgcodecs.imwrite("D:\\Users\\quenaz\\Desktop\\camera.jpg", frame);
					System.out.println("OK");
					
					System.out.println("\nRunning FaceDetector");
					
					MatOfRect faceDetections = new MatOfRect();
					faceDetector.detectMultiScale(frame, faceDetections);
			        
					System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
					
			        if( faceDetections.toArray().length != 0 ) {
				        
			        	for (Rect rect : faceDetections.toArray()) {
				            Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
				                    new Scalar(0, 255, 0));
				        }
				        
				        String filename = "output.jpg";
				        System.out.println(String.format("Writing %s", filename));
				        Imgcodecs.imwrite(filename, frame);
			        }
			        
			        System.out.println("\nEND!!!");
					
					break;
				}
			}
		}
		camera.release();
	}

}
