package Coeur.Entete.IP;

import Coeur.Conversion.Conversion;
import Coeur.Entete.Champs;

public class IPHeader {

    private final String header;
    String[] options = {"End of Options List","No Operation","Security","Loose Source Route","Time Stamp","Extended Security","Commercial Security","Record Route","Stream ID","Strict Source Route","Experimental Measurement","MTU Probe","MTU Reply","Experimental Flow Control","Experimental Access Control","ENCODE","IMI Traffic Descriptor","Extended Internet Protocol","Traceroute","Address Extension","Router Alert","Selective Directed Broadcast","Unassigned (Released 18 October 2005)","Dynamic Packet State","Upstream Multicast Pkt.","Quick-Start","Unassigned","Unassigned","Unassigned","Unassigned","RFC3692-style Experiment"};

    public IPHeader (String header) {
        this.header = header;
    }

    public String getType() throws Exception {
        String s = Champs.getChamps(header,0,1);
        s = Conversion.hexToBinary(s);
        if (s.startsWith("0100")) {
            return (s.substring(0,4) + " ...." + " = " + "Version " + Integer.parseInt(s.substring(0, 4), 2) +"\n\n"); }
        else throw new Exception("Protocol non traité");
    }

    public String getHeaderLength() {
        String s = Champs.getChamps(header,0,1);
        s = Conversion.hexToBinary(s);
        return (".... " + s.substring(4,8) + " = Header Length: " +  Integer.parseInt(s.substring(4,8),2) * 4 + " bytes" + " (" + Integer.parseInt(s.substring(4, 8), 2) + ")\n\n");
    }

    public String getToS() {
        return "Differentiated Services Field: " +
                "0x" + Champs.getChamps(header, 1, 1) + "\n" +
                "\t" + Conversion.hexToBinary(Champs.getChamps(header, 1, 1)).substring(0, 6) + ".. = DSCP\n" +
                "\t......" + Conversion.hexToBinary(Champs.getChamps(header, 1, 1)).substring(6, 8) + " = ECN\n\n";
    }

    public String getTotalLength() {
        return ("Total Length : " + Integer.parseInt(Champs.getChamps(header,2,2).replaceAll(" ", ""),16)+"\n\n");
    }

    public String getIdentification() {
        return "Identification: 0x" + Champs.getChamps(header, 4, 2).replaceAll(" ", "") +
                " (" + Integer.parseInt(Champs.getChamps(header, 4, 2).replaceAll(" ", ""), 16) + ")\n\n";
    }

    public String getFlags() {
        StringBuilder sb = new StringBuilder();
        String flagsBin = Conversion.hexToBinary(Champs.getChamps(header,6,1));
        String RB = flagsBin.substring(0,1);
        String DF = flagsBin.substring(1,2);
        String MF = flagsBin.substring(2,3);


        sb.append("Flags: 0x").append(Champs.getChamps(header, 6, 1)).append("\n");

        sb.append("\t").append(RB).append("... .... = Reserved bit: ");
        if (RB.equals("0")) sb.append("Not set");
        else sb.append("Set");
        sb.append("\n\t.").append(DF).append(".. .... = Don't fragment: ");
        if (DF.equals("0")) sb.append("Not set");
        else sb.append("Set");
        sb.append("\n\t..").append(MF).append(". .... = More fragments: ");
        if (MF.equals("0")) sb.append("Not set");
        else sb.append("Set");
        sb.append("\n\n");
        return sb.toString();
    }

    public String getFragmentOffset() {
        String fragmentOffset =Conversion.hexToBinary(Champs.getChamps(header,6,4).replaceAll(" ", ""));

        return ("Fragment Offset: " + Integer.parseInt(fragmentOffset.substring(3,16),2)+"\n\n");
    }

    public String getTTL() {
        return ("Time to Live: " + Integer.parseInt(Champs.getChamps(header,8,1).replaceAll(" ", ""), 16)+"\n\n");
    }

    public String getProtocol() {
        String s = Champs.getChamps(header,9,1);
        if (Integer.parseInt(s,16) == 6) return ("Protocol TCP - not treated\n\n");
        if (Integer.parseInt(s,16) == 17) return ("Protocol: UDP (" + Integer.parseInt(s,16) +")\n\n");
        else return ("Unknown Protocol - not treated\n\n");
    }

    public String getChecksum() {
        return ("Header Checksum: 0x"+ Champs.getChamps(header,10,2).replaceAll(" ", "") + "\n\n");
    }

    public String getIpSrc() {
        StringBuilder sb = new StringBuilder("Source Address : ");
        String s = Champs.getChamps(header,12,4);
        String[] octets = s.split("\\s+");
        for (String o : octets) {
            sb.append(Integer.parseInt(o, 16)).append(".");
        }
        sb.setLength(sb.length()-1);
        sb.append("\n");
        return sb.toString();
    }

    public String getIpDest() {
        StringBuilder sb = new StringBuilder("Destination Address: ");
        String s = Champs.getChamps(header,16,4);
        String[] octets = s.split("\\s+");
        for (String o : octets) {
            sb.append(Integer.parseInt(o, 16)).append(".");
        }
        sb.setLength(sb.length()-1);
        sb.append("\n");
        return sb.toString();
    }

    public String getOptions() {
        StringBuilder sb = new StringBuilder();
        int headerLength = Integer.parseInt(Conversion.hexToBinary(Champs.getChamps(header, 0, 1)).substring(4, 8), 2);

        if (headerLength == 5) {
            return "";

        } else {
            int NbOct = headerLength * 4 - 20;
            int offset = 20;
            sb.append("\nOptions:");
            while (NbOct != 0) {

                String Type = Champs.getChamps(header, offset, 1);
                offset++;

                String TypeBinaire = Conversion.hexToBinary(Type);
                if (Integer.parseInt(TypeBinaire.substring(3, 8), 2) > 30) {
                    sb.append("\n\tErreur dans la partie d'options: Option inexistante\n");
                    return sb.toString();
                }
                sb.append("\n\tIP Option - ").append(options[Integer.parseInt(TypeBinaire.substring(3, 8), 2)]).append("\n");
                sb.append("\t\tType: ").append(Integer.parseInt(Type, 16)).append("\n");

                sb.append("\t\t\t").append(TypeBinaire.charAt(0)).append("... .... = ");

                if (TypeBinaire.charAt(0)=='0') {
                    sb.append("Copy on fragmentation: No\n");
                } else sb.append("Copy on fragmentation: Yes\n");

                sb.append("\t\t\t.").append(TypeBinaire, 1, 3).append(". .... = ");

                if (TypeBinaire.startsWith("00", 1)) sb.append("Class : Control (0)\n");

                else sb.append("Class : Unknown " + "(").append(Integer.parseInt(TypeBinaire.substring(1, 3), 2)).append(")\n");

                sb.append("\t\t\t...").append(TypeBinaire.charAt(3)).append(" ").append(TypeBinaire, 4, 8).append(" = Number: ");

                sb.append(options[Integer.parseInt(TypeBinaire.substring(3, 8), 2)]).append(" option (").append(Integer.parseInt(TypeBinaire.substring(3, 8), 2)).append(")\n");

                String length = "";
                if (!Type.equals("00"))
                {
                    length = Champs.getChamps(header,offset,1);
                    sb.append("\t\tLength: ").append(Integer.parseInt(length, 16));
                    offset++;
                }



                if (Type.equals("00")) {

                    NbOct=0;
                    continue;
                }

                if (Type.equals("07")) {

                    sb.append("\n\t\tPointer: ").append(Integer.parseInt(Champs.getChamps(header, offset, 1), 16));
                    offset++;
                    int ptrRR = 3;
                    String route; String routeState;
                    sb.append("\n");
                    while (ptrRR<Integer.parseInt(length,16)) {
                        route = this.IPHextoDec(Champs.getChamps(header, offset, 4));
                        if (route.equals("0.0.0.0")) routeState = "Empty Route";
                        else routeState = "Recorded Route";
                        sb.append("\t\t\t").append(routeState).append(": ").append(route);
                        ptrRR+=4;
                        offset+=4;
                        sb.append("\n");
                    }
                    }
                if (!Type.equals("07")) offset+= Integer.parseInt(length, 16) - 1;
                NbOct -= Integer.parseInt(length, 16);

                }
            }
        return sb.toString();
    }


    //Gives back the IP address in decimal values
    private String IPHextoDec (String IPHex){
        StringBuilder sb = new StringBuilder();
        String[] octets = IPHex.split("\\s+");
        for (String o : octets) {
            sb.append(Integer.parseInt(o, 16)).append(".");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(getType());
            sb.append(getHeaderLength());
            sb.append(getToS());
            sb.append(getTotalLength());
            sb.append(getIdentification());
            sb.append(getFlags());
            sb.append(getFragmentOffset());
            sb.append(getTTL());
            sb.append(getProtocol());
            sb.append(getChecksum());
            sb.append(getIpSrc());
            sb.append(getIpDest());
            sb.append(getOptions());
            return sb.toString();
        }
        catch (Exception e) {
            return "Erreur dans la partie IP";
        }
    }
}
