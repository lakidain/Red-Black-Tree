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
import java.util.*;

public class RedBlackTree<E extends Comparable<E>> {

    private RedBlackTreeNode root;  //Tenemos una referencia a la raiz

    public RedBlackTree() { //constructor del arbol
    }

    public boolean insert(E key) {  //metodo publico para insertar, se encarga de iniciar atributos
        RedBlackTreeNode<E> parent = null;
        RedBlackTreeNode<E> node = root;
        while (node != null && !node.isNilNode()) {
            parent = node;
            int compare = key.compareTo(parent.getKey());
            if (compare == 0) { //Si el nodo tiene la misma key no lo añadimos
                node = parent.getLeft();    //Vamos a dejar meter valores con la misma key, no va a ser unica, ira a la izquierda si tienen la misma key, esta implementación esta hecha para adaptarlo al Fair Scheduler
            }
            if (compare < 0) {
                node = parent.getLeft();    //El nodo pasa a ser el hijo izquierdo con todo a null
            } else {
                node = parent.getRight();   //El nodo pasa a ser el hijo derecho con todo a null
            }
        }
        if (parent == null) {   //Si no hay raiz porque es el primer elemento la crea
            node = new RedBlackTreeNode(key, null);
            root = node;
        } else {    //hace los setter
            node.setParent(parent);
            node.setKey(key);
            node.setNilNode(false);
            node.setColor(RedBlackTreeNode.Color.RED);
        }
        node.setColor(RedBlackTreeNode.Color.RED);
        insertFixup(node);  //llama al metodo de balanceo del arbol
        return true;
    }

    private void insertFixup(RedBlackTreeNode<E> node) {    //metodo auxiliar de insertar, estudiar si hace falta balancear el arbol
        while (node.getParent() != null && node.getGrandparent() != null && node.getParent().getColor() == RedBlackTreeNode.Color.RED) {
            if (node.getParent() == node.getGrandparent().getLeft()) {  //Si el padre del nodo es el izquierdo del abuelo
                RedBlackTreeNode<E> uncle = node.getGrandparent().getRight();   //tio es el hijo derecho del abuelo
                if (uncle.getColor() == RedBlackTreeNode.Color.RED) {   //si el tio tiene color rojo
                    node.getParent().setColor(RedBlackTreeNode.Color.BLACK);    //cambiamos el color del padre y del tio a negro
                    uncle.setColor(RedBlackTreeNode.Color.BLACK);
                    node = node.getGrandparent();
                    node.setColor(RedBlackTreeNode.Color.RED);  //cambiamos el color del abuelo a rojo
                } else {    //si el tio tiene color negro
                    if (node == node.getParent().getRight()) {  //y el nodo es el derecho del padre
                        node = node.getParent();   //el nodo pasa a ser el padre 
                        rotateLeft(node);   //rotamos a la izquierda
                    }
                    node.getParent().setColor(RedBlackTreeNode.Color.BLACK);
                    node.getGrandparent().setColor(RedBlackTreeNode.Color.RED);
                    rotateRight(node.getGrandparent()); ///rotamos a la derecha
                }
            } else if (node.getParent() == node.getGrandparent().getRight()) { //Si el padre del nodo es el derecho del abuelo
                RedBlackTreeNode<E> uncle = node.getGrandparent().getLeft();    //tio es el hijo izquierdo del abuelo
                if (uncle.getColor() == RedBlackTreeNode.Color.RED) {   //si el tio tiene color rojo
                    node.getParent().setColor(RedBlackTreeNode.Color.BLACK);
                    uncle.setColor(RedBlackTreeNode.Color.BLACK);
                    node = node.getGrandparent();
                    node.setColor(RedBlackTreeNode.Color.RED);
                } else {    //si el tio tiene color negro
                    if (node == node.getParent().getLeft()) {
                        node = node.getParent();
                        rotateRight(node);  //rotamos a la derecha
                    }
                    node.getParent().setColor(RedBlackTreeNode.Color.BLACK);
                    node.getGrandparent().setColor(RedBlackTreeNode.Color.RED);
                    rotateLeft(node.getGrandparent());  //rotamos a la izquierda
                }
            }
        }
        root.setColor(RedBlackTreeNode.Color.BLACK);    //la raiz siempre va a ser negra
    }

    private void rotateLeft(RedBlackTreeNode<E> x) {    //rotacion a la izquierda
        RedBlackTreeNode<E> y = x.getRight();   //coges el hijo derecho de x
        x.setRight(y.getLeft());    //el hijo izquierdo de y se lo pone a x
        if (y.getLeft() != null) {  //si el hijo izquierdo de y no es nulo
            y.getLeft().setParent(x);   //le ponemos como padre al nodo x
        }
        y.setParent(x.getParent()); //el nodo y tiene como padre a x
        if (x.getParent() == null) {    //si el padre es nulo x pasa a ser raiz
            root = y;
        } else {
            if (x == x.getParent().getLeft()) { //si x estaba a la izquierda del padre le comen su lugar igual a la derecha
                x.getParent().setLeft(y);
            } else {
                x.getParent().setRight(y);
            }
        }
        y.setLeft(x);   //x pasa a estar a la izquierda de y
        x.setParent(y); //y pasa a ser el padre de x
    }

    private void rotateRight(RedBlackTreeNode<E> x) {   //rotacion a la derecha
        RedBlackTreeNode<E> y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != null) {
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else {
            if (x == x.getParent().getLeft()) {
                x.getParent().setLeft(y);
            } else {
                x.getParent().setRight(y);
            }
        }
        y.setRight(x);
        x.setParent(y);
    }

    public void delete(E key) { //Aqui pasas la key que quieras borrar
        RedBlackTreeNode<E> node = search(key); //ultimo nodo con la key que estabamos buscando
        RedBlackTreeNode<E> y, x;
        if (node.getLeft().isNilNode() || node.getRight().isNilNode()) {    //Si alguno de los hijos del nodo son nulos 
            y = node;   //y pasa a ser el nodo
        } else {        //si ambos hijos contienen algo
            y = treeSuccessor(node);    //y pasa a ser el sucesor
        }
        if (y.getLeft() != null && !y.getLeft().isNilNode()) {  //si el nodo izquierdo de y no es nulo
            x = y.getLeft();
        } else {
            x = y.getRight();
        }
        x.setParent(y.getParent()); //x pasa a tener como padre al padre de y
        if (y.getParent() == null) {
            root = x;   //si el padre era nulo x es la nueva raiz
        } else {
            if (y == y.getParent().getLeft()) {
                y.getParent().setLeft(x);   //reemplaza el lugar de y para el padre
            } else {
                y.getParent().setRight(x);
            }
        }
        if (y != node) {    //si y no es el nodo
            node.setKey(y.getKey());    //la nueva key del nodo es la de y
        }
        if (y.getColor() == RedBlackTreeNode.Color.BLACK) { //si el color de y es negro llamamos al Fixup
            deleteFixup(x);
        }
    }

    private void deleteFixup(RedBlackTreeNode<E> node) {    //metodo auxiliar borrado, por si hiciera falta rebalancear el arbol
        while (node != root && node.getColor() == RedBlackTreeNode.Color.BLACK) {
            if (node == node.getParent().getLeft()) {   //si el nodo es el izquierdo del padre
                RedBlackTreeNode w = node.getParent().getRight();   //w es el derecho
                if (w.getColor() == RedBlackTreeNode.Color.RED) {   //si w es rojo
                    w.setColor(RedBlackTreeNode.Color.BLACK);
                    node.getParent().setColor(RedBlackTreeNode.Color.RED);
                    rotateLeft(node.getParent());
                }
                if (w.getLeft().getColor() == RedBlackTreeNode.Color.BLACK && w.getRight().getColor() == RedBlackTreeNode.Color.BLACK) {    //si los dos hijos de w son negros
                    w.setColor(RedBlackTreeNode.Color.RED);
                    node = node.getParent();
                } else {
                    if (w.getRight().getColor() == RedBlackTreeNode.Color.BLACK) {  //si el derecho de w es negro
                        w.getLeft().setColor(RedBlackTreeNode.Color.BLACK);
                        w.setColor(RedBlackTreeNode.Color.RED);
                        rotateRight(w);
                        w = node.getParent().getRight();
                    }
                    w.setColor(node.getParent().getColor());
                    node.getParent().setColor(RedBlackTreeNode.Color.BLACK);
                    w.getRight().setColor(RedBlackTreeNode.Color.BLACK);
                    rotateLeft(node.getParent());
                    node = root;
                }
            } else {    //si el nodo es el derecho del padre
                RedBlackTreeNode w = node.getParent().getLeft();
                if (w.getColor() == RedBlackTreeNode.Color.RED) {
                    w.setColor(RedBlackTreeNode.Color.BLACK);
                    node.getParent().setColor(RedBlackTreeNode.Color.RED);
                    rotateRight(node.getParent());
                }
                if (w.getRight().getColor() == RedBlackTreeNode.Color.BLACK && w.getLeft().getColor() == RedBlackTreeNode.Color.BLACK) {
                    w.setColor(RedBlackTreeNode.Color.RED);
                    node = node.getParent();
                } else {
                    if (w.getLeft().getColor() == RedBlackTreeNode.Color.BLACK) {
                        w.getRight().setColor(RedBlackTreeNode.Color.BLACK);
                        w.setColor(RedBlackTreeNode.Color.RED);
                        rotateLeft(w);
                        w = node.getParent().getLeft();
                    }
                    w.setColor(node.getParent().getColor());
                    node.getParent().setColor(RedBlackTreeNode.Color.BLACK);
                    w.getLeft().setColor(RedBlackTreeNode.Color.BLACK);
                    rotateRight(node.getParent());
                    node = root;
                }
            }
        }
        node.setColor(RedBlackTreeNode.Color.BLACK);    //ponemos la raiz en black
    }

    private RedBlackTreeNode<E> treeSuccessor(RedBlackTreeNode<E> node) {   //devuelve el sucesor del nodo que le hemos pasado
        if (node.getRight() != null && !node.isNilNode()) { //Si el nodo no es nulo y su hijo derecho tampoco 
            return treeMinimum(node.getRight());    //devuelve el minimo a la derecha del que hemos pasado
        }
        RedBlackTreeNode<E> successor = node.getParent();
        while (successor != null && !successor.isNilNode() && node == successor) {  //aqui nunca deberiamos entrar pero si por lo que sea llegamos el sucesor sera el padre
            node = successor;
            successor = node.getParent();
        }
        return successor;
    }

    private RedBlackTreeNode<E> treeMinimum(RedBlackTreeNode<E> node) { //devuelve el nodo mas a la izquierda, el menor
        while (!node.getLeft().isNilNode() && !node.isNilNode()) {
            node = node.getLeft();
        }
        return node;
    }

    public RedBlackTreeNode<E> search(E key) {  //metodo publico,devuelve el ultimo nodo con la key asociada
        if (root == null) {
            return null;
        }

        return search(key, root);
    }

    private RedBlackTreeNode<E> search(E key, RedBlackTreeNode<E> node) {    //recorrido PreOrden buscando el nodo 
        if (key == node.getKey()) {
            return node;
        }

        if (key.compareTo(node.getKey()) <= 0) {
            if (!node.leftExists()) {
                return null;
            }
            return search(key, node.getLeft());
        }

        if (key.compareTo(node.getKey()) > 0) {
            if (!node.rightExists()) {
                return null;
            }
            return search(key, node.getRight());
        }

        return null;
    }

    public Integer elementPrioritario() {   //Funcion para extraer el proceso con menor tiempo de ejecucion
        Integer a;
        a = elementPriority(this.root);
        return a;
    }

    private Integer elementPriority(RedBlackTreeNode node) {        //", pasamos el nodo base y recursivamente se llama
        Integer key = 0;

        if (node.getLeft().isNilNode()) {   //caso base 
            key = (Integer) node.getKey();  //extrae el proceso
        } else {
            key = elementPriority(node.getLeft());  //llamada recursiva
        }

        return key;
    }

    public Iterator elementsInOrder() {
        Collection a;
        Iterator c;
        a = elementsInOrder(this.root);
        c = a.iterator();
        return c;
    }

    private Collection elementsInOrder(RedBlackTreeNode root) {        //recorrer inOrder, devuelve collection
        Collection coleccion = new ArrayList();

        if (!root.getLeft().isNilNode()) {  //Se pueden mostrar hojas exteriores, se ve peor, habria que poner root.leftExists() y en Right =
            coleccion.add(elementsInOrder(root.getLeft()));
        }
        coleccion.add(root);
        if (!root.getRight().isNilNode()) {
            coleccion.add(elementsInOrder(root.getRight()));
        }

        return coleccion;
    }

    public Iterator elementspreOrder() {
        Collection a;
        Iterator c;
        a = elementspreOrder(this.root);
        c = a.iterator();
        return c;
    }

    private Collection elementspreOrder(RedBlackTreeNode root) {        //recorrer preOrden
        Collection coleccion = new ArrayList();

        coleccion.add(root);

        if (!root.getLeft().isNilNode()) {
            coleccion.add(elementsInOrder(root.getLeft()));
        }

        if (!root.getRight().isNilNode()) {
            coleccion.add(elementsInOrder(root.getRight()));
        }

        return coleccion;
    }

    public String toString() {  //metodo toString, llama al metodo inOrder y recorre la coleccion
        Iterator toString = elementsInOrder();
        String imprimir = "";

        while (toString.hasNext()) {
            imprimir = imprimir + toString.next();
        }

        return imprimir;
    }

}
