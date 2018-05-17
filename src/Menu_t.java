//메뉴를 정의하는 타입
public class Menu_t {
	private boolean has_menu_;
	
	private String breakfast;
	private String lunch;
	private String dinner;
	
	public Menu_t(boolean has_menu)
	{
		if(has_menu==false)
		{
			this.has_menu_=false;
			this.breakfast="오늘은 메뉴가 없습니다.";
		}
		else
			this.has_menu_=true;
	}
	
	public Menu_t(String breakf, String lunch, String dinner)
	{
		this.has_menu_=true;
		
		this.breakfast=breakf;
		this.lunch=lunch;
		this.dinner=dinner;
	}
	
	public boolean has_menu()
	{
		return has_menu_;
	}
	
	//getter
	public String[] get_menu_as_string()
	{
		return new String[]{breakfast, lunch, dinner};
	}
	public String get_breakfast()
	{
		return this.breakfast;
	}
	public String get_lunch()
	{
		return this.lunch;
	}
	public String get_dinner()
	{
		return this.dinner;
	}
	//getter
	
	//setter
	public void initialize(String breakf, String lunch, String dinner)
	{
		this.set_breakfast(breakf);
		this.set_lunch(lunch);
		this.set_dinner(dinner);
	}
	public void set_breakfast(String value)
	{
		this.breakfast=value;
	}
	public void set_lunch(String value)
	{
		this.lunch=value;
	}
	public void set_dinner(String value)
	{
		this.dinner=value;
	}
	//setter
	
	
}