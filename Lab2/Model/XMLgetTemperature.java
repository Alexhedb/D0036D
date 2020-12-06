
package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to parse the temperature "value" out of the XML document and save it in a 2D ArrayList. 2D ArrayList is needed
 * since the [specific document to save data to][temperature values from document]
 *
 * @author Alexander Hedberg
 * 2019-10-03
 */

public class XMLgetTemperature {

    List<List<String>> Temps2d = new ArrayList<>();


    public XMLgetTemperature() throws ParserConfigurationException, IOException, SAXException {
        XMLplaces xml = new XMLplaces();
        Node node;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        /**
         * creates one ArrayList docs which is used to store the 3 different documents after parsing the URL.
         * crates a NodeList (temperatures) which gets all the temperature values to the specific document
         */

        ArrayList<Document> docs = new ArrayList<>();
        ArrayList<NodeList> temperatures = new ArrayList<>();

        for (int i = 0; i < xml.getArrlength(); i++) {

            Document doc = db.parse(new URL("https://api.met.no/weatherapi/locationforecast/1.9/?lat=" + xml.getlatitude(i) + "&lon=" + xml.getlongitude(i) +"&msl="+xml.getAltitude(i)).openStream());
            docs.add(doc);
            temperatures.add(docs.get(i).getElementsByTagName("temperature"));
            }
        /**
         * for loop used to add the document to the first position of the 2D array and the first 24 temperature values to the second position in the
         * 2D array
         */
        for (int j = 0; j < docs.size(); j++) {
            Temps2d.add(new ArrayList<>());
            for (int y = 0; y < 24 ; y++) {
                node = temperatures.get(j).item(y);
                Temps2d.get(j).add(node.getAttributes().getNamedItem("value").getNodeValue());

            }
        }
    }
    public String getLstValues(int i, int j) {
        return Temps2d.get(i).get(j);
    }
}


