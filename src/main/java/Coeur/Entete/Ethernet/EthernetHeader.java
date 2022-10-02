package Coeur.Entete.Ethernet;

import Coeur.Conversion.Conversion;
import Coeur.Entete.Champs;

public class EthernetHeader {

    private final String header;



    public EthernetHeader (String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return getMacSrc() +
                getMacDest() +
                getType();
    }


    public String getType() {
        StringBuilder sb = new StringBuilder();
        String type = Champs.getChamps(header, 12,2);
        if (type.equals("08 00")) {
            sb.append("Type : IPv4 (0x0800)\n\n");
        }
        else {
            if (type.equals("08 06")) {
                sb.append("Type : ARP (0x0806) - not treated\n\n");
            }
            else {
                sb.append("Protocol not recognized\n\n");
            }
        }

        return sb.toString();
    }


    public String getMacSrc() {
        return "MAC Source Address: " + Champs.getChamps(header, 6, 6).replaceAll(" ", ":") + "\n" +
                getMacDetails(Champs.getChamps(header, 6, 6).replaceAll(" ", ":"));
    }

    public String getMacDest() {
        return "MAC Destination Address: " + Champs.getChamps(header, 0, 6).replaceAll(" ", ":") + "\n" +
                getMacDetails(Champs.getChamps(header, 0, 6).replaceAll(" ", ":"));
    }

    public String getMacDetails(String mac) {
        String tmp = mac.replaceAll(":", "");

        StringBuilder sb = new StringBuilder();
        String macBinaire = Conversion.hexToBinary(tmp);
        char LGbit = macBinaire.charAt(6);
        char IGbit = macBinaire.charAt(7);

        sb.append("\tLGBit : ").append(LGbit).append("\n");
        sb.append("\tIGBit : ").append(IGbit).append("\n\n");
        return sb.toString();
    }


}
