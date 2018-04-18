/*
 * ��data.xls����Ŀγ���
 */

package baselineFromFile;

public class CourseLoad implements Comparable<CourseLoad>{
	public static final String[] FIRSTTABLE = 
	{"11","13","21","23","31","33","41","43","51","53"}; // time=0�Ŀ���"data.xls"�еĿγ�ʱ��
	public static final String[] SECONDTABLE = 
	{"12","14","22","24","32","34","42","44","52","54"}; // time=1�Ŀ���"data.xls"�еĿγ�ʱ��
	public CourseLoad(String ID, String Name, int Capa, String Ctime)
	{
		id = ID;
		name = Name;
		capacity = Capa;
		ctime = Ctime;
		if(Ctime.charAt(1)=='1'||Ctime.charAt(1)=='3')	// �ڶ����ַ���1��3˵��������/�����һ�ڿ�
		{
			time = 0;
		}
		else
		{
			time = 1;									// ����������/����ڶ��ڿ�
		}
	}
	String id;					// courseID, Ψһ
	private String name;		// course name
	private int capacity;		// ������
	private String ctime;		// �Ͽ�ʱ���ַ���
	private int time;			// 0: ����/�����һ��; 1������/����ڶ���
	private String buildingName;// ���ڽ�ѧ¥����
	private String roomName;	// ���ڷ��������
	
	public int compareTo(CourseLoad cl)		// ���γ̰��������Ӵ�С����
	{
		int tcapa = cl.getCapacity();
		return capacity > tcapa ? -1 : ( capacity==tcapa? 0 : 1);
	}
	public String toString()	// ����toString��������syso����ֱ�Ӵ�ӡCourseRead������
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
