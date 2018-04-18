/*
 * 一个由学生组成的班级，班里的学生都选一样的课
 * 取巧： Aclass类可以从DataSet.Student继承
 */

package dataSet2;

import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.Vector;

import DataSet.Course;
import DataSet.Student;

public class Aclass extends Student{
	public Aclass(Integer ID, Integer sNum)
	{
		super(ID);
		studentNum = sNum;
	}
	
	private Integer studentNum;					// 一个班级的学生人数
	
	public Integer getStudentNum()
	{
		return studentNum;
	}
	// 重载registCourse函数
	public void registCourse(Map<Integer,Course> firstCourse, 
			Map<Integer,Course> secondCourse, Map<TreeSet<Integer>,Integer> conflictMap,
			Vector<Integer> firstID, Vector<Integer> secondID)	// 一个班级同时选2门课
	{
		Random rand = new Random();								// 随机数种子：随机生成课序号
		int rand1,rand2,key1,key2;
		int i=0;	// i: 班级学生计数变量
		Vector<Integer> courses1 = new Vector<Integer>();	// 这个班的学生选的上午第一节课列表
		Vector<Integer> courses2 = new Vector<Integer>();	// 这个班的学生选的上午第二节课列表 
		while(i<studentNum)									// 如果课满了选不上则一直循环随机
		{
			rand1 = rand.nextInt(firstID.size());				// 随机上午第一节课的下标
			key1 = firstID.get(rand1);							// 上午第一节课的键值
			Course c1 = firstCourse.get(key1);					// 从课表里找出这节上午第一节课
			label1: for (; i<studentNum; i++)					// 一个班一起选上
			{
				if(!c1.beChosen())								// 一个班选到一半，课选满了
				{
					break label1;
				}
				else
				{
					courses1.add(key1);							// 选上key1
				}
			}
//			System.out.println(i);
		}
		i=0;	// 班级学生计数变量重新归零
		while(i<studentNum)									// 如果课满了选不上则一直循环随机
		{
			rand2 = rand.nextInt(secondID.size());			// 随机上午第一节课的下标
			key2 = secondID.get(rand2);						// 上午第一节课的键值
			Course c2 = secondCourse.get(key2);				// 从课表里找出这节上午第一节课
			label2: for (; i<studentNum; i++)				// 一个班一起选上
			{
				if(!c2.beChosen())					// 一个班选到一半选满了
				{
					break label2;
				}
				else
				{
					courses2.add(key2);				// 选上key2
				}
			}
		}
		// 更新conflictMap
		Integer oldValue = 0;
		for (i=0; i< studentNum; i++)
		{
			TreeSet<Integer> con = new TreeSet<Integer>();
			con.add(courses1.get(i));
			con.add(courses2.get(i));
			if(conflictMap.get(con)==null)						// 查询不到键值，就新建一个条目
			{
				conflictMap.put(con,1);
			}
			else												// 键值存在则value+1
			{
				oldValue = conflictMap.get(con);
				conflictMap.put(con,oldValue+1);
			}
		}
	}
}