package ru.itis.kpfu.skvortsova.aatree;

public class Main {
    public static void main(String[] args) {
        RandomNumbers rn=new RandomNumbers();
        int[] arr= rn.createArray();
        int[] arrS=rn.createArrayS();
        int[] arrR= rn.createArrayR();

//        int[] arr= new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13};
//        int[] arrS=new int[]{1,14};
//        int[] arrR= new int[]{1};

        AATree aat = new AATree();
        long starttime;
        long endTime;

        starttime = System.nanoTime();
        for (int i = 0; i < arr.length; i++) {
            aat.insert(arr[i]);
        }
        endTime = System.nanoTime();
        System.out.println((endTime - starttime)+" "+aat.getInsertTimes());

        starttime = System.nanoTime();
        for (int i = 0; i < arrS.length; i++) {
            aat.find(arrS[i]);
        }
        endTime = System.nanoTime();
        System.out.println((endTime - starttime)+" "+aat.getFindTimes());

        starttime = System.nanoTime();
        for (int i = 0; i < arrR.length; i++) {
            aat.remove(arrR[i]);
        }
        endTime = System.nanoTime();
        System.out.println((endTime - starttime)+" "+aat.getRemoveTimes());
    }
}
