package Coeur.Entete.DHCP;

import Coeur.Conversion.Conversion;
import Coeur.Entete.Champs;

public class DHCPHeader {
    private final String header;

    private final String[] dhcpOptions = {"Pad","Subnet Mask","Time Offset","Router","Time Server","Name Server","Domaine Server","Log Server","Quotes Server","LPR Server","Impress Server","RLP Server","Hostname","Boot File Size","Merit Dump File","Domain Name","Swap Server","Root Path","Extension File","Forward On/Off","SrcRte On/Off","Policy Filter","Max DG Assembly","Default IP TTL","MTU Timeout","MTU Plateau","MTU Interface","MTU Subnet","Broadcast Address","Mask Discovery","Mask Supplier","Router Discovery","Router Request","Static Route","Trailers","ARP Timeout","Ethernet","Default TCP TTL","Keepalive Time","Keepalive Data","NIS Domain","NIS Servers","NTP Servers","Vendor Specific","NETBIOS Name Srv","NETBIOS Dist Srv","NETBIOS Node Type","NETBIOS Scope","X Window Font","X Window Manager","Address Request","Address Time","Overload","DHCP Msg Type","DHCP Server Id","Parameter List","DHCP Message","DHCP Max Msg Size","Renewal Time","Rebinding Time","Class Id","Client Id","NetWare/IP Domain","NetWare/IP Option","NIS-Domain-Name","NIS-Server-Addr","Server-Name","Bootfile-Name","Home-Agent-Addrs","SMTP-Server","POP3-Server","NNTP-Server","WWW-Server","Finger-Server","IRC-Server","StreetTalk-Server","STDA-Server","User-Class","Directory Agent","Service Scope","Rapid Commit","Client FQDN","Relay Agent Information","iSNS","REMOVED/Unassigned","NDS Servers","NDS Tree Name","NDS Context","BCMCS Controller Domain Name list","BCMCS Controller IPv4 address option","Authentication","client-last-transaction-time option","associated-ip option","Client System","Client NDI","LDAP","REMOVED/Unassigned","UUID/GUID","User-Auth","GEOCONF_CIVIC","PCode","TCode","REMOVED/Unassigned","REMOVED/Unassigned","REMOVED/Unassigned","REMOVED/Unassigned","REMOVED/Unassigned","REMOVED/Unassigned","IPv6-Only Preferred","OPTION_DHCP4O6_S46_SADDR","REMOVED/Unassigned","Unassigned","Netinfo Address","Netinfo Tag","DHCP Captive-Portal","REMOVED/Unassigned","Auto-Config","Name Service Search","Subnet Selection Option","Domain Search","SIP Servers DHCP Option","Classless Static Route Option","CCC","GeoConf Option","V-I Vendor Class","V-I Vendor-Specific Information","Removed/Unassigned","Removed/Unassigned","PXE - undefined (vendor specific)","PXE - undefined (vendor specific)","PXE - undefined (vendor specific)","PXE - undefined (vendor specific)","PXE - undefined (vendor specific)","PXE - undefined (vendor specific)","PXE - undefined (vendor specific)","PXE - undefined (vendor specific)","OPTION_PANA_AGENT","OPTION_V4_LOST","OPTION_CAPWAP_AC_V4","OPTION-IPv4_Address-MoS","OPTION-IPv4_FQDN-MoS","SIP UA Configuration Service Domains","OPTION-IPv4_Address-ANDSF","OPTION_V4_SZTP_REDIRECT","GeoLoc","FORCERENEW_NONCE_CAPABLE","RDNSS Selection","OPTION_V4_DOTS_RI","OPTION_V4_DOTS_ADDRESS","Unassigned","TFTP server address","status-code","base-time","start-time-of-state","query-start-time","query-end-time","dhcp-state","data-source","OPTION_V4_PCP_SERVER","OPTION_V4_PORTPARAMS","Unassigned","OPTION_MUD_URL_V4","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Etherboot (Tentatively Assigned - 2005-06-23)","IP Telephone (Tentatively Assigned - 2005-06-23)","Etherboot (Tentatively Assigned - 2005-06-23)","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","PXELINUX Magic","Configuration File","Path Prefix","Reboot Time","OPTION_6RD","OPTION_V4_ACCESS_DOMAIN","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Unassigned","Subnet Allocation Option","Virtual Subnet Selection (VSS) Option","Unassigned","Unassigned","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","Reserved (Private Use)","End"};
    private final String[] dhcpTypes = {"DHCP DISCOVER","DHCP OFFER","DHCP REQUEST","DHCP DECLINE","DHCP ACK","DHCP NAK","DHCP RELEASE","DHCP INFORM"};


    public DHCPHeader(String header) {
        this.header = header;
    }


    public String getMessageType() throws Exception {
        String messageType = Champs.getChamps(header,0,1);
        if (messageType.equals("01")) {
            return ("Message type: Boot Request (1)\n");
        }
        else {
            if (messageType.equals("02")) {
                return ("Message type: Boot Reply (2)\n");
            }
            else throw new Exception();
        }
    }

    public String getHardwareType() throws Exception {
        String hardwareType = Champs.getChamps(header,1,1);
        if (hardwareType.equals("01")) {
            return ("Hardware type: Ethernet (0x01)\n");
        }
        else throw new Exception();
    }

    public String getHardwareLength() {
        String hardwareLength = Champs.getChamps(header,2,1);
        return ("Hardware address length: " + hardwareLength);
    }

    public String getHops() {
        String hop= Champs.getChamps(header,3,1);
        return ("\nHops: " + hop + "\n");
    }

    public String getTransactionID() {
        String transactionid = Champs.getChamps(header,4,4);
        return ("Transaction ID: 0x" + transactionid.replaceAll(" ", "") +"\n");
    }

    public String getSecondsElapsed() {
        String seconds = Champs.getChamps(header,8,2);
        return ("Seconds elapsed: " + Integer.parseInt(seconds.replaceAll(" ", ""),16)+ "\n");
    }

    public String getBootpFlags() {
        StringBuilder sb = new StringBuilder();

        String bootp = Champs.getChamps(header,10,2).replaceAll(" ", "");
        String typeofcast;

        String bootpBinary = Conversion.hexToBinary(bootp);

        if (bootpBinary.charAt(0) == '0') typeofcast = "Unicast";
        else typeofcast = "Broadcast";

        sb.append("Bootp flags: 0x").append(bootp).append(" (").append(typeofcast).append(")\n");
        sb.append("\t").append(bootpBinary.charAt(0)).append("............... = Broadcast flag: ").append(typeofcast).append("\n");
        sb.append("\t" + ".").append(bootpBinary, 1, 16).append(" = Reserved flags: 0x").append(bootp).append("\n");
        return sb.toString();
    }

    public String getClientIPaddress() {
        StringBuilder sb = new StringBuilder("Client IP address : ");
        String s = Champs.getChamps(header,12,4);
        String[] octets = s.split("\\s+");
        for (String o : octets) {
            sb.append(Integer.parseInt(o, 16)).append(".");
        }
        sb.setLength(sb.length()-1);
        sb.append("\n");
        return sb.toString();
    }

    public String getYourClientIpAddress() {
        StringBuilder sb = new StringBuilder("Your (client) IP address : ");
        String s = Champs.getChamps(header,16,4);
        String[] octets = s.split("\\s+");
        for (String o : octets) {
            sb.append(Integer.parseInt(o, 16)).append(".");
        }
        sb.setLength(sb.length()-1);
        sb.append("\n");
        return sb.toString();
    }

    public String getNextServerIpAddress() {
        StringBuilder sb = new StringBuilder("Next server IP address : ");
        String s = Champs.getChamps(header,20,4);
        String[] octets = s.split("\\s+");
        for (String o : octets) {
            sb.append(Integer.parseInt(o, 16)).append(".");
        }
        sb.setLength(sb.length()-1);
        sb.append("\n");
        return sb.toString();
    }

    public String getRelayAgentIpAddress() {
        StringBuilder sb = new StringBuilder("Relay agent IP address : ");
        String s = Champs.getChamps(header,24,4);
        String[] octets = s.split("\\s+");
        for (String o : octets) {
            sb.append(Integer.parseInt(o, 16)).append(".");
        }
        sb.setLength(sb.length()-1);
        sb.append("\n");
        return sb.toString();
    }

    public String getClientMacAddress() {
        StringBuilder sb = new StringBuilder();
        String hardwareLength = getHardwareLength().replaceAll("Hardware address length: ", "");
        sb.append("Client Mac address : ").append(Champs.getChamps(header, 28, Integer.parseInt(hardwareLength)).replaceAll(" ", ":")).append("\n");
        int PaddingOcts = 16 - Integer.parseInt(hardwareLength);
        if (PaddingOcts!=0) {
            sb.append("Client hardware address padding: ").append("00".repeat(Math.max(0, PaddingOcts))).append("\n");
        }
        return sb.toString();
    }

    public String getServerHostName() {
        StringBuilder sb = new StringBuilder();
        String serverHostNameBytes = Champs.getChamps(header,44,64);

        if (Conversion.hexToAscii(serverHostNameBytes.replaceAll(" ","")).equals("")) {
            sb.append("Server host name not given\n");
        }
        else {
            sb.append("Server host name: ").append(Conversion.hexToAscii(serverHostNameBytes.replaceAll(" ", ""))).append("\n");
        }
        return sb.toString();
    }

    public String getBootFileName() {
        StringBuilder sb = new StringBuilder();
        String bootFileName = Champs.getChamps(header,108,128);

        if (Conversion.hexToAscii(bootFileName.replaceAll(" ","")).equals("")) {
            sb.append("Boot file name not given\n");
        }
        else {
            sb.append("Boot file name: ").append(Conversion.hexToAscii(bootFileName.replaceAll(" ", ""))).append("\n");
        }
        return sb.toString();
    }

    public String getMagicCookie() {
        String magicCookie = Champs.getChamps(header,236,4);
        if (magicCookie.equals("63 82 53 63")) {
            return "Magic cookie: DHCP\n";
        }
        else return ("Bootp vendore specific options: " + Champs.getChamps(header,236, Champs.nbrOctets(header)-236).replaceAll(" ", ""));
    }


    public String getOptions() {

        StringBuilder sb = new StringBuilder();

        String option;
        int optionType;
        int debutOption = 240;
        int length = 1;
        int exception = 0;
        option = Champs.getChamps(header, debutOption, 1);
        sb.append("Options: \n");
        if (!getMagicCookie().contains("DHCP")) { return "";}
        else {
            while (!option.equals("ff")) {
                sb.append("\t");
                optionType = Integer.parseInt(option, 16);

                sb.append("Option: (").append(optionType).append(") ").append(dhcpOptions[optionType]).append("\n");

                sb.append("\t").append(getOptionDetails(Champs.getChamps(header, debutOption, 2 + length)));

                debutOption += 2 + length;
                try {
                    length = Integer.parseInt(Champs.getChamps(header, debutOption + 1, 1), 16);
                }
                catch (Exception e) {
                    sb.append("\n\tOption: (255) End\n\n");
                    sb.append("Padding: No Padding\n");
                    exception = 1;
                }
                option = Champs.getChamps(header, debutOption, 1);
                sb.append("\n");
            }
            if (exception == 0) {
                sb.append("\t");
                sb.append("Option: (255) End");

                sb.append("\n\nPadding: ").append("00".repeat(Math.max(0, (Champs.nbrOctets(header) - debutOption - 1))));
            }

        }
        return sb.toString();
    }


    public String getOptionDetails(String s) {
        StringBuilder sb = new StringBuilder();
        String optionType = Champs.getChamps(s,0,1);
        String tmp;

        sb.append("\tLength: ").append(Integer.parseInt(Champs.getChamps(s, 1, 1), 16)).append("\n\t");
        switch(Integer.parseInt(optionType,16)) {
            case 58:
                tmp = Champs.getChamps(s,2,4).replaceAll(" ", "");
                sb.append("\tRenewal Time Value: (").append(Integer.parseInt(tmp.replaceAll("^0+(?!$)", ""), 16)).append("s)\n");
                break;


            case 53:
                int dhcpType = Integer.parseInt(Champs.getChamps(s,2,1),16);
                sb.append("\tDHCP: ").append(dhcpTypes[dhcpType - 1]).append(" (").append(dhcpType).append(")\n");
                break;


            case 59:
                tmp = Champs.getChamps(s,2,4).replaceAll(" ", "");
                sb.append("\tRebinding Time Value: (").append(Integer.parseInt(tmp.replaceAll("^0+(?!$)", ""), 16)).append("s)\n");
                break;


            case 51:
                tmp = Champs.getChamps(s,2,4).replaceAll(" ", "");
                sb.append("\tIP Address Lease Time: (").append(Integer.parseInt(tmp.replaceAll("^0+(?!$)", ""), 16)).append("s)\n");
                break;


            case 1:
                tmp = Champs.getChamps(s,2,4);
                String[] octets = tmp.split("\\s+");
                StringBuilder sbtmp = new StringBuilder();
                for (String o : octets) {

                    sbtmp.append(Integer.parseInt(o, 16)).append(".");

                }
                sbtmp.setLength(sbtmp.length()-1);

                sb.append("\tSubnet Mask: ").append(sbtmp).append("\n");
                break;

            case 61:
                tmp = Champs.getChamps(s,2,1);
                if (tmp.equals("01")) {
                    sb.append("\tHardware type: Ethernet (0x01)");
                }
                else {
                    sb.append("\tHardware type: Unknown (0x").append(tmp).append(")");
                }
                tmp = Champs.getChamps(s,3,6).replaceAll(" ", ":");
                sb.append("\n\tClient MAC address: ").append(tmp).append("\n");
                break;

            case 50:
                tmp = Champs.getChamps(s,2,4);
                octets = tmp.split("\\s+");
                sbtmp = new StringBuilder();
                for (String o : octets) {

                    sbtmp.append(Integer.parseInt(o, 16)).append(".");

                }
                sbtmp.setLength(sbtmp.length()-1);
                sb.append("\tRequested IP Address: ").append(sbtmp).append("\n");
                break;

            case 54:
                tmp = Champs.getChamps(s,2,4);
                octets = tmp.split("\\s+");
                sbtmp = new StringBuilder();
                for (String o : octets) {

                    sbtmp.append(Integer.parseInt(o, 16)).append(".");

                }
                sbtmp.setLength(sbtmp.length()-1);
                sb.append("\tDHCP Server Identifier: ").append(sbtmp).append("\n");
                break;

        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(getMessageType()).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sb.append(getHardwareType()).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append(getHardwareLength()).append("\n");
        sb.append(getHops()).append("\n");
        sb.append(getTransactionID()).append("\n");
        sb.append(getSecondsElapsed()).append("\n");
        sb.append(getBootpFlags()).append("\n");
        sb.append(getClientIPaddress()).append("\n");
        sb.append(getYourClientIpAddress()).append("\n");
        sb.append(getNextServerIpAddress()).append("\n");
        sb.append(getRelayAgentIpAddress()).append("\n");
        sb.append(getClientMacAddress()).append("\n");
        sb.append(getServerHostName()).append("\n");
        sb.append(getBootFileName()).append("\n");
        sb.append(getMagicCookie()).append("\n");
        sb.append(getOptions()).append("\n");

        return sb.toString();
    }
}
