package AVL;

import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.Timer;

;

public class AVLTree<T extends Comparable<T>>
{
    public static class AVLNode<T extends Comparable<T>>
    {
	private T element;
	private AVLNode<T> right = null, left = null;
	int height = 1;

	public int getBalance()
	{
	    if(left == null && right == null)
		return 0;
	    if(left == null)
		return right.getHeight();
	    if(right == null)
		return -left.getHeight();
	    return right.getHeight() - left.getHeight();
	}

	public T getElement()
	{
	    return element;
	}

	public void setElement(T element)
	{
	    this.element = element;
	}

	public AVLNode<T> getRight()
	{
	    return right;
	}

	public AVLNode<T> getLeft()
	{
	    return left;
	}

	public void setRight(AVLNode<T> right)
	{
	    this.right = right;
	    setHeight();
	}

	public void setLeft(AVLNode<T> left)
	{
	    this.left = left;
	    setHeight();
	}

	private void setHeight()
	{
	    if(left == null && right == null)
		height = 1;
	    else if(left == null)
		height = right.getHeight() + 1;
	    else if(right == null)
		height = left.getHeight() + 1;
	    else
		height = Math.max(right.getHeight(), left.getHeight()) + 1;
	}

	public int getHeight()
	{
	    return height;
	}

	/**
	 * Adds node to the tree
	 * 
	 * @param node
	 *            Node to be added
	 * @return the node that is on top after rebalancing
	 */
	public AVLNode<T> addNode(AVLNode<T> node)
	{
	    int c = element.compareTo(node.getElement());
	    if(c == 0)
		throw new IllegalArgumentException("Same key not supported");
	    if(c > 0)
	    {
		if(left == null)
		    left = node;
		else
		    this.setLeft(left.addNode(node));
	    } else
	    {
		if(right == null)
		    right = node;
		else
		    this.setRight(right.addNode(node));
	    }
	    setHeight();
	    if(getBalance() <= -2)
	    {
		int leftBalance = left.getBalance();
		if(leftBalance <= 0)
		    return this.rotateLeft();
		else
		    return this.rotateDoubleLeft();
	    }
	    if(getBalance() >= 2)
	    {
		int rightBalance = right.getBalance();
		if(rightBalance >= 0)
		    return this.rotateRight();
		else
		    return this.rotateDoubleRight();
	    }
	    return this;
	}

	public AVLNode<T> rotateLeft()
	{
	    AVLNode<T> nodeB = this.getLeft();
	    this.setLeft(nodeB.getRight());
	    nodeB.setRight(this);
	    return nodeB;
	}

	public AVLNode<T> rotateDoubleLeft()
	{
	    AVLNode<T> nodeB = this.getLeft();
	    AVLNode<T> nodeC = nodeB.getRight();
	    nodeB.setRight(nodeC.getLeft());
	    this.setLeft(nodeC.getRight());
	    nodeC.setRight(this);
	    nodeC.setLeft(nodeB);
	    return nodeC;
	}

	public AVLNode<T> rotateRight()
	{
	    AVLNode<T> nodeB = this.getRight();
	    this.setRight(nodeB.getLeft());
	    nodeB.setLeft(this);
	    return nodeB;
	}

	public AVLNode<T> rotateDoubleRight()
	{
	    AVLNode<T> nodeB = this.getRight();
	    AVLNode<T> nodeC = nodeB.getLeft();
	    this.setRight(nodeC.getLeft());
	    nodeB.setLeft(nodeC.getRight());
	    nodeC.setRight(nodeB);
	    nodeC.setLeft(this);
	    return nodeC;
	}

	/**
	 * Delete node
	 * 
	 * @param key
	 *            Key of the node to be deleted
	 * @return node that is on top after rebalancing
	 */
	public AVLNode<T> deleteNode(T element)
	{
	    int c = this.getElement().compareTo(element);
	    AVLNode<T> returnNode = this;
	    if(c == 0)
	    {
		if(this.getLeft() == null && this.getRight() == null)
		    return null;
		if(this.getLeft() != null && this.getRight() == null)
		    return this.getLeft();
		if(this.getLeft() == null && this.getRight() != null)
		    return this.getRight();
		if(this.getLeft() != null && this.getRight() != null)
		{
		    AVLNode<T> successorNode = this.successorInOrder();
		    AVLNode<T> rightChild = this.getRight();
		    rightChild = rightChild.deleteNode(successorNode.getElement());
		    successorNode.setLeft(this.getLeft());
		    successorNode.setRight(rightChild);
		    returnNode = successorNode;
		}
	    }
	    if(c < 0)
	    {
		if(this.getRight() != null)
		    this.setRight(this.getRight().deleteNode(element));
	    }
	    if(c > 0)
	    {
		if(this.getLeft() != null)
		    this.setLeft(this.getLeft().deleteNode(element));
	    }
	    returnNode.setHeight();
	    if(returnNode.getBalance() <= -2)
	    {
		int leftBalance = returnNode.getLeft().getBalance();
		if(leftBalance <= 0)
		    return returnNode.rotateLeft();
		else
		    return returnNode.rotateDoubleLeft();
	    }
	    if(returnNode.getBalance() >= 2)
	    {
		int rightBalance = returnNode.getRight().getBalance();
		if(rightBalance >= 0)
		    return returnNode.rotateRight();
		else
		    return returnNode.rotateDoubleRight();
	    }
	    return returnNode;
	}

	public AVLNode<T> successorInOrder()
	{
	    AVLNode<T> currentNode = this.getRight();
	    if(currentNode == null)
		return null;
	    while(currentNode.getLeft() != null)
		currentNode = currentNode.getLeft();
	    return currentNode;
	}

	public String toString()
	{
	    if(this.getLeft() == null && this.getRight() == null)
		return element.toString();
	    if(this.getLeft() == null && this.getRight() != null)
		return "[" + element.toString() + " : : " + this.getRight().toString() + "]";
	    if(this.getLeft() != null && this.getRight() == null)
		return "[" + element.toString() + " : " + this.getLeft().toString() + " : ]";
	    return "[" + element.toString() + " : " + this.getLeft().toString() + " : " + this.getRight().toString() + "]";
	}

	public boolean isAVL()
	{
	    if(this.getLeft() == null && this.getRight() == null)
		return true;
	    if(this.getLeft() == null && this.getRight() != null)
		return Math.abs(this.getBalance()) < 2 && this.getRight().isAVL();
	    if(this.getLeft() != null && this.getRight() == null)
		return Math.abs(this.getBalance()) < 2 && this.getLeft().isAVL();
	    return Math.abs(this.getBalance()) < 2 && this.getRight().isAVL() && this.getLeft().isAVL();
	}
    }

    AVLNode<T> first;

    public void addNode(AVLNode<T> node)
    {
	if(first == null)
	    first = node;
	else
	    first = first.addNode(node);
    }

    public void addElement(T element)
    {
	AVLNode<T> node = new AVLNode<>();
	node.setElement(element);
	addNode(node);
    }

    public void deleteElement(T key)
    {
	if(first != null)
	    first = first.deleteNode(key);
    }

    public boolean isAVL()
    {
	return first == null || first.isAVL();
    }

    public String toString()
    {
	return first.toString();
    }

    public AVLNode<T> getFirstNode()
    {
	return first;
    }
}
