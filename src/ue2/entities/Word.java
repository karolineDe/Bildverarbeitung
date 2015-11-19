package ue2.entities;

public class Word extends GenericToken<Integer, String> {
	
	public Word(){
        super();
    }
	
    public Word(Integer lineIndex){
        super(lineIndex);
    }

    public Word(Integer lineIndex, String value) {
        super(lineIndex, value);
    }
}
