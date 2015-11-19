package ue2.filters;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

import ue2.filters.DataTransformationFilter;
import ue2.helpers.ImageSaver;
import interfaces.Readable;
import interfaces.Writeable;

/**
 * This Filter reads a Image from file and displays on screen
 * @author Karoline
 */
public class ViewImageFilter  extends DataTransformationFilter<PlanarImage>{

	PlanarImage _image;
	
	public ViewImageFilter(Readable<PlanarImage> input) throws InvalidParameterException, StreamCorruptedException {
		super(input);
		_image = input.read();
	}

	@Override
	protected void process(PlanarImage entity) {
		
		// Create a frame for display.
		 JFrame frame = new JFrame();
		 
		 Container contentPane = frame.getContentPane();
		 contentPane.setLayout(new BorderLayout());
		 
		 // Create an instance of DisplayJAI.
		 DisplayJAI dj = new DisplayJAI(_image);
		 
		 contentPane.add(new JScrollPane(dj),BorderLayout.CENTER);
		 
		 frame.setSize(500,400); // adjust the frame size  width/height.
		 frame.setVisible(true); // show the frame.
		 
	}
	
	
}
