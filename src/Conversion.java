import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



public class Conversion {
    public static Scanner sc= new Scanner(System.in);
    public static List<Map<String,String>> datos = new ArrayList<>();

    public static void conversionFile(List<Map<String,String>> datos){
        if(datos.isEmpty()){
            System.out.println("Primero debes leer un archivo antes de convertirlo.");
            return;
        }

        System.out.println("Selecciona el formato de conversión:");
        System.out.println("1.CSV");
        System.out.println("2.JSON");
        System.out.println("3.XML");

        String formato = sc.nextLine().toLowerCase();
        System.out.println("Ingresa el nombre del archivo de salida:");
        String nombreSalida = sc.nextLine();
        
        switch (formato) {
            case "1":
                exportarCSV(nombreSalida + ".csv",datos);
                break;
                
                case "2":
                exportarJSON(nombreSalida + ".json",datos);
                break;
                
                case "3":
                //exportarXML(datos,nombreSalida + ".xml");
                break;
        
        
            default:
                System.out.println("Opción no válida.");
        }
    }


    private static void exportarCSV(String nombreSalida,List<Map<String,String>> datos){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreSalida))) {
            Set<String> claves = datos.get(0).keySet();
            bw.write(String.join(",",claves));
            bw.newLine();

            for(Map<String,String> registro : datos){
                List<String> valores = new ArrayList<>();
                for(String clave : claves){
                    valores.add(registro.getOrDefault(clave, ""));
                }
                bw.write(String.join(",",valores));
                bw.newLine();
            }
            System.out.println("Datos exportados a CSV: " + nombreSalida);
        } catch (IOException e) {
            System.out.println("Error al exportar CSV: " + e.getMessage());
        }

    }

    private static void exportarJSON(String nombreSalida,List<Map<String,String>> datos){
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreSalida))) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(datos);
                bw.write(json);
                System.out.println("Datos exportados a JSON: " + nombreSalida);

                
            } catch (IOException e) {
                System.out.println("Error al exportar JSON: " + e.getMessage());
            }

    }

    private static void exportarXML(String nombreSalida,List<Map<String,String>> datos){
            try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("registros");
            doc.appendChild(rootElement);

            for(Map<String,String> registro : datos){
                Element elementoRegistro = doc.createElement("registros");
                rootElement.appendChild(eelementoRegistro);
                    for(Map.Entry<String,String> entrada : registro.entrySet()){
                        Element campo = doc.create

                    }

            }

            } catch (Exception e) {
                // TODO: handle exception
            }
    }



    
}
