
public class Crawler 
{

	public static void main(String[] args) 
	{
		Date_Tracker dateTraking = new Date_Tracker();
		String date = "2016-11-31";

		for(int i = 0; i < 365; i++){
			date = dateTraking.get_next_date(date);
		}
	}

}
