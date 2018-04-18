/*
 * 暴力枚举法求cost最小的解：
 * 由于算法时间复杂度的限制，只能跑5节第一节课、5节第二节课、3教学楼6教室的mini模型
 * 采用setBuildings2()方法生成builings表和COST矩阵
 */

package optimal;

import java.util.*;
import baseline.Arrange;
import baseline.CourseRead;

public class Optimal extends Arrange{
//	public static void main(String args[])
//	{
//		int minCost = 0;					// 最小cost函数
//		setBuildings2();
//		readCourseList();
//		readConflictVec();
//		long startTime = System.currentTimeMillis();	// program starting time
//		minCost = arrange2();
//		long endTime = System.currentTimeMillis();	// program ending time
//		System.out.println("Run time: "+(endTime-startTime)/1000+" s");
//		System.out.println("Cost function: "+minCost);
//	}
	
	public int arrange2()
	/*
	 * 排课算法2：
	 * 暴力枚举求最优解
	 * 返回最小的cost值
	 */
	{
		Vector<CourseRead> allSet = new Vector<CourseRead>();	// 合并firstSet和secondSet
		for (CourseRead c1 : firstSet)
		{
			allSet.add(c1);
		}
		for (CourseRead c2 : secondSet)
		{
			allSet.add(c2);
		}
		// enu: 遍历所有情况的数组, enu[i] = 0~4代表5栋教学楼
		// pnu: enu的前一步，与enu一起可以刻画上升沿
		int enu[] = new int[firstSet.size()+secondSet.size()];
		int i = 0;
		int len = enu.length;			// 第一节课和第二节课课程总数
		int b_len = buildings.size();	// 教学楼总数
		for (i=0; i<len; i++)
		{
			enu[i] = 0;
		}
		int costMin = Integer.MAX_VALUE;	// 维护最小的cost值
		int costThis = Integer.MAX_VALUE;	// 当前安排方式下的cost值
		boolean feasible = true;			// 该解是否是可行解
		int cnt = 0;						// 枚举计数器
		int divider = 1;
		int total = 1;						// 总的情况个数
		for (i=0; i<len; i++)
		{
			total *= b_len;
		}
		while (cnt < total)					// 遍历所有可能的组合
		{
			divider = 1;
			for (i=0; i<len; i++)
			{
				enu[i] = (cnt%(divider*b_len))/divider;
				divider *= b_len;
			}
			cnt++;
			System.out.println(cnt+"/"+total);
			// 清除所有教学楼内原有课程, 为教学楼恢复原状
			buildings.clear();				// 清空buildings
			COST.clear();					// 清空COST
			setBuildings2();				// 重新生成教学楼
			feasible = true;				// 一个新解先默认它是可行解
			for (i=0; i<len; i++)
			{
				CourseRead cr = allSet.get(i);
				if(!buildings.get(enu[i]).useRoom(cr))
				{
					feasible = false;		// 该解不可行
					break;
				}
			}
			if (feasible)					// 如果是可行解
			{
				costThis = computeCost();	// 计算当前解的cost值
				System.out.println("current cost: "+costThis);
				costMin = costMin < costThis? costMin : costThis;	// 更新最小的cost值
			}
		}
		return costMin;
	}
}
