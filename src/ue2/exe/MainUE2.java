package ue2.exe;

import ue2.filters.ViewImageFilter;
import ue2.pipes.ImageStreamSupplierPipe;

public class MainUE2 {
	
	public static void main(String[] args) {
	
        /*********** 1. das Bild laden und visualisieren */
		ViewImageFilter viewImageFilter = new ViewImageFilter(new ImageStreamSupplierPipe());
		
		
        /*********** 2. eine ROI (region of interest1) definieren */
        /* Achtung: man mu� nicht den komplizierten ROI Operator in JAI benutzen, sondern kann das Ganze
        ganz einfach so realisieren: */
//
//        Rectangle rectangle = new Rectangle(int x, int y, int width, int height)
//
//        PlanarImage image = PlanarImage.wrapRenderedImage((RenderedImage)image.getAsBufferedImage(rectangle,
//                image.getColorModel()));

        /* wobei image ein PlanarImage ist, und rectangle ein java.awt.Rectangle ist, das die ROI angibt relativ
        zum Bild (rectangle = new Rectangle(int x, int y, int width, int height)). Achtung: Pixel oben links
        usgeschnittenen Bildes hat wieder die Koordinaten 0,0. Sie m�ssen noch etwas tun, damit Sie die
        Position dieses Pixels im Originalbild mitspeichern im Bild (der letzte Filter braucht das!)
        Option f�r den Benutzer: zeige das Rechteck in weiss mit dem Ausgangsbild */

        /*********** 3. einen Operator zur Bildsegmentierung ausw�hlen: Threshold Operator /*

        /*********** 3a. Parameterwerte des Operators w�hlen */

        /*********** 4. beseitige lokale St�rungen (z.B. schwarzer Fleck im 2. Anschluss von rechts) */

        /*********** 4a.w�hle Parameter des Filters: Gr��e der Maske zur Medianberechnung */

        /*********** 5. nun bleiben noch die Kabelanschl�sse der �balls�; man nutzt die Kreisform der Balls aus und
        benutzt einen Opening-Operator mit kreisf�rmiger Maske (in JAI: "erode" und �dilate�): */

        /**********  5a. w�hle Parameter des Operators: Gr��e der Maske (Alternative: laufe mehrmals mit dem Operator
        �ber das Bild) */

        /********** 6.Resultatbild (ein Bild, in dem nur die �balls� als Scheiben zu sehen sind.)in einer Datei abspeichern,
        aber nicht als Sink realisieren, sondern nach der Abspeicherung das unver�nderte Bild weiterleiten. */

        /********** 7.ie Scheiben z�hlen, ihre Zentren (Centroid, siehe unten) bestimmen, und pr�fen, ob sie im
         Toleranzbereich der Qualit�tskontrolle liegen. Letztere Information wird bei Erzeugung des Filters im
         "main" als Initialisierungsdaten an das Filterobjekt �bergeben. Resultat in eine Datei schreiben. */

        /* THREADMODEL  */
//        Thread aThread = new Thread({
//                PipedInputStream pis = new PipedInputStream();
//
//        }
		
	}
}
