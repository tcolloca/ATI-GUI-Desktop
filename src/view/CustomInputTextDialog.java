package view;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class CustomInputTextDialog {

  TextInputDialog dialog = new TextInputDialog("walter");
dialog.setTitle("Text Input Dialog");
dialog.setHeaderText("Look, a Text Input Dialog");
dialog.setContentText("Please enter your name:");

  // Traditional way to get the response value.
  Optional<String> result = dialog.showAndWait();
if (result.isPresent()){
    System.out.println("Your name: " + result.get());
  }

// The Java 8 way to get the response value (with lambda expression).
result.ifPresent(name -> System.out.println("Your name: " + name));

}
