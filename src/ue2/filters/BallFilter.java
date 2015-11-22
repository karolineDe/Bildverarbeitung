package ue2.filters;

/** please do not use strg+shift+f in eclipse because of kernel values format **/

import java.security.InvalidParameterException;

import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;

import interfaces.Readable;
import interfaces.Writeable;

public class BallFilter extends DataTransformationFilter<PlanarImage>{

	private static final int _kernelSize = 10;
	
	/** please do not use strg+shift+f in eclipse because of kernel values format **/
	/* should be a circle */
	private static final float[] _kernelValues = new float[]{
			0,0,0,0,1,1,0,0,0,0,
			0,0,1,1,1,1,1,1,0,0,
			0,1,1,1,1,1,1,1,1,0,
			0,1,1,1,1,1,1,1,1,0,
			1,1,1,1,1,1,1,1,1,1,
			1,1,1,1,1,1,1,1,1,1,
			0,1,1,1,1,1,1,1,1,0,
			0,1,1,1,1,1,1,1,1,0,
			0,0,1,1,1,1,1,1,0,0,
			0,0,0,0,1,1,0,0,0,0
	};
	
	private static final KernelJAI _kernel = new KernelJAI(_kernelSize, _kernelSize, _kernelValues);
	
	
	public BallFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output) throws InvalidParameterException {
		super(input, output);
	}

	@Override
	protected void process(PlanarImage image) {
				//TODO implement process
	}

}
