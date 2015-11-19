package ue2.pipes;

import java.io.StreamCorruptedException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import interfaces.Readable;

public class ImageStreamSupplierPipe implements Readable<PlanarImage> {

	private String _filePath;

	public ImageStreamSupplierPipe(String filePath) {
		_filePath = filePath;
	}

	@Override
	public PlanarImage read() throws StreamCorruptedException {

		PlanarImage image = JAI.create("fileload", _filePath);
		return image;
	}
}
