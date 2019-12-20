/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import static innui.webtec.Ejecutores.k_mapa_nombre_clase;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emilio
 */
public class Webtec_menuTest {
    
    public Webtec_menuTest() {
    }

    /**
     * Test of ejecutar method, of class menu_aplicaciones.
     */
    @Test
    public void testEjecutar() {
        System.out.println("ejecutar");
        Map<String, Object> objects_mapa = new HashMap();
        objects_mapa.put(k_mapa_nombre_clase, "innui.webtec.gui.Webtec_menu");
        String[] error = {""};
        menu_aplicaciones instance = new menu_aplicaciones();
        boolean expResult = true;
        boolean result = instance.ejecutar(objects_mapa, error);
        assertEquals(expResult, result);
    }
    
}
