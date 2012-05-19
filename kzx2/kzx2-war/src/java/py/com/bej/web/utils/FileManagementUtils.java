/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego_M
 */
public class FileManagementUtils {

    public static final Logger LOGGER = Logger.getLogger(FileManagementUtils.class.getName());

    public static void crearArchivo(String nombreReporte) {
        try {
            FileWriter fw = new FileWriter(nombreReporte);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            salida.close();
        } catch (java.io.IOException ioex) {
            LOGGER.log(Level.SEVERE, "se presento el error: ", ioex);
        }
    }

    public static byte[] convertFileToBytes(File file) {

        byte[] b = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);

        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File Not Found.", e);
        } catch (IOException e1) {
            LOGGER.log(Level.SEVERE, "Error Reading The File.", e1);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileManagementUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return b;
    }

    public static void escribirByte(String nombre, byte[] info) {
        FileOutputStream fos = null;
        File archivo = null;
        try {
            archivo = new File(nombre);
            fos = new FileOutputStream(archivo);
        } catch (FileNotFoundException fnfe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", archivo.getAbsolutePath());
        }
        try {
            fos.write(info);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede escribir en el fichero {0}", archivo.getAbsolutePath());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", archivo.getAbsolutePath());
                }
            }
        }
    }

    public static void escribirByte(File nombre, byte[] info) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(nombre);
        } catch (FileNotFoundException fnfe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", nombre.getAbsolutePath());
        }
        try {
            fos.write(info);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede escribir en el fichero {0}", nombre.getAbsolutePath());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", nombre.getAbsolutePath());
                }
            }
        }
    }

    public static void escribirByte(String nombre, byte[] info, boolean agregar) {
        FileOutputStream fos = null;
        File archivo = null;
        try {
            archivo = new File(nombre);
            fos = new FileOutputStream(archivo, agregar);
        } catch (FileNotFoundException fnfe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", archivo.getAbsolutePath());
        }
        try {
            fos.write(info);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede escribir en el fichero {0}", archivo.getAbsolutePath());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", archivo.getAbsolutePath());
                }
            }
        }
    }

    public static void escribirByte(File nombre, byte[] info, boolean agregar) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(nombre, agregar);
        } catch (FileNotFoundException fnfe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", nombre.getAbsolutePath());
        }
        try {
            fos.write(info);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede escribir en el fichero {0}", nombre.getAbsolutePath());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", nombre.getAbsolutePath());
                }
            }
        }
    }

    public static void escribirChar(String nombre, char[] info) {
        FileWriter fos = null;
        File archivo = null;
        try {
            archivo = new File(nombre);
            fos = new FileWriter(archivo);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", archivo.getAbsolutePath());
        }
        try {
            fos.write(info);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede escribir en el fichero {0}", archivo.getAbsolutePath());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", archivo.getAbsolutePath());
                }
            }
        }
    }

    public static void escribirChar(File nombre, char[] info) {
        FileWriter fos = null;
        try {
            fos = new FileWriter(nombre);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", nombre);
        }
        try {
            fos.write(info);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede escribir en el fichero {0}", nombre);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", nombre);
                }
            }
        }
    }

    public static void escribirChar(String nombre, char[] info, boolean agregar) {
        FileWriter fos = null;
        File archivo = null;
        try {
            archivo = new File(nombre);
            fos = new FileWriter(archivo, agregar);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", archivo.getAbsolutePath());
        }
        try {
            fos.write(info);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede escribir en el fichero {0}", archivo.getAbsolutePath());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", archivo.getAbsolutePath());
                }
            }
        }
    }

    public static void escribirChar(File nombre, char[] info, boolean agregar) {
        FileWriter fos = null;
        try {
            fos = new FileWriter(nombre, agregar);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", nombre);
        }
        try {
            fos.write(info);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede escribir en el fichero {0}", nombre);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", nombre);
                }
            }
        }
    }

    public static void escribirObj(String nombre, Object obj) {
        ObjectOutputStream fos = null;
        File archivo = null;
        try {
            archivo = new File(nombre);
            fos = new ObjectOutputStream(new FileOutputStream(archivo));
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", nombre);
        }
        try {
            fos.writeObject(obj);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede escribir en el fichero {0}", nombre);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", nombre);
                }
            }
        }
    }

    public static void escribirObj(File nombre, Object obj) {
        ObjectOutputStream fos = null;
        try {
            fos = new ObjectOutputStream(new FileOutputStream(nombre));
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", nombre.getAbsolutePath());
        }
        try {
            fos.writeObject(obj);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede escribir en el fichero {0}", nombre.getAbsolutePath());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", nombre.getAbsolutePath());
                }
            }
        }
    }

    public static String leerFichero(String nombre) {
        FileReader fis = null;
        File archivo = new File(nombre);
        try {
            fis = new FileReader(archivo);
        } catch (FileNotFoundException fnfe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", nombre);
        }
        char[] info = new char[(int) archivo.length()];
        try {
            fis.read(info);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede leer del fichero {0}", nombre);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", nombre);
                }
            }
        }
        return new String(info);
    }

    public static String leerFichero(File nombre) {
        FileReader fis = null;
        try {
            fis = new FileReader(nombre);
        } catch (FileNotFoundException fnfe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", nombre.getAbsolutePath());
        }
        char[] info = new char[(int) nombre.length()];
        try {
            fis.read(info);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede leer del fichero {0}", nombre.getAbsolutePath());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", nombre.getAbsolutePath());
                }
            }
        }
        return new String(info);
    }

    public static Object leerObj(String nombre) {
        Object leido = null;
        ObjectInputStream fis = null;
        File archivo = new File(nombre);
        try {
            fis = new ObjectInputStream(new FileInputStream(archivo));
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", archivo.getAbsolutePath());
        }
        try {
            leido = fis.readObject();
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede leer del fichero {0}", archivo.getAbsolutePath());
        } catch (ClassNotFoundException cnfe) {
            LOGGER.log(Level.SEVERE, "No se puede convertir el objeto leido del fichero {0}", archivo.getAbsolutePath());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", archivo.getAbsolutePath());
                }
            }
        }
        return leido;
    }

    public static Object leerObj(File nombre) {
        Object leido = null;
        ObjectInputStream fis = null;
        try {
            fis = new ObjectInputStream(new FileInputStream(nombre));
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "Error de acceso al fichero {0}", nombre.getAbsolutePath());
        }
        try {
            leido = fis.readObject();
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "No se puede leer del fichero {0}", nombre.getAbsolutePath());
        } catch (ClassNotFoundException cnfe) {
            LOGGER.log(Level.SEVERE, "No se puede convertir el objeto leido del fichero {0}", nombre.getAbsolutePath());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ioe) {
                    LOGGER.log(Level.SEVERE, "No se puede cerrar el fichero {0}", nombre.getAbsolutePath());
                }
            }
        }
        return leido;
    }
}
