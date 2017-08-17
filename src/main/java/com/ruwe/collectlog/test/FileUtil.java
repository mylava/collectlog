package com.ruwe.collectlog.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author lipengfei
 */
public class FileUtil {

    public static String readJsonFile(String srcPath) {
        String line = null;
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(srcPath));
            line = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return line;
    }
}
