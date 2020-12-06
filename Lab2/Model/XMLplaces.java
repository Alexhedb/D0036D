//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Model;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 4 different ArrayLists are needed for each object
 *
 * @author Alexander Hedberg
 * 2019-10-03
 */

public class XMLplaces {
    ArrayList<String> locations = new ArrayList();
    ArrayList<String> latitude = new ArrayList();
    ArrayList<String> altitude = new ArrayList();
    ArrayList<String> longitude = new ArrayList();

    public XMLplaces() {
        try {
            File inputFile = new File("places.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            NodeList places = doc.getElementsByTagName("locality");

            /**
             * for loop adds the locations to the locations ArrayList
             */

            for(int i = 0; i < places.getLength(); ++i) {
                Node orter = places.item(i);
                NodeList list = orter.getChildNodes();
                NamedNodeMap attr = orter.getAttributes();
                Node nodeAttr = attr.getNamedItem("name");
                this.locations.add(nodeAttr.getNodeValue());

                /**
                 * adds the altitudes, latitudes and longitudes to their Arrays
                 */

                for(int y = 0; y < list.getLength(); ++y) {
                    Node node = list.item(y);
                    if (node.getNodeType() == 1) {
                        this.altitude.add(node.getAttributes().getNamedItem("altitude").getNodeValue());
                        this.latitude.add(node.getAttributes().getNamedItem("latitude").getNodeValue());
                        this.longitude.add(node.getAttributes().getNamedItem("longitude").getNodeValue());
                    }
                }
            }
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }

    public String getAltitude(int i) {
        return (String)this.altitude.get(i);
    }

    public String getlatitude(int i) {
        return (String)this.latitude.get(i);
    }

    public String getlongitude(int i) {
        return (String)this.longitude.get(i);
    }

    public ArrayList<String> getlocation() {
        return this.locations;
    }

    public ArrayList<String> getLst() {
        return this.locations;
    }

    public int getArrlength() {
        return this.altitude.size();
    }
}
