package ue2.pipes;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import interfaces.Writeable;


public class OutputFileSink<T> implements Writeable<T>{

	private Path _outputFilePath;
	private BufferedWriter _bw;
	
	public OutputFileSink(String outputfilepath){		
		
		_outputFilePath = Paths.get(outputfilepath);
		
		try{
			_bw = Files.newBufferedWriter(_outputFilePath);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void write(T value) throws StreamCorruptedException {
		// TODO Auto-generated method stub
		
	}
}
