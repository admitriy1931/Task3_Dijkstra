package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        var data = args[0];
        var result = args[1];
        List<String> lines = new ArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String[] linesAsArrayWithSpace = lines.toArray(new String[lines.size()]);

        var vartexCount = Integer.parseInt(linesAsArrayWithSpace[0]);


        int start = Integer.parseInt(linesAsArrayWithSpace[vartexCount+1]) - 1;
        int v = Integer.parseInt(linesAsArrayWithSpace[vartexCount+1]) - 1;
        int end = Integer.parseInt(linesAsArrayWithSpace[vartexCount+2]) - 1;

        int[][] matrix = new int[vartexCount][vartexCount];
        int[] t = new int[vartexCount];
        for (int i = 0; i < vartexCount; i++){
            t[i] = Integer.MAX_VALUE;
        }
        for (int i = 1; i < vartexCount + 1; i++) {
            for (int j = 0; j < vartexCount; j++){
                var str = linesAsArrayWithSpace[i].replaceAll("\\s+", " ").split(" ");;
                matrix[i - 1][j] = Integer.parseInt(str[j]);
                //System.out.println(matrix[i-1][j]);
            }
        }

        var m = new int[vartexCount];
        var s = new HashSet<Integer>();
        s.add(v);
        t[v] = 1;
        while (v!= -1){
            for (int j:getIncidentVertices(v, matrix)) {
                if(s.contains(j) == false){
                    var w = t[v] * matrix[v][j];
                    if( w < t[j]){
                        t[j] = w;
                        m[j] = v;
                    }
                }
            }
            v = argMin(t, s);
            if (v >= 0){
                s.add(v);
            }
        }
        var endList = new ArrayList<Integer>();
        for(int l = end; l!= start; l = m[l]){
            endList.add(l);
        }
        endList.add(start);
        Collections.sort(endList);

        FileWriter writter = new FileWriter(result);
        if(endList.size() != 0){
            writter.append("Y" + "\n");
            for (int i = 0; i<endList.size();i++){
                writter.append(endList.get(i) + 1 + " ");
            }
            writter.append("\n" + t[t.length-1] + " ");
        }
        else{
            writter.append("N");
        }
        writter.flush();


    }

    public static ArrayList<Integer> getIncidentVertices(int v, int[][] d){
        var inc = new ArrayList<Integer>();
        for(int j = 0; j < d.length; j++){
            if(d[v][j] < 32767){
                inc.add(j);
            }
        }
        return inc;
    }

    public  static Integer argMin(int[] t, HashSet<Integer> s){
        int aMin = -1;

        int max = Integer.MAX_VALUE;
        for(int i = 0; i < t.length; i++){
            if(t[i] < max && s.contains(i) == false){
                max = t[i];
                aMin = i;
            }
        }
        return aMin;
    }
}
