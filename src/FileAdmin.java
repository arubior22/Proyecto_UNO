import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileAdmin {
    private String selectedFolder = "";
    private List<Map<String,String>> data = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public void seleccionFolder(Scanner scanner){
        System.out.println("Ingresa la ruta de la carpeta");
        selectedFolder = sc.nextLine();
        File folder = new File(selectedFolder);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Carpeta NO válida.");
            selectedFolder = "";
        }else{
            System.out.println("Carpeta seleccionada: " + selectedFolder);
            listFiles(folder);
        }
    }

    private void listFiles(File folder){
        System.out.println("Archivos en la carpeta:");
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                System.out.println(" - " + file.getName());
            }
        }
    }

   

}
