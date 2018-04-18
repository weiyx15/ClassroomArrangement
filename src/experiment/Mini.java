/*
 * 多次随机mini数据集:(7,7:3,3,2)生成数据集跑
 * 1. baseline算法
 * 2. 启发式算法
 * 3. 最优解
 * 比较算法性能
 */

package experiment;

import java.io.*;
import dataSet2.GenerateSparse;
import baseline.Arrange;
import algorithm1.Branch;
import optimal.Optimal;

public class Mini {
	protected static final int EXP_TIME = 30;		// 重复实验次数
	public static void main(String args[])
	{
		long startTime = System.currentTimeMillis();// program starting time
		miniExp();
		long endTime = System.currentTimeMillis();	// program ending time
		System.out.println("Run time: "+(endTime-startTime)/60000+" min");
	}
	
	// 在(7,7:3,3,2)数据上跑baseline
	public static void miniExp()
	{
		try {
			File file = new File("MiniExperiment.txt");
			OutputStream fout = new FileOutputStream(file);
			int cost_baseline, cost_proposed, cost_optimal;
			int i = 0;
			for (i=0; i<EXP_TIME; i++)
			{
				Arrange arr = new Arrange();
				arr.clearAll();
				// 生成选课数据]
				GenerateSparse.generate(7, 7);
				// baseline算法
				arr.setBuildings2();
				arr.readCourseList();
				arr.readConflictVec();
				arr.naiveArrange();
				cost_baseline = arr.computeCost();
				// 启发式算法
				Branch bra = new Branch();
				bra.clearAll();
				bra.setBuildings2();
				bra.readCourseList();
				bra.readConflictVec();
				bra.initCourseLeft();
				bra.arrange1();
				cost_proposed = bra.computeCost();
				// 枚举法
				Optimal opt = new Optimal();
				opt.clearAll();
				opt.readCourseList();
				opt.readConflictVec();
				opt.setBuildings2();
				cost_optimal = opt.arrange2();
				System.out.println(cost_baseline);
				System.out.println(cost_proposed);
				System.out.println(cost_optimal);
				String s0 = String.valueOf(cost_baseline);
				String s1 = String.valueOf(cost_proposed);
				String s2 = String.valueOf(cost_optimal);
				String sout = s0+"\t"+s1+"\t"+s2+"\r\n";
				fout.write(sout.getBytes());
			}
			fout.close();
		}
		catch (FileNotFoundException nfx)
		{
			nfx.printStackTrace();
			return;
		}
		catch (IOException iox)
		{
			iox.printStackTrace();
			return;
		}
	}
}
