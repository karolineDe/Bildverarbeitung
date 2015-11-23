package ue2.filters;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import interfaces.Readable;
import interfaces.Writeable;

public abstract class MyAbstractFilter<T>  extends AbstractFilter<T, T> implements Runnable {

    public MyAbstractFilter(Readable<T> input, Writeable<T> output) throws InvalidParameterException {
		super(input, output);
	}

	public MyAbstractFilter(Readable<T> input) throws InvalidParameterException {
		super(input);
	}

	public MyAbstractFilter(Writeable<T> output) throws InvalidParameterException {
		super(output);
	}

	/**
     * read an entity from the filter. the filter will act like an passive-filter
     */
    public T read() throws StreamCorruptedException{
    	return readInput();
    }

    /**
     * write an entity into the filter. the filter will act like an passive-filter 
     * and passes the entity to the next filter, after it processed it
     * @param value
     * @throws StreamCorruptedException
     */
    public void write(T value) throws StreamCorruptedException {
        if (value != ENDING_SIGNAL) {
            writeOutput(process(value));
        } else {
            sendEndSignal();
        }
    }
    
    /**
     * runs the filter in active-mode
     */
    public void run() {
        try {
        	
        	T output;
        	
            do {

                output = read();
                if (output != null) {
                    write(output);
                }
                
            } while(output != null);

            sendEndSignal();
        } catch (StreamCorruptedException e) {
            System.out.print("Thread reports error: ");
            System.out.println(Thread.currentThread().getId() + " (" + Thread.currentThread().getName() + ")");
            e.printStackTrace();
        }
    }
    
    /**
     * fill the entity with the next given value
     * @param nextVal
     * @return true if the entity is finished
     */
    protected abstract T process(T nextVal);
}
