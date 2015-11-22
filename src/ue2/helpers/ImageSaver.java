package ue2.helpers;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

public class ImageSaver {

	private static final String FILEEXTENSION = "JPEG";

	public static void save(PlanarImage image, String filter) {
		
		String filePath = filter + "." + FILEEXTENSION;

		JAI.create("filestore", image, filePath, FILEEXTENSION);
	}
}
