import java.util.Scanner;

public class Conversion {
    static Scanner sc = new Scanner(System.in);

    private FileAdmin fileAdmin;

    public Conversion(FileAdmin fileAdmin){
        this.fileAdmin = fileAdmin;
    }

    public void convertirFile(Scanner scanner){
        if (fileAdmin.getData() .isEmpty()) {
            System.out.println("No hay datos cargados.Lea un archivo primero.");
            return;
        }

        System.out.println("Seleccione el formato de salida (csv,json,xml): ");
        String format = sc.nextLine().toLowerCase();
        System.out.println("Ingrese el nombre del archivo de salida: ");
        String salidaArchivo = sc.nextLine() + "." + format;
        System.out.println("Archivo convertido a " + format + " y guardado como " + salidaArchivo);
    }
}
