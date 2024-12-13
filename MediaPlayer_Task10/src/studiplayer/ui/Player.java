package studiplayer.ui;

import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;

public class Player extends Application {
	
	public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
	private String PLAYLIST_DIRECTORY;
	private String INITIAL_PLAY_TIME_LABEL;
	private String NO_CURRENT_SONG;
	private PlayList playList;
	private boolean useCertPlayList = false;
	private Button playButton = new Button("play");
	private Button pauseButton = new Button("pause");
	private Button stopButton = new Button("stop");
	private Button nextButton = new Button("next");
	private Label playListLabel = new Label("Playlist");
	private Label playTimeLabel = new Label("Current Song");
	private Label currentSongLabel = new Label("Playtime");
	private ChoiceBox<String> sortChoiceBox = new ChoiceBox<>();
	private TextField searchTextField = new TextField();
	private Button filterButton = new Button("display");
	
	public Player() {}
	
	public void start(Stage stage) {
		TableView<String> table = new TableView<>();
		String path = "playList.cert.m3u";
		String fileName = "etc";
		BorderPane mainPane = new BorderPane();
		stage.setTitle("Player");
		GridPane positionBox = new GridPane();
		positionBox.setPadding(new Insets(5));
		positionBox.setHgap(10);
		positionBox.setVgap(10);
	
		TitledPane filterHeader = new TitledPane();
		filterHeader.setText("Filter");
		filterHeader.setCollapsible(true);
	
		Label searchLabel = new Label("Search text");
		searchTextField.setPromptText("");
		positionBox.add(searchLabel, 0, 0);
		positionBox.add(searchTextField, 1, 0);
	
		Label sortLabel = new Label("Sort by");
		sortChoiceBox.getItems().addAll("AUTHOR", "TITLE", "ALBUM", "DURATION");
		sortChoiceBox.setValue("AUTHOR");
		positionBox.add(sortLabel, 0, 1);
		positionBox.add(sortChoiceBox, 1, 1);
	
		positionBox.add(filterButton, 2, 1);
		
		VBox filterBox = new VBox(0);
		filterBox.getChildren().addAll(filterHeader, positionBox);
		mainPane.setTop(filterBox);
		
		TableColumn<String, String> artist = new TableColumn<>("Artist");
		TableColumn<String, String> title = new TableColumn<>("Title");
		TableColumn<String, String> album = new TableColumn<>("Album");
		TableColumn<String, String> duration = new TableColumn<>("Duration");
        
        table.getColumns().addAll(artist, title, album, duration);
		
		if (!this.useCertPlayList) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select File To Load");
			File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
//            	System.out.println(file.getPath());
            	path = file.getPath();
            	fileName = file.getName();
            	
            }
			setPlayList(path);
		}
		
		VBox songInfoBox = new VBox(0);
		
		GridPane positionBoxBottom = new GridPane();
		positionBoxBottom.setPadding(new Insets(5));
		positionBoxBottom.setHgap(10);
		positionBoxBottom.setVgap(10);
		
		Label pathLabel = new Label(path);
		positionBoxBottom.add(playListLabel, 0, 0);
		positionBoxBottom.add(pathLabel, 1, 0);

		Label songNameLabel = new Label(fileName);
		positionBoxBottom.add(currentSongLabel, 0, 1);
		positionBoxBottom.add(songNameLabel, 1, 1);
		
		Label durationLabel = new Label(path);
		positionBoxBottom.add(playTimeLabel, 0, 2);
		positionBoxBottom.add(durationLabel, 1, 2);
	
		songInfoBox.getChildren().addAll(table, positionBoxBottom);
		mainPane.setCenter(songInfoBox);
		
		FlowPane buttonPane = new FlowPane();
		buttonPane.getChildren().addAll(playButton, pauseButton, stopButton, nextButton);
		buttonPane.setAlignment(Pos.CENTER);
		mainPane.setBottom(buttonPane);

		Scene scene = new Scene(mainPane, 600, 400);
		stage.setScene(scene);
		stage.show();
	}

	public void setUseCertPlayList(boolean value) {
		this.useCertPlayList = value;
	}
	
	public void setPlayList(String pathname) {
		this.playList = new PlayList(pathname);
	}
	
	public void loadPlayList(String pathname) throws NotPlayableException {
		if (pathname == null || pathname.isEmpty()) {
			this.playList.loadFromM3U(DEFAULT_PLAYLIST);
		} else {
			this.playList.loadFromM3U(pathname);	
		}
	}
	
	public void createButton(String iconfile) {
		
	}

	
	public static void main(String[] args) {
		launch();
	}
}