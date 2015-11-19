package interfaces;

import java.io.StreamCorruptedException;

import ue2.entities.Line;

public interface Readable<T>  {
	public T read() throws StreamCorruptedException;
}
