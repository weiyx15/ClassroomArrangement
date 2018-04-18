/*
 * CourseRead类：从文件CourseList中读入的课程类模板，相对于Dataset.Course更精简
 */

package baseline;

public class CourseRead {
	public Integer ID;								// 课程号，用整数表示
	private String name;							// 课程名：用String表示，与文件中一致
	private Integer capacity;						// 课容量
	private Integer cTime;							// 0: 上午第一节; 1: 上午第二节
	private Building b;								// 所在教学楼
	private Integer roomCapa;						// 所在教室的容量（用于最后在楼内排教室）
	
	public CourseRead(Integer id)
	{
		ID = id;
		name = String.format("%05d",id);	// 高位补0
	}
	public String getName()
	{
		return name;
	}
	public void setTime(Integer cT)
	{
		cTime = cT;
	}
	public Integer getTime()
	{
		return cTime;
	}
	public void setBuilding(Building buil)
	{
		b = buil;
	}
	public Building getBuilding()
	{
		return b;
	}
	public void setRoomCapa(Integer rc)
	{
		roomCapa = rc;
	}
	public Integer getRoomCapa()
	{
		return roomCapa;
	}
	public void setCapacity(Integer capa)
	{
		capacity = capa;
	}
	public Integer getCapacity()
	{
		return capacity;
	}
}
