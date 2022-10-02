package com.example.projres;

import Coeur.Conversion.Conversion;
import Coeur.Entete.Champs;
import Coeur.Entete.DHCP.DHCPHeader;
import Coeur.Entete.DNS.DNSHeader;
import Coeur.Entete.Ethernet.EthernetHeader;
import Coeur.Entete.IP.IPHeader;
import Coeur.Entete.UDP.UDPHeader;
import Coeur.Opener.Opener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class HelloController {

    List<String> tramesString;
    String selectedTrame;



    @FXML
    private TabPane main;

    @FXML
    private Button openFile,analyseButton,exportButton, closeCreditsButton, creditsButton;

    @FXML
    private Tab HomeTab,BrutTab, EthernetTab, IpTab, UdpTab, DnsDhcpTab;

    @FXML
    private MenuButton TrameList;

    @FXML
    private TextArea trameAfterFilter, IPDecodage, EthernetDecodage, UDPDecodage, DnsDhcpDecodage, credits;

    @FXML
    private Label errorMessage;



    @FXML
    protected void onOpenFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir le fichier txt contenant la trame");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                "Fichier text (*.txt)", "*.txt"
        ));
        Stage stage = (Stage) main.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        // trames = Opener.openTrames(file.getAbsolutePath());
        try {
            tramesString = Opener.filtreFinal(file.getAbsolutePath());
        }
        catch (Exception e) {
            if (file.getAbsolutePath()==null) return;
                errorMessage.setText(e.getMessage());
                errorMessage.setTextFill(Color.RED);
                errorMessage.setVisible(true);
                selectedTrame = null;

                IpTab.setText("IP");
                UdpTab.setText("UDP");
                DnsDhcpTab.setText("DNS/DHCP");

                trameAfterFilter.clear();
                EthernetDecodage.clear();
                IPDecodage.clear();
                UDPDecodage.clear();
                DnsDhcpDecodage.clear();

                analyseButton.setDisable(true);
                exportButton.setDisable(true);
                TrameList.setDisable(true);
                return;
            }
        errorMessage.setVisible(false);
        TrameList.getItems().clear();
        for (int i = 0 ; i<tramesString.size() ; i++) {
            MenuItem m = new MenuItem("Trame "+(i+1));
            if (TrameList == null) TrameList = new MenuButton("Trames", null, m);
            else TrameList.getItems().add(m);
            if (i == 0) { TrameList.setText("Trame 1"); }
            m.setOnAction(event -> TrameList.setText(m.getText())
            );
        }

        analyseButton.setDisable(false);
        exportButton.setDisable(false);
        TrameList.setDisable(false);
    }

    @FXML
    protected void OnAnalyseButtonClick() {

        IpTab.setText("IP");
        UdpTab.setText("UDP");
        DnsDhcpTab.setText("DNS/DHCP");

        trameAfterFilter.clear();
        EthernetDecodage.clear();
        IPDecodage.clear();
        UDPDecodage.clear();
        DnsDhcpDecodage.clear();

        errorMessage.setVisible(false);

        String numTrame = TrameList.getText().replaceAll("[^0-9]", "");
        String s = tramesString.get(Integer.parseInt(numTrame)-1);

        String[] octets = s.split(" ");
        for (int i = 0; i < octets.length; i++) {
            try {
            } catch (Exception e) {

                errorMessage.setText("Erreur dans la trame numero " + numTrame + ": Octet " + (i + 1) + " (" + octets[i]+")");
                errorMessage.setTextFill(Color.RED);
                errorMessage.setVisible(true);
                selectedTrame = null;
                return;
            }
        }




        errorMessage.setText("Chargement de trame " + numTrame + " fait avec succès!");
        errorMessage.setTextFill(Color.GREEN);
        errorMessage.setVisible(true);

        selectedTrame = tramesString.get(Integer.parseInt(numTrame)-1);


        FillEthernet();
        String couche2 = EthernetDecodage.getText();

        if (couche2.contains("ARP")) {
            IpTab.setText("ARP");
            UdpTab.setText("");
            DnsDhcpTab.setText("");
            IPDecodage.clear();
            UDPDecodage.clear();
            DnsDhcpDecodage.clear();
        }
        else
        {
            FillIP();
            String couche3 = IPDecodage.getText();
            if (couche3.contains("TCP"))  {
                UdpTab.setText("TCP");
                DnsDhcpTab.setText("");
                UDPDecodage.clear();
                DnsDhcpDecodage.clear();
                return;
            }
            if (couche3.contains("Unknown Protocol"))
            {
                UdpTab.setText("Unknown");
                DnsDhcpTab.setText("");
                UDPDecodage.clear();
                DnsDhcpDecodage.clear();
                return;
            }
            IPDecodage.clear();
            IpTab.setText("IP");
            UdpTab.setText("UDP");
            FillUDP();
            String couche7 = UDPDecodage.getText();
            if (couche7.contains("Destination Port: 53")||couche7.contains("53")) {
                DnsDhcpTab.setText("DNS");
            } else if ((couche7.contains("Destination Port: 68") && couche7.contains("Source Port: 67"))   || (couche7.contains("Destination Port: 67") && couche7.contains("Source Port: 68"))) DnsDhcpTab.setText("DHCP");
            else DnsDhcpTab.setText("Unknown");
        }
    }



    @FXML
    protected void FillBrut() {
        trameAfterFilter.clear();
        if (selectedTrame != null) {
            trameAfterFilter.appendText(selectedTrame);
        }
    }

    @FXML
    protected void FillEthernet() {
        EthernetDecodage.clear();
        if (selectedTrame != null) {
            try {
                EthernetHeader ethernet = new EthernetHeader(Champs.getChamps(selectedTrame, 0, Champs.nbrOctets(selectedTrame)));
                EthernetDecodage.appendText(ethernet.toString());
                //if (ethernet.getType().contains("ARP")) selectedTrame=null;
            } catch (Exception e) {
                EthernetDecodage.setText("Erreur dans la partie Ethernet");
            }
        }
    }

    @FXML
    protected void FillIP() {
        IPDecodage.clear();
            if (selectedTrame != null && IpTab.getText().equals("IP")) {
                try {
                    IPHeader ip = new IPHeader(Champs.getChamps(selectedTrame, 14, Champs.nbrOctets(selectedTrame) - 14));
                    IPDecodage.appendText(ip.toString());
                } catch (Exception e) {
                    IPDecodage.setText("Erreur dans la partie IP");
                }
            }
        }

    @FXML
    protected void FillUDP() {
        UDPDecodage.clear();
        if (selectedTrame != null && IpTab.getText().equals("IP") && UdpTab.getText().equals("UDP"))  {
            try {
                String s = Champs.getChamps(selectedTrame, 14, 1);
                s = Conversion.hexToBinary(s);
                int i = Integer.parseInt(s.substring(4, 8), 2) * 4;
                int UDPStart = i + 14;
                UDPHeader udp = new UDPHeader(Champs.getChamps(selectedTrame, UDPStart, Champs.nbrOctets(selectedTrame) - (14 + i)));
                UDPDecodage.appendText(udp.toString());
            }
            catch (Exception e) {
                UDPDecodage.setText("Erreur dans la partie UDP");
            }
        }
    }


    @FXML
    protected void FillDnsDhcp() {
        //Pour distinguer entre DNS et DHCP
        if (selectedTrame != null && IpTab.getText().equals("IP") && UdpTab.getText().equals("UDP")) {
            String s = Champs.getChamps(selectedTrame, 14, 1);
            s = Conversion.hexToBinary(s);
            int i = Integer.parseInt(s.substring(4, 8), 2) * 4;
            int UDPStart = i + 14;
            UDPHeader udp = new UDPHeader(Champs.getChamps(selectedTrame, UDPStart, Champs.nbrOctets(selectedTrame) - (14 + i)));

            if ((udp.getPortSrc().contains("67") && udp.getPortDest().contains("68")) || (udp.getPortSrc().contains("68") && udp.getPortDest().contains("67"))) {
                int udpPayload = Integer.parseInt(udp.getPayload().replaceAll("[^0-9]", ""));
                FillDHCP(udpPayload);
            } else {
                if ((udp.getPortDest().contains("53") || udp.getPortSrc().contains("53"))) {
                    FillDNS();
                } else DnsDhcpDecodage.setText("Protocol non traité");
            }
        }
    }

    @FXML
    protected void FillDHCP(int udpPayload) {

        DnsDhcpDecodage.clear();
        try {
            if (selectedTrame != null) {
                String s = Champs.getChamps(selectedTrame, 14, 1);
                s = Conversion.hexToBinary(s);
                int i = Integer.parseInt(s.substring(4, 8), 2) * 4;
                int DHCPStart = i + 14 + 8;
                DHCPHeader dhcp = new DHCPHeader(Champs.getChamps(selectedTrame, DHCPStart, udpPayload));
                DnsDhcpDecodage.appendText(dhcp.toString());
            }
        }
        catch (Exception e) {
            DnsDhcpDecodage.setText("Erreur dans la partie DHCP");
        }
    }

    @FXML
    protected void FillDNS() {
        DnsDhcpDecodage.clear();
        if (selectedTrame != null) {
            try {
                String s = Champs.getChamps(selectedTrame, 14, 1);
                s = Conversion.hexToBinary(s);
                int i = Integer.parseInt(s.substring(4, 8), 2) * 4;
                int DNSstart = i + 14 + 8;
                DNSHeader dhcp = new DNSHeader(Champs.getChamps(selectedTrame, DNSstart, Champs.nbrOctets(selectedTrame) - (DNSstart)));
                DnsDhcpDecodage.appendText(dhcp.toString());

            }
            catch (Exception e){
                DnsDhcpDecodage.setText("Erreur dans la partie DNS");
            }
        }

    }

    @FXML
    protected void exportResults() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvgarder le fichier txt contenant l'analyse de la trame");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                "Fichier text (*.txt)", "*.txt"
        ));
        Stage stage = (Stage) main.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file!=null) {
            PrintWriter out = new PrintWriter(file);
            out.println("-*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_* " + errorMessage.getText().replaceAll("^0-9", "") + " -*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*\n");
            out.println("/////////////////////// Brut ///////////////////////\n");
            FillBrut();
            out.println(trameAfterFilter.getText());
            out.println("\n/////////////////////// Couche 2 ///////////////////////\n");
            FillEthernet();
            out.println(EthernetDecodage.getText());
            out.println("\n/////////////////////// Couche 3 ///////////////////////\n");
            FillIP();
            out.println(IPDecodage.getText());
            out.println("\n/////////////////////// Couche 4 ///////////////////////\n");
            FillUDP();
            out.println(UDPDecodage.getText());
            out.println("\n/////////////////////// Couche 7 ///////////////////////\n");
            FillDnsDhcp();
            out.println(DnsDhcpDecodage.getText());
            out.close();
        }
    }

    @FXML
    protected void openCredits() {
        credits.setVisible(true);
        closeCreditsButton.setVisible(true);
    }

    @FXML
    protected void closeCredits() {
        credits.setVisible(false);
        closeCreditsButton.setVisible(false);
    }

}



