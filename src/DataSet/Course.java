package DataSet;

public class Course {
	public Course(int key)
	{
		courseKey = key;
		courseID = String.format("%05d",key);	// ��λ��0
	}
	public int courseKey;			// courseID in integer
	private String courseID;		// course name
	private int capacity;			// ������
	private int time;				// 0: �����һ��; 1������ڶ���
	private int residual;			// ������ 
	
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
	public Boolean vetifyFull()						// 0: δ����ѡ; 1: ��������ѡ
	{
		return (residual==0? true:false);
	}
	public Boolean beChosen()			// true: ѡ����; false: û�������ˣ�ѡ����
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