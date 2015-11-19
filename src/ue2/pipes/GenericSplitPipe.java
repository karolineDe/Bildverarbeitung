package ue2.pipes;

import interfaces.Writeable;
import ue2.pipes.BufferedSyncPipe;

import java.io.StreamCorruptedException;
import java.util.LinkedList;


public class GenericSplitPipe<T> extends BufferedSyncPipe<T> {
    private final Writeable<T> _output;

    public GenericSplitPipe(int maxBufferSize, Writeable<T> output) {
        super(maxBufferSize);
        _output = output;
    }

    @Override
    public synchronized T read() throws StreamCorruptedException {
        T t = super.read();

        //forwarding to observer pipe.
        _output.write(t);

        //forwarding to real destination.
        return t;
    }
}
