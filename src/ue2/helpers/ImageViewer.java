package ue2.helpers;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.image.RenderedImage;
import java.io.StreamCorruptedException;

import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

import interfaces.Readable;

public class ImageViewer {

	public static void show (PlanarImage image, String filter){
		String title = filter;
		
		// Create a frame for display.
		 JFrame frame = new JFrame(title);
		 
		 Container contentPane = frame.getContentPane();
		 contentPane.setLayout(new BorderLayout());
		 
		 // Create an instance of DisplayJAI.
		 DisplayJAI dj;
		
		dj = new DisplayJAI((RenderedImage) image);
		contentPane.add(new JScrollPane(dj),BorderLayout.CENTER);

		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setSize(500,400); // adjust the frame size  width/height.
		 frame.setVisible(true); // show the frame.
	}
}
