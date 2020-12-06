package Model;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * controller class used to handle user input from view by getting data from model. Binds model and view together.
 * controller object has a model object for data, "Cachetime" timer that is used to get the requested input "lifetime"
 *
 * @author Alexander Hedberg
 * 2019-10-03
 */

public class controller{

    private model Model;
    private String LifeTime;
    private Timer Cachetime;
    private boolean started=false;


    Timer t = new Timer();
    TimerTask tt = new TimerTask() {
        @Override
        public void run() {
            try {
                Model.UpdateTimes();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                Model.UpdateTemps();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    };
    public controller(model model){
        this.LifeTime="";
        this.Model=model;
        this.Cachetime=new Timer();
    }

    /**
     *
     * @param ort input location that user wants to get the temperature of
     * @param timme input time that user wants to get the temperature of
     * @return requested temperature value
     */
    public String newRequest(String ort, String timme) {
        if (!Model.getLocations().contains(ort)) {
            return "Invalid location";
        }
        for (int i = 0; i < Model.getLocations().size(); i++) {
            if (this.Model.getLocIndex(ort) == i) {
                if (this.Model.getHour(i, Model.getTimeIndex(timme)).equals(timme)) { //checks what index time is at in the arraylist, if it equals input time.
                    return this.Model.getdegrees(i, Model.getTimeIndex(timme)); //returns requested degree value
                }
            }
        }
        return "Please enter a correct time";
    }
    /**
     *get methods
     */
    public String SetAge(String i){
        return this.LifeTime=i;
    }
    public String getLifeTime(){
        return this.LifeTime;
    }
    public ArrayList<String> getOrter(){
        return this.Model.getLocations();
    }

    /**
     *
     * @param i the requsted "lifetime" (the time before new data is requested)
     */
    public void StartTimer(String i) {
        if(!started) {
            t.schedule(tt, 1000, 1000 * Integer.parseInt(i));
            started=true;
        }else{
            System.out.println("You can only set cache time once!");
        }
    }
}

