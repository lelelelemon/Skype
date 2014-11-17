package com.microsoft.research.rainbow;

import org.alljoyn.bus.BusException;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;

public class TestUI {

	final int INTERVAL = 200;// /you may use interval
	IplImage image;
	CanvasFrame canvas = new CanvasFrame("Web Cam");
	final SampleInterface camera;

	public TestUI(SampleInterface camera) {
		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.camera = camera;
	}

	public void run() throws BusException {
		camera.start();
		IplImage img;
		while (true) {
			img = camera.grab().asImage();
			if (img != null) {
				// new CameraInterface.BitmapDataObject(img)

				// show image on window
				canvas.showImage(img);
			}
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
			}
		}
	}
}
