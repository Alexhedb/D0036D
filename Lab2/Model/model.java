package Model;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * model class holds time, temperature and location data in ArrayLists. The idea is to save temperature, time and location values
 * at the same ArrayList indexes, that way you can match them together, ex time[0]=01, temperature[0]=3.
 *
 * @author Alexander Hedberg
 * 2019-10-03
 */

public class model {


    private XMLgetTemperature degrees;
    private XMLgetTime timeList;
    private XMLplaces xmlplaces;


    public model() throws IOException, SAXException, ParserConfigurationException {

        this.xmlplaces = new XMLplaces();

        this.timeList = new XMLgetTime();

        this.degrees = new XMLgetTemperature();

    }

    public String getdegrees(int i, int j) {
        return this.degrees.getLstValues(i, j);
    }

    public String getHour(int i, int j) {
        return this.timeList.getValues(i, j);
    }

    public ArrayList<String> getLocations() {
        return this.xmlplaces.getlocation();
    }

    /**
     *
     * @param inputloc the location which you wish to get the index of
     * @return index of location in Array.
     */
    public int getLocIndex(String inputloc) {
        int i = 0;
        i = this.xmlplaces.getLst().indexOf(inputloc);
        return i;
    }

    /**
     *
     * @param tid the time which you wish to get the index of
     * @return index of time array
     */

    public int getTimeIndex(String tid) {
        for (int k = 0; k < 24; k++) {
            if (timeList.getValues(0, k).equals(tid)) {
                return k;
            }
        }
        return 0;
    }

    /**
     * Used to update the time when new data is requested
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void UpdateTimes() throws IOException, SAXException, ParserConfigurationException {
        this.timeList=new XMLgetTime();
    }

    /**
     * used to update temperatures when new data is requested
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void UpdateTemps() throws IOException, SAXException, ParserConfigurationException {
        this.degrees=new XMLgetTemperature();
    }
}
//}
