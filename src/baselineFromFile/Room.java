/*
 * 教室类
 */

package baselineFromFile;

public class Room implements Comparable<Room> {
	public Room(int ID, String bn, String rn, int Capa)
	{
		id = ID;
		buildingName = bn;
		roomName = rn;
		capacity = Capa;
	}
	public int id;						// 唯一的id
	public String buildingName;			// 教室所在教学楼的名字
	public String roomName;				// 教室的名字
	public int capacity;				// 教室容量
	protected String firstID = "";		// 放入的第一节课的id
	protected String secondID = "";		// 放入的第二节课的id
	
	public String toString()			// 重载toString方法，让syso可以直接打印Room的内容
	{
		String ans = roomName + "\t" + String.valueOf(capacity);
		return ans;
	}
	public int compareTo(Room rm)		// 重载教室类的比较函数：按课容量比较
	{
		int capa = rm.getCapacity();
		return ( capacity < capa ? -1 : (capacity==capa)? 0 : 1);
	}
	public int getCapacity()
	{
		return capacity;
	}
	public boolean fitin(CourseLoad cl)	// 给定一课程，如果能放下就返回true，否则返回false
	{
		int time = cl.getTime();
		int courseCapa = cl.getCapacity();
		if (capacity < courseCapa)		// 如果教室容量 < 课容量
		{
			return false;
		}
		else if (time==0 && firstID=="")// 如果是第一节且本教室第一节没课
		{
			firstID = cl.id;
			cl.setBuildingName(buildingName);
			cl.setRoomName(roomName);
			return true;
		}
		else if (time==1 && secondID=="")// 如果是第二节且本教室第二节没课
		{
			secondID = cl.id;
			cl.setBuildingName(buildingName);
			cl.setRoomName(roomName);
			return true;
		}
		else
		{
			return false;
		}
	}
}
