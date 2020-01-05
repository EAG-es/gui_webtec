/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingui.javafx.webtec.gui;

import java.net.URL;
import java.net.URLClassLoader;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author emilio
 */
public class Gui_webtecTest {
    
    public Gui_webtecTest() {
    }

    /**
     * Test of configurar method, of class Gui_webtec.
     */
    @Ignore
    public void testConfigurar() {
        System.out.println("configurar");
        Stage stage = null;
        String[] error = null;
        Gui_webtec instance = new Gui_webtec();
        boolean expResult = false;
        boolean result = instance.configurar(stage, error);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iniciar method, of class Gui_webtec.
     */
    @Ignore
    public void testIniciar() {
        System.out.println("iniciar");
        String[] error = null;
        Gui_webtec instance = new Gui_webtec();
        boolean expResult = false;
        boolean result = instance.iniciar(error);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Gui_webtec.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = { "" };
        String comando;
        URL [] urls_array = ((URLClassLoader) (Thread.currentThread().getContextClassLoader())).getURLs();
        String classpath;
        classpath = "";
        for (URL url : urls_array) {
            if (classpath.isEmpty() == false) {
                classpath = classpath + ":";
            }
            classpath = classpath + url.toExternalForm();
        }
        comando = "java -classpath " + classpath + " ";
        comando = comando + Gui_webtec.class.getName() + " ";
        args[0] =
        "https://innui/webtec/";
        Runtime.getRuntime().exec(comando + args[0]);
        args[0] =
        "https://innui/webtec/gui/ejemplo/ejemplo_autoformularios?accion=https://innui/webtec/gui/ejemplo/ejemplo_procesar_autoformularios";
        Runtime.getRuntime().exec(comando + args[0]);
//        args[0] =
//        "https://innui/webtec/gui/ejemplo/ejemplo_procesar_autoformularios?fecha=&texto=&opinion=&valor=101112&peso=0.0&si_no=1&aceptacion=1&innui_webtec_gui_autoformularios_cancelacion=https%3A%2F%2Finnui%2Fwebtec%2F&innui_webtec_gui_autoformularios_enviar=";
//        Runtime.getRuntime().exec(comando + args[0]);
        args[0] =
        "https://innui/webtec/gui/ver_historiales";
        Runtime.getRuntime().exec(comando + args[0]);        
//        args[0] =
//        "https://innui/webtec/gui/ejemplo/ejemplo_procesar_autoformularios?fecha=&texto=&opinion=&valor=101112&peso=0.0&si_no=1&aceptacion=1&innui_webtec_gui_autoformularios_cancelacion=https%3A%2F%2Finnui%2Fwebtec%2F&innui_webtec_gui_autoformularios_enviar=";
//        Runtime.getRuntime().exec(comando + args[0]);
    }
    
}
