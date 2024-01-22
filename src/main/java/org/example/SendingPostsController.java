package org.example;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class SendingPostsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea postTextArea;

    @FXML
    private Text selectedImageText;

    @FXML
    private Text textInStartButton;

    @FXML
    private TextField facebookProfileIDField;

    File selectedFile;

    @FXML
    void selectImageButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null) {
            selectedImageText.setText(selectedFile.getName());
        } else {
            System.out.println("Image file is null");
        }
    }
    @FXML
    void startButton(ActionEvent event) {
        FacebookClientForPostingPosts facebookClientForPostingPosts = new FacebookClientForPostingPosts();
        String[] groupIDs = {"160135217057405", "304564452025678", "100662940121262", "130968980816441", "145139842899310", "151258751982122", "265039496909527", "273900755963158", "276486285893819", "416914565050438", "473690766874911", "522565094546254", "564258321115397", "741340576745009", "788690365155249", "809082029127347", "953747601653911", "1368570139882657", "1486836004877610", "1535077323413607", "1660946480830080", "1687585784814001", "1923096101296917", "2210861162340791", "5051641138211736", "294450737340465", "367069680033403"};
        facebookClientForPostingPosts.sendPostIntoTheGroup(groupIDs, postTextArea.getText(), selectedFile, facebookProfileIDField.getText());
    }

    @FXML
    void initialize() {

    }

}
