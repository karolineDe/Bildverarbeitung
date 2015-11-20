package ue2.filters;

import javax.media.jai.PlanarImage;

import interfaces.Readable;
import interfaces.Writeable;
import ue2.helpers.ImageSaver;
import ue2.helpers.ImageViewer;

/**
 * This Filter reads a Image from file and displays on screen
 * @author Karoline
 */
public class ViewImageFilter  extends DataTransformationFilter<PlanarImage>{

	Readable<PlanarImage> _image;
	
	public ViewImageFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output){
		super(input, output);
		_image = input;
	}

	public ViewImageFilter(Readable<PlanarImage> input) {
		super(input);
		_image = input;
	}

	@Override
	protected void process(PlanarImage entity) {
		
		String filter = "ViewImageFilter";
		
		ImageSaver.save(entity, filter);
//		ImageViewer.show(entity, filter);
		 
	}
}
