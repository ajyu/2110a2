/*
 * A2: 20-Sept-2011
 * Ansha Yu | ay226 | 2392034
 */

public class DoubleList implements LinkedList {
	
	public static void main(String[] args){
		DoubleList dl= new DoubleList();
		dl.add(0.0);
		dl.clear();
		double[] d= {0.0,1.1,2.2};
		System.out.println("Add all: "+dl.addAll(d));
		System.out.println("Contains: "+dl.contains(1.1));
		//double[] a= {0.0,2.2};
		//System.out.println("Contains all: "+dl.containsAll(a));
		System.out.println("get: "+dl.get(0));
		System.out.println("indexOf: "+dl.indexOf(5));
		System.out.println("isEmpty: "+dl.isEmpty());
		
		
	}
	
	private Node HeadNode; //the first node of this DoubleList
	private int size; //size of this DoubleList
	
	// Constructor: create a new DoubleList of size 0
	public DoubleList() {
		HeadNode= null;
		size= 0;
	}
	
	@Override
	public boolean add(double element) {
		try {
			add(size(),element);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	@Override
	public void add(int index, double element) throws IndexOutOfBoundsException {
		if (index <0 || index > size()) throw new IndexOutOfBoundsException();
		Node n = getHeadNode();
		if (n == null) { //for empty DoubleLists;
			if (index==0) {
				HeadNode = new Node(element,null,null);
				if (size()<Integer.MAX_VALUE) size= size()+1;
			} else throw new IndexOutOfBoundsException();
		} else { //for non-empty DoubleLists
			for (int i=0; i<index; i=i+1) { //insertion spot
				if (i+1 == index) {
					Node newNode = new Node(element,n,n.getNext());
					n.setNext(newNode);
					if (newNode.getNext()!=null) newNode.getNext().setPrev(newNode);
					if (size()<Integer.MAX_VALUE) size= size()+1;
				} else {
					n=n.getNext();
				}
			}
		}
	}
	
	@Override
	public boolean addAll(double[] c) {
		if (c.length==0) return false;
		int i=0;
		Node n = getHeadNode();
		if (n==null) {
			HeadNode= new Node(c[0],null,null);
			n= getHeadNode();
			size=size()+1;
			i=1;
		} else	while (n.getNext()!=null) {
			n= n.getNext();
		}
		while (i<c.length) {
			n.setNext(new Node(c[i],n,null));
			if (size()<Integer.MAX_VALUE) size= size()+1;
			n= n.getNext();
			i=i+1;
		}
		return true;
	}

	@Override
	public void clear() {
		HeadNode=null;
		size=0;
	}

	@Override
	public boolean contains(double v) {
		Node n = getHeadNode();
		for (int i=0; i<size(); i=i+1) {
			if (n==null) return false;
			if (n.getDatum()==v) return true;
			else n=n.getNext();
		}
		return false;
	}

	@Override
	public boolean containsAll(double[] c) {
		for (int i=0; i<size(); i=i+1) {
			if (!contains(c[i])) return false;
		}
		return true;
	}

	@Override
	public double get(int index) throws IndexOutOfBoundsException {
		if (index<0 || index >= size()) throw new IndexOutOfBoundsException();
		Node n = getHeadNode();
		for (int i=0; i<index; i=i+1) {
			if (n==null) throw new IndexOutOfBoundsException();
			n= n.getNext();
		}
		return n.getDatum();
	}

	@Override
	public int indexOf(double o) {
		Node n = getHeadNode();
		for (int i=0; i<size(); i=i+1) {
			if (n==null) return -1;
			if (n.getDatum()==o) return i;
			else n=n.getNext();
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size()==0;
	}

	@Override
	public double remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
		Node n= getHeadNode();
		if(n==null) throw new IndexOutOfBoundsException();
		for (int i=0; i<index; i=i+1) {
			if(n.getNext()==null) throw new IndexOutOfBoundsException();
			n= n.getNext();
		}
		if (n.getPrev()!=null) n.getPrev().setNext(n.getNext());
		if (n.getNext()!=null) {
			n.getNext().setPrev(n.getPrev());
			if (n.getPrev()==null) HeadNode= n.getNext();
		}
		size = size()-1;
		return n.getDatum();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public double[] toArray() {
		double[] d= new double[size()];
		Node n= getHeadNode();
		if (n==null) return d;
		for (int i=0; i<size(); i=i+1) {
			d[i]=n.getDatum();
		}
		return null;
	}

	@Override
	public Node getHeadNode() {
		return HeadNode;
	}

}
