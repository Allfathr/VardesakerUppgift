// Anton Sandström ansa6928
// Karl Jonsson 

import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Gui extends Application {
	private TextArea area;
	private RadioButton nameButton;
	private RadioButton valueButton;
	ArrayList<Vardesaker> allaSaker = new ArrayList<>();
	
	@Override
	public void start(Stage primaryStage) {
		Pane root = new FlowPane();
		Label headLab = new Label("Värdesaker");
		Scene scene = new Scene(root);
		area = new TextArea();
		Button show = new Button("Show");
		show.setOnAction(new showHandler());
		root.getChildren().addAll(headLab, area, show);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	class ShowHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			area.setText("");
			area.setText("This is working");
			
			if (nameButton.isSelected()) {
				allaSaker.sort(new NameComparator());
				for (Vardesaker sak : allaSaker) {
					area.appendText(sak.toString());
					area.appendText("\n");
					
				}
				
			}
			
			else if (valueButton.isSelected()) {
				allaSaker.sort(new VardeComparator());
				for (Vardesaker sak : allaSaker) {
					area.appendText(sak.toString());
					area.appendText("\n)");
				}
			}
		}
	}
	
	class ExchangeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			for (Vardesaker sak : allaSaker) {
				if (sak instanceof Aktie) {
					Aktie a = (Aktie) sak;
					a.setNyKurs(0);
				}
			}
		}
	}
	
	class NameComparator implements Comparator<Vardesaker> {
		public int compare (Vardesaker v1, Vardesaker v2) {
			return v1.getNamn().compareTo(v2.getNamn());
		}
	}
	
	class VardeComparator implements Comparator<Vardesaker> {
		public int compare (Vardesaker v1, Vardesaker v2) {
			return Double.compare(v2.getRealVarde(), v1.getRealVarde());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
