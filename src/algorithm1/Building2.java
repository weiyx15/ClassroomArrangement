/*
 * 继承Building类以增加对于courseLeft和redundant的维护
 */

package algorithm1;

import java.util.Map;

import baseline.Building;
import baseline.CourseRead;
import baseline.Arrange;

public class Building2 extends Building{
	public Building2(String s, int ID)
	{
		super(s, ID);
	}
	
	private void courseLeftMinus(CourseRead cr)	// 将一门课放入教学楼后执行courseLeft[i][j]--
	{
		int i=cr.getTime();						// 区分是上午第一节课还是上午第二节课
		int capa = cr.getCapacity();			// 课容量
		if (capa > Arrange.ROOM_CAPACITY[2])	// >150
		{
			Branch.courseLeft[i][0]--;
		}
		if (capa > Arrange.ROOM_CAPACITY[1])	// >60
		{
			Branch.courseLeft[i][1]--;
		}
		if (capa > Arrange.ROOM_CAPACITY[0])	// >30
		{
			Branch.courseLeft[i][2]--;
		}
	}
	private void redundantMinus(int capa, CourseRead cr)	// 教室被占用后剩余教室--
	{
		int i = cr.getTime();					// // 区分是上午第一节课还是上午第二节课
		if (capa > Arrange.ROOM_CAPACITY[2])	// >150
		{
			Branch.redundant[i][0]--;
		}
		if (capa > Arrange.ROOM_CAPACITY[1])	// >60
		{
			Branch.redundant[i][1]--;
		}
		if (capa > Arrange.ROOM_CAPACITY[0])	// >30
		{
			Branch.redundant[i][2]--;
		}
	}
	private boolean verifyRoomScarce(CourseRead cr, int roomCapa)
	// 是否发生了课程越级占用稀缺教室资源的情况
	// i: 即将占用教室的课程是上午第一节还是上午第二节
	// roomCapa： 该课程瞄准的教室容量
	{
		int i = cr.getTime();					// i: 上课时间
		int courseCapa = cr.getCapacity();		// 课容量
		int j = -1, k = -1;	// j:教室的稀缺等级，k: 课程的稀缺等级,都初始化为-1表示不稀缺
		if (roomCapa>Arrange.ROOM_CAPACITY[2])	// >150
		{
			j = 0;
		}
		else if(roomCapa>Arrange.ROOM_CAPACITY[1])// > 60
		{
			j = 1;
		}
		else if(roomCapa>Arrange.ROOM_CAPACITY[0])// >30
		{
			j = 2;
		}
		if (courseCapa>Arrange.ROOM_CAPACITY[2])	// >150
		{
			k = 0;
		}
		else if(courseCapa>Arrange.ROOM_CAPACITY[1])// > 60
		{
			k = 1;
		}
		else if(courseCapa>Arrange.ROOM_CAPACITY[0])// >30
		{
			k = 2;
		}
		if (j!=-1 && (j<k||k==-1) && Branch.redundant[i][j]<=Branch.courseLeft[i][j])
			// 如果越级占用稀缺资源
		{
			return true;		// 该教室资源稀缺，不能再用了
		}
		else
		{
			return false;
		}	
	}
	// 重载useRoom方法，加入对于courseLeft和redundant的处理
	public Boolean useRoom(CourseRead cr)// 被一个课程占用教室. true: 放进去了; false:满了放不进
	{
		int oldValue = 0, oldKey = 0;
		if(cr.getTime()==0)									// 在排上午第一节
		{
			for (Map.Entry<Integer, Integer> entry : roomMap.entrySet())
			{
				oldKey = entry.getKey();					// oldKey: 房间容量
				oldValue = entry.getValue();				// oldValue: 此容量的房间个数
				if (!verifyRoomScarce(cr,oldKey) && 
						oldKey>=cr.getCapacity() && oldValue>0)	// 如果有满足要求的空房间
				{
					roomMap.put(oldKey, oldValue-1);		// 房间库存减少一个
					firstIn.put(cr, oldKey);				// 教学楼的课程列表加入一项
					cr.setBuilding(this);					// 课程所在的教学楼
					cr.setRoomCapa(oldKey);					// 课程所在的教室容量
					courseLeftMinus(cr);					// courseLeft[i][j]--
					redundantMinus(oldKey,cr);				// redundant[i][j]--
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
				if (!verifyRoomScarce(cr,oldKey) && 
						oldKey>=cr.getCapacity() && oldValue>0)
				{
					roomMap2.put(oldKey, oldValue-1);		// 房间库存减少一个
					secondIn.put(cr, oldKey);				// 教学楼的课程列表加入一项
					cr.setBuilding(this);					// 课程所在的教学楼
					cr.setRoomCapa(oldKey);					// 课程所在的教室容量
					courseLeftMinus(cr);					// courseLeft[i][j]--
					redundantMinus(oldKey,cr);				// redundant[i][j]--
					return true;							// 这门课可以放在这栋楼里
				}
			}
			return false;									// 这门课放不进这栋楼里
		}
	}
}
