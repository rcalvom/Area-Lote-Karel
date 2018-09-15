package unal.poo.practica;

import becker.robots.*;

/**
 * Clase que inicializa el robot y la ciudad con atributos dados
 * @since 15/09/2018
 * @author Ricardo Andrés Calvo
 */
public class RobotBase{
        
    public static City objetos;
    public static Robot estudiante;
        
    public static void main (String[] args){
        objetos = new City("Area del Lote.txt");
        objetos.showThingCounts(true);
        estudiante = new Robot(objetos,1, 1, Direction.EAST,10);
        System.out.println("El área del lote es : "+AreaFigura.AreaLote(estudiante)+" metros cuadrados.");//Imprime el área del lote en la consola de comandos.    
    }    
}