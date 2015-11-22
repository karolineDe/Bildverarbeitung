package ue2.exe;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderableImage;
import java.awt.image.renderable.RenderableImageOp;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.ThresholdDescriptor;

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
		runTaskAPull();

		// runTaskAPush();

		// runTaskB();

		// runTaskB();

	}

	private static void runTaskAPull() {

		PlanarImage image = null;

		/* Startpunkt der ROI */
		Point roiOrigin = new Point(40, 50);

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
		 */

		/***********
		 * 4a.wähle Parameter des Filters: Größe der Maske zur Medianberechnung
		 */

		/***********
		 * 5. nun bleiben noch die Kabelanschlüsse der „balls“; man nutzt die
		 * Kreisform der Balls aus und benutzt einen Opening-Operator mit
		 * kreisförmiger Maske (in JAI: "erode" und „dilate“):
		 */

		/**********
		 * 5a. wähle Parameter des Operators: Größe der Maske (Alternative:
		 * laufe mehrmals mit dem Operator über das Bild)
		 */

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

	private static void runTaskAPush() {

		// TODO:

	}

	/**
	 * Threaded Task
	 */
	private static void runTaskB() {

		PlanarImage image;

		/** Pipes **/
		/** source: image supplier pipe **/
		ImageStreamSupplierPipe imageStreamSupplierPipe = new ImageStreamSupplierPipe("loetstellen.jpg");
		BufferedSyncPipe<PlanarImage> endOfViewPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> thresholdPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> searchMedianPipe = new BufferedSyncPipe<>(1);

		/*********** 1. das Bild laden und speichern */
		new Thread(new ViewImageFilter(imageStreamSupplierPipe, endOfViewPipe)).start();

		try {
			ImageViewer.show(imageStreamSupplierPipe.read(), "ViewImageFilter");
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*********** 2. eine ROI (region of interest) definieren */
		/* Startpunkt der ROI */
		Point roiOrigin = new Point(40, 50);
		/*
		 * Rectangle, das relevanten Bereich umschliesst: x= 40, y= 50, width=
		 * 390, height= 60
		 */
		Rectangle roiRectangle = new Rectangle(40, 50, 390, 60);

		new Thread(new RegionOfInterestFilter(imageStreamSupplierPipe, thresholdPipe, roiRectangle)).start();

		/************* 3. Bildsegmentierung Threshold Operator ************/
		double[][] thresholdParameters = { new double[] { 0 }, new double[] { 25 }, new double[] { 255 } };
		new Thread(new ThresholdFilter(thresholdPipe, searchMedianPipe, thresholdParameters)).start();
	}
}
