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

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RedBlackTree arbol = new RedBlackTree();
        Integer a;
        Iterator preOrder;

        Random r1 = new Random();   //Sera cada uno de los procesos
        int key_1 = r1.nextInt(10);  //Para poder buscarlos guardamos en un entero

        Random r2 = new Random();
        int key_2 = r1.nextInt(10);

        Random r3 = new Random();
        int key_3 = r1.nextInt(10);

        Random r4 = new Random();
        int key_4 = r1.nextInt(10);

        Random r5 = new Random();
        int key_5 = r1.nextInt(10);

        Random r6 = new Random();
        int key_6 = r1.nextInt(10);

        Random r7 = new Random();
        int key_7 = r1.nextInt(10);

        Random r8 = new Random();
        int key_8 = r1.nextInt(10);

        Random r9 = new Random();
        int key_9 = r1.nextInt(10);

        Random r10 = new Random();
        int key_10 = r1.nextInt(10);

        arbol.insert(key_1);    //Insertamos el proceso en el arbol
        arbol.insert(key_2);
        arbol.insert(key_3);
        arbol.insert(key_4);
        arbol.insert(key_5);
        arbol.insert(key_6);
        arbol.insert(key_7);
        arbol.insert(key_8);
        arbol.insert(key_9);
        arbol.insert(key_10);

        System.out.print("Recorrido preOrder del arbol: "); //Hacemos un recorrido preOrden, tenemos un metodo
        preOrder = arbol.elementspreOrder();
        while (preOrder.hasNext()) {
            System.out.print(preOrder.next());
        }
        System.out.print("\n\n");

        System.out.print("Recorrido inOrder del arbol: " + arbol);  //Recorrido inOrder del arbol, toString


        /*a = arbol.elementPrioritario();
        System.out.print("\n\nElemento prioritario:" + a);
        arbol.delete(a);

        System.out.println("\n\nDespués de la eliminación" + arbol.toString());*/
        System.out.print("\n");

        for (int i = 0; i < 10; i++) {  //Simulamos 10 evaluaciones del proceso mas prioritario
            a = arbol.elementPrioritario(); //Sacamos el elemento mas prioritario
            key_1 = r1.nextInt(10); //Sacamos un random

            if (key_1 == a) {   //Si el random es igual al tiempo de proceso, el tiempo será borrado
                System.out.print("\nEl proceso con tiempo " + a + " ha finalizado, borramos el proceso!\n");
                arbol.delete(a);
                System.out.print(arbol);
                System.out.print("\n");
            } else {    //Si no se borra el proceso para reevaluarlo
                System.out.print("\nEl proceso con tiempo " + a + " no ha podido finalizar, procedemos a reasignarle otro tiempo");
                arbol.delete(a);
                key_2 = r1.nextInt(10);
                System.out.print(" ,el nuevo tiempo de ejecucion es: " + key_2 + "\n");
                arbol.insert(key_2);
                System.out.print(arbol);
                System.out.print("\n"); //Las impresiones las realizamos para ver como se hacen inserciones y borrado
            }
        }

    }
}
