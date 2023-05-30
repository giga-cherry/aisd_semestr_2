package ru.itis.kpfu.skvortsova.aatree;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.FileWriter;
import java.io.IOException;

public class RandomNumbers {

    private static final int MAXL=10000;
    private static final int MAXS=100;
    private static final int MAXR=1000;
    private static final String PATH="C:\\Users\\Leingereld\\IdeaProjects\\AA-tree";
    private static final String PATH1="\\Array.txt";
    private static final String PATH2="\\ArrayS.txt";
    private static final String PATH3="\\ArrayR.txt";

    private int[] arr;
    private int[] arr1;
    private int[] arrS;
    private int[] arrR;

    public RandomNumbers() {
        arr=new int[MAXL];
        arr1=new int[MAXL];
        arrS=new int[MAXS];
        arrR=new int[MAXR];
    }

    public int[] createArray(){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1+(int) ( Math.random() * (Integer.MAX_VALUE-2) );
        }

        try (FileWriter fileWriter = new FileWriter(PATH+PATH1, true)) {
            for (int elem : arr) {
                fileWriter.append(String.valueOf(elem))
                        .append("\n");
                fileWriter.flush();
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }

        return arr;
    }

    public int[] createArrayS(){
        for (int i = 0; i < arr.length; i++) {
            arr1[i] = arr[i];
        }

        for (int i = 0; i < arrS.length; i++) {
            arrS[i] = nextRandom(arr1);
        }

        try (FileWriter fileWriter = new FileWriter(PATH+PATH2, true)) {
            for (int elem : arrS) {
                fileWriter.append(String.valueOf(elem))
                        .append("\n");
                fileWriter.flush();
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }

        return arrS;
    }

    public int[] createArrayR(){
        for (int i = 0; i < arr.length; i++) {
            arr1[i] = arr[i];
        }

        for (int i = 0; i < arrR.length; i++) {
            arrR[i] = nextRandom(arr1);
        }

        try (FileWriter fileWriter = new FileWriter(PATH+PATH3, true)) {
            for (int elem : arrR) {
                fileWriter.append(String.valueOf(elem))
                        .append("\n");
                fileWriter.flush();
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }

        return arrR;

    }

    private static int nextRandom(int[] numbers) {
        int resp=0;
        while (true) {
            int n = 1+(int)(Math.random()*(MAXL-1));
            int pos = numbers[n]!=-1 ? n : -1;
            if (pos >= 0) {
                resp=numbers[n];
                numbers[pos] = -1;
                return resp;
            }
        }
    }
}
