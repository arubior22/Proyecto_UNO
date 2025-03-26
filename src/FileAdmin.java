
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
  
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;




public class FileAdmin {
    public static Scanner sc= new Scanner(System.in);
    private static String rutaCarpeta = "";
    private static String fichero = "";
    public static List<Map<String,String>> datos = new ArrayList<>();
    

    public static void buscarCarpeta(){
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
                        break;
                    case "json":
                        datos = leerJSON(archivo);
                        break;
                    case "xml":
                        datos = leerXML(archivo);
                        break;
                    default:
                        System.out.println("Formato no válido.");
                        return;
                }
                System.out.println("El fichero, " + fichero + " , ha sido leído correctamente.");
            } catch (IOException | ParserConfigurationException | SAXException e) {
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
            System.err.println("Error al procesar el CSV: " + e.getMessage());
        }
         return datos;
    }

    public static List <Map<String, String>> leerJSON (File archivo) {
        List <Map<String, String>> datos = new ArrayList<>();
        try {
            // Leemos el contenido del archivo como String
            String contenido = new String(Files.readAllBytes(archivo.toPath())).trim();
            // para quitar los corchetes iniciales [ ] del JSON
            if (contenido.startsWith("[") && contenido.endsWith("]")) {
                contenido = contenido.substring(1, contenido.length() - 1).trim();
            }
            // Separar los objetos JSON por "},{" (mostrando los datos)
            String[] objetos = contenido.split("\\},\\{");

            for (String obj : objetos) {
                obj = obj.replace("{", "").replace("}", ""); // Quitamos las llaves { }
                String[] atributos = obj.split(",");

                Map<String, String> linea = new HashMap<>();
                for (String atributo : atributos) {
                    String[] claveValor = atributo.split(":");

                    // Limpiar y quitar comillas
                    String clave = claveValor[0].trim().replace("\"", "");
                    String valor = claveValor[1].trim().replace("\"", "");

                    linea.put(clave, valor);
                }
                datos.add(linea);
            }
            System.out.println("Archivo JSON leído correctamente.");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
        }
        
        return datos;
        
    }
    
    public static List<Map<String, String>> leerXML(File archivo) throws IOException, ParserConfigurationException, SAXException {
        List<Map<String, String>> datos = new ArrayList<>();
        try {
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructor = fabrica.newDocumentBuilder();
            Document documento = constructor.parse(archivo);
            documento.getDocumentElement().normalize();

            NodeList listaNodos = documento.getElementsByTagName("registro");
            for (int i = 0; i < listaNodos.getLength(); i++) {
                Node nodo = listaNodos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    Map<String, String> fila = new HashMap<>();
                    NodeList hijos = elemento.getChildNodes();
                    for (int j = 0; j < hijos.getLength(); j++) {
                        Node hijo = hijos.item(j);
                        if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                            fila.put(hijo.getNodeName(), hijo.getTextContent());
                        }
                    }
                    datos.add(fila);
                }
            }
            System.out.println("Archivo XML se ha leído correctamente.");
        } catch (Exception e) {
            System.out.println("Error al leer el archivo XML: " + e.getMessage());
        }
        return datos;
    }
    
    
    public static void main(String[] args) {
        buscarCarpeta();
        leerFichero();
    }
}
