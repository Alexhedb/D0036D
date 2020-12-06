package Model;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

/**
 * Class used to display the add to the user
 *
 * @author Alexander Hedberg
 * 2019-10-03
 */

public class view extends JFrame {
    /**
     * all the buttons/fields/labels the app is going to contain
     */
    private JLabel timeLabel, placeLabel, tempLabel;
    private JButton requestButton, cachebutton;
    private TextField Namefield;
    private TextField Timefield;
    private TextField cachefield;
    private controller controller;
    private JList lista;

    private Box menu;


    /**
     * @param controller object since its needed in order to use the user input requests
     * calls CreateGUI() to launch the application
     */

    public view(controller controller) {
        this.controller=controller;
        CreateGUI();
    }

    /**
     * Creates the menu, adds all the labels and buttons
     */

    public void createMenu(){
        menu=new Box(BoxLayout.X_AXIS);

        addJList();
        addPlacelabel();
        addLocationField();
        addTimeName();
        addtimefield();
        addRequestButton();
        addCachefield();
        addCachebutton();
        addTemperatureLabel();

        this.add(menu, BorderLayout.NORTH);
    }

    /**
     * Calls creatmenu to create the structure of the app then launches it
     */

    public void CreateGUI(){
        createMenu();

        this.setTitle("Temperature application");
        this.setSize(1000,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * adds the "location" field
     */
    public void addLocationField(){
        Namefield=new TextField("");
        menu.add(Namefield);
    }
    /**
     * adds the "cache" field
     */
    public void addCachefield() {
        cachefield=new TextField("");
        menu.add(cachefield);
    }
    /**
     * adds the "time" field
     */
    public void addtimefield(){
        Timefield=new TextField("",2);
        menu.add(Timefield);
    }
    /**
     * adds the Placelabel
     */
    public void addPlacelabel(){
        placeLabel=new JLabel("Location:");
        menu.add(placeLabel);
    }
    /**
     * adds the "location" field
     */
    public void addTimeName(){
        timeLabel=new JLabel("Time:");
        menu.add(timeLabel);
    }
    /**
     * adds the list with the locations from XML.places
     */
    public void addJList(){
        lista=new JList(this.controller.getOrter().toArray());
        lista.addListSelectionListener(arg0 -> {
            if (!arg0.getValueIsAdjusting()) {
                Namefield.setText(lista.getSelectedValue().toString());
            }
        });
        menu.add(lista);
    }

    /**
     * Adds requestbutton which is used whenever the user wants to know a temperature
     * Actionlistener used to let controller know whenever the button has been pressed
     */

    public void addRequestButton() {
        requestButton = new JButton("Get temperature");
            this.requestButton.addActionListener(e -> tempLabel.setText(view.this.controller.newRequest(Namefield.getText(), Timefield.getText())));
            menu.add(requestButton);
    }
    /**
     * Adds Cachebutton which is used whenever the user wants to set the cache time
     * Actionlistener used to let controller know whenever the button has been pressed
     */
    public void addCachebutton(){
        cachebutton=new JButton("Cache time (s)");
        this.cachebutton.addActionListener(e -> {
            view.this.controller.SetAge(cachefield.getText());
            view.this.controller.StartTimer(view.this.controller.getLifeTime());
        });
        menu.add(cachebutton);
        }

    /**
     * Method used to present the output result, temperature
     */
    private void addTemperatureLabel() {
        tempLabel = new JLabel("", JLabel.CENTER);
        tempLabel.setFont(new Font(tempLabel.getFont().getName(), 5, 50));
        this.add(tempLabel, BorderLayout.CENTER); }


    /**
     * main to start the application
     * @param args
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        model testmodel=new model();
        controller test=new controller(testmodel);
        view testview=new view(test);
    }
}