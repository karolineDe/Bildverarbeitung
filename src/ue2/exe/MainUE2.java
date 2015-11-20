package ue2.exe;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import javax.media.jai.PlanarImage;

import ue2.filters.RegionOfInterrestFilter;
import ue2.filters.ViewImageFilter;
import ue2.helpers.ImageSaver;
import ue2.pipes.BufferedSyncPipe;
import ue2.pipes.ImageStreamSupplierPipe;

public class MainUE2 {
	
	public static void main(String[] args) {
	
		//TODO: Zeitmessung der verschiedenen Tasks
		
		runTaskAPull();
		
	//	runTaskAPush();
		
		runTaskB();
		
	}
	
	private static void runTaskAPull(){
		
		/** source: image supplier pipe **/
		ImageStreamSupplierPipe imageStreamSupplierPipe0 = new ImageStreamSupplierPipe("loetstellen.jpg");
		
		/** point to save ROI origin **/
		Point originOfROI = new Point(40,50);
		
        /*********** 1. das Bild laden und visualisieren */
		ViewImageFilter viewImageFilter = null;
		try {
			viewImageFilter = new ViewImageFilter(imageStreamSupplierPipe0);
			
		} catch (InvalidParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
        /*********** 2. eine ROI (region of interest1) definieren */
        /* Rectangle, das relevanten Bereich umschliesst: 
		 * x= 40, y= 50, width= 390, height= 60 */
		
		Rectangle rectangle = new Rectangle(40,50,390,60);
		
		try {
			
			PlanarImage image = viewImageFilter.read();
			ImageSaver.save(image, "ViewImageFilter1");
			
			/**get ROI**/
			image = PlanarImage.wrapRenderedImage((RenderedImage)image.getAsBufferedImage(rectangle, image.getColorModel()));
			ImageSaver.save(image, "ViewImageFilter2");
			
		
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//		
//		PlanarImage image = viewImageFilter.read();
//		ImageSaver.save(image, "ViewImageFilter1");
//		
//		
//		
//	
//	} catch (StreamCorruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}

    /* Achtung: Pixel oben links ausgeschnittenen Bildes hat wieder die Koordinaten 0,0. 
     * Sie müssen noch etwas tun, damit Sie die Position dieses Pixels im Originalbild mitspeichern 
     * im Bild (der letzte Filter braucht das!)
    Option für den Benutzer: zeige das Rechteck in weiss mit dem Ausgangsbild */

    /*********** 3. einen Operator zur Bildsegmentierung auswählen: Threshold Operator /*

    /*********** 3a. Parameterwerte des Operators wählen */

    /*********** 4. beseitige lokale Störungen (z.B. schwarzer Fleck im 2. Anschluss von rechts) */

    /*********** 4a.wähle Parameter des Filters: Größe der Maske zur Medianberechnung */

    /*********** 5. nun bleiben noch die Kabelanschlüsse der „balls“; man nutzt die Kreisform der Balls aus und
    benutzt einen Opening-Operator mit kreisförmiger Maske (in JAI: "erode" und „dilate“): */

    /**********  5a. wähle Parameter des Operators: Größe der Maske (Alternative: laufe mehrmals mit dem Operator
    über das Bild) */

    /********** 6.Resultatbild (ein Bild, in dem nur die „balls“ als Scheiben zu sehen sind.)in einer Datei abspeichern,
    aber nicht als Sink realisieren, sondern nach der Abspeicherung das unveränderte Bild weiterleiten. */

    /********** 7.ie Scheiben zählen, ihre Zentren (Centroid, siehe unten) bestimmen, und prüfen, ob sie im
     Toleranzbereich der Qualitätskontrolle liegen. Letztere Information wird bei Erzeugung des Filters im
     "main" als Initialisierungsdaten an das Filterobjekt übergeben. Resultat in eine Datei schreiben. */

    /* THREADMODEL  */
//    Thread aThread = new Thread({
//            PipedInputStream pis = new PipedInputStream();
//
//    }
		
		
	}
	
	private static void runTaskAPush(){
		
		//TODO:
		
	}
	
	/**
	 * Threaded Task
	 */
	private static void runTaskB(){
		
		/**Pipes**/
		/** source: image supplier pipe **/
		ImageStreamSupplierPipe imageStreamSupplierPipe0 = new ImageStreamSupplierPipe("loetstellen.jpg");
		BufferedSyncPipe<PlanarImage> endOfViewPipe = new BufferedSyncPipe<>(1);
		BufferedSyncPipe<PlanarImage> threshholdPipe = new BufferedSyncPipe<>(1);
		
		/** point to save ROI origin **/
		Point originOfROI = new Point(40,50);
		
        /*********** 1. das Bild laden und visualisieren */	
		new Thread(
		new ViewImageFilter(imageStreamSupplierPipe0, endOfViewPipe)
		).start();
				
        /*********** 2. eine ROI (region of interest1) definieren */
        /* Rectangle, das relevanten Bereich umschliesst: 
		 * x= 40, y= 50, width= 390, height= 60 */
		Rectangle rectangle = new Rectangle(40,50,390,60);				
		new RegionOfInterrestFilter(endOfViewPipe, threshholdPipe, rectangle);
		
	}
}
