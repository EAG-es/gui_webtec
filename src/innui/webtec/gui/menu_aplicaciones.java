/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import static innui.archivos.Rutas.aumentar_ruta;
import innui.archivos.Utf8;
import innui.contextos.a_eles;
import innui.json.Textos;
import innui.webtec.A_ejecutores;
import static innui.webtec.Ejecutores.k_mapa_nombre_plantilla;
import static innui.webtec.chunk.Procesamientos.k_datos_extension;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import static innui.webtec.chunk.Procesamientos.k_ruta_plantillas;

/**
 *
 * @author daw
 */
public class menu_aplicaciones extends A_ejecutores {
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String ruta;
        File file;
        String contenidos_texto = ""; //NOI18N
        String acciones_texto = ""; //NOI18N
        String objetos_texto = ""; //NOI18N
        Map<String, String> [] contenidos_mapas_array = null;
        URL recurso_url;
        String nombre_plantilla;
        a_eles ele_contenido_array;
        a_eles ele_accion_array;
        a_eles ele_objeto_array;
        try {
            ele_contenido_array = contexto.leer("innui_webtec_gui_menu_aplicaciones_contenido_array"); //NOI18N
            ele_accion_array = contexto.leer("innui_webtec_gui_menu_aplicaciones_accion_array"); //NOI18N
            ele_objeto_array = contexto.leer("innui_webtec_gui_menu_aplicaciones_objeto_array"); //NOI18N
            if (ele_contenido_array.es_nulo()) {
                nombre_plantilla = (String) objects_mapa.get(k_mapa_nombre_plantilla);
                ruta = k_ruta_plantillas + nombre_plantilla;
                file = new File(ruta);
                ruta = file.getParent();
                ruta = aumentar_ruta(ruta, getClass().getSimpleName() + k_datos_extension, error);
                ret = (ruta != null);
                if (ret) {
                    recurso_url = getClass().getResource(ruta);
                    file = new File(recurso_url.toURI());
                    ruta = file.getAbsolutePath();
                    contenidos_texto = Utf8.leer(ruta, error);
                    contenidos_mapas_array = Textos.leer(contenidos_texto, error);
                    ret = (contenidos_mapas_array != null);
                }
                if (ret) {
                    int i = 0;
                    String contenido_texto = ""; //NOI18N
                    Set<String> acciones_set = new TreeSet();
                    Set<String> objetos_set = new TreeSet();
                    String [] palabras;
                    for (Map<String, String> contenido: contenidos_mapas_array) {
                        contenido_texto = contenido.get("texto"); //NOI18N
                        palabras = contenido_texto.split("\\s"); //NOI18N
                        i = 0;
                        for (String palabra : palabras) {
                            if (i == 0) {
                                acciones_set.add( 
                                        "{"
                                        + "\"texto\": \"" + palabra + "\"," //NOI18N //NOI18N
                                        + "\"prefijo\": " + "\"<a href='' class='innui_webtec_gui_menu_aplicaciones_a' onclick=\\\"innui_webtec_gui_menu_aplicaciones_encontrar_contenido('\"," //NOI18N
                                        + "\"infijo\": " + "\"'); return false;\\\" >\"," //NOI18N
                                        + "\"sufijo\": " + "\"</a>&nbsp;&nbsp;&nbsp;&nbsp;\"" //NOI18N
                                        + "}"); //NOI18N
                            } else {
                                objetos_set.add( 
                                        "{"
                                        + "\"texto\": \"" + palabra + "\"," //NOI18N //NOI18N
                                        + "\"prefijo\": " + "\"<a href='' class='innui_webtec_gui_menu_aplicaciones_a' onclick=\\\"innui_webtec_gui_menu_aplicaciones_encontrar_contenido('\"," //NOI18N
                                        + "\"infijo\": " + "\"'); return false;\\\" >\"," //NOI18N
                                        + "\"sufijo\": " + "\"</a>&nbsp;&nbsp;&nbsp;&nbsp;\"" //NOI18N
                                        + "}"); //NOI18N
                            }
                            i = i + 1;
                        }
                    }
                    acciones_texto = "["; //NOI18N
                    for (String texto: acciones_set) {
                        if (acciones_texto.endsWith("}")) { //NOI18N
                            acciones_texto = acciones_texto + ", "; //NOI18N
                        }
                        acciones_texto = acciones_texto + texto;
                    }                
                    acciones_texto = acciones_texto + "]"; //NOI18N
                    objetos_texto = "["; //NOI18N
                    for (String texto: objetos_set) {
                        if (objetos_texto.endsWith("}")) { //NOI18N
                            objetos_texto = objetos_texto + ", "; //NOI18N
                        }
                        objetos_texto = objetos_texto + texto;
                    }                 
                    objetos_texto = objetos_texto + "]"; //NOI18N
                    objects_mapa.put("innui_webtec_gui_menu_aplicaciones_contenido_array", contenidos_texto); //NOI18N
                    objects_mapa.put("innui_webtec_gui_menu_aplicaciones_accion_array", acciones_texto); //NOI18N
                    objects_mapa.put("innui_webtec_gui_menu_aplicaciones_objeto_array", objetos_texto); //NOI18N
                    contexto.fondear_con_datos(
                        "innui_webtec_gui_menu_aplicaciones_contenido_array", contenidos_texto //NOI18N
                        , "innui_webtec_gui_menu_aplicaciones_accion_array", acciones_texto //NOI18N
                        , "innui_webtec_gui_menu_aplicaciones_objeto_array", objetos_texto); //NOI18N
                }            
            } else {
                objects_mapa.put("innui_webtec_gui_menu_aplicaciones_contenido_array", ele_contenido_array.dar()); //NOI18N
                objects_mapa.put("innui_webtec_gui_menu_aplicaciones_accion_array", ele_accion_array.dar()); //NOI18N
                objects_mapa.put("innui_webtec_gui_menu_aplicaciones_objeto_array", ele_objeto_array.dar()); //NOI18N
            }
        } catch (Exception e) {
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ERROR EN EJECUTAR. {0}"), new Object[] {error[0]});
            ret = false;
        }
        return ret;
    }

}
