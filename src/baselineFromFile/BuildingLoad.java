/*
 * 包含Room类的教学楼类，从文件"room.xls"读入
 */

package baselineFromFile;

import java.util.*;

public class BuildingLoad implements Comparable<BuildingLoad> {
	public BuildingLoad(int ID, String Name)
	{
		id = ID;
		name = Name;
	}
	public int id;								// 唯一的id
	public String name;							// 教学楼名称
	public List<Room> roomlist = new ArrayList<Room>();	// 教学楼教室列表
	private int roomNum = 0;					// 教室数量,初始化为0
	
	public int getRoomNum()						// 返回教室数量
	{
		return roomNum;
	}
	public int compareTo(BuildingLoad bb)		// 重载比较函数，按教室数量从大到小排序
	{
		int brn = bb.getRoomNum();
		return roomNum > brn ? -1 : ( roomNum==brn? 0 : 1);
	}
	public void addRoom(Room rm)				// 向roomlist中加入一个房间
	{
		roomlist.add(rm);
		roomNum++;
	}
	public void sortRoom()						// 将roomlist中的房间按容量从小到大排序
	{
		Collections.sort(roomlist);
	}
	public boolean useRoom(CourseLoad cl)		// 课程cl能否放入该教学楼,可以返回true,否则false
	{
		for (Room rm : roomlist)				// 遍历教学楼的所有教室
		{
			if (rm.fitin(cl))					// 如果可以放下，就返回true
			{
				return true;
			}
		}
		return false;							// 每个教室都放不下则返回false
	}
	
}
