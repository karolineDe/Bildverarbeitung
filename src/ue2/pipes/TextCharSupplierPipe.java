package ue2.pipes;

import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import interfaces.Readable;
import ue2.entities.Char;

public class TextCharSupplierPipe implements Readable<Char>{

	private InputStream _is;
	
	public TextCharSupplierPipe(String sourceFilePath) {
		
		Path _sourceFilePath = null;
		
		if(sourceFilePath != null){
			_sourceFilePath = Paths.get(sourceFilePath);
		}
		
		try{
			_is = Files.newInputStream(_sourceFilePath);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Char read() throws StreamCorruptedException {
		
		if (_is != null) {

            try {

                int r = _is.read();
                if (r != -1) {
                    Char c = new Char();
                    c.setValue((char) r);
                    return c;
                } else {
                    _is.close();
                    _is = null;

                    return new Char(); // stop signal
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
	}
}
