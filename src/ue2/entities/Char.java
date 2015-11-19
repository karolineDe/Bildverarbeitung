package ue2.entities;


public class Char extends GenericToken<Integer, Character> {
    public Char(){
        super();
    }

    public Char(Integer lineIndex){
        super(lineIndex);
    }

    public Char(Integer lineIndex, Character value) {
        super(lineIndex, value);
    }
}
