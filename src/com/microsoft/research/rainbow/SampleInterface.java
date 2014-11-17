package com.microsoft.research.rainbow;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;
import org.alljoyn.bus.annotation.Position;
import org.bytedeco.javacpp.opencv_core.IplImage;

@BusInterface (name = "com.microsoft.research.rainbow.camera")
public interface SampleInterface {
	
	public class BitmapDataObject {
		@Position(0)
	    public byte[] imageByteArray;
		
		public BitmapDataObject() {
			
		}
		
		public BitmapDataObject(IplImage img) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(img.getBufferedImage(), "jpg", baos);
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageByteArray = baos.toByteArray();
		}
		
		public IplImage asImage() {
			InputStream in = new ByteArrayInputStream(imageByteArray);
			BufferedImage buffered;
			try {
				buffered = ImageIO.read(in);
				return IplImage.createFrom(buffered);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}

	@BusMethod
	public void start() throws BusException;
	
	@BusMethod
	public BitmapDataObject grab() throws BusException;

}
