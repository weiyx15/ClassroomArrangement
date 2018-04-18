/* Building类刻画教学楼的信息：教室，教室容量 */

package baseline;

import java.util.*;

public class Building {
	public String name;	// 名字： String类，不能作为标识符索引
	public int id;		// id: 整数，教学楼的唯一标识符
	protected Map<Integer,Integer> roomMap = new TreeMap<Integer,Integer>();
	// 房间列表，第一项是房间容量，第二项是这种容量的房间个数
	// 有序Map，按键值升序，这样可以优先用满足课容量的尽量小的教室
	protected Map<Integer,Integer> roomMap2 = new TreeMap<Integer,Integer>();
	// 对上午第二节，需要同样一套新的教室资源
	protected Map<CourseRead,Integer> firstIn= new HashMap<CourseRead,Integer>();
	// 该栋教学楼的上午第一节课程列表, value是所在教室的容量
	protected Map<CourseRead,Integer> secondIn= new HashMap<CourseRead,Integer>();
	// 该栋教学楼的上午第二节课程列表, value是所在教室的容量
	
	public Building(String s, int ID)
	{
		name = s;
		id = ID;
	}
	public Map<Integer,Integer> getRoomMap()				// 返回房间列表
	{
		return roomMap;
	}
	public Map<CourseRead,Integer> getFirstIn()
	{
		return firstIn;
	}
	public Map<CourseRead,Integer> getSecondIn()
	{
		return secondIn;
	}
	public void addRoom(Integer capacity, Integer num)		// 创建教学楼时设计教室容量和个数
	{
		roomMap.put(capacity, num);
		roomMap2.put(capacity, num);
	}
	public Boolean useRoom(CourseRead cr)// 被一个课程占用教室. true: 放进去了; false:满了放不进
	{
		int oldValue = 0, oldKey = 0;
		if(cr.getTime()==0)									// 在排上午第一节
		{
			for (Map.Entry<Integer, Integer> entry : roomMap.entrySet())
			{
				oldKey = entry.getKey();					// oldKey: 房间容量
				oldValue = entry.getValue();				// oldValue: 此容量的房间个数
				if (oldKey>=cr.getCapacity() && oldValue>0)	// 如果有满足要求的空房间
				{
					roomMap.put(oldKey, oldValue-1);		// 房间库存减少一个
					firstIn.put(cr, oldKey);				// 教学楼的课程列表加入一项
					cr.setBuilding(this);					// 课程所在的教学楼
					cr.setRoomCapa(oldKey);					// 课程所在的教室容量
					return true;							// 这门课可以放在这栋楼里
				}
			}
			return false;									// 这门课放不进这栋楼里
		}
		else												// 在排上午第二节
		{
			for (Map.Entry<Integer, Integer> entry : roomMap2.entrySet())
			{
				oldKey = entry.getKey();
				oldValue = entry.getValue();
				if (oldKey>=cr.getCapacity() && oldValue>0)
				{
					roomMap2.put(oldKey, oldValue-1);
					secondIn.put(cr, oldKey);
					cr.setBuilding(this);
					cr.setRoomCapa(oldKey);
					return true;							// 这门课可以放在这栋楼里
				}
			}
			return false;									// 这门课放不进这栋楼里
		}
	}
}