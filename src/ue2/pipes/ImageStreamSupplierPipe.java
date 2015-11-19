package ue2.pipes;

import java.io.StreamCorruptedException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import interfaces.Readable;

public class ImageStreamSupplierPipe implements Readable<PlanarImage> {

	@Override
	public PlanarImage read() throws StreamCorruptedException {
		
		PlanarImage image = JAI.create("fileload", "C:\\FHV\\Semester 5\\Systemarchitekturen\\Uebungen\\Uebung2\\loetstellen.jpg");
		return image;
	}
}
