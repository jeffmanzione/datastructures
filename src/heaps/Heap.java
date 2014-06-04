package heaps;


public interface Heap<P extends Comparable<P>, E> {
	public abstract void insert(P priority, E element);
	public abstract E delete();
	public abstract boolean delete(E element);
	public int size();
}
