package DataSet;

import java.util.Map;
import java.util.Random;
import java.util.Vector;

// 一个学生，随机选随机门课，先到先得
public class Student {
	public Student(Integer ID)
	{
		sID = ID;
	}
	
	public Integer sID;											// 学生ID
	protected Integer course1,course2;							// 该学生选的2门课
	// 设成protected让子类Aclass可以使用
 
	public int getCourse1()
	{
		return course1;
	}
	public int getCourse2()
	{
		return course2;
	}
	public void registCourse(Map<Integer,Course> firstCourse, Map<Integer,Course> secondCourse, 
			Vector<Integer> firstID, Vector<Integer> secondID)	// 一个学生选2门课
	{
		Random rand = new Random();								// 随机数种子：随机生成课序号
		int rand1,rand2,key1,key2;
		rand1 = rand.nextInt(firstID.size());					// 随机上午第一节课的下标
		rand2 = rand.nextInt(secondID.size());					// 随机上午第二节课的下标
		key1 = firstID.get(rand1);								// 上午第一节课的键值
		key2 = secondID.get(rand2);								// 上午第二节课的键值
		Course c1 = firstCourse.get(key1);						// 从课表里找出这节上午第一节课
		Course c2 = secondCourse.get(key2);						// 从课表里找出这节上午第二节课
		while(c1.vetifyFull())									// 如果课满了选不上则一直循环随机
		{
			rand1 = rand.nextInt(firstID.size());					// 随机上午第一节课的下标
			key1 = firstID.get(rand1);								// 上午第一节课的键值
			c1 = firstCourse.get(key1);								// 从课表里找出这节上午第一节课
		}
		c1.beChosen();
		course1 = key1;
		while(c2.vetifyFull())										// 如果课满了选不上则一直循环随机
		{
			rand2 = rand.nextInt(secondID.size());					// 随机上午第一节课的下标
			key2 = secondID.get(rand2);								// 上午第一节课的键值
			c2 = secondCourse.get(key2);							// 从课表里找出这节上午第一节课
		}
		c2.beChosen();
		course2 = key2;
	}
}