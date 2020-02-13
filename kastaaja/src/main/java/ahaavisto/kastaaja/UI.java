/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ahaavisto.kastaaja;

import java.io.File;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

public class UI extends Application {

    @Override
    public void start(Stage ikkuna) {
        ikkuna.setTitle("Hei Maailma!");
        ikkuna.show();
    }

    public static void launchTestThing() {
        launch(UI.class);
    }
    
    public static void tiedostonvalitsin() {
        FileChooser valitsin = new FileChooser();
        File selectedFile = valitsin.showOpenDialog(null);
        
        
        //Create a file chooser
        //final JFileChooser fc = new JFileChooser();

//In response to a button click:
        //int returnVal = fc.showOpenDialog(aComponent);
    }

}
