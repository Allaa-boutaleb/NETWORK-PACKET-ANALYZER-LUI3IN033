# NETWORK-PACKET-ANALYZER-LUI3IN033
### By BOUTALEB MOHAMED ALLAA EDDINE, EL GHALI YOSR.

A network packet analyzer java application. This was a project I worked on with the collabrtion of EL GHALI YOSR, under the supervision of PROMETHEE SPATHIS and KIM THAI.

## TUTORIAL LINK AND SHOWCASE OF THE SOFTWARE : https://www.youtube.com/watch?v=APvsJgZAGxQ

-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

The structure of our code is as follows.

There are two main packages:

	- Coeur: this package contains the heart of the program, the logic behind the decoding.
	- com.example.projres : this package contains the controller and the launcher for the JavaFX application.

1 - Coeur:
	1.1 There are three packages in this program:
		1.1.1 - Conversion : This package contains one class "Conversion.java". It contains all the methods we needed to convert Hex numbers to Binary, or Hex to ASCII characters.

		1.1.2 - Opener : This package contains one class "Opener.java". It's the class that contains the input parser that filters out all the offsets and ascii characters in the given .txt files in the beginning.

		1.1.3 - Entete : This package contains a package for each type of Header: Ethernet, IP, UDP, DHCP, DNS. It also contains a class "Champs.java" which is super crucial to our project. 
			 
                           getChamps(header, int1, int2) : gives us the string of bytes, starting at byte with index (ind1) from "Header". The returned string will contains (int2) bytes.


	1.2 Each package in (Entete) contains a class, for example: 

		1.2.1 Ethernet package has the class (EthernetHeader.java) in it. This class will contain Smart getters based on the fields that exist in an Ethernet Header. So, for this example, we have :
			getType() : returns a String elaborating the type detected in the Ethernet Header.
			getMacSrc() : returns the source MAC address.
			getMacDest() : returns the destination MAC address.
			getMacDetails() : returns the LGBit and IGBit of the mac address.
			All of these Getters are then reunited in the overridden method toString() that will return a full decoding of the header.

		1.2.2 The same principal applies to the rest of the classes : IPHeader, UDPHeader, DHCPHeader, DNSHeader.

		1.2.3 Each Header class has an attribute "String header" which will contain only the concerned part of the packet, and not the entire packet. 
			For example: The header attribute in EthernetHeader will contain the entire packet, since it starts at byte 0.
				     The header attribute in IPHeader will contain the series of bytes starting at byte 14... etc




2 - com.example.projres :
		2.1 - This package contains three classes:
			2.1.1 - HelloApplication : Which is the standard launcher for a JavaFX scene.
			2.1.2 - HelloController : The class which will serve as the link between the logic implemented in the "Coeur" package and the GUI.
			2.1.3 - FakeMain : This class has been created to allow us to export a .jar file that works without any hastle. Since Javafx::deploy has been removed. This class serves as a workaround for that issue.
		
		2.2 - The class HelloController :
			2.2.1 - Has different methods for different buttons, a method to open .txt files, a method to analyse a selected packet, other methods that will show information according to the selected Tab (shows IP Info when IP tab is selected...) and a method to export whatever has been decoded in the program, into a .txt file.

			2.2.2 - This class also reinforces the handling of errors that we have already done in the classes in the "Coeur" package, by making tests on Exceptions redudant, to minimize errors and crashes as much as possible.



- The pom.xml file that contains the Maven dependencies has been modified and tweaked to include all different types of JavaFX files, whether it's for Windows, MAC or Linux, allowing us to make a cross-platform program.
	

	
			
