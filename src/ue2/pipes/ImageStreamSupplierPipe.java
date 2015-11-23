package ue2.pipes;

import java.io.StreamCorruptedException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import interfaces.Readable;
import ue2.helpers.ImageSaver;

public class ImageStreamSupplierPipe implements Readable<PlanarImage> {

	private String _filePath;
	private boolean isDone = false;

	public ImageStreamSupplierPipe(String filePath) {
		_filePath = filePath;
	}

	@Override
	public PlanarImage read() throws StreamCorruptedException {
		if (!isDone) {
			isDone = true;

			PlanarImage image = JAI.create("fileload", _filePath);
			return image;
		}
		
		return null;
	}
}
