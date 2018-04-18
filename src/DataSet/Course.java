package DataSet;

public class Course {
	public Course(int key)
	{
		courseKey = key;
		courseID = String.format("%05d",key);	// 高位补0
	}
	public int courseKey;			// courseID in integer
	private String courseID;		// course name
	private int capacity;			// 课容量
	private int time;				// 0: 上午第一节; 1：上午第二节
	private int residual;			// 课余量 
	
	public String getName()
	{
		return courseID;
	}
	public void setCapacity(int cap)
	{
		capacity = cap;
		residual = cap;
	}
	public int getCapacity()
	{
		return capacity;
	}
	public void setTime(int t)
	{
		time = t;
	}
	public int getTime()
	{
		return time;
	}
	public Boolean vetifyFull()						// 0: 未满可选; 1: 已满不可选
	{
		return (residual==0? true:false);
	}
	public Boolean beChosen()			// true: 选上了; false: 没课余量了，选不上
	{
		if(residual > 0)
		{
			residual --;
//			System.out.println(residual);
			return true;
		}
		else
		{
//			System.out.println("false");
			return false;
		}
	}
}