package ue2.pipes;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import interfaces.Writeable;
import ue2.entities.Line;

public class OutputFileSinkFormatted implements Writeable<Line>{

	    private Path _outputFilePath;
	    private BufferedWriter _bw;

	    public OutputFileSinkFormatted(String outputFilePath) {

	        if(outputFilePath != null) {
	            _outputFilePath = Paths.get(outputFilePath);
	        }

	        try {
	            _bw = Files.newBufferedWriter(_outputFilePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void write(Line stringLine) throws StreamCorruptedException {
	        if(_bw != null) {

	            try {

	                if (stringLine != null && stringLine.getValue() != null) {

	                    _bw.write(stringLine.getValue() + "\n");
	                    _bw.flush();

	                } else {
	                    _bw.close();
	                    _bw = null;

	                    System.out.println("Output file \"" + _outputFilePath.getFileName() + "\" was updated.");
	                }

	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
