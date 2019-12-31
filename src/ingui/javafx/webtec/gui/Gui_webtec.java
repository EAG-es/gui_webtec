/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingui.javafx.webtec.gui;

import ingui.javafx.webtec.Webtec;
import innui.contextos.contextos;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author daw
 */
public class Gui_webtec extends Webtec {
       
    public Gui_webtec() {
        contexto = new contextos();
        rutas_mapa = new HashMap () { 
            {
                put("/", "innui.webtec.gui.ejemplo.ejemplo_pagina_principal"); //NOI18N
            }
        };
    }

    @Override
    public boolean configurar(Stage stage, String [] error) {
        boolean ret = true;
        stage.setTitle(java.util.ResourceBundle.getBundle("in/javafx/webtec/gui/in").getString("GUI (WEBTEC)"));
        ObservableList<Image> observableList = stage.getIcons();
        InputStream inputStream
                = Webtec.class.getResourceAsStream(
                "/recursos/ingui/javafx/webtec/gui/icono_web_carpeta.png"); //NOI18N
        Image image = new Image(inputStream);
        observableList.add(image);
        return ret;
    }
    
    @Override
    public boolean iniciar(String [] error) {
        boolean ret = true;
        Map <String, Object> datos_mapa;
        try {
            datos_mapa = new LinkedHashMap();
            ret = fXML_webtec_jafController.procesar_url(new URL("https://"+Webtec.k_prefijo_url+"/"), datos_mapa, error); //NOI18N
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/javafx/webtec/gui/in").getString("ERROR EN INICIAR EN GUI_WEBTEC. {0}"), new Object[] {error[0]});
            ret = false;
        }
        return ret;
    }
    
    public static void main(String[] args) {
        launch(Gui_webtec.class, args);
    }
    
}
