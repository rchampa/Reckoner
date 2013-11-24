package es.rczone.dariuslib;
public abstract class Scale {
    public abstract int map(double mathCoord);
    public abstract double unmap(int screenCoord);
}
