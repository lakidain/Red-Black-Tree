/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redblacktree2;

/**
 *
 * @author Ander
 */
public class RedBlackTreeNode<E extends Comparable<E>> implements Comparable<RedBlackTreeNode<E>> {

    private final String nullNodeString = "_B"; //Los nodos nulos (las hojas exteriores) los representamos con _B
    private RedBlackTreeNode left;  //referencia al nodo a su izquierda
    private RedBlackTreeNode right; //referencia al nodo a su derecha
    private RedBlackTreeNode parent;    //referencia al padre

    private E key;  //La key sera un objeto 
    private boolean isNilNode;  //Si es nulo se ve con un booleano
    private Color color;    //El color del Nodo

    /**
     * Creates a new {@link RedBlackTreeNode}.
     *
     * @param key The key of the new node.
     * @param parent The parent of the new node.
     */
    public RedBlackTreeNode(E key, RedBlackTreeNode parent) {
        this.key = key;
        this.parent = parent;
        this.color = Color.RED;
        this.setNilNode(false);
    }

    /**
     * Creates a new nil (black) {@link RedBlackTreeNode}.
     *
     * @param parent The parent of the new node.
     */
    private RedBlackTreeNode(RedBlackTreeNode parent) {
        this.parent = parent;
        this.color = Color.BLACK;
        this.setNilNode(true);
    }

    /**
     * @return A string that textually describes the node.
     */
    @Override
    public String toString() {
        if (isNilNode) {
            return nullNodeString;
        }
        return key + " " + getColorCode();
    }

    /**
     * @return The color code for the node, either red or black.
     */
    private String getColorCode() { //metodo getter para el color
        if (color == Color.BLACK) {
            return "BLACK";
        }
        return "RED";
    }

    public boolean leftExists() {   //devuelve un boolean si a la izquierda existe un nodo
        return left != null;
    }

    public boolean rightExists() {  //devuelve un boolean si a la derecha existe un nodo
        return right != null;
    }

    public E getKey() { //metodo getter de la key
        return key;
    }

    public void setKey(E key) { //metodo setter de la key
        this.key = key;
    }

    public RedBlackTreeNode getLeft() {
        // Create nil leaf nodes lazily
        if (left == null) {
            left = new RedBlackTreeNode(this);
        }
        return left;
    }

    public void setLeft(RedBlackTreeNode left) {
        this.left = left;
    }

    public RedBlackTreeNode getRight() {    //getter nodo derecho
        // Create nil leaf nodes lazily
        if (right == null) {
            right = new RedBlackTreeNode(this);
        }
        return right;
    }

    public void setRight(RedBlackTreeNode right) {  //setter nodo derecho
        this.right = right;
    }

    public RedBlackTreeNode getParent() {   //getter padre
        return parent;
    }

    public RedBlackTreeNode getGrandparent() {
        if (parent != null && parent.getParent() != null) {
            return parent.getParent();
        }
        return null;
    }

    public void setParent(RedBlackTreeNode parent) {
        this.parent = parent;
    }

    public Color getColor() {   //getter del color
        return color;
    }

    /**
     * Sets the color of this node.
     *
     * @param color The new color node.
     */
    public void setColor(Color color) { //setter del color, colo acepta de los colores que tenemos definidos
        this.color = color;
    }

    /**
     * @return Whether this is a nil node.
     */
    public boolean isNilNode() {
        return isNilNode;
    }

    /**
     * Sets weather this is a nil node.
     *
     * @param isNilNode Whether it is a nil node.
     */
    public final void setNilNode(boolean isNilNode) {
        this.isNilNode = isNilNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(RedBlackTreeNode<E> o) {
        return this.key.compareTo(o.getKey());
    }

    /**
     * Represents the color of a Red Black Tree node.
     */
    public enum Color { //metodo para contener los colores que podra tener un nodo
        BLACK,
        RED
    }
}
