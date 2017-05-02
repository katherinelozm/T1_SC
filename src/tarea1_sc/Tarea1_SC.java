/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea1_sc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author katherinelozano
 */
public class Tarea1_SC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int opt = 0;
        do {
            System.out.println("Menu:\n1.Encriptar\n2.Desencriptar\n3.Decodificar\n4.Salir");
            opt = in.nextInt();
            switch(opt){
                case 1:
                    encrypt();
                    break;
                case 2:
                    decrypt();
                    break;
                case 3:
                    decipher();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        } while (opt!=4);
        
    }
    
    public static void encrypt(){
        Scanner in = new Scanner(System.in);
        String str;
        str = readFile();
        int shift = 0;
        System.out.println("Ingrese el corrimiento: ");
        shift = in.nextInt();
        char[] abc = new char[26];
        int start = 97;
        for (int i = 0; i < 26; i++) {
            abc[i] = (char) (start+i);
        }
        str = str.toLowerCase();
        String encrypted = "";
        for (int i = 0; i < str.length(); i++) {
            int pos = findPosition(abc, str.charAt(i));
            if (pos > -1) {
                int shiftedpos = pos+shift;
                if (shiftedpos>26) {
                    shiftedpos-=26;
                }
                encrypted+=abc[shiftedpos];
            } else {
                encrypted+=" ";
            }
        }
        writeFile(encrypted);
        System.out.println("\nMensaje encriptado: " + encrypted + "\n");
    }
    
    public static void decrypt(){
        Scanner in = new Scanner(System.in);
        String str;
        str = readFile();
        int shift = 0;
        System.out.println("Ingrese el corrimiento: ");
        shift = in.nextInt();
        char[] abc = new char[26];
        int start = 97;
        for (int i = 0; i < 26; i++) {
            abc[i] = (char) (start+i);
        }
        str = str.toLowerCase();
        String decrypted = "";
        for (int i = 0; i < str.length(); i++) {
            int pos = findPosition(abc, str.charAt(i));
            if (pos > -1) {
                int shiftedpos = pos-shift;
                if (shiftedpos<0) {
                    shiftedpos=26-shiftedpos;
                }
                decrypted+=abc[shiftedpos];
            } else {
                decrypted+=" ";
            }
        }
        System.out.println("\nMensaje desencriptado: " + decrypted + "\n");
    }
    
    public static void decipher(){
        String str;
        str = readFile();
        int shift = 1;
        char[] abc = new char[26];
        int start = 97;
        for (int i = 0; i < 26; i++) {
            abc[i] = (char) (start+i);
        }
        str = str.toLowerCase();
        ArrayList<String> dictionary = readDictionary();
        String deciphered = "";
        String shifted = "";
        boolean found = false;
            for (int i = 0; i < str.length(); i++) {
                int pos = findPosition(abc, str.charAt(i));
                if (pos > -1) {
                    int shiftedpos = pos-shift;
                    while (shiftedpos<0) {
                        shiftedpos=26-shiftedpos;
                    }
                    shifted+=abc[shiftedpos];
                } else {
                    shifted+=" ";
                    if (!found) {
                        for (int k = 0; k < dictionary.size(); k++) {
                            shifted = shifted.replaceAll(" ", "");
                            if (dictionary.get(k).equals(shifted)) {
                                found = true;
                                deciphered += shifted+" ";
                                shifted = "";
                                break;
                            }
                        }
                        if (!found) {
                            shift++;
                            shifted = "";
                            i=-1;
                        }
                    } 
                }
                if (found) {
                    deciphered += shifted;
                    shifted = "";
                }
                
            }
        System.out.println("\nMensaje decifrado: " + deciphered + "\n");
    }
    
    public static int findPosition(char[] abc, char c){
        for (int i = 0; i < abc.length; i++) {
            if (abc[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public static String readFile(){
        String str = "";
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int cont = 0;
        try {
            archivo = new File("./file.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                str += linea;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException ex) {
            }
        }
        return str;
    }
    
    
    public static ArrayList<String> readDictionary(){
        ArrayList<String> s = new ArrayList();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int cont = 0;
        try {
            archivo = new File("./diccionario.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.replaceAll(" ", "");
                s.add(linea);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException ex) {
            }
        }
        return s;
    }
    
    public static void writeFile(String str){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter("./file2.txt", false);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < str.length(); i++) {
                bw.write(str.charAt(i));
            }
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fw.close();
                bw.close();
            } catch (IOException ex) {

            }

        }
    }
    
}
