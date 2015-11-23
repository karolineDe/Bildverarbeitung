package ue2.filters;

import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import java.security.InvalidParameterException;

import javax.media.jai.PlanarImage;

import interfaces.Writeable;
import ue2.helpers.ImageSaver;
import ue2.helpers.ImageViewer;
import interfaces.Readable;

public class RegionOfInterestFilter extends MyAbstractFilter<PlanarImage> {

	private final Rectangle _rectangle;

	public RegionOfInterestFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, Rectangle rectangle)
			throws InvalidParameterException {
		super(input, output);
		_rectangle = rectangle;
	}

	public RegionOfInterestFilter(Readable<PlanarImage> input, Rectangle rectangle) throws InvalidParameterException {
		super(input);
		_rectangle = rectangle;
	}

	public RegionOfInterestFilter(Writeable<PlanarImage> output, Rectangle rectangle) throws InvalidParameterException {
		super(output);
		_rectangle = rectangle;
	}

	@Override
	protected PlanarImage process(PlanarImage image) {

		/** get ROI **/
		image = PlanarImage
				.wrapRenderedImage((RenderedImage) image.getAsBufferedImage(_rectangle, image.getColorModel()));
		ImageSaver.save(image, "RegionOfInterestFilter");
		ImageViewer.show(image, "RegionOfInterestFilter");
		
		return image;
	}
}
