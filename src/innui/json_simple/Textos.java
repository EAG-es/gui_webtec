/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.json_simple;

/**
 *
 * @author emilio
 */
public class Textos {
    
    /**
     * Adapta json_simple a json
     * - No hay comillas acotando los atributos, ni en los datos.
     * - No hay espacios en blanco, ni saltos de linea, decorativos (salvo en la , separadora de objetos).
     * - Para que: ", \n, \r, \t, [, ], {, }, : y , sean literales, se ponen: {'}, {n}, {r}, {t}, {(}, {)}, {{, }}, ::, y ,,
     * @param json_simple cadena json_simple
     * @param error mensaje de error, si lo hay
     * @return el texto json_simple con comillas dobles añadidas, o null si hay error.
     */
    public static String convertir_json_simple_a_json(String json_simple, String [] error) {
        String resultado = json_simple;
        boolean contiene_llave = false;
        resultado = resultado.replace(",,", "\",,\""); // marcar: ,,
        resultado = resultado.replaceAll("\\s*,\\s+", ",");
        resultado = resultado.replaceAll("\\s+,\\s*", ",");
        resultado = resultado.replace("\",,\"", ",,"); // desmarcar: ,,
        if (resultado.contains("{")) {
            contiene_llave = true;
            resultado = resultado.replace("{{", "\"{{\""); // marcar: {{
            resultado = resultado.replace("}}", "\"}}\""); // marcar: }}
            resultado = resultado.replace("{(}", "\"{(}\""); // marcar: {{
            resultado = resultado.replace("{)}", "\"{)}\""); // marcar: {{           
            resultado = resultado.replace("{n}", "\n");
            resultado = resultado.replace("{r}", "\r");
            resultado = resultado.replace("{t}", "\t");
            resultado = resultado.replace("{'}", "\"");
            resultado = resultado.replace("{", "{\"");
            resultado = resultado.replace("{\"{\"", "{{");
            resultado = resultado.replace("}", "\"}");
            resultado = resultado.replace("\"}\"}", "}}");
        }
        resultado = resultado.replace(":", "\":\"");
        resultado = resultado.replace("\":\"\":\"", ":");
        resultado = resultado.replace(",", "\",\"");
        resultado = resultado.replace("\",\"\",\"", ",");
        resultado = resultado.replace(":\"[", ":[");
        resultado = resultado.replace(",\"[", ",[");
        resultado = resultado.replace("[", "[\"");
        resultado = resultado.replace("[\"[", "[[");
        resultado = resultado.replace("]", "\"]");
        resultado = resultado.replace("]\"]", "]]");
        resultado = resultado.replace("]\",", "],");
        if (contiene_llave) {
            resultado = resultado.replace(":\"{", ":{");
            resultado = resultado.replace(",\"{", ",{");
            resultado = resultado.replace("[\"{", "[{");
            resultado = resultado.replace("}\",", "},");
            resultado = resultado.replace("}\"]", "}]");
            resultado = resultado.replace("]\"}", "]}");
            resultado = resultado.replace("\"{\"(\"}\"", "["); // desmarcar: {{
            resultado = resultado.replace("\"{\")\"}\"", "]"); // desmarcar: {{           
            resultado = resultado.replace("\"{{\"", "{{"); // desmarcar: {{
            resultado = resultado.replace("\"}}\"", "}}"); // desmarcar: {{
        }
        if (resultado.startsWith("{") == false) {
            if (resultado.startsWith("[") == false) {
                resultado = "{\"" + resultado + "\"}";
            } else if (resultado.startsWith("[{") == false) {
                resultado = "[\"" + resultado + "\"]";
            }
        }
        return resultado;
    } 
    
    /**
     * Adapta un texto a json_simple: 
     * - Convierte: ", \n, \r, \t, [, ], {, }, : y , en literales especiales: {'}, {n}, {r}, {t}, {(}, {)}, {{, }}, :: y ,,
     * @param valor_json cadena con el texto
     * @param error mensaje de error, si lo hay
     * @return el texto json_simple con comillas dobles añadidas, o null si hay error.
     */
    public static String convertir_valor_json_a_json_simple(String valor_json, String [] error) {
        String resultado = "";
        resultado = valor_json.replace(",", ",,");
        resultado = resultado.replace(":", "::");
        resultado = resultado.replace("{", "{{");
        resultado = resultado.replace("}", "}}");
        resultado = resultado.replace("[", "{(}");
        resultado = resultado.replace("]", "{)}");
        resultado = resultado.replace("\"", "{'}");
        resultado = resultado.replace("\n", "{n}");
        resultado = resultado.replace("\r", "{r}");
        resultado = resultado.replace("\t", "{t}");
        return resultado;
    }
        
}
