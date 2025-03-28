import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;





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
                exportarXML(nombreSalida + ".xml",datos);
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
              
                bw.write("[\n]");
                //System.out.println("Datos exportados a JSON: " + nombreSalida);
                
                
                for(int i = 0;i<datos.size();i++){
                    Map<String,String> registro = datos.get(i);

                    bw.write("  {\n");

                    int j = 0;
                    for(Map.Entry<String, String> entry : registro.entrySet()){
                        bw.write("    \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"");

                        if(j < registro.size() -1 ){
                            bw.write(",\n");
                        }else{
                            bw.write("\n");
                        }
                        j++;
                    }
                    
                bw.write("  }");
                if(i < datos.size()-1){
                    bw.write(",\n");
                }
            }

            bw.write("\n]");
            System.out.println("Archivo exportado ok");

               
                
            } catch (IOException e) {
                System.out.println("Error al exportar JSON: " + e.getMessage());
            }

    }

    private static void exportarXML(String nombreSalida,List<Map<String,String>> datos){
            try {
           StringBuilder xmlBuilder = new StringBuilder();
           xmlBuilder.append("<registros>\n");

            for(Map<String,String> registro : datos){
                    xmlBuilder.append("<registros>\n");
                    for(Map.Entry<String,String> entrada : registro.entrySet()){
                        xmlBuilder.append("    <" + entrada.getKey() + ">" + entrada.getValue() + "</" + entrada.getKey() + ">\n");

                    }

                    xmlBuilder.append(" </registro>\n");

            }
            xmlBuilder.append("</registros>");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreSalida))) {
                bw.write(xmlBuilder.toString());
            } 

        System.out.println("Datos exportados a XML: " + nombreSalida);
            } catch (IOException e) {
                System.out.println("Error al exportar XML: " + e.getMessage());
            }
    }



    
}
