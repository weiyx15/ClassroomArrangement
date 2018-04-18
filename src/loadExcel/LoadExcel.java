/*
 * 从开课信息数据data.xls文件中读取每一个时段(11,12,...)的课程名和课程总数
 */

package loadExcel;

import java.io.*;
import jxl.Sheet;  
import jxl.Workbook;  
import jxl.read.biff.BiffException;

public class LoadExcel {
	public int loadExcel(String ctime)	// 输入参数：上课时间段，如"11"表示上午第一节
	{
		try {
			int cnt = 0;				// 该时间段的课程门数
			InputStream fis = new FileInputStream(new File("data.xls"));// 输入字节流
			Workbook wb = Workbook.getWorkbook(fis);					// Excel Workbook类
			Sheet sheet = wb.getSheet(0);	// 标签页，getSheet()里是标签页号
			int i = 0;
			for (i=0; i<sheet.getRows(); i++)
			{
				String depart = sheet.getCell(0,i).getContents();	// 索引是(column,row)
				String courseName = sheet.getCell(3,i).getContents();// 读出的结果是字符串
				String ttime = sheet.getCell(7,i).getContents();
				if (ttime.contains(ctime) && !depart.contains("美术学院"))
					// 美术学院不在教学楼上课，要去掉
				{
					System.out.println(depart+"\t"+courseName+"\t"+ttime);
					cnt++;
				}
			}
			return cnt;
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
			return -1;						// file error 返回-1表示读取错误
		}
		catch (BiffException e)
		{
			e.printStackTrace();			// Biff error
			return -1;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return -1;						// IO error
		}
	}
}
