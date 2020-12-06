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
 * Class used to mask out the "from" and "to" times and compare them in order to get the hour needed
 * Adds the time to a 2D ArrayList finaltimes.
 *
 * @author Alexander Hedberg
 * 2019-10-03
 */

public class XMLgetTime {

    static List<List<String>> finaltimes=new ArrayList<>();
    static ArrayList<String> timesfrom =new ArrayList<>();
    static ArrayList<String>timesto =new ArrayList<>();

    public XMLgetTime() throws ParserConfigurationException, IOException, SAXException {
        XMLplaces xml = new XMLplaces();
        Node node;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        ArrayList<Document> docs = new ArrayList<>();
        ArrayList<NodeList> times = new ArrayList<>();

        /**
         * Adds all times of the XML document to the document
         */

        for (int i = 0; i < xml.getArrlength(); i++) {
            Document doc = db.parse(new URL("https://api.met.no/weatherapi/locationforecast/1.9/?lat=" + xml.getlatitude(i) + "&lon=" + xml.getlongitude(i) + "&msl=" + xml.getAltitude(i)).openStream());
            docs.add(doc);
            times.add(docs.get(i).getElementsByTagName("time"));
        }

        /**
         * Saves the first to document to index 0, second to 1 etc...
         */

        for (int j = 0; j < docs.size(); j++) {
            finaltimes.add(new ArrayList<>());

            /**
             * Compares the times that start with "from" and "to" if they are the same, add to finaltimes
             */

            for (int y = 0; y < docs.size()*39; y++) {
                node = times.get(j).item(y);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    timesto.add(node.getAttributes().getNamedItem("to").getNodeValue());
                    timesfrom.add(node.getAttributes().getNamedItem("from").getNodeValue());


                    if (timesfrom.get(y).equals(timesto.get(y))) {
                        String m = timesto.get(y).substring(11, 13);
                        finaltimes.get(j).add(m);
                    }
                }
            }
        }

    }
    public String getValues (int i, int j) {
        return finaltimes.get(i).get(j);
    }
}
