package ue2.filters;

import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;

import interfaces.Readable;
import interfaces.Writeable;
import ue2.helpers.ImageSaver;

public class MedianFilter extends DataTransformationFilter<PlanarImage> {

	private Integer _maskSize;
	private MedianFilterShape _maskShape;

	public MedianFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, Integer maskSize)
			throws InvalidParameterException {
		super(input, output);
		_maskSize = maskSize;
		_maskShape = MedianFilterDescriptor.MEDIAN_MASK_SQUARE;
	}

	public MedianFilter(Readable<PlanarImage> input, Integer maskSize) throws InvalidParameterException {
		super(input);
		_maskSize = maskSize;
		_maskShape = MedianFilterDescriptor.MEDIAN_MASK_SQUARE;
	}

	@Override
	protected void process(PlanarImage image) {

		ParameterBlock pb = prepareParameterBlock(image);
		PlanarImage planarImage = JAI.create("medianfilter", pb);
		ImageSaver.save(planarImage, "MedianFilter");
	}

	private ParameterBlock prepareParameterBlock(PlanarImage image) {

		/* source, mask shape, mask size, render hint */
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image);
		pb.add(_maskShape);
		pb.add(_maskSize);

		return pb;
	}

	public PlanarImage getMedianImage(PlanarImage image) {

		ParameterBlock pb = prepareParameterBlock(image);
		PlanarImage planarImage = JAI.create("medianfilter", pb);

		return planarImage;
	}
}
