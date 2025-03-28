import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Rebeca Poma
 */

public class Conversion {
    public static Scanner sc = new Scanner(System.in);

    public static void conversionFile() {
        if (FileAdmin.datos.isEmpty()) {
        System.out.println("Primero debes leer un archivo antes de convertirlo.");
        return;
        }

        System.out.println("Selecciona el formato de conversión:");
        System.out.println("1.CSV");
        System.out.println("2.JSON");
        System.out.println("3.XML");
        System.out.print("Elige una opcion: ");

        int formato = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingresa el nombre del archivo de salida(sin la extension): ");
        String nombreFichero = sc.nextLine();
        

        switch (formato) {
            case 1:

                exportarCSV(FileAdmin.datos, nombreFichero + ".csv");
                break;

            case 2:
                exportarJSON(FileAdmin.datos, nombreFichero + ".json");
                break;

            case 3:
                exportarXML(FileAdmin.datos, nombreFichero + ".xml");
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void exportarCSV(List<Map<String, String>> datos, String nombreFichero) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))) {

            if (!datos.isEmpty()) {
                Map<String, String> primerRegistro = datos.get(0);
                for (String clave : primerRegistro.keySet()) {
                    bw.write(clave + ";");
                }
                bw.write("\n");

                for (Map<String, String> registro : datos) {
                    for (String valor : registro.values()) {
                        bw.write(valor + ",");
                    }
                    bw.write("\n");
                }

            }

            System.out.println("Archivo exportado con exito");

        } catch (IOException e) {
            System.out.println("Error al exportar CSV: " + e.getMessage());
        }

    }

    private static void exportarJSON(List<Map<String, String>> datos, String nombreFichero) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))) {
            bw.write("[\n]");

            for (int i = 0; i < datos.size(); i++) {
                Map<String, String> registro = datos.get(i);
                bw.write("  {\n");

                int j = 0;
                for (Map.Entry<String, String> entry : registro.entrySet()) {
                    bw.write("    \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"");

                    if (j < registro.size() - 1) {
                        bw.write(",\n");
                    } else {
                        bw.write("\n");
                    }
                    j++;
                }

                bw.write("  }");
                if (i < datos.size() - 1) {
                    bw.write(",\n");
                }
            }

            bw.write("\n]");
            System.out.println("Archivo exportado ok");

        } catch (IOException e) {
            System.out.println("Error al exportar JSON: " + e.getMessage());
        }

    }

    private static void exportarXML(List<Map<String, String>> datos, String nombreFichero) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))) {
            bw.write("<datos>\n");

            for (Map<String, String> registro : datos) {
                bw.write(" <registros>\n");
                for (Map.Entry<String, String> entrada : registro.entrySet()) {
                    bw.write("    <" + entrada.getKey() + ">" + entrada.getValue() + "</" + entrada.getKey() + ">\n");

                }

                bw.write(" </registro>\n");

            }
            bw.write("</datos>");

            System.out.println("Datos exportados a XML");
        } catch (IOException e) {
            System.out.println("Error al exportar XML: " + e.getMessage());
        }
    }

}
