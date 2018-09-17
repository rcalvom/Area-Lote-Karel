package unal.poo.practica;

import becker.robots.Robot;

/**
 * Esta Clase contiene todas las funciones necesarias para hallar el área de una figura irregular
 * @since 15/09/2018
 * @author Ricardo Andrés Calvo
 * @author Julio Andrés Rodríguez
 * @author Jorge Aurelio Morales
 * @author Jhon Sebastián Rojas
 * @author Julio Enrique Aguilera
 */
public class AreaFigura{    
    private static final int X=17; //Tamaño vertical de la ciudad de karel
    private static final int Y=22; //Tamaño horizontal de la ciudad de karel
    
    /**
     * Devuelve el Área del lote cerrado en la ciudad de Karel
     * @param estudiante Robot
     * @return Área de una figura irregular cerrada en el rango x a y
     */
    public static int AreaLote(Robot estudiante){
        byte[][] Terreno = new byte[X][Y];
        while(!estudiante.canPickThing()){
            Terreno[estudiante.getStreet()+1][estudiante.getAvenue()+1] = Avanzar(estudiante);
        }                                                           //recorre el borde de el lote.
        Terreno[estudiante.getStreet()+1][estudiante.getAvenue()+1]=1;        
        RellenarTerreno(Terreno);
        
        int AreaTerreno = 0;
        for (byte[] Terreno1 : Terreno) {
            for (int j = 0; j < Terreno[0].length; j++) {
                if (Terreno1[j] == 3) {
                    AreaTerreno++;//Cuenta la cantidad de 3 en la matriz Terreno.
                }
            }
        }
        return AreaTerreno;
    }    
    /**
     * Gira el robot en el sentido de las manecillas del reloj 90 grados
     * @param estudiante Robot Karel de la ciudad
     */ 
    public static void turnRight(Robot estudiante){//Gira hacia la derecha a Karel
        estudiante.turnLeft();
        estudiante.turnLeft();
        estudiante.turnLeft();
    }
    
    /**
     * Verifica si hay un muro en frente, si lo hay gira a la derecha, de lo contrario avanza
     * @param estudiante Robot Karel de la ciudad
     * @return Retorna 1 si karel logra avanzar, Retorna 0 en caso contrario
     */
    public static byte Avanzar(Robot estudiante){
        if(estudiante.frontIsClear()){
            estudiante.move();
            turnRight(estudiante);
            return 1;
        }else{
            estudiante.turnLeft(); 
           return 0;
        }
    }
    
    /**
     * Coloca en todas las casilla de las primeras y ultimas filas y columnas el número 2
     * @param Terreno Matriz que representa las intersecciones de la ciudad
     */
    public static void RellenarTerreno(byte[][] Terreno) {
        for (byte[] Terreno1 : Terreno) {
            for (int j = 0; j < Terreno[0].length; j++) {
                Terreno[0][j]=2;
                Terreno[Terreno.length-1][j]=2;
            }   
            Terreno1[0] = 2;
            Terreno1[Terreno[0].length-1] = 2;
        }
        QuitarCeros(Terreno);
    }     
    
    /**
     * Quita todos los ceros de la matriz, coloca un 2 si el 0 esta fuera del Lote y coloca un 3 si esta al interior
     * @param Terreno Matriz que representa las intersecciones de la ciudad
     */
    public static void QuitarCeros(byte[][] Terreno){
        for (int i = 1; i < Terreno.length-1; i++) {
            for (int j = 1; j < Terreno[0].length-1; j++) {
                if(Vecino(Terreno,i,j,2) && Terreno[i][j]==0){
                    Terreno[i][j]=2;
                }else if(!Vecino(Terreno, i, j,2) && Terreno[i][j]==0){
                    Terreno[i][j]=3;
                    Interior(Terreno);
                    return;
                }
            }
        }
    }
    
    /**
     * Determina si una casilla vecina es igual a un número dado
     * @param Terreno Matriz que representa las intersecciones de la ciudad
     * @param i Fila de la Matriz
     * @param j Columna de la Matriz
     * @param c Número a comprobar en los vecinos
     * @return Falso o verdadero si uno de los vecinos a una posición [i][j] de la matriz dada es igual a c
     */
    public static boolean Vecino(byte[][] Terreno, int i, int j, int c) {
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if(Terreno[i-1+k][j-1+l]==c){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cambia los 0 que esten al interior del lote por un 3
     * @param Terreno Matriz que representa las intersecciones de la ciudad
     */
    public static void Interior(byte[][] Terreno) {
        for(int a = 0; a<Math.max(Terreno.length,Terreno[0].length)/2;a++){
            for (int k = 1; k < Terreno.length-1; k++) {
               for (int l = 1; l < Terreno[0].length-1; l++) {
                    if(Vecino(Terreno,k,l,3) && Terreno[k][l]==0){
                        Terreno[k][l]=3;
                    }
                }
            }
            for (int k = 1; k < Terreno.length-1; k++) {
               for (int l = 1; l < Terreno[0].length-1; l++) {
                    if(Vecino(Terreno,Terreno.length-1-k,Terreno[0].length-1-l,3) && Terreno[Terreno.length-1-k][Terreno[0].length-1-l]==0){
                        Terreno[Terreno.length-1-k][Terreno[0].length-1-l]=3;
                    }
                }
            }
        }       
    }
}
