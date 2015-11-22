package ue2.filters;

import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import interfaces.Writeable;
import ue2.helpers.ImageSaver;
import ue2.helpers.ImageViewer;
import interfaces.Readable;

public class ThresholdFilter extends DataTransformationFilter<PlanarImage> {

    private final double[][] _parameters;

    public ThresholdFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output, double[]... parameters)
    throws InvalidParameterException {
        super(input, output);
        _parameters = parameters;
    }

    public ThresholdFilter(Writeable<PlanarImage> output, double[][] parameters)
    throws InvalidParameterException {
        super(output);
        _parameters = parameters;
    }

    public ThresholdFilter(Readable<PlanarImage> input, double[][] parameters)
    throws InvalidParameterException {
        super(input);
        _parameters = parameters;
    }

    @Override
    protected void process(PlanarImage image) {
    	
        ParameterBlock pb = prepareParameterBlock(image, _parameters);
        
        image = JAI.create("threshold", pb);
        ImageSaver.save(image, "ThresholdFilter");
    }

	private ParameterBlock prepareParameterBlock(PlanarImage image, double[][] parameters) {

		ParameterBlock parameterBlock = new ParameterBlock();

		for (double[] param : parameters) {
			parameterBlock.add(param);
		}

		parameterBlock.addSource(image);

		return parameterBlock;
	}
    
    public PlanarImage getThImage(PlanarImage image){
    	ParameterBlock pb = prepareParameterBlock(image, _parameters);
    	return JAI.create("threshold", pb);
    }
}
