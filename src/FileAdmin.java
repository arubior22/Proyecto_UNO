import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileAdmin {
    public static Scanner sc= new Scanner(System.in);
    private static String rutaCarpeta = "";
    private static String fichero = "";
    private List<Map<String,String>> datos = new ArrayList<>();

    public static void buscarCarpeta(){
        System.out.println("Escribe la carpeta donde se encuentran los ficheros a leer:");
        rutaCarpeta = sc.nextLine();
        File carpeta = new File(rutaCarpeta);
        if (carpeta.exists() && carpeta.isDirectory()){
            System.out.println("La carpeta " + rutaCarpeta + " ,contiene:");
            String [] listaDeArchivos = carpeta.list();
            if (listaDeArchivos != null){
                for (String archivo : listaDeArchivos){
                    System.out.println("-" + archivo);
                }    
            }
        } else {
            System.out.println("La carpeta no existe, prueba otra ruta.");
        }
    }
    public static void leerFichero(){

    }
    public static void main(String[] args) {
        buscarCarpeta();
        //leerFichero();
    }

   

}
