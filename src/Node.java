/*
 * A2: 20-Sept 2011
 * Ansha Yu | ay226 | 2392034
 */

/**
 * This is a node for the LinkedList. Use it well.
 */

public class Node {
	private double datum; //stored data as a double
	private Node next; // the next Node of this Node
	private Node prev; // the previous Node of this Node
	
	/* Constructor: creates new node containing datum d,
	 * with previous node p and next node n
	 */
	public Node(double d, Node p, Node n) {
		datum = d;
		next = n;
		prev = p;
	}
	
	// sets datum of this node to double d
	public void setDatum(double d) {
		datum = d;
	}
	
	// gets datum of this node
	public double getDatum() {
		return datum;
	}
	
	// gets next node of this node
	public Node getNext() {
		return next;
	}
	
	// gets previous node of this node
	public Node getPrev() {
		return prev;
	}
	
	// sets the next node of this node
	public void setNext(Node n) {
		next = n;
	}
	
	// sets the previous node of this node
	public void setPrev(Node n) {
		prev = n;
	}
}
