/*
 * @autor Ana Rubio
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class FileAdmin {
    public static Scanner sc= new Scanner(System.in);
    private static String rutaCarpeta = "";
    private static String fichero = "";
    public static List<Map<String,String>> datos = new ArrayList<>();
    

    public static void seleccionarCarpeta(){
        System.out.println("Escribe la carpeta donde se encuentran los ficheros a leer:");
        rutaCarpeta = sc.nextLine();
        File carpeta = new File(rutaCarpeta);
        if (carpeta.exists() && carpeta.isDirectory()){
            System.out.println("La carpeta " + rutaCarpeta + " ,contiene:");
            String [] listaDeArchivos = carpeta.list();
            if (listaDeArchivos != null){
                for (String archivo : listaDeArchivos){
                    System.out.println(archivo);
                }    
            }
        } else {
            System.out.println("La carpeta no existe, prueba otra ruta.");
        }
    }

    public static void leerFichero(){
        if (rutaCarpeta.isEmpty()) {
            System.out.println("Selecciona una carpeta primero.");
            return;
        }

        System.out.println("Escribe el nombre del fichero que quieres leer con su extensión:");
        fichero = sc.nextLine();
        File archivo = new File(rutaCarpeta, fichero);

        if (archivo.exists()) {
            String tipoFichero = obtenerTipoFichero (archivo);
            try{
                switch (tipoFichero) {
                    case "csv":
                        datos = leerCSV(archivo);
                        mostrarContenido();
                        break;
                    case "json":
                        datos = leerJSON(archivo);
                        mostrarContenido();
                        break;
                    case "xml":
                        datos = leerXML(archivo);
                        mostrarContenido();
                        break;
                    default:
                        System.out.println("Formato no válido.");
                        return;
                }
                System.out.println("El fichero, " + fichero + " , ha sido leído correctamente.");
                
            } catch (IOException e) {
                System.err.println("Error al leer el fichero: " + e.getMessage());
            }
        } else {
            System.out.println("El fichero no existe.");
        }
    }
    public static String obtenerTipoFichero (File archivo){
        String nombreArchivo = archivo.getName();
        int posicionPunto = nombreArchivo.lastIndexOf('.');
        return (posicionPunto == -1) ? "" : nombreArchivo.substring(posicionPunto + 1).toLowerCase();
    }

    public static void mostrarContenido(){
        if (datos.isEmpty()){
            System.out.println("El archivo no tiene datos.");
            return;
        }
        System.out.println("El contenido del fichero es: ");
        //Para ver por pantalla el registro de los datos.
        for(Map<String, String> registro : datos){
            for(Map.Entry<String, String> entrada : registro.entrySet()){
                System.out.println(entrada.getKey() + ": " + entrada.getValue());
            }
            System.out.println();
        }
    }

    public static List <Map<String, String>> leerCSV (File archivo){
        List <Map<String, String>> datos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            String [] cabecera = null;
            //Leemos cabecera.
            if ((linea = br.readLine()) != null) {
                cabecera = linea.split(",");
                for (int i = 0; i < cabecera.length; i++) {
                    cabecera[i] = cabecera[i].trim();
                }
            }
            //Leemos los datos.
            while ((linea = br.readLine()) != null){
                String [] valores = linea.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                Map<String, String> registroDatos = new HashMap<>();
                for(int i = 0; i < cabecera.length && i < valores.length; i++){
                    registroDatos.put(cabecera[i].trim(), valores[i].trim()); //.trim() eliminamos espacios en blanco.
                }
                datos.add(registroDatos);
            }
        } catch (Exception e) {
            System.err.println("Error al leer el CSV: " + e.getMessage());
        }
         return datos;
    }

    public static List <Map<String, String>> leerJSON (File archivo) {
        List <Map<String, String>> datos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            StringBuilder contenidoBuilder = new StringBuilder();
            String linea;
        
            // Leer el archivo línea por línea
            while ((linea = br.readLine()) != null) {
                contenidoBuilder.append(linea);
            }

            String contenido = contenidoBuilder.toString().trim();

            // para quitar los corchetes iniciales [ ] del JSON
            if (contenido.startsWith("[") && contenido.endsWith("]")) {
                contenido = contenido.substring(1, contenido.length() - 1).trim();
            }

                // Separar los objetos JSON por "},{"
            String[] objetos = contenido.split("\\},\\s*\\{");

            for (String obj : objetos) {
                obj = obj.replace("{", "").replace("}", "").trim();
                String[] atributos = obj.split(",");

                Map<String, String> lineaMap = new HashMap<>();
                for (String atributo : atributos) {
                    String[] claveValor = atributo.split(":");
                    if (claveValor.length == 2) {
                        // Limpiar y quitar comillas
                        String clave = claveValor[0].trim().replace("\"", "");
                        String valor = claveValor[1].trim().replace("\"", "");
                        lineaMap.put(clave, valor);
                    }
                }
                datos.add(lineaMap);
            }
            System.out.println("Archivo JSON leído correctamente.");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    
        return datos;
    }
    public static List<Map<String, String>> leerXML(File archivo) throws IOException{ 
        List<Map<String, String>> datos = new ArrayList<>();
        if (!archivo.exists()) {
            System.err.println("¡El archivo" + archivo + " no existe!");
            return datos;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine();
            boolean dentroDeElemento = false;
            HashMap<String, String> elementos = new HashMap<>();
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                
                // Si es una etiqueta de apertura 
                if (linea.startsWith("<") && !linea.startsWith("</") && !linea.contains("</")) {
                    dentroDeElemento = true;
                    elementos = new HashMap<>();
                }  
                if (linea.startsWith("</") && dentroDeElemento) {
                    dentroDeElemento = false;
                    datos.add(elementos);
                }
                if (dentroDeElemento) {
                    linea = linea.replace("<", "").replace(">", " ").replace("/", " ");
                    String[] palabras = linea.split(" ");
                
                    if (palabras.length >= 2) { 
                        String clave = palabras[0].trim();
                        String valor = palabras[1].trim();

                        elementos.put(clave, valor);
                    }
                }
            }     
        } catch (IOException e) {
            System.err.println("Error al leer XML: " + e.getMessage());  
        }
        return datos;
    }
    
    // public static void main(String[] args) {
    //     String opcion;

    //     do {
    //         System.out.println("\n--- MENÚ ---");
    //         System.out.println("1. Seleccionar carpeta");
    //         System.out.println("2. Leer fichero");
    //         System.out.println("3. Conversión a");
    //         System.out.println("0. Salir");
    
    //         opcion = sc.nextLine();
    
    //         switch (opcion) {
    //             case "1":
    //                 seleccionarCarpeta();
    //                 break;
    //             case "2":
    //                 leerFichero();
    //                 break;
    //             /*case "3":
    //                 conversion();
    //                 break;*/
    //             case "0":
    //                 System.out.println("Saliendo del programa.");
    //                 break;
    //             default:
    //                 System.out.println("Opción no válida.");
    //         }
    //     } while (!opcion.equals("0"));
    // }
    
}
