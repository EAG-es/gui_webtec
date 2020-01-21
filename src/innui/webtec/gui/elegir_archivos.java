/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.gui;

import static innui.archivos.Rutas.aumentar_ruta;
import static innui.archivos.Rutas.leer_ruta_trabajo;
import static innui.html.Urls.completar_URL;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.webtec.A_ejecutores;
import innui.webtec.Rutas;
import innui.webtec.String_webtec_controlador;
import static innui.webtec.gui.autoformularios.k_dato_plantilla_ocultos;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_nombre;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_nombre_archivo;
import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_nombre_imagen;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_titulo;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_valor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author emilio
 */
public class elegir_archivos extends A_ejecutores {
    /**
     * Clase de cada elemento que elegir
     */
    public static String k_elegir_archivos_lineas = "elegir_archivos_lineas"; //NOI18N
    /**
     * Nombre del resultado que presentar
     */
    public static String k_mapa_elegir_archivos_elegir_archivos = "innui_webtec_gui_elegir_archivos"; //NOI18N
    /**
     * Ruta seleccionada
     */
    public static String k_mapa_elegir_archivos_ruta_seleccionada = "innui_webtec_gui_elegir_archivos_ruta_seleccionada"; //NOI18N
    public static String k_nombre_archivo_archivo = "archivo"; //NOI18N
    public static String k_nombre_archivo_directorio = "carpeta"; //NOI18N
    public static String k_nombre_imagen_archivo = java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ARCHIVO");
    public static String k_nombre_imagen_directorio = java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("CARPETA");
    public static String k_codificacion_texto = "UTF-8"; //NOI18N
    
    /**
     * Código para la construcción de un formulario automáticamenta a partir de un archivo JSON
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String ruta_base;
        File file = null;
        File superior_file = null;
        File [] files_array = null;
        List<File> files_lista = null;
        String formulario = ""; //NOI18N
        String_webtec_controlador string_webtec_controlador = null;
        String base_plantilla = ""; //NOI18N
        String elegir_archivos_lineas_plantilla = ""; //NOI18N
        String ocultos_plantilla = ""; //NOI18N
        URL url_plantilla = null;
        Map<String, Object> objects_mapa_local = null;
        String ruta_ultima = ""; //NOI18N
        String ruta_archivo = ""; //NOI18N
        String ruta_seleccionada = ""; //NOI18N
        try {
            ruta_base = (String) objects_mapa.get(k_mapa_autoformularios_valor);
            if (ruta_base != null && ruta_base.isEmpty() == false) {
                file = new File(ruta_base);
                if (file.exists()) {
                    ruta_seleccionada = file.getAbsolutePath();
                    if (file.isDirectory() == false) {
                        file = file.getParentFile();
                    }
                } else {
                    ruta_base = null;
                }
            } else {
                ruta_base = null;
            }
            if (ruta_base == null) {
                ruta_base = leer_ruta_trabajo(error);
                ret = (ruta_base != null);
                if (ret) {
                    file = new File(ruta_base);
                    ruta_seleccionada = file.getAbsolutePath();
                }
            }
            if (ret) {
                string_webtec_controlador = new String_webtec_controlador();
                base_plantilla = this.getClass().getPackage().getName();
                base_plantilla = Rutas.convertir_nombre_clase_a_ruta(base_plantilla, error);
                ret = (base_plantilla != null);
            }
            if (ret) {
                elegir_archivos_lineas_plantilla = aumentar_ruta(base_plantilla, k_elegir_archivos_lineas, error);
                ret = (elegir_archivos_lineas_plantilla != null); 
            }
            if (ret) {
                files_array = file.listFiles();
                files_lista = new ArrayList(Arrays.asList(files_array));
                superior_file = file.getParentFile();
                if (superior_file != null) {
                    files_lista.add(superior_file);
                }
                files_lista.sort(
                    new Comparator<File> () {
                        @Override
                        public int compare(File file_1, File file_2) {
                            return file_1.compareTo(file_2);
                        }                    
                });
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);
                if (objects_mapa_local.containsKey(k_mapa_autoformularios_nombre) == false) {
                    objects_mapa_local.put(k_mapa_autoformularios_nombre, k_mapa_elegir_archivos_ruta_seleccionada); 
                }
                for (File nodo_file: files_lista) {
                    if (nodo_file.isDirectory()) {
                        objects_mapa_local.put(k_mapa_autoformularios_nombre_archivo, k_nombre_archivo_directorio);
                        objects_mapa_local.put(k_mapa_autoformularios_nombre_imagen, k_nombre_imagen_directorio);
                    } else {
                        objects_mapa_local.put(k_mapa_autoformularios_nombre_archivo, k_nombre_archivo_archivo);
                        objects_mapa_local.put(k_mapa_autoformularios_nombre_imagen, k_nombre_imagen_archivo);
                    }
                    if (ret) {
                        ruta_archivo = nodo_file.getAbsolutePath();
                        objects_mapa_local.put(k_mapa_autoformularios_valor, ruta_archivo);
                        if (nodo_file == superior_file) {
                            objects_mapa_local.put(k_mapa_autoformularios_titulo, ".."); //NOI18N
                        } else {
                            objects_mapa_local.put(k_mapa_autoformularios_titulo, nodo_file.getName());
                        }
                        url_plantilla = completar_URL(elegir_archivos_lineas_plantilla, k_protocolo_por_defecto, error);
                        ret = (url_plantilla != null);
                    }
                    if (ret) {
                        ret = string_webtec_controlador.procesar_url_sin_historial(contexto, url_plantilla, objects_mapa_local, error);
                    }
                    if (ret == false) {
                        string_webtec_controlador.poner_error(error[0]);
                        formulario = string_webtec_controlador.contenido;
                        break;
                    } else {
                        formulario = formulario + string_webtec_controlador.contenido;
                    }
                }
            }
            if (ret) {
                ocultos_plantilla = aumentar_ruta(base_plantilla, k_dato_plantilla_ocultos, error); //NOI18N
                ret = (ocultos_plantilla != null); 
            }
            if (ret) {
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);
                url_plantilla = completar_URL(ocultos_plantilla, k_protocolo_por_defecto, error);
                if (objects_mapa_local.containsKey(k_mapa_autoformularios_nombre) == false) {
                    objects_mapa_local.put(k_mapa_autoformularios_nombre, k_mapa_elegir_archivos_ruta_seleccionada); 
                }
                objects_mapa_local.put(k_mapa_autoformularios_valor, ruta_seleccionada); 
                ret = string_webtec_controlador.procesar_url_sin_historial(contexto, url_plantilla, objects_mapa_local, error);
                if (ret == false) {
                    string_webtec_controlador.poner_error(error[0]);
                    formulario = string_webtec_controlador.contenido;
                } else {
                    formulario = formulario + string_webtec_controlador.contenido;
                }
                objects_mapa.put(k_mapa_autoformularios_valor, ruta_seleccionada); //NOI18N
            }
            if (ret) {
                objects_mapa.put(k_mapa_elegir_archivos_elegir_archivos, formulario);
            } else {
                objects_mapa.put(k_mapa_elegir_archivos_elegir_archivos, error[0]);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/innui/webtec/gui/in").getString("ERROR EN ELEGIR_ARCHIVOS.EJECUTAR. {0}"), new Object[] {error[0]});
            ret = false;
        }        return ret;
    }
}
