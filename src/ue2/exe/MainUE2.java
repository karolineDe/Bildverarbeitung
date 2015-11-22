package ue2.exe;

import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

import javax.media.jai.PlanarImage;

import ue2.helpers.Coordinate;
import ue2.filters.BallFilter;
import ue2.filters.CalcCentroidsFilter;
import ue2.filters.MedianFilter;
import ue2.filters.RegionOfInterestFilter;
import ue2.filters.ThresholdFilter;
import ue2.filters.ViewImageFilter;
import ue2.helpers.ImageSaver;
import ue2.helpers.ImageViewer;
import ue2.pipes.BufferedSyncPipe;
import ue2.pipes.ImageStreamSupplierPipe;

public class MainUE2 {

	public static void main(String[] args) {
		
		// TODO: Zeitmessung der verschiedenen Tasks		
		long timeTaskAPush = System.currentTimeMillis();
		//runTaskAPush();
		System.out.println("Zeit Task a (push): " + (System.currentTimeMillis()-timeTaskAPush)+"ms");
		
		long timeTaskAPull = System.currentTimeMillis();	
		//runTaskAPull();
		System.out.println("Zeit Task a (pull): " + (System.currentTimeMillis()-timeTaskAPull)+"ms");

		long timeTaskB = System.currentTimeMillis();
		 runTaskB();
		System.out.println("Zeit Task b: " + (System.currentTimeMillis()-timeTaskB)+"ms");

	}

	private static void runTaskAPush() {

		PlanarImage image = null;

		/* Startpunkt der ROI */
		Coordinate roiOrigin = new Coordinate(40, 50);
		
		List<Coordinate> coordinates = new LinkedList<>();
		/** Fill list of Coordinates **/
//		coordinates.add(new Coordinate(7,77));
		coordinates.add(new Coordinate(72, 77));
		coordinates.add(new Coordinate(137,81));
		coordinates.add(new Coordinate(202, 81));
		coordinates.add(new Coordinate(266, 80));
		coordinates.add(new Coordinate(330, 82));
		coordinates.add(new Coordinate(396, 81));

		/*
		 * Rectangle, das relevanten Bereich umschliesst: x= 40, y= 50, width=
		 * 390, height= 60
		 */
		Rectangle roiRectangle = new Rectangle(40, 50, 390, 60);

		/** source: image supplier pipe **/
		ImageStreamSupplierPipe imageStreamSupplierPipe = new ImageStreamSupplierPipe("loetstellen.jpg");
		BufferedSyncPipe<PlanarImage> endOfViewPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> thresholdPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> searchMedianPipe = new BufferedSyncPipe<>(1);
		

		/*********** 1. das Bild laden und visualisieren */
		try {
			ViewImageFilter viewImageFilter = new ViewImageFilter(imageStreamSupplierPipe);
			String filter = "ViewImageFilter";
			image = viewImageFilter.read();
			// ImageSaver.save(_image, filter);
			ImageViewer.show(image, filter);
		} catch (InvalidParameterException | StreamCorruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*********** 2. eine ROI (region of interest1) definieren */
		/** get ROI **/
		String roiFilter = "RegionOfInterestFilter";
		image = PlanarImage
				.wrapRenderedImage((RenderedImage) image.getAsBufferedImage(roiRectangle, image.getColorModel()));
		ImageSaver.save(image, roiFilter);
		ImageViewer.show(image, roiFilter);
		

		/***********
		 * 3. einen Operator zur Bildsegmentierung auswählen: Threshold Operator
		 * 3a. Parameterwerte des Operators wählen
		 */
		/* color values: lower range, upper range, end result */
		double[][] thresholdParameters = { new double[] { 0 }, new double[] { 28 }, new double[] { 255 } };

		ThresholdFilter thresholdFilter = new ThresholdFilter(thresholdPipe, searchMedianPipe, thresholdParameters);
		PlanarImage thImage = thresholdFilter.getThImage(image);
		ImageViewer.show(thImage, "ThresholdFilter");

		/***********
		 * 4. beseitige lokale Störungen (z.B. schwarzer Fleck im 2. Anschluss
		 * von rechts) 
		 * 4a.wähle Parameter des Filters: Größe der Maske zur
		 * Medianberechnung
		 */
		Integer maskSize = 6;
		
		MedianFilter medianFilter = new MedianFilter(searchMedianPipe, new BufferedSyncPipe<PlanarImage>(1), maskSize);
		PlanarImage medianImage = medianFilter.getMedianImage(thImage);
		ImageSaver.save(medianImage, "MedianFilter");
		ImageViewer.show(medianImage, "MedianFilter");

		/***********
		 * 5. nun bleiben noch die Kabelanschlüsse der „balls“; man nutzt die
		 * Kreisform der Balls aus und benutzt einen Opening-Operator mit
		 * kreisförmiger Maske (in JAI: "erode" und „dilate“):
		 *
		 * 5a. wähle Parameter des Operators: Größe der Maske (Alternative:
		 * laufe mehrmals mit dem Operator über das Bild)
		 */
		BallFilter ballFilter = new BallFilter(searchMedianPipe, endOfViewPipe);
		PlanarImage ballImage = ballFilter.getBallImage(medianImage);
		ImageSaver.save(ballImage, "BallFilter");
		ImageViewer.show(ballImage, "BallFilter");
		
		/**********
		 * 6.Resultatbild (ein Bild, in dem nur die „balls“ als Scheiben zu
		 * sehen sind.)in einer Datei abspeichern, aber nicht als Sink
		 * realisieren, sondern nach der Abspeicherung das unveränderte Bild
		 * weiterleiten.
		 */

		
		/**********
		 * 7.Scheiben zählen, ihre Zentren (Centroid, siehe unten) bestimmen,
		 * und prüfen, ob sie im Toleranzbereich der Qualitätskontrolle liegen.
		 * Letztere Information wird bei Erzeugung des Filters im "main" als
		 * Initialisierungsdaten an das Filterobjekt übergeben. Resultat in eine
		 * txt Datei schreiben.
		 */

	}

	private static void runTaskAPull() {

		// TODO:
		PlanarImage thImage = null;
		PlanarImage medianImage = null;
		PlanarImage image = null;
		
		/* Startpunkt der ROI */
		Coordinate roiOrigin = new Coordinate(40, 50);
		
		List<Coordinate> coordinates = new LinkedList<>();
		/** Fill list of Coordinates **/
		coordinates.add(new Coordinate(7,77));
		coordinates.add(new Coordinate(72, 77));
		coordinates.add(new Coordinate(137,81));
		coordinates.add(new Coordinate(202, 81));
		coordinates.add(new Coordinate(266, 80));
		coordinates.add(new Coordinate(330, 82));
		coordinates.add(new Coordinate(396, 81));
		
		/** source: image supplier pipe **/
		ImageStreamSupplierPipe imageStreamSupplierPipe = new ImageStreamSupplierPipe("loetstellen.jpg");
		BufferedSyncPipe<PlanarImage> endOfViewPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> thresholdPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> searchMedianPipe = new BufferedSyncPipe<>(1);
		
		/*
		 * Rectangle, das relevanten Bereich umschliesst: x= 40, y= 50, width=
		 * 390, height= 60
		 */
		Rectangle roiRectangle = new Rectangle(40, 50, 390, 60);
		
		/** Write result to file **/
		
		
		/** Calculate Centeroids **/
		
		
		/** Balls **/
		BallFilter ballFilter = new BallFilter(searchMedianPipe, endOfViewPipe);
		PlanarImage ballImage = ballFilter.getBallImage(medianImage);
		ImageSaver.save(ballImage, "BallFilter");
		ImageViewer.show(ballImage, "BallFilter");
		
		/** Median Filter **/
		Integer maskSize = 6;
		
		MedianFilter medianFilter = new MedianFilter(searchMedianPipe, new BufferedSyncPipe<PlanarImage>(1), maskSize);
		medianImage = medianFilter.getMedianImage(thImage);
		ImageSaver.save(medianImage, "MedianFilter");
		ImageViewer.show(medianImage, "MedianFilter");

		
		/** Threshold Filter **/
		/* color values: lower range, upper range, end result */
		double[][] thresholdParameters = { new double[] { 0 }, new double[] { 28 }, new double[] { 255 } };

		ThresholdFilter thresholdFilter = new ThresholdFilter(thresholdPipe, searchMedianPipe, thresholdParameters);
		thImage = thresholdFilter.getThImage(image);
		ImageViewer.show(thImage, "ThresholdFilter");
		
		/** ROI **/
		String roiFilter = "RegionOfInterestFilter";
		image = PlanarImage
				.wrapRenderedImage((RenderedImage) image.getAsBufferedImage(roiRectangle, image.getColorModel()));
		ImageSaver.save(image, roiFilter);
		ImageViewer.show(image, roiFilter);

		/** load Data **/
		try {
			ViewImageFilter viewImageFilter = new ViewImageFilter(imageStreamSupplierPipe);
			String filter = "ViewImageFilter";
			image = viewImageFilter.read();
			// ImageSaver.save(_image, filter);
			ImageViewer.show(image, filter);
		} catch (InvalidParameterException | StreamCorruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Threaded Task
	 */
	private static void runTaskB() {
		
		/* Startpunkt der ROI */
		Coordinate roiOrigin = new Coordinate(40, 50);
		
		List<Coordinate> coordinates = new LinkedList<>();
		/** Fill list of Coordinates **/
		coordinates.add(new Coordinate(7,77));
		coordinates.add(new Coordinate(72, 77));
		coordinates.add(new Coordinate(137,81));
		coordinates.add(new Coordinate(202, 81));
		coordinates.add(new Coordinate(266, 80));
		coordinates.add(new Coordinate(330, 82));
		coordinates.add(new Coordinate(396, 81));
		
		/** Pipes **/
		/** source: image supplier pipe **/
		ImageStreamSupplierPipe imageStreamSupplierPipe = new ImageStreamSupplierPipe("loetstellen.jpg");
		BufferedSyncPipe<PlanarImage> endOfViewPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> thresholdPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> searchMedianPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> ballPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> resultPipe = new BufferedSyncPipe<>(1);
		

		/*********** 1. das Bild laden und speichern */
		new Thread(
				new ViewImageFilter(imageStreamSupplierPipe, endOfViewPipe)
		).start();

		try {
			ImageViewer.show(imageStreamSupplierPipe.read(), "ViewImageFilter");
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*********** 2. eine ROI (region of interest) definieren */
		/*
		 * Rectangle, das relevanten Bereich umschliesst: x= 40, y= 50, width=
		 * 390, height= 60
		 */
		Rectangle roiRectangle = new Rectangle(40, 50, 390, 60);

		new Thread(
				new RegionOfInterestFilter(imageStreamSupplierPipe, thresholdPipe, roiRectangle)
		).start();

		/************* 3. Bildsegmentierung Threshold Operator ************/
		double[][] thresholdParameters = { new double[] { 0 }, new double[] { 28 }, new double[] { 255 } };
		new Thread(
				new ThresholdFilter(thresholdPipe, searchMedianPipe, thresholdParameters)
		).start();
		
		/***********
		 * 4. beseitige lokale Störungen (z.B. schwarzer Fleck im 2. Anschluss
		 * von rechts) 
		 * 4a.wähle Parameter des Filters: Größe der Maske zur
		 * Medianberechnung
		 */
		Integer maskSize = 6;
		
		new Thread(
				new MedianFilter(searchMedianPipe, ballPipe, maskSize)
		).start();
		
		/***********
		 * 5. nun bleiben noch die Kabelanschlüsse der „balls“; man nutzt die
		 * Kreisform der Balls aus und benutzt einen Opening-Operator mit
		 * kreisförmiger Maske (in JAI: "erode" und „dilate“):
		 *
		 * 5a. wähle Parameter des Operators: Größe der Maske (Alternative:
		 * laufe mehrmals mit dem Operator über das Bild)
		 */
		new Thread(
				new BallFilter(ballPipe, resultPipe)
		).start();
		
		
		/**********
		 * 6.Resultatbild (ein Bild, in dem nur die „balls“ als Scheiben zu
		 * sehen sind.)in einer Datei abspeichern, aber nicht als Sink
		 * realisieren, sondern nach der Abspeicherung das unveränderte Bild
		 * weiterleiten.
		 */
		
		

		/**********
		 * 7.Scheiben zählen, ihre Zentren (Centroid, siehe unten) bestimmen,
		 * und prüfen, ob sie im Toleranzbereich der Qualitätskontrolle liegen.
		 * Letztere Information wird bei Erzeugung des Filters im "main" als
		 * Initialisierungsdaten an das Filterobjekt übergeben. Resultat in eine
		 * txt Datei schreiben.
		 */
		
		 
	}
}
