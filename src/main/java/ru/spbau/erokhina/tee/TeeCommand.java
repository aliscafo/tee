package ru.spbau.erokhina.tee;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TeeCommand {
    static private boolean isAppend;
    static private boolean isIgnore;
    static private int curIndex = 0;
    static ArrayList<String> listOfFiles = new ArrayList<>();

    static private int readFlags(String[] args) {
        while (args[curIndex].charAt(0) == '-' && args[curIndex].length() > 1) {
            for (int i = 1; i < args[curIndex].length(); i++) {
                if (args[curIndex].charAt(i) == 'a') {
                    isAppend = true;
                } else if (args[curIndex].charAt(i) == 'i') {
                    isIgnore = true;
                } else {
                    System.out.println("illegal option -- " + args[curIndex].charAt(i));
                    System.out.println("usage: java ru.spbau.erokhina.tee.TeeCommand [-ai] [file ...]");
                    return 0;
                }
            }

            curIndex++;
        }
        return 1;
    }

    static private void readFiles(String[] args) {
        for (; curIndex < args.length; curIndex++) {
            listOfFiles.add(args[curIndex]);
        }
    }

    static private void createFiles() throws IOException {
        for (String fileName : listOfFiles) {
            File curFile = new File(fileName);
            if (!curFile.exists()) {
                try {
                    curFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            if (!isAppend) {
                try (PrintWriter writer = new PrintWriter(fileName)) {
                    writer.print("");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }

            }
        }
    }

    @SuppressWarnings("sunapi")
    static private void readAndWriteInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            System.out.println(str);
            for (String fileName : listOfFiles) {

                FileWriter fileWriter = new FileWriter(fileName, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.write(str + "\n");
                printWriter.close();
            }

            if (isIgnore) {
                Signal.handle(new Signal("INT"), new SignalHandler() {
                    public void handle(Signal sig) {
                    }
                });
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int isSuccess = readFlags(args);

        if (isSuccess == 0) {
            return;
        }

        readFiles(args);
        createFiles();

        try {
            readAndWriteInput();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
