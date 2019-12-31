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
     * - No hay comillas dobles en el texto. Ni acotando los atributos, ni en los datos.
     * - No hay espacios en blanco, ni saltos de linea, decorativos (salvo en la , separadora de objetos).
     * - Para que: ", \n, \r, \t, [, ], {, }, : y , sean literales, se ponen: {'}, {n}, {r}, {t}, {(}, {)}, {{, }}, ::, y ,,
     * @param json_simple cadena json_simple
     * @param error mensaje de error, si lo hay
     * @return el texto json_simple con comillas dobles añadidas, o null si hay error.
     */
    public static String convertir_json_simple_a_json(String json_simple, String [] error) {
        String resultado = json_simple;
        boolean contiene_llave = false;
        resultado = resultado.replace(",,", "\",,\""); // marcar: ,, //NOI18N
        resultado = resultado.replaceAll("\\s*,\\s+", ","); //NOI18N
        resultado = resultado.replaceAll("\\s+,\\s*", ","); //NOI18N
        resultado = resultado.replace("\",,\"", ",,"); // desmarcar: ,, //NOI18N
        if (resultado.contains("{")) { //NOI18N
            contiene_llave = true;
            resultado = resultado.replace("{{", "\"{{\""); // marcar: {{ //NOI18N
            resultado = resultado.replace("}}", "\"}}\""); // marcar: }} //NOI18N
            resultado = resultado.replace("{(}", "\"{(}\""); // marcar: {{ //NOI18N
            resultado = resultado.replace("{)}", "\"{)}\""); // marcar: {{            //NOI18N
            resultado = resultado.replace("{n}", "\n"); //NOI18N
            resultado = resultado.replace("{r}", "\r"); //NOI18N
            resultado = resultado.replace("{t}", "\t"); //NOI18N
            resultado = resultado.replace("{'}", "\""); //NOI18N
            resultado = resultado.replace("{", "{\""); //NOI18N
            resultado = resultado.replace("{\"{\"", "{{"); //NOI18N
            resultado = resultado.replace("}", "\"}"); //NOI18N
            resultado = resultado.replace("\"}\"}", "}}"); //NOI18N
        }
        resultado = resultado.replace(":", "\":\""); //NOI18N
        resultado = resultado.replace("\":\"\":\"", ":"); //NOI18N
        resultado = resultado.replace(",", "\",\""); //NOI18N
        resultado = resultado.replace("\",\"\",\"", ","); //NOI18N
        resultado = resultado.replace(":\"[", ":["); //NOI18N
        resultado = resultado.replace(",\"[", ",["); //NOI18N
        resultado = resultado.replace("[", "[\""); //NOI18N
        resultado = resultado.replace("[\"[", "[["); //NOI18N
        resultado = resultado.replace("]", "\"]"); //NOI18N
        resultado = resultado.replace("]\"]", "]]"); //NOI18N
        resultado = resultado.replace("]\",", "],"); //NOI18N
        if (contiene_llave) {
            resultado = resultado.replace(":\"{", ":{"); //NOI18N
            resultado = resultado.replace(",\"{", ",{"); //NOI18N
            resultado = resultado.replace("[\"{", "[{"); //NOI18N
            resultado = resultado.replace("}\",", "},"); //NOI18N
            resultado = resultado.replace("}\"]", "}]"); //NOI18N
            resultado = resultado.replace("]\"}", "]}"); //NOI18N
            resultado = resultado.replace("\"{\"(\"}\"", "["); // desmarcar: {{ //NOI18N
            resultado = resultado.replace("\"{\")\"}\"", "]"); // desmarcar: {{            //NOI18N
            resultado = resultado.replace("\"{{\"", "{{"); // desmarcar: {{ //NOI18N
            resultado = resultado.replace("\"}}\"", "}}"); // desmarcar: {{ //NOI18N
        }
        if (resultado.startsWith("{") == false) { //NOI18N
            if (resultado.startsWith("[") == false) { //NOI18N
                resultado = "{\"" + resultado + "\"}"; //NOI18N
            } else if (resultado.startsWith("[{") == false) { //NOI18N
                resultado = "[\"" + resultado + "\"]"; //NOI18N
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
        String resultado = ""; //NOI18N
        resultado = valor_json.replace(",", ",,"); //NOI18N
        resultado = resultado.replace(":", "::"); //NOI18N
        resultado = resultado.replace("{", "{{"); //NOI18N
        resultado = resultado.replace("}", "}}"); //NOI18N
        resultado = resultado.replace("[", "{(}"); //NOI18N
        resultado = resultado.replace("]", "{)}"); //NOI18N
        resultado = resultado.replace("\"", "{'}"); //NOI18N
        resultado = resultado.replace("\n", "{n}"); //NOI18N
        resultado = resultado.replace("\r", "{r}"); //NOI18N
        resultado = resultado.replace("\t", "{t}"); //NOI18N
        return resultado;
    }
        
}
