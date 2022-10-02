package Coeur.Entete.DNS;

import Coeur.Conversion.Conversion;
import Coeur.Entete.Champs;

public class DNSHeader {

    private final String header;
    private int answersBegin; //Offset passed by the getQueries_ method to let the program know where answers will begin (right after the end of Queries)
    private int authorityBegin; //Offset passed by the getAnswers_ method to let the program know where Authority nameservers will begin (right after the end of answers)
    private int additionalBegin; //Offset passed by the getAuthority_ method to let the program know where Additional section will begin (right after the end of authorities)



    public DNSHeader (String header) {
        this.header = header;
    }



    public String getID() {
        return "Transaction ID: 0x" + Champs.getChamps(header, 0, 2).replaceAll(" ", "") + "\n";
    }

    public String getFlags() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbFinal = new StringBuilder();
        String flags = Champs.getChamps(header,2,2).replaceAll(" ", "");
        String flagsBinary = Conversion.hexToBinary(flags);
        String QR = flagsBinary.substring(0,1);
        String OpCode = flagsBinary.substring(1,5);
        String AA = flagsBinary.substring(5,6);
        String TC = flagsBinary.substring(6,7);
        String RD = flagsBinary.substring(7,8);
        String RA = flagsBinary.substring(8,9);
        String Z = flagsBinary.substring(9,10);
        String AD = flagsBinary.substring(10,11);
        String ADN = flagsBinary.substring(11,12);
        String RCODE = flagsBinary.substring(12,16);

        sbFinal.append("Flags: 0x").append(flags).append("\n");

        if (QR.equals("0")) {
            sb.append("\t");
            sb.append(QR).append("... .... .... .... = Response: Message is a query\n");

            switch (OpCode) {
                case "0000" : {
                    sb.append("\t");
                    sb.append(".000 0... .... .... = OpCode: Standard query(0)\n");break;
                }
                case "0001" : {
                    sb.append("\t");
                    sb.append(".000 1... .... .... = OpCode: Inverse query (1)\n");break;
                }
                case "0010" : {
                    sb.append("\t");
                    sb.append(".001 0... .... .... = OpCode: Server status request (2)\n");break;
                }
                case "0100" : {
                    sb.append("\t");
                    sb.append(".010 0... .... .... = OpCode: Zone change notification (4)\n");break;
                }
                case "0101" : {
                    sb.append("\t");
                    sb.append(".010 1... .... .... = OpCode: Dynamic update (5)\n");break;
                }
                case "0110" : {
                    sb.append("\t");
                    sb.append(".011 0... .... .... = OpCode: DNS Stateful operations (DSO) (6)\n");break;
                }
                default : {
                    sb.append("\t");
                    sb.append("Opcode ").append(OpCode).append(" :  Unknown\n");break;
                }
            }

            if (TC.equals("0")) {
                sb.append("\t");
                sb.append(".... ..0. .... .... = Truncated: Message is not truncated\n");
            }
            else {
                sb.append("\t");
                sb.append(".... ..1. .... .... = Truncated: Message is truncated\n");
            }

            if (RD.equals("1")) {
                sb.append("\t");
                sb.append(".... ...1 .... .... = Recursion desired: Do query recursively\n");
            }
            else {
                sb.append("\t");
                sb.append(".... ...0 .... .... = Recursion desired: Don't do query recursively\n");
            }

            if (Z.equals("0")) {
                sb.append("\t");
                sb.append(".... .... .0.. .... = Z: reserved (0)\n");
            }
            else {
                sb.append("\t");
                sb.append(".... .... .1.. .... = Z: reserved - INCORRECT");
            }

            if (ADN.equals("0")) {
                sb.append("\t");
                sb.append(".... .... ...0 .... =  Non-authenticated data: Unacceptable\n");
            }
            else {
                sb.append("\t");
                sb.append(".... .... ...1 .... =  Non-authenticated data: Acceptable\n");
            }
        }

        else {
            sb.append("\t");
            sb.append(QR).append("... .... .... .... = Response: Message is a response\n");

            switch (OpCode) {
                case "0000" : {
                    sb.append("\t");
                    sb.append(".000 0... .... .... = OpCode: Standard query(0)\n");break;
                }
                case "0001" : {
                    sb.append("\t");
                    sb.append(".000 1... .... .... = OpCode: Inverse query (1)\n");break;
                }
                case "0010" : {
                    sb.append("\t");
                    sb.append(".001 0... .... .... = OpCode: Server status request (2)\n");break;
                }
                case "0100" : {
                    sb.append("\t");
                    sb.append(".010 0... .... .... = OpCode: Zone change notification (4)\n");break;
                }
                case "0101" : {
                    sb.append("\t");
                    sb.append(".010 1... .... .... = OpCode: Dynamic update (5)\n");break;
                }
                case "0110" : {
                    sb.append("\t");
                    sb.append(".011 0... .... .... = OpCode: DNS Stateful operations (DSO) (6)\n");break;
                }
                default : {
                    sb.append("\t");
                    sb.append("Opcode ").append(OpCode).append(" :  Unknown\n");break;
                }
            }

            if (AA.equals("1")) {
                sb.append("\t");
                sb.append(".... .1.. .... .... = Authoritative: Server is an authority for domain\n");
            }
            else  {
                sb.append("\t");
                sb.append(".... .0.. .... .... = Authoritative: Server is not an authority for domain\n");
            }

            if (TC.equals("0")) {
                sb.append("\t");
                sb.append(".... ..0. .... .... = Truncated: Message is not truncated\n");
            }
            else {
                sb.append("\t");
                sb.append(".... ..1. .... .... = Truncated: Message is truncated\n");
            }

            if (RD.equals("1")) {
                sb.append("\t");
                sb.append(".... ...1 .... .... = Recursion desired: Do query recursively\n");
            }
            else {
                sb.append("\t");
                sb.append(".... ...0 .... .... = Recursion desired: Don't do query recursively\n");
            }

            if (RA.equals("0")) {
                sb.append("\t");
                sb.append(".... .... 0... .... = Recursion available: Server can't do recursive queries\n");
            }
            else {
                sb.append("\t");
                sb.append(".... .... 1... .... = Recursion available: Server can do recursive queries\n");
            }

            if (Z.equals("0")) {
                sb.append("\t");
                sb.append(".... .... .0.. .... = Z: reserved (0)\n");
            }
            else {
                sb.append("\t");
                sb.append(".... .... .1.. .... = Z: reserved - INCORRECT");
            }

            if (AD.equals("1")) {
                sb.append("\t");
                sb.append(".... .... ..1. .... = AD bit: Set\n");
            }
            else {
                sb.append("\t");
                sb.append(".... .... ..0. .... = Answer authenticated: Answer/authority portion was not authenticated by server\n");

            }

            if (ADN.equals("0")) {
                sb.append("\t");
                sb.append(".... .... ...0 .... = Non-authenticated data: Unacceptable\n");
            }
            else {
                sb.append("\t");
                sb.append(".... .... ...1 .... = Non-authenticated data: Acceptable\n");
            }

            switch (RCODE) {
                case "0000" : {
                    sb.append("\t");
                    sb.append(".... .... .... 0000 = Reply code: No error (0)\n");break;
                }
                case "0001" : {
                    sb.append("\t");
                    sb.append(".... .... .... 0001 = Reply code: Format error (1)\n");break;
                }
                case "0010" : {
                    sb.append("\t");
                    sb.append(".... .... .... 0010 = Reply code: Server failure (2)\n");break;
                }
                case "0011" : {
                    sb.append("\t");
                    sb.append(".... .... .... 0011 = Reply code: No such name (3)\n");break;
                }
                case "0100" : {
                    sb.append("\t");
                    sb.append(".... .... .... 0100 = Reply code: Not implemented (4)\n");break;
                }
                case "0101" : {
                    sb.append("\t");
                    sb.append(".... .... .... 0101 = Reply code: Refused (5)\n");break;
                }
                case "0110" : {
                    sb.append("\t");
                    sb.append(".... .... .... 0110 = Reply code: Name exists (6)\n");break;
                }
                case "0111" : {
                    sb.append("\t");
                    sb.append(".... .... .... 0111 = Reply code: RRset exists (7)\n");break;
                }
                case "1000" : {
                    sb.append("\t");
                    sb.append(".... .... .... 1000 = Reply code: RRset does not exist (8)\n");break;
                }
                case "1001" : {
                    sb.append("\t");
                    sb.append(".... .... .... 1001 = Reply code: Not authoritative (9)\n");break;
                }
                case "1010" : {
                    sb.append("\t");
                    sb.append(".... .... .... 1010 = Reply code: Name out of zone (10)\n");break;
                }
                case "1011" : {
                    sb.append("\t");
                    sb.append(".... .... .... 1011 = Reply code: DSO-Type not implemented (11)\n");break;
                }
                default : {
                    sb.append("\t");
                    sb.append("Reply code: UNKNOWN\n");break;
                }
            }
        }
        sbFinal.append(sb);
        return sbFinal.toString();
    }

    public String getQuestions() {
        int nbQuestions = Integer.parseInt(Champs.getChamps(header,4,2).replaceAll(" ",""),16);
        return ("Questions: "+ nbQuestions + "\n");
    }

    public String getAnswers() {
        int nbAnswers = Integer.parseInt(Champs.getChamps(header,6,2).replaceAll(" ",""),16);
        return ("Answer RRs: " + nbAnswers+"\n");
    }

    public String getAuthorities() {
        int nbAuthorities = Integer.parseInt(Champs.getChamps(header,8,2).replaceAll(" ",""),16);
        return ("Authority RRs: " + nbAuthorities + "\n");
    }


    public String getAdditionalRRs() {
        int nbAdd = Integer.parseInt(Champs.getChamps(header,10,2).replaceAll(" ",""),16);
        return ("Additional RRs: " + nbAdd + "\n");
    }



    public String getQueries_() {

        StringBuilder sb = new StringBuilder();
        StringBuilder url = new StringBuilder();

        int nbrQueries = Integer.parseInt(getQuestions().replaceAll("[^0-9]", ""));
        if (nbrQueries==0) { return "";}

        int labelLength; int labelStart = 12; int labelCount = 0;
        int mainOffset = labelStart;


        String label;
        sb.append("Queries:\n\tName: ");
        for (int i=0; i<nbrQueries; i++) {
            String oct = "";
            {
                while (!oct.equals("00")) {
                    labelLength = Integer.parseInt(Champs.getChamps(header, mainOffset, 1), 16);
                    mainOffset++;
                    label = Champs.getChamps(header, mainOffset, labelLength);
                    url.append(Conversion.hexToAscii(label.replaceAll(" ", "")));
                    url.append(".");
                    mainOffset += labelLength;
                    labelCount++;
                    oct = Champs.getChamps(header, mainOffset, 1);
                }


                mainOffset++;
                url.setLength(url.length() - 1);
                sb.append(url);

                sb.append("\n\t[Name Length: ").append(url.length()).append("]\n");

                sb.append("\t[Label count: ").append(labelCount).append("]\n");

                sb.append("\tType: ");
                String type = Champs.getChamps(header,mainOffset,2).replaceAll(" ", "");
                int typeDec = Integer.parseInt(Champs.getChamps(header,mainOffset,2).replaceAll(" ", ""),16);

                switch (type) {
                    case "0001" : sb.append("A (Host address) (").append(typeDec).append(")\n");break;
                    case "001c" : sb.append("AAAA (IPv6 address) (").append(typeDec).append(")\n");break;
                    case "000c" : sb.append("PTR (domain name pointer) (").append(typeDec).append(")\n");break;
                    case "0002" : sb.append("NS (authoritative Name Server) (").append(typeDec).append(")\n");break;
                    case "000f" : sb.append("MX (Mail eXchange) (").append(typeDec).append(")\n");break;
                    case "0005" : sb.append("CNAME (Canonical name record) (").append(typeDec).append(")\n");break;
                    default : sb.append("???? (Resource record not treated in our program)\n");break;
                }

                mainOffset+=2;
                String Qclass = Champs.getChamps(header,mainOffset,2).replaceAll(" ", "");

                switch (Qclass) {
                    case "0001" : sb.append("\tClass: IN (0x").append(Qclass).append(")\n");break;
                    case "0003" : sb.append("\tClass: CH (Chaos) (0x").append(Qclass).append(")\n");break;
                    case "0004" : sb.append("\tClass: HS (Hesiod) (0x").append(Qclass).append(")\n");break;
                    default : sb.append("\t???? (Class Type not treated in our program)\n");break;
                }
                mainOffset+=2;
            }
        }

        answersBegin = mainOffset;
        authorityBegin = mainOffset;
        additionalBegin = mainOffset;
        return sb.toString();
    }


    public String getAnswers_() {
        StringBuilder sb = new StringBuilder();
        StringBuilder url = new StringBuilder();

        int nbrAnswers = Integer.parseInt(getAnswers().replaceAll("[^0-9]", ""));
        if (nbrAnswers==0) { return "";}


        int labelLength;
        int labelStart = answersBegin;
        int dataLength;
        int mainOffset = labelStart;


        StringBuilder oct;
        String label;

        sb.append("\nAnswers:");
        for (int i = 0; i < nbrAnswers; i++) {
            //Name decoding

            url.setLength(0);

            String ptr =  Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");
            url.append(getCompressed(ptr));
            sb.append("\n\tName: ").append(url);

            mainOffset += 2;

            sb.append("\n\tType: ");
            String type = Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");
            int typeDec = Integer.parseInt(Champs.getChamps(header, mainOffset, 2).replaceAll(" ", ""), 16);

            mainOffset += 2;

            switch (type) {
                case "0001" : sb.append("\tA (Host address) (").append(typeDec).append(")\n");break;
                case "001c" : sb.append("\tAAAA (IPv6 address) (").append(typeDec).append(")\n");break;
                case "000c" : sb.append("\tPTR (domain name pointer) (").append(typeDec).append(")\n");break;
                case "0002" : sb.append("\tNS (authoritative Name Server) (").append(typeDec).append(")\n");break;
                case "000f" : sb.append("\tMX (Mail eXchange) (").append(typeDec).append(")\n");break;
                case "0005" : sb.append("\tCNAME (Canonical name record) (").append(typeDec).append(")\n");break;
                default : sb.append("\t???? (Resource record not treated in our program)\n");break;
            }

            String Qclass = Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");

            mainOffset += 2;

            switch (Qclass) {
                case "0001" : sb.append("\tClass: IN (0x").append(Qclass).append(")\n");break;
                case "0003" : sb.append("\tClass: CH (Chaos) (0x").append(Qclass).append(")\n");break;
                case "0004" : sb.append("\tClass: HS (Hesiod) (0x").append(Qclass).append(")\n");break;
                default : sb.append("\t???? (Class Type not treated in our program)\n");break;
            }

            sb.append("\tTime to Live: ").append(Integer.parseInt(Champs.getChamps(header, mainOffset, 4).replaceAll(" ", ""), 16)).append(" seconds\n");

            mainOffset += 4;

            dataLength = Integer.parseInt(Champs.getChamps(header, mainOffset, 2).replaceAll(" ", ""), 16);
            int cpt = dataLength;


            mainOffset += 2;

            sb.append("\tData length: ").append(dataLength).append("\n");


            switch (type) {
                case "0001": {
                    sb.append("\tAddress : ");
                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    for (String o : bytes) {
                        sb.append(Integer.parseInt(o, 16)).append(".");
                    }
                    sb.setLength(sb.length() - 1);
                    sb.append("\n");
                    mainOffset += dataLength;

                    break;
                }
                case "001c": {
                    sb.append("\tAAAA Address : ");
                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    for (int k = 0; k < dataLength; k += 2) {
                        String champsAdr = bytes[k] + bytes[k + 1];
                        sb.append(champsAdr.replaceAll("^0+(?!$)", "")).append(":");
                    }
                    sb.setLength(sb.length() - 1);

                    sb.append("\n");
                    mainOffset += dataLength;

                    break;
                }
                case "000c": {
                    sb.append("\tDomain Name: ");
                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    int k = 0;
                    for (String o : bytes) {
                        if (!Conversion.hexToBinary(o).startsWith("11")) {
                            sb.append(Conversion.hexToAscii(o));
                            k++;
                        } else {
                            sb.append(".");
                            ptr = bytes[k] + bytes[k + 1];
                            sb.append(getCompressed(ptr));
                            break;
                        }
                    }
                    mainOffset += dataLength;
                    break;
                }
                case "0005":

                    int offset = mainOffset;
                    oct = new StringBuilder(Champs.getChamps(header, offset, 1));
                    sb.append("\tCNAME: ");
                    while (cpt > 1) {
                        if (Conversion.hexToBinary(oct.toString()).startsWith("11")) {
                            offset++;
                            oct.append(Champs.getChamps(header, offset, 1));
                            sb.append(getCompressed(oct.toString()));
                            offset++;
                            oct = new StringBuilder(Champs.getChamps(header, offset, 1));
                            cpt -= 2;
                            continue;
                        }
                        labelLength = Integer.parseInt(Champs.getChamps(header, offset, 1), 16);
                        offset++;
                        label = Champs.getChamps(header, offset, labelLength);
                        sb.append(Conversion.hexToAscii(label.replaceAll(" ", "")));
                        sb.append(".");
                        offset += labelLength;
                        oct = new StringBuilder(Champs.getChamps(header, offset, 1));
                        cpt -= (1 + labelLength);
                    }
                    if (sb.toString().charAt(sb.length() - 1) == '.') {
                        sb.setLength(sb.length() - 1);
                    }
                    mainOffset += dataLength;
                    break;
                case "0002": {

                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    sb.append("\tName server: ");
                    int k = 0;
                    for (String o : bytes) {
                        if (!Conversion.hexToBinary(o).startsWith("11")) {
                            sb.append(Conversion.hexToAscii(o));
                            k++;
                        } else {
                            sb.append(".");
                            ptr = bytes[k] + bytes[k + 1];
                            sb.append(getCompressed(ptr));
                            break;
                        }
                    }
                    mainOffset += dataLength;
                    break;
                }
                case "000f": {
                    sb.append("\t Preference: ");
                    String preference = Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");
                    mainOffset += 2;
                    sb.append(Integer.parseInt(preference, 16)).append("\n");
                    sb.append("\tMail Exchange: ");
                    String s = Champs.getChamps(header, mainOffset, dataLength - 2);
                    StringBuilder sburl = new StringBuilder();
                    String[] bytes = s.split("\\s+");
                    int k = 0;
                    for (String o : bytes) {
                        if (!Conversion.hexToBinary(o).startsWith("11")) {
                            sburl.append(Conversion.hexToAscii(o));
                            k++;
                        } else {
                            sburl.append(".");
                            ptr = bytes[k] + bytes[k + 1];
                            sburl.append(getCompressed(ptr));
                            break;
                        }
                    }
                    sb.append(sburl);
                    mainOffset += (dataLength - 2);
                    break;
                }
            }
            sb.append("\n");
        }
        authorityBegin = mainOffset;
        additionalBegin = mainOffset;
        return sb.toString();
    }

    public String getAuthority_() {
        StringBuilder sb = new StringBuilder();
        StringBuilder url = new StringBuilder();

        int nbrAuthorities = Integer.parseInt(getAuthorities().replaceAll("[^0-9]", ""));
        if (nbrAuthorities==0) { return "";}


        int labelLength;
        int labelStart = authorityBegin;
        int dataLength;
        int mainOffset = labelStart;


        StringBuilder oct;
        String label;

        sb.append("\nAuthoritative Nameservers:");
        for (int i = 0; i < nbrAuthorities; i++) {
            //Name decoding

            url.setLength(0);

            String ptr =  Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");
            url.append(getCompressed(ptr));
            sb.append("\n\tName: ").append(url);

            mainOffset += 2;

            sb.append("\n\tType: ");
            String type = Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");
            int typeDec = Integer.parseInt(Champs.getChamps(header, mainOffset, 2).replaceAll(" ", ""), 16);

            mainOffset += 2;

            switch (type) {
                case "0001" : sb.append("\tA (Host address) (").append(typeDec).append(")\n");break;
                case "001c" : sb.append("\tAAAA (IPv6 address) (").append(typeDec).append(")\n");break;
                case "000c" : sb.append("\tPTR (domain name pointer) (").append(typeDec).append(")\n");break;
                case "0002" : sb.append("\tNS (authoritative Name Server) (").append(typeDec).append(")\n");break;
                case "000f" : sb.append("\tMX (Mail eXchange) (").append(typeDec).append(")\n");break;
                case "0005" : sb.append("\tCNAME (Canonical name record) (").append(typeDec).append(")\n");break;
                default : sb.append("\t???? (Resource record not treated in our program)\n");break;
            }

            String Qclass = Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");

            mainOffset += 2;

            switch (Qclass) {
                case "0001" : sb.append("\tClass: IN (0x").append(Qclass).append(")\n");break;
                case "0003" : sb.append("\tClass: CH (Chaos) (0x").append(Qclass).append(")\n");break;
                case "0004" : sb.append("\tClass: HS (Hesiod) (0x").append(Qclass).append(")\n");break;
                default : sb.append("\t???? (Class Type not treated in our program)\n");break;
            }

            sb.append("\tTime to Live: ").append(Integer.parseInt(Champs.getChamps(header, mainOffset, 4).replaceAll(" ", ""), 16)).append(" seconds\n");

            mainOffset += 4;

            dataLength = Integer.parseInt(Champs.getChamps(header, mainOffset, 2).replaceAll(" ", ""), 16);
            int cpt = dataLength;


            mainOffset += 2;

            sb.append("\tData length: ").append(dataLength).append("\n");


            switch (type) {
                case "0001": {
                    sb.append("\tAddress : ");
                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    for (String o : bytes) {
                        sb.append(Integer.parseInt(o, 16)).append(".");
                    }
                    sb.setLength(sb.length() - 1);
                    sb.append("\n");
                    mainOffset += dataLength;

                    break;
                }
                case "001c": {
                    sb.append("\tAAAA Address : ");
                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    for (int k = 0; k < dataLength; k += 2) {
                        String champsAdr = bytes[k] + bytes[k + 1];
                        sb.append(champsAdr.replaceAll("^0+(?!$)", "")).append(":");
                    }
                    sb.setLength(sb.length() - 1);

                    sb.append("\n");
                    mainOffset += dataLength;

                    break;
                }
                case "000c": {
                    sb.append("\tDomain Name: ");
                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    int k = 0;
                    for (String o : bytes) {
                        if (!Conversion.hexToBinary(o).startsWith("11")) {
                            sb.append(Conversion.hexToAscii(o));
                            k++;
                        } else {
                            sb.append(".");
                            ptr = bytes[k] + bytes[k + 1];
                            sb.append(getCompressed(ptr));
                            break;
                        }
                    }
                    mainOffset += dataLength;
                    break;
                }
                case "0005":

                    int offset = mainOffset;
                    oct = new StringBuilder(Champs.getChamps(header, offset, 1));
                    sb.append("\tCNAME: ");
                    while (cpt > 1) {
                        if (Conversion.hexToBinary(oct.toString()).startsWith("11")) {
                            offset++;
                            oct.append(Champs.getChamps(header, offset, 1));
                            sb.append(getCompressed(oct.toString()));
                            offset++;
                            oct = new StringBuilder(Champs.getChamps(header, offset, 1));
                            cpt -= 2;
                            continue;
                        }
                        labelLength = Integer.parseInt(Champs.getChamps(header, offset, 1), 16);
                        offset++;
                        label = Champs.getChamps(header, offset, labelLength);
                        sb.append(Conversion.hexToAscii(label.replaceAll(" ", "")));
                        sb.append(".");
                        offset += labelLength;
                        oct = new StringBuilder(Champs.getChamps(header, offset, 1));
                        cpt -= (1 + labelLength);
                    }
                    if (sb.toString().charAt(sb.length() - 1) == '.') {
                        sb.setLength(sb.length() - 1);
                    }
                    mainOffset += dataLength;
                    break;
                case "0002": {

                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    sb.append("\tName server: ");
                    int k = 0;
                    for (String o : bytes) {
                        if (!Conversion.hexToBinary(o).startsWith("11")) {
                            sb.append(Conversion.hexToAscii(o));
                            k++;
                        } else {
                            sb.append(".");
                            ptr = bytes[k] + bytes[k + 1];
                            sb.append(getCompressed(ptr));
                            break;
                        }
                    }
                    mainOffset += dataLength;
                    break;
                }
                case "000f": {
                    sb.append("\t Preference: ");
                    String preference = Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");
                    mainOffset += 2;
                    sb.append(Integer.parseInt(preference, 16)).append("\n");
                    sb.append("\tMail Exchange: ");
                    String s = Champs.getChamps(header, mainOffset, dataLength - 2);
                    StringBuilder sburl = new StringBuilder();
                    String[] bytes = s.split("\\s+");
                    int k = 0;
                    for (String o : bytes) {
                        if (!Conversion.hexToBinary(o).startsWith("11")) {
                            sburl.append(Conversion.hexToAscii(o));
                            k++;
                        } else {
                            sburl.append(".");
                            ptr = bytes[k] + bytes[k + 1];
                            sburl.append(getCompressed(ptr));
                            break;
                        }
                    }
                    sb.append(sburl);
                    mainOffset += (dataLength - 2);
                    break;
                }
            }
            sb.append("\n");
        }
        additionalBegin = mainOffset;
        return sb.toString();
    }


    public String getAdd_() {
        StringBuilder sb = new StringBuilder();
        StringBuilder url = new StringBuilder();

        int nbrAdd = Integer.parseInt(getAdditionalRRs().replaceAll("[^0-9]", ""));
        if (nbrAdd==0) { return "";}

        int labelLength;
        int labelStart = additionalBegin;
        int dataLength;
        int mainOffset = labelStart;

        StringBuilder oct;
        String label;

        sb.append("\nAdditional :");


        for (int i = 0; i < nbrAdd; i++) {

            //Name decoding

            url.setLength(0);

            String ptr =  Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");
            url.append(getCompressed(ptr));
            sb.append("\n\tName: ").append(url);

            mainOffset += 2;

            sb.append("\n\tType: ");
            String type = Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");
            int typeDec = Integer.parseInt(Champs.getChamps(header, mainOffset, 2).replaceAll(" ", ""), 16);

            mainOffset += 2;

            switch (type) {
                case "0001" : {
                    sb.append("\tA (Host address) (").append(typeDec).append(")\n");
                    break;
                }
                case "001c" : {
                    sb.append("\tAAAA (IPv6 address) (").append(typeDec).append(")\n");
                    break;
                }
                case "000c" : {
                    sb.append("\tPTR (domain name pointer) (").append(typeDec).append(")\n");break;
                }
                case "0002" : {
                    sb.append("\tNS (authoritative Name Server) (").append(typeDec).append(")\n");break;
                }
                case "000f" : {
                    sb.append("\tMX (Mail eXchange) (").append(typeDec).append(")\n");
                    break;
                }
                case "0005" : {
                    sb.append("\tCNAME (Canonical name record) (").append(typeDec).append(")\n");
                    break;
                }
                default : {
                    sb.append("\t???? (Resource record not treated in our program)\n");
                    break;
                }
            }

            String Qclass = Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");

            mainOffset += 2;

            switch (Qclass) {
                case "0001" : {
                    sb.append("\tClass: IN (0x").append(Qclass).append(")\n");break;
                }
                case "0003" : sb.append("\tClass: CH (Chaos) (0x").append(Qclass).append(")\n");break;
                case "0004" : {
                    sb.append("\tClass: HS (Hesiod) (0x").append(Qclass).append(")\n");break;
                }
                default : {
                    sb.append("\t???? (Class Type not treated in our program)\n");break;
                }
            }

            sb.append("\tTime to Live: ").append(Integer.parseInt(Champs.getChamps(header, mainOffset, 4).replaceAll(" ", ""), 16)).append(" seconds\n");

            mainOffset += 4;

            dataLength = Integer.parseInt(Champs.getChamps(header, mainOffset, 2).replaceAll(" ", ""), 16);
            int cpt = dataLength;

            mainOffset += 2;

            sb.append("\tData length: ").append(dataLength).append("\n");


            switch (type) {
                case "0001" : {
                    sb.append("\tAddress : ");
                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    for (String o : bytes) {
                        sb.append(Integer.parseInt(o, 16)).append(".");
                    }
                    sb.setLength(sb.length() - 1);
                    sb.append("\n");
                    mainOffset += dataLength;

                    break;
                }
                case "001c" : {
                    sb.append("\tAAAA Address : ");
                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    for (int k = 0; k < dataLength; k += 2) {
                        String champsAdr = bytes[k] + bytes[k + 1];
                        sb.append(champsAdr.replaceAll("^0+(?!$)", "")).append(":");
                    }
                    sb.setLength(sb.length() - 1);

                    sb.append("\n");
                    mainOffset += dataLength;

                    break;
                }
                case "000c" : {
                    sb.append("\tDomain Name: ");
                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    int k = 0;
                    for (String o : bytes) {
                        if (!Conversion.hexToBinary(o).startsWith("11")) {
                            sb.append(Conversion.hexToAscii(o));
                            k++;
                        } else {
                            sb.append(".");
                            ptr = bytes[k] + bytes[k + 1];
                            sb.append(getCompressed(ptr));
                            break;
                        }
                    }
                    mainOffset += dataLength;
                    break;
                }
                case "0005" : {
                    int offset = mainOffset;
                    oct = new StringBuilder(Champs.getChamps(header, offset, 1));
                    sb.append("\tCNAME: ");
                    while (cpt > 1) {
                        if (Conversion.hexToBinary(oct.toString()).startsWith("11")) {
                            offset++;
                            oct.append(Champs.getChamps(header, offset, 1));
                            sb.append(getCompressed(oct.toString()));
                            offset++;
                            oct = new StringBuilder(Champs.getChamps(header, offset, 1));
                            cpt -= 2;
                            continue;
                        }
                        labelLength = Integer.parseInt(Champs.getChamps(header, offset, 1), 16);
                        offset++;
                        label = Champs.getChamps(header, offset, labelLength);
                        sb.append(Conversion.hexToAscii(label.replaceAll(" ", "")));
                        sb.append(".");
                        offset += labelLength;
                        oct = new StringBuilder(Champs.getChamps(header, offset, 1));
                        cpt -= (1 + labelLength);
                    }
                    if (sb.toString().charAt(sb.length() - 1) == '.') {
                        sb.setLength(sb.length() - 1);
                    }
                    mainOffset += dataLength;
                    break;
                }
                case "0002" : {

                    String s = Champs.getChamps(header, mainOffset, dataLength);
                    String[] bytes = s.split("\\s+");
                    sb.append("\tName server: ");
                    int k = 0;
                    for (String o : bytes) {
                        if (!Conversion.hexToBinary(o).startsWith("11")) {
                            sb.append(Conversion.hexToAscii(o));
                            k++;
                        } else {
                            sb.append(".");
                            ptr = bytes[k] + bytes[k + 1];
                            sb.append(getCompressed(ptr));
                            break;
                        }
                    }
                    mainOffset += dataLength;
                    break;
                }
                case "000f" : {
                    sb.append("\t Preference: ");
                    String preference = Champs.getChamps(header, mainOffset, 2).replaceAll(" ", "");
                    mainOffset += 2;
                    sb.append(Integer.parseInt(preference, 16)).append("\n");
                    sb.append("\tMail Exchange: ");
                    String s = Champs.getChamps(header, mainOffset, dataLength - 2);
                    StringBuilder sburl = new StringBuilder();
                    String[] bytes = s.split("\\s+");
                    int k = 0;
                    for (String o : bytes) {
                        if (!Conversion.hexToBinary(o).startsWith("11")) {
                            sburl.append(Conversion.hexToAscii(o));
                            k++;
                        } else {
                            sburl.append(".");
                            ptr = bytes[k] + bytes[k + 1];
                            sburl.append(getCompressed(ptr));
                            break;
                        }
                    }
                    sb.append(sburl);
                    mainOffset += (dataLength - 2);
                    break;
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {

        return getID() + "\n" +
                getFlags() + "\n" +
                getQuestions() + "\n" +
                getAnswers() + "\n" +
                getAuthorities() + "\n" +
                getAdditionalRRs() + "\n" +
                getQueries_() + "\n" +
                getAnswers_() + "\n" +
                getAuthority_() + "\n" +
                getAdd_() + "\n";
    }


    //Gives back the corresponding URL based on the offset passed in the parameter (It will look for it in the previous lines)
    public String getCompressed(String offset) {
        StringBuilder sb = new StringBuilder();
        int ptr = Integer.parseInt(offset,16)-49152; //Remove the value 0b1100 0000 0000 0000 to have an offset starting from the header of DNS
        String oct = "";
        String binary = "";
        while (!oct.equals("00")) {
            if (!oct.equals("")) binary = Conversion.hexToBinary(oct).substring(0,2);
            if (!binary.equals("11")) {
                int labelLength = Integer.parseInt(Champs.getChamps(header, ptr, 1), 16);
                ptr++;
                String label = Champs.getChamps(header, ptr, labelLength);
                sb.append(Conversion.hexToAscii(label.replaceAll(" ", "")));
                sb.append(".");
                ptr += labelLength;
                oct = Champs.getChamps(header, ptr, 1);
            }
            else {
                sb.append(getCompressed(Champs.getChamps(header,ptr,2).replaceAll(" ", "")));
                return sb.toString();
            }
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }

}
