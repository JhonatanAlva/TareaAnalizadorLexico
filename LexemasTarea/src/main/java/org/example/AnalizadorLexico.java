package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorLexico {

    public static List<ElementoLexico> analizar(String codigoFuente) {
        List<ElementoLexico> tokens = new ArrayList<>();
        String[] patronesTokens = {
                "\\bif\\b", "IF",
                "\\belse\\b", "ELSE",
                "\\bpublic\\b", "ALCANCE",
                "\\bprivate\\b", "ALCANCE",
                "\\;", "PUNTO_Y_COMA",
                "\\b[a-z_0-9]\\w*\\b", "IDENTIFICADOR",
                "\\+", "SUMA",
                "-", "RESTA",
                "\\*", "MULTIPLICACION",
                "/", "DIVISION",
                "=", "ASIGNACION",
                "\\(", "PARENTESIS_IZQ",
                "\\)", "PARENTESIS_DER",
                "\\{", "CORCHETE_IZQ",
                "\\}", "CORCHETE_DER",
                "\\b[A-Z][a-z]\\w*\\b", "CLASES",
        };

        StringBuilder patronesUnidos = new StringBuilder();
        for (int i = 0; i < patronesTokens.length; i += 2) {
            patronesUnidos.append(patronesTokens[i]);
            if (i < patronesTokens.length - 2) {
                patronesUnidos.append("|");
            }
        }

        Pattern patron = Pattern.compile(patronesUnidos.toString());
        Scanner scanner = new Scanner(codigoFuente);
        while (scanner.hasNext()) {
            if (scanner.hasNext(patron)) {
                String lexema = scanner.next(patron);
                String token = obtenerToken(lexema, patronesTokens);
                tokens.add(new ElementoLexico(lexema, token));
            } else {
                scanner.next();
            }
        }

        return tokens;
    }

    private static String obtenerToken(String lexema, String[] patronesTokens) {
        for (int i = 0; i < patronesTokens.length; i += 2) {
            Pattern patron = Pattern.compile(patronesTokens[i]);
            Matcher matcher = patron.matcher(lexema);
            if (matcher.matches()) {
                return patronesTokens[i + 1];
            }
        }
        return "DESCONOCIDO";
    }

    public static void main(String[] args) {
        String rutaArchivo = "C:\\NUEVOS ARCHIVOS\\Universidad\\SEPTIMO SEMESTRE\\COMPILADORES\\Nueva carpeta\\Lexemas\\LexemasTarea\\src\\main\\java\\org\\example\\Sumador.txt";

        try {
            String codigoFuente = leerCodigoFuenteDesdeArchivo(rutaArchivo);

            List<ElementoLexico> resultadoAnalisisLexico = analizar(codigoFuente);

            // Mostrar los lexemas y tokens encontrados
            for (ElementoLexico elemento : resultadoAnalisisLexico) {
                System.out.println("Lexema: " + elemento.obtenerLexema() + ", Token: " + elemento.obtenerToken());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado.");
        }
    }

    private static String leerCodigoFuenteDesdeArchivo(String rutaArchivo) throws FileNotFoundException {
        StringBuilder codigoFuente = new StringBuilder();
        Scanner scanner = new Scanner(new File(rutaArchivo));

        while (scanner.hasNextLine()) {
            codigoFuente.append(scanner.nextLine()).append("\n");
        }

        return codigoFuente.toString();
    }
}