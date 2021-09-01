package com.boi.grp.utilities;

import java.io.File;
import java.io.IOException;

/**
 * Created by C112083 on 25/11/2020.
 */
public class JavaProcess {

    private JavaProcess() {}

    public static int exec(Class klass) throws IOException,
            InterruptedException {
        System.out.println("Java process");
        Process process = null;
        try {
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome +
                    File.separator + "bin" +
                    File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            String className = klass.getCanonicalName();
            ProcessBuilder builder = new ProcessBuilder(
                    javaBin, "-cp", classpath, className);

            process = builder.start();
            process.waitFor();
            System.out.println("exit value = "+process.exitValue());
            return process.exitValue();
        } catch (Exception e) {
            System.err.println("Error in JavaProcess Class, error = "+e.getMessage());
            return -1;
        }
    }

}
