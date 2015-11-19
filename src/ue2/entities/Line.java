package ue2.entities;



public class Line extends GenericToken<Integer, String>{
   
	public Line(){
		
	}
	
	public Line(Integer lineCounter) {
        super(lineCounter);
    }

    public Line(Integer lineCounter, String value) {
        super(lineCounter, value);
    }

	
}
