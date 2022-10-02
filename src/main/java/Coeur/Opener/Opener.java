package Coeur.Opener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Opener {

    //Filters content of file and returns an ArrayList of packets in the form of strings of bytes
    public static ArrayList<String> filtreFinal(String fileName) throws Exception {
        BufferedReader br = null; StringBuilder res=new StringBuilder(); StringBuilder sb= new StringBuilder();
        List<String> tmpList = new ArrayList<>();
        ArrayList<String> finale = new ArrayList<>();
        try{
            br = new BufferedReader(new FileReader(fileName));
            String line;
            int nbrOctets=0;
            int lineIndex=0;

            while((line=br.readLine())!=null) {
                lineIndex+=1;

                String[] tmps=line.split(" ");

                if(tmps[0].equals("0000")) {
                    nbrOctets = 0;
                    if(lineIndex>1) {
                        tmpList.add(sb.toString());
                        sb = new StringBuilder();
                    }

                } else if(tmps[0].length()==4) {
                    if(HexCheck(tmps[0])) {
                        if(Integer.parseInt(tmps[0],16)==nbrOctets) {} else { throw new Exception("Offset Invalide ou manque d'octets dans la ligne : "+(lineIndex-1));}
                    } else { continue; }

                }else { continue; }


                for (int i =1; i<tmps.length; i++) {
                    if(tmps[i].length()==2 && HexCheck(tmps[i])) {
                        nbrOctets+=1;
                    }
                    sb.append(tmps[i].toLowerCase()).append(" ");
                }
                sb.deleteCharAt(sb.length()-1);
                res.append(line);
            }

        }
        catch (FileNotFoundException e)
        {
            throw e;
        }
        catch (IOException io) {
            System.out.println("Erreur lors de la lecture du fichier\n");
            io.printStackTrace();

        }
        finally {
            if(br!=null) {
                br.close();
            }
            
        }

        if(sb.length()>0 ) {
            tmpList.add(sb.toString());
        }
        
        String[] tmp = new String[tmpList.size()];
        for(int j=0;j<tmp.length;j++) {
            tmp[j]=tmpList.get(j);
        }
        String[][] beforeFinal = filtrage(tmp);

        for (String[] s : beforeFinal) {
            StringBuilder sb2 = new StringBuilder();
            for (String o : s) {
                sb2.append(o).append(" ");
            }
            sb2.setLength(sb2.length()-1);
            finale.add(sb2.toString());
        }

        if (finale.size()==0) throw new Exception("Fichier Vide");

        return finale;
    }

    public static String[] backToSpace(String[] in) {
        String tmp;
        for(int i=0;i<in.length;i++) {
            tmp = in[i];
            StringBuilder sb = new StringBuilder();
            for(int j=0;j<tmp.length();j++) {
                if(tmp.charAt(j)=='\n') {
                    sb.append(' ');
                }
                else {
                    sb.append(tmp.charAt(j));
                }
            }
            in[i] = sb.toString();
        }
        return in;
    }

    public static void filtrageP(String[] in) {
        String tmp;
        for(int i=0;i<in.length;i++) {
            tmp=in[i];
            String[] table = tmp.split(" ");
            StringBuilder news= new StringBuilder();
            for(int j=0; j<table.length-1;j++) {
                if(!Objects.equals(table[j], "") && table[j].length()==2 && HexCheck(table[j])) {
                    news.append(table[j]).append(" ");
                }
            }
            if(!table[table.length - 1].equals("") && table[table.length-1].length()==2) {
                news.append(table[table.length-1]);
            }
            in[i]=news.toString();
        }
    }

    public static String[][] filterageBlancs(String[] in) {
        String[][] result = new String[in.length][];
        String tmp;
        for(int i=0;i<in.length;i++) {
            tmp=in[i];
            result[i]=tmp.split(" ");
        }
        return result;

    }

    public static String[][] filtrage(String[] in){
        String[]restmp;
        String[][]res;
        restmp=backToSpace(in);
        filtrageP(restmp);
        res=filterageBlancs(in);
        return res;
    }

    public static boolean HexCheck(String s) {
        try {
            Long.parseLong(s,16);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }


}
