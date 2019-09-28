package AVL;


import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;



public class DrawAVL extends JFrame
{
    Draw d;
    boolean drawKey;

    public DrawAVL(AVLTree<?> tree, boolean drawKey)
    {
	this.setVisible(true);
	this.setSize(1800, 600);
	this.drawKey = drawKey;
	d = new Draw(tree);
	this.add(d);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void repaint()
    {
	d.repaint();
    }

    class Draw extends JComponent
    {
	AVLTree<?> tree;

	public Draw(AVLTree<?> tree)
	{
	    super();
	    this.tree = tree;
	}

	@Override
	public void paint(Graphics g)
	{
	    paintRec(g, 900, 10, 450, tree.getFirstNode());
	}

	private void paintRec(Graphics g, int x, int y, int dx, AVLTree.AVLNode<?> node)
	{
	    if(node.getLeft() != null)
	    {
		paintRec(g, x - dx, y + 60, dx / 2, node.getLeft());
		g.setColor(Color.BLUE);
		g.drawLine(x, y, x - dx, y + 60);
	    }
	    if(node.getRight() != null)
	    {
		paintRec(g, x + dx, y + 60, dx / 2, node.getRight());
		g.setColor(Color.BLUE);
		g.drawLine(x, y, x + dx, y + 60);
	    }
	    if(drawKey)
	    {
		g.setColor(Color.BLACK);
		g.drawString(node.getElement().toString(), x, y);
	    }
	}
    }

}
