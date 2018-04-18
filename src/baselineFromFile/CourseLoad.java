/*
 * 从data.xls读入的课程类
 */

package baselineFromFile;

public class CourseLoad implements Comparable<CourseLoad>{
	public static final String[] FIRSTTABLE = 
	{"11","13","21","23","31","33","41","43","51","53"}; // time=0的课在"data.xls"中的课程时间
	public static final String[] SECONDTABLE = 
	{"12","14","22","24","32","34","42","44","52","54"}; // time=1的课在"data.xls"中的课程时间
	public CourseLoad(String ID, String Name, int Capa, String Ctime)
	{
		id = ID;
		name = Name;
		capacity = Capa;
		ctime = Ctime;
		if(Ctime.charAt(1)=='1'||Ctime.charAt(1)=='3')	// 第二个字符是1或3说明是上午/下午第一节课
		{
			time = 0;
		}
		else
		{
			time = 1;									// 否则是上午/下午第二节课
		}
	}
	String id;					// courseID, 唯一
	private String name;		// course name
	private int capacity;		// 课容量
	private String ctime;		// 上课时间字符串
	private int time;			// 0: 上午/下午第一节; 1：上午/下午第二节
	private String buildingName;// 所在教学楼名字
	private String roomName;	// 所在房间的名字
	
	public int compareTo(CourseLoad cl)		// 将课程按课容量从大到小排序
	{
		int tcapa = cl.getCapacity();
		return capacity > tcapa ? -1 : ( capacity==tcapa? 0 : 1);
	}
	public String toString()	// 重载toString方法，让syso可以直接打印CourseRead的内容
	{
		String ans = String.valueOf(id) + "\t" + name + "\t" + String.valueOf(capacity)
		+ "\t" + ctime + ":\t" + buildingName + "-" + roomName;
		return ans;
	}
	public String getName()
	{
		return name;
	}
	public String getCTime()
	{
		return ctime;
	}
	public int getTime()
	{
		return time;
	}
	public int getCapacity()
	{
		return capacity;
	}
	public void setBuildingName(String bn)
	{
		buildingName = bn;
	}
	public void setRoomName(String rn)
	{
		roomName = rn;
	}
	public String getBuildingName()
	{
		return buildingName;
	}
	public String getRoomName()
	{
		return roomName;
	}
}
