import static java.lang.System.out;

import java.io.*;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;

public class Main extends Application{
	
	public static final String default_menu_filename="default_menu";
	public static final String student_address="http://www.andong.ac.kr/index.sko?menuCd=AA06003005001";
	public static final String staff_address="http://www.andong.ac.kr/index.sko?menuCd=AA06003005000";
	public static final String dorm_address="http://dorm.andong.ac.kr/2014/food_menu/food_menu.htm";
	public static int cur_menu=-1; //현재 떠있는 메뉴
	
	private int default_menu_file_check() throws Exception //디폴트로 열 홈페이지 체크..
	{
		File default_menu_file = new File(default_menu_filename);

		if(!default_menu_file.isFile()) //없으면 만들기
		{
			FileWriter writer = new FileWriter(default_menu_file);
			writer.write(0);
			// 0 학식
			// 1 교식
			// 2 기숙사
			writer.close();
		}
		
		FileReader reader = new FileReader(default_menu_filename);
		int retval = reader.read();
		reader.close();
		return retval;
	}
	
	private void default_menu_change(int value)
	{
		File default_menu_file = new File(default_menu_filename);
		
		try {
			FileWriter writer = new FileWriter(default_menu_file);
			writer.write(value);
			// 0 학식
			// 1 교식
			// 2 기숙사
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private Text[] make_menu(int menu_type)
	{
		switch(menu_type)
		{
		case 0: Menu_Parser.parse(0); break;
		case 1: Menu_Parser.parse(1); break;
		case 2: Menu_Parser.parse(2); break;
		default: System.out.println("make_menu switch error");
		}
		
		Menu_t menu = Menu_Parser.extract_menu();
		
		return new Text[] {
			new Text(menu.get_breakfast()),
			new Text(menu.get_lunch()),
			new Text(menu.get_dinner())
		};
	}
	
	private VBox make_vbox(Text[] texts)
	{
		for(Text e : texts)
			e.setTextAlignment(TextAlignment.CENTER);
		
		String restaurant = null;
		switch(cur_menu)
		{
		case 0 : restaurant="\n학생식당\n"; break;
		case 1 : restaurant="\n교수식당\n"; break;
		case 2 : restaurant="\n기숙사\n"; break;
		default : out.println("switch error in make_vbox");
		}
		
		VBox texts_box=new VBox();
		texts_box.getChildren().addAll(new Text(restaurant),texts[0],texts[1],texts[2]);
		
		for(Text e : texts)
			VBox.setMargin(e, new Insets(5,0,0,0));
		
		texts_box.setSpacing(5);
		texts_box.setAlignment(Pos.CENTER);
		
		return texts_box;
	}
	
	private void image_load()
	{
	}
	
	private void browse_menu(FlowPane menu_pane, int menu_event)
	{	
		switch(menu_event)
		{
		case 0 : cur_menu=menu_event; menu_pane.getChildren().clear(); menu_pane.getChildren().add(make_vbox(make_menu(0)));
			default_menu_change(0); break;
		case 1 : cur_menu=menu_event; menu_pane.getChildren().clear(); menu_pane.getChildren().add(make_vbox(make_menu(1)));
			default_menu_change(1); break;
		case 2 : cur_menu=menu_event; menu_pane.getChildren().clear(); menu_pane.getChildren().add(make_vbox(make_menu(2)));
			default_menu_change(2); break;
		case 3 : menu_pane.getChildren().clear(); menu_pane.getChildren().add(make_vbox(make_menu(cur_menu))); break;
		//case 4 : menu_pane.getChildren().clear(); menu_pane.getChildren().add(make_vbox(make_menu(cur_menu))); break; //unuse
		default:out.println("browse_menu switch exception");
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		int default_menu_num=default_menu_file_check();
		
		cur_menu=default_menu_num;
		
		//title
		stage.setTitle("Today Menu");
		//title
		
		//menu
		FlowPane menu_pane=new FlowPane();
		menu_pane.getChildren().add(make_vbox(make_menu(default_menu_num)));
		menu_pane.setAlignment(Pos.CENTER);
		menu_pane.setMaxWidth(265); menu_pane.setMinWidth(265);
		//menu
		
		//button
		Button student_button=new Button("학생식당!"); student_button.setOnAction(event -> browse_menu(menu_pane,0));
		Button staff_button=new Button("교수식당!"); staff_button.setOnAction(event-> browse_menu(menu_pane,1));
		Button dorm_button=new Button("기숙사!!!"); dorm_button.setOnAction(event-> browse_menu(menu_pane,2));
		Button refresh_button =new Button("새로고침"); refresh_button.setOnAction(event->browse_menu(menu_pane,3));
		//Button dogfresh_button =new Button("개로고침"); refresh_button.setOnAction(event->browse_menu(menu_pane,4));//unuse
		HBox buttons_pane=new HBox(); 
		buttons_pane.getChildren().addAll(student_button, staff_button, dorm_button, refresh_button/*, dogfresh_button*/);
		buttons_pane.setMaxWidth(330); buttons_pane.setMinWidth(330);
		//button
		
		//root_node
		VBox root_node=new VBox();
		root_node.getChildren().addAll(buttons_pane,menu_pane);
		root_node.autosize();
		//root_node.
		
		//scene
		Scene my_scene=new Scene(root_node,265,450);
		//scene
		
		//stage
		stage.setScene(my_scene);
		//stage.sizeToScene();
		//stage.setResizable(false);
		stage.show();
		//stage
	}
	
	
	//main
	public static void main(String[] args) throws IOException
	{	
		launch(args);
	}
}