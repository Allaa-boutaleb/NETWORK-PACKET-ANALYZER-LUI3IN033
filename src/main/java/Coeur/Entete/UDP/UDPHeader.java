package Coeur.Entete.UDP;

import Coeur.Entete.Champs;

public class UDPHeader {

    private final String header;
    private final int nbrOctets; //Used for the Payload

    public UDPHeader(String header) {
        this.header = header;
        String[] octets = header.split("\\s+");
        nbrOctets = octets.length;

    }

    public String getPortSrc() {
        return ("Source Port: " + Integer.parseInt(Champs.getChamps(header,0,2).replaceAll(" ", ""),16)+"\n");
    }

    public String getPortDest() {
        return ("Destination Port: " + Integer.parseInt(Champs.getChamps(header,2,2).replaceAll(" ", ""),16)+"\n");
    }

    public String getLength() {
        return ("Length: " + Integer.parseInt(Champs.getChamps(header,4,2).replaceAll(" ", ""),16)+"\n");

    }

    public String getChecksum() {
        String checksum = Champs.getChamps(header,6,2).replaceAll(" ", "");
        if (checksum.equals("0000")) return ("- [Checksum: [missing]]\n");
        else return ("[Checksum: [0x" + Champs.getChamps(header,6,2).replaceAll(" ", "")+"]]\n");

    }

    public String getPayload() {
        int payloadLength = nbrOctets-8;
        return "UDP payload " +
                "(" + payloadLength + " bytes)\n";
    }


    @Override
    public String toString() {
        return getPortSrc() + "\n" +
                getPortDest() + "\n" +
                getLength() + "\n" +
                getChecksum() + "\n" +
                getPayload() + "\n";
    }


}
