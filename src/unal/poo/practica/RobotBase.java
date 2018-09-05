package unal.poo.practica;

import becker.robots.*;

/** 
 * Practica de los conceptos de Programacion Estructurada
 * @author Fabian Andres Giraldo */
public class RobotBase
{    
       //Declaracion de Variables -- Forma temporal - No es buena practica tener
       //variables estaticas
        public static City objetos;
        public static Robot estudiante;
        private static final int x=17; //Tamaño vertical de la ciudad de karel
        private static final int y=22; //Tamaño horizontal de la ciudad de karel
        
        
	public static void main (String[] args){
            //Declarar la creacion de la ciudad
            objetos = new City("Field.txt");
	    objetos.showThingCounts(true);
            
            //Direction.NORTH, EAST, SOUTH, WEST
            //Definicion de la ubicacion del robot, Ciudad, posicion, Direccion, Numero things en el bolso.
            estudiante = new Robot(objetos,1, 1, Direction.EAST,10);
            estudiante.turnLeft();
            
            
            System.out.println("El área del lote es : "+AreaLote()+" metros cuadrados.");//Imprime el área del lote en la consola de comandos.
            
            
	}
    /////////////////////////////////////////Funciones/////////////////////////////////////////
        
    public static int AreaLote(){//Esta Función Determina el Area de una figura irregular cerrada en el rango x a y
        byte[][] Terreno = new byte[x][y];
        while(!estudiante.canPickThing()){
            Terreno[estudiante.getStreet()+1][estudiante.getAvenue()+1] = Avanzar();
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
        
        
    public static void turnRight(){//Gira hacia la derecha a Karel
        estudiante.turnLeft();
        estudiante.turnLeft();
        estudiante.turnLeft();
    }
    
    public static byte Avanzar(){//Avanza al rededor de la figura 
        if(estudiante.frontIsClear()){
            estudiante.move();
            turnRight();
            return 1;
        }else{
            estudiante.turnLeft(); 
           return 0;
        }
    }

    public static void RellenarTerreno(byte[][] Terreno) {//Rellena con 2 los bordes de la Matriz y posteriormente llena con 2 las casillas exteriores al borde
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
    
    public static void QuitarCeros(byte[][] Terreno){//Esta función encuentra El primer 0 al interior de la figura y los convierte en 3
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

    public static boolean Vecino(byte[][] Terreno, int i, int j, int c) {//Determina si uno de los vecinos a una casilla [i][j] es igual a c
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if(Terreno[i-1+k][j-1+l]==c){
                    return true;
                }
            }
        }
        return false;
    }

    public static void Interior(byte[][] Terreno) {//Encontrado el primer 3 rellena el interior de la figura del número 3
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