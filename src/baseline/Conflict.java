package baseline;

import java.util.HashSet;

public class Conflict {// Conflict类，用于构造Vector<Conflict>保存从"ConflictMap"的数据并按common排序
	private HashSet<Integer> twoCourses = new HashSet<Integer>();	
	// 发生冲突的2门课程的id构成的HashSet
	private Integer common;							// 共同选课的学生人数
	private Integer ID1, ID2;						// 用于简便地返回课程ID
	
	public void setConflict(Integer id1, Integer id2, Integer com)
	{
		twoCourses.add(id1);
		twoCourses.add(id2);
		common = com;
		ID1 = id1;
		ID2 = id2;
	}
	public Integer getCourse1()
	{
		return ID1;
	}
	public Integer getCourse2()
	{
		return ID2;
	}
	public Integer getCommon()
	{
		return common;
	}
}