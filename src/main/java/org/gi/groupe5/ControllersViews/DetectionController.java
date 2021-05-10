package org.gi.groupe5.ControllersViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class DetectionController implements Initializable {

    @FXML
    private TextField textBox;
    @FXML
    private Button detect;
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane anchorPane;
    String path = "";
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void detect_mat(ActionEvent actionEvent) {
        try {
            String input = path;
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/img/tessdata/");
            String output= tesseract.doOCR(new File(input));
            textBox.setText(output);
        }catch (Exception e){}
    }

    public void Load_Plate(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Car Plate");
        File files = fileChooser.showOpenDialog(null);
        path = files.getAbsolutePath();
        System.out.println(path);
        Image image1 = new Image(files.toURI().toString());
        imageView.setImage(image1);
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//        InputStream is;
//        Image img = new Image(getClass().getResourceAsStream("/img/upload.png"));
//        imageView.setImage(img);
//        textBox.setEditable(false);
//
//    }

//



}
