package ue2.entities;

public interface IGenericToken<T, U> {
    T getLineIndex();

    void setLineIndex(T id);

    U getValue();

    void setValue(U value);
}
