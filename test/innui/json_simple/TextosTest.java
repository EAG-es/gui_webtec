/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.json_simple;

import static innui.json_simple.Textos.convertir_json_simple_a_json;
import static innui.json_simple.Textos.convertir_valor_json_a_json_simple;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emilio
 */
public class TextosTest {
    
    public TextosTest() {
    }

    /**
     * Test of convertir_json_simple_a_json method, of class Urls.
     */
    @Test
    public void testConvertir_json_simple_a_json() {
        System.out.println("convertir_json_simple_a_json");
        String json_simple = 
                    "["
                    + "{"
                    + "parametro:fechas,"
                    + "titulo:Fecha::"
                    + "}\t, \n{"
                    + "parametro:lineas,"
                    + "nombre:texto,"
                    + "titulo:[[Introduzca,, un {'}texto{'}::],[{(}otra prueba{)}]]"
                    + "},{"
                    + "parametro:multilineas,"
                    + "nombre:opinion,titulo:Su opinión::"
                    + "},{"
                    + "parametro:enteros,"
                    + "nombre:valor,"
                    + "valor:10,"
                    + "titulo:Escriba un número::"
                    + "},{"
                    + "parametro: decimales,"
                    + "nombre:peso,"
                    + "valor:0.0,"
                    + "titulo:Escriba el peso,,{n}(3 decimales)::"
                    + "},{"
                    + "parametro:radios,"
                    + "titulo:Sí,"
                    + "nombre:si_no,"
                    + "checked:,"
                    + "valor:1"
                    + "},{"
                    + "parametro:radios,"
                    + "titulo:No,"
                    + "nombre:si_no,"
                    + "valor:0"
                    + "},{"
                    + "parametro:checkboxes,"
                    + "titulo:Acepto la LOPD,"
                    + "checked:,"
                    + "nombre:aceptacion,"
                    + "valor:1"
                    + "}"
                    + "]";        
        String[] error = { "" };
        /*
[{"parametro":"fechas","titulo":"Fecha:"},{"parametro":"lineas","nombre":"texto","titulo":[["Introduzca, un "texto":"],["[otra prueba]"]]},{"parametro":"multilineas","nombre":"opinion","titulo":"Su opinión:"},{"parametro":"enteros","nombre":"valor","valor":"10","titulo":"Escriba un número:"},{"parametro":" decimales","nombre":"peso","valor":"0.0","titulo":"Escriba el peso,
(3 decimales):"},{"parametro":"radios","titulo":"Sí","nombre":"si_no","checked":"","valor":"1"},{"parametro":"radios","titulo":"No","nombre":"si_no","valor":"0"},{"parametro":"checkboxes","titulo":"Acepto la LOPD","checked":"","nombre":"aceptacion","valor":"1"}]        
        */
        String expResult = "[{\"parametro\":\"fechas\",\"titulo\":\"Fecha:\"},{\"parametro\":\"lineas\",\"nombre\":\"texto\",\"titulo\":[[\"Introduzca, un \"texto\":\"],[\"[otra prueba]\"]]},{\"parametro\":\"multilineas\",\"nombre\":\"opinion\",\"titulo\":\"Su opinión:\"},{\"parametro\":\"enteros\",\"nombre\":\"valor\",\"valor\":\"10\",\"titulo\":\"Escriba un número:\"},{\"parametro\":\" decimales\",\"nombre\":\"peso\",\"valor\":\"0.0\",\"titulo\":\"Escriba el peso,\n" +
"(3 decimales):\"},{\"parametro\":\"radios\",\"titulo\":\"Sí\",\"nombre\":\"si_no\",\"checked\":\"\",\"valor\":\"1\"},{\"parametro\":\"radios\",\"titulo\":\"No\",\"nombre\":\"si_no\",\"valor\":\"0\"},{\"parametro\":\"checkboxes\",\"titulo\":\"Acepto la LOPD\",\"checked\":\"\",\"nombre\":\"aceptacion\",\"valor\":\"1\"}]";
        String result = convertir_json_simple_a_json(json_simple, error);
        assertEquals(expResult, result);
    }

    /**
     * Test of convertir_valor_json_a_json_simple method, of class Urls.
     */
    @Test
    public void testConvertir_valor_json_a_json_simple() {
        System.out.println("convertir_texto_a_json_simple");
        String valor_json = "\t{Introduzca, un \"texto\":\n}";
        String[] error = { "" };
        String expResult = "{t}{{Introduzca,, un {'}texto{'}::{n}}}";
        String result = convertir_valor_json_a_json_simple(valor_json, error);
        assertEquals(expResult, result);
    }
    
}
