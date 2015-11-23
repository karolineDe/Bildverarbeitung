package ue2.pipes;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import interfaces.Readable;
import interfaces.Writeable;
import ue2.helpers.Coordinate;


public class OutputFileSink implements Writeable<LinkedList<Coordinate>>, Runnable {

	private Readable<LinkedList<Coordinate>> _input;
	private Path _outputFilePath;
	private BufferedWriter _bw;
	
	public OutputFileSink(Readable<LinkedList<Coordinate>> input, String outputfilepath){		
		_input = input;
		_outputFilePath = Paths.get(outputfilepath);
		
		try{
			_bw = Files.newBufferedWriter(_outputFilePath);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void write(LinkedList<Coordinate> values) throws StreamCorruptedException {
		if (values != null) {
			for (Coordinate coordinate : values) {
				System.out.println(coordinate._x  + " | " +  coordinate._y);
			}
		}
	}

	@Override
	public void run() {
		
		if (_input != null) {
			LinkedList<Coordinate> inputObject;
			
			try {
				
				do {
				
					inputObject = _input.read();
					write(inputObject);
					
				} while (inputObject != null);
			
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
