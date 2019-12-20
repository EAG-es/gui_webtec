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
    public static String k_menu_aplicaciones_archivo = "menu_aplicaciones.json";
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String ruta;
        File file;
        String contenidos_texto = "";
        String acciones_texto = "";
        String objetos_texto = "";
        Map<String, String> [] contenidos_mapas_array = null;
        URL url_recurso;
        String nombre_plantilla;
        a_eles ele_contenido_array;
        a_eles ele_accion_array;
        a_eles ele_objeto_array;
        try {
            ele_contenido_array = contexto.leer("innui_webtec_gui_menu_aplicaciones_contenido_array");
            ele_accion_array = contexto.leer("innui_webtec_gui_menu_aplicaciones_accion_array");
            ele_objeto_array = contexto.leer("innui_webtec_gui_menu_aplicaciones_objeto_array");
            if (ele_contenido_array.es_nulo()) {
                nombre_plantilla = (String) objects_mapa.get(k_mapa_nombre_plantilla);
                ruta = k_ruta_plantillas + nombre_plantilla;
                file = new File(ruta);
                ruta = file.getParent();
                ruta = aumentar_ruta(ruta, k_menu_aplicaciones_archivo, error);
                ret = (ruta != null);
                if (ret) {
                    url_recurso = getClass().getResource(ruta);
                    file = new File(url_recurso.toURI());
                    ruta = file.getAbsolutePath();
                    contenidos_texto = Utf8.leer(ruta, error);
                    contenidos_mapas_array = Textos.leer(contenidos_texto, error);
                    ret = (contenidos_mapas_array != null);
                }
                if (ret) {
                    int i = 0;
                    String contenido_texto = "";
                    Set<String> acciones_set = new TreeSet();
                    Set<String> objetos_set = new TreeSet();
                    String [] palabras;
                    for (Map<String, String> contenido: contenidos_mapas_array) {
                        contenido_texto = contenido.get("texto");
                        palabras = contenido_texto.split("\\s");
                        i = 0;
                        for (String palabra : palabras) {
                            if (i == 0) {
                                acciones_set.add( 
                                        "{"
                                        + "\"texto\": \"" + palabra + "\","
                                        + "\"prefijo\": " + "\"<div class='innui_webtec_gui_menu_aplicaciones_elemento' onclick=innui_webtec_gui_menu_aplicaciones_encontrar_contenido('"
                                            + palabra + "') >\","
                                        + "\"sufijo\": " + "\"</div>&nbsp;&nbsp;&nbsp;&nbsp;\""
                                        + "}");
                            } else {
                                objetos_set.add( 
                                        "{"
                                        + "\"texto\": \"" + palabra + "\","
                                        + "\"prefijo\": " + "\"<div class='innui_webtec_gui_menu_aplicaciones_elemento' onclick=innui_webtec_gui_menu_aplicaciones_encontrar_contenido('"
                                            + palabra + "') >\","
                                        + "\"sufijo\": " + "\"</div>&nbsp;&nbsp;&nbsp;&nbsp;\""
                                        + "}");
                            }
                            i = i + 1;
                        }
                    }
                    acciones_texto = "[";
                    for (String texto: acciones_set) {
                        if (acciones_texto.endsWith("}")) {
                            acciones_texto = acciones_texto + ", ";
                        }
                        acciones_texto = acciones_texto + texto;
                    }                
                    acciones_texto = acciones_texto + "];";
                    objetos_texto = "[";
                    for (String texto: objetos_set) {
                        if (objetos_texto.endsWith("}")) {
                            objetos_texto = objetos_texto + ", ";
                        }
                        objetos_texto = objetos_texto + texto;
                    }                 
                    objetos_texto = objetos_texto + "];";
                    objects_mapa.put("innui_webtec_gui_menu_aplicaciones_contenido_array", contenidos_texto);
                    objects_mapa.put("innui_webtec_gui_menu_aplicaciones_accion_array", acciones_texto);
                    objects_mapa.put("innui_webtec_gui_menu_aplicaciones_objeto_array", objetos_texto);
                    contexto.fondear_con_datos(
                        "innui_webtec_gui_menu_aplicaciones_contenido_array", contenidos_texto
                        , "innui_webtec_gui_menu_aplicaciones_accion_array", acciones_texto
                        , "innui_webtec_gui_menu_aplicaciones_objeto_array", objetos_texto);
                }            
            } else {
                objects_mapa.put("innui_webtec_gui_menu_aplicaciones_contenido_array", ele_contenido_array.dar());
                objects_mapa.put("innui_webtec_gui_menu_aplicaciones_accion_array", ele_accion_array.dar());
                objects_mapa.put("innui_webtec_gui_menu_aplicaciones_objeto_array", ele_objeto_array.dar());
            }
        } catch (Exception e) {
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = "";
            }
            error[0] = "Error en ejecutar. " + error[0];
            ret = false;
        }
        return ret;
    }

}
