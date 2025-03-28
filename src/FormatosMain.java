import java.util.Scanner;

public class FormatosMain {

     public static Scanner sc= new Scanner(System.in);
    public static void main(String[] args) {
        
        boolean salir = false;

        while (!salir) {
            System.out.println("\n       Menú");
            System.out.println("1.Selecciona Carpeta");
            System.out.println("2.Leer Fichero");
            System.out.println("3.Conversion");
            System.out.println("4.Salir");
            System.out.print("Elige una opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine();


                switch (opcion) {
                    case 1:
                        FileAdmin.seleccionarCarpeta();
                        break;

                    case 2:
                        FileAdmin.leerFichero();
                        break;

                    
                    case 3:
                        Conversion.conversionFile();
                    break;

                    case 4:
                        System.out.println("Hasta luego!");
                        salir = true;
                    break;
                
                    default:
                        System.out.println("Opción NO válida");
                        break;
                }
        
        
        
        }

    }
}
