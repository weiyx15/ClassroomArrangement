/*
 * 将约束松弛化
 * 重载CourseTable/CourseTableSparse的generate方法，
 * >150, >60, >30的课不要满限额，给启发式算法回旋的余地
 */

package dataSet3;

import java.util.Random;
import DataSet.Course;
import dataSet2.CourseTableSparse;

public class CourseTableSlack extends CourseTableSparse {
	public CourseTableSlack(int n1, int n2)
	{
		super(n1, n2);						// n1： 上午第一节课门数; n2: 上午第二节课门数
	}
	// 重载CourseTable/CourseTableSparse的generate方法，>150, >60, >30的课不要满限额
	public int generateTable()// randomly create course and add to list，返回最多容许的选课学生
	{
		Random rand = new Random();			// 随机数种子
		int i=0;
		int larger150 = 0;										// >150人的课
		int larger60 = 0;										// >60人的课
		int larger30 = 0;										// >30人的课
		int getRand = 0;	// 根据课容量的随机结果限制大容量的课防止教室放不下,因此要记录每次随机值
		int totalFirst = 0;			// 上午第一节的总人数
		int totalSecond = 0;		// 上午第二节的总人数
		for (i=0; i<num1; i++)
		{
			Course c = new Course(i);
			if(larger30==164/2)						// 将>30人的课程上限减为一半
			{
				getRand = 0;
			}
			else if(larger60==52/2)					// 将>60人的课程上限减为一半
			{
				getRand = rand.nextInt(2);
			}
			else if(larger150==6/2)					// 将>150人的课程上限减为一半
			{
				getRand = rand.nextInt(4);
			}
			else
			{
				getRand = rand.nextInt(6);
			}
			switch(getRand)
			{
			case 4:
			case 5:
				larger150++;
			case 2:
			case 3:
				larger60++;
			case 1:
				larger30++; break;
			default: break;
			}
			c.setCapacity(CAPACITY[getRand]);		// 6: length of const array CAPACITY
			c.setTime(0);
			firstCourse.put(i,c);					// 0: 上午第一节,1: 上午第二节
			firstID.add(i);
			totalFirst += c.getCapacity();
			courseMap.put(i,c);	
		}
		larger30 = 0;
		larger60 = 0;
		larger150 = 0;
		for (i=num1; i<num2+num1; i++)
		{
			Course c = new Course(i);
			if(larger30==164/2)									// 将>30人的课程上限减为一半
			{
				getRand = 0;
			}
			else if(larger60==52/2)								// 将>60人的课程上限减为一半
			{
				getRand = rand.nextInt(2);
			}
			else if(larger150==6/2)								// 将>150人的课程上限减为一半
			{
				getRand = rand.nextInt(4);
			}
			else
			{
				getRand = rand.nextInt(6);
			}
			switch(getRand)
			{
			case 4:
			case 5:
				larger150++;
			case 2:
			case 3:
				larger60++;
			case 1:
				larger30++; break;
			default: break;
			}
			c.setCapacity(CAPACITY[getRand]);			// 6: length of const array CAPACITY
			c.setTime(1);
			secondCourse.put(i,c);							// 0: 上午第一节
			secondID.add(i);
			totalSecond += c.getCapacity();
			courseMap.put(i,c);	
		}
		return totalFirst<totalSecond? totalFirst:totalSecond;	// 返回两者之间的小者
	}
	
}
