package ue2.filters;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.image.RenderedImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

import ue2.filters.DataTransformationFilter;
import ue2.helpers.ImageSaver;
import ue2.helpers.ImageViewer;
import ue2.pipes.ImageStreamSupplierPipe;
import interfaces.Readable;
import interfaces.Writeable;

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
		
		
		// Create a frame for display.
		 JFrame frame = new JFrame("ViewImageFilter");
		 
		 Container contentPane = frame.getContentPane();
		 contentPane.setLayout(new BorderLayout());
		 
		 // Create an instance of DisplayJAI.
		 DisplayJAI dj = new DisplayJAI((RenderedImage) entity);
		 
		 contentPane.add(new JScrollPane(dj),BorderLayout.CENTER);
		 
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setSize(500,400); // adjust the frame size  width/height.
		 frame.setVisible(true); // show the frame.
		 
	}
}
