package ue2.entities;

import java.util.Comparator;

public class LineComparator implements Comparator<Line> {

	@Override
	public int compare(Line line1, Line line2) {
		
		String s1 = line1.toString();
		String s2 = line2.toString();
	    int ml = Math.min(s1.length(),s2.length());
	    
	    for(int i=0; i<ml; i++)
	    {
	      char c1 = s1.charAt(i);
	      char c2 = s2.charAt(i);
	      int r = Character.toUpperCase(c1)-Character.toUpperCase(c2);
	      if(r!=0)
	        return r;
	      boolean c1u = Character.isUpperCase(c1);
	      boolean c2u = Character.isUpperCase(c2);
	      if(c1u!=c2u)
	        return (c1u)?-1:1; // Hier vertauschen, für Kleinbuchstaben zuerst
	    }
	    return s1.length()-s2.length(); // Kürzere zuerst
	}

}
