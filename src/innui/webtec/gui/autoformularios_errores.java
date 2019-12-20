/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static innui.archivos.Rutas.aumentar_ruta;
import innui.archivos.Utf8;
import innui.contextos.a_eles;
import innui.contextos.filas;
import static innui.html.Urls.completar_URL;
import static innui.html.Urls.extraer_path;
import static innui.html.Urls.extraer_protocolo;
import innui.webtec.String_webtec_controlador;
import innui.webtec.A_ejecutores;
import static innui.webtec.Ejecutores.k_mapa_ruta_base;
import static innui.webtec.Webtec_controlador.k_urls_fila_nombre;
import java.util.Map;
import java.net.URLEncoder;
import java.net.URL;
import java.util.LinkedHashMap;

/**
 *
 * @author emilio
 */
public class autoformularios_errores extends A_ejecutores {
    
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String_webtec_controlador string_webtec_controlador = new String_webtec_controlador();
        filas fila;
        a_eles a_ele;
        String url_recibida = "";
        String url_formulario = "";
        String path_formulario = "";
        String nombre_accion_plantilla = "";
        String nombre_accion_protocolo = "";
        URL url_accion_plantilla = null;
        String parametros = "";
        URL url_plantilla = null;
        String url_protocolo = "";
        String contenido = "";
        String ruta_json = "";
        Map<String, Object> objects_mapa_local = null;
        try {
            contexto.subir();
            a_ele = contexto.leer(k_urls_fila_nombre);
            fila = a_ele.dar();             
            int tam = fila.size();
            tam = tam - 1;
            if (tam >= 0) {
                a_ele = fila.get(tam);
                if (a_ele != null) {
                    url_recibida = a_ele.dar();
                    nombre_accion_plantilla = extraer_path(url_recibida, "", error);
                    ret = (nombre_accion_plantilla != null);
                    if (ret) {
                        nombre_accion_protocolo = extraer_protocolo(url_recibida, error);
                        ret = (nombre_accion_protocolo != null);
                    }                                     
                }
            } else {
                ret = false;
                error[0] = "No se ha podido obtener la url recibida. ";
            }
            if (ret) {
                while (true) {
                    tam = tam - 1;
                    if (tam < 0) {
                        ret = false;
                        error[0] = "No se ha podido obtener la url del formulario. ";
                        break;
                    }
                    a_ele = fila.get(tam);
                    if (a_ele != null) {
                        url_formulario = a_ele.dar();
                        path_formulario = extraer_path(url_formulario, "", error);
                        ret = (path_formulario != null);
                        if (ret == false) {
                            break;
                        }
                        if (path_formulario.startsWith(nombre_accion_plantilla) == false) { // url distinta de la del formulario
                            break;
                        }
                    }                
                }
            }
            if (ret) {
                ruta_json = (String) objects_mapa.get(k_mapa_ruta_base);
                ruta_json = aumentar_ruta(ruta_json, nombre_accion_plantilla, error);
                ret = (ruta_json != null);
            }
            if (ret) {
                contenido = Utf8.leer(ruta_json + ".json", error);
                ret = (contenido != null);                
            }
            if (ret) {
                ret = string_webtec_controlador.configurar(contexto, false, error);
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);                
                ret = poner_json_en_mapa(contenido, objects_mapa_local, error);
            }                             
            if (ret) {
                url_protocolo = extraer_protocolo(url_formulario, error);
                ret = (url_protocolo != null);
            }                                     
            if (ret) {
                url_accion_plantilla = completar_URL(nombre_accion_plantilla, nombre_accion_protocolo, error);
                ret = (url_accion_plantilla != null);
            }
            if (ret) {
                nombre_accion_plantilla = url_accion_plantilla.toExternalForm();
                nombre_accion_plantilla = URLEncoder.encode(nombre_accion_plantilla, "UTF-8");
                parametros = "accion=" + nombre_accion_plantilla + "&" + parametros;
                url_recibida = path_formulario + "?" + parametros;
            }
            if (ret) {
                url_plantilla = completar_URL(url_recibida, url_protocolo, error);
                ret = (url_plantilla != null);
            }
            if (ret) {
                ret = string_webtec_controlador.procesar_url(url_plantilla, objects_mapa_local, error);
            }
            if (ret == false) {
                string_webtec_controlador.poner_error(error[0]);
            }
            if (ret) {
                objects_mapa.put("innui_webtec_gui_autoformularios_errores", string_webtec_controlador.contenido);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = "";
            }
            error[0] = "Error al ejecutar en autoformularios_errores. " + error[0];
            ret = false;
        } finally {
            contexto.bajar();
        }
        return ret;
    }
    
    public boolean poner_json_en_mapa(String json_texto, Map<String, Object> objects_mapa, String [] error) {
        boolean ret = true;
        Gson gson;
        String nombre;
        String valor;
        Map<String, String> [] mapas_array = new Map [1];
        try {
            gson = new GsonBuilder().create();
            mapas_array = gson.fromJson(json_texto, mapas_array.getClass());
            for (Map<String, String> mapa: mapas_array) {
                nombre = mapa.get("nombre");
                if (nombre == null) {
                    ret = false;
                    error[0] = "No se encuentra el atributo: nombre. ";
                    break;
                }
                valor = (String) objects_mapa.get(nombre);
                mapa.put("valor_anterior", mapa.get("valor"));
                mapa.put("valor", valor);
            }
            if (ret) {
                objects_mapa.put("innui_webtec_gui_autoformularios_mapas_array", mapas_array);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = "";
            }
            error[0] = "Error al generar_parametros_url en autoformularios_errores. " + error[0];
            ret = false;
        }
        return ret;
    }

}
