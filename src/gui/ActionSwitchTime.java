package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import baselineFromFile.CourseLoad;

public class ActionSwitchTime implements ActionListener			
// 根据按下的单选按钮的编号改变ctime即显示的课表的时间段
{
	public int i;					// 单选按钮编号
	public ActionSwitchTime(int ii)	// 设置单选按钮的编号
	{
		i = ii;
	}
	public void actionPerformed(ActionEvent e)	// 根据按钮编号改变课表时间段字符串
	{
		int len1 = CourseLoad.FIRSTTABLE.length;
		if (i < len1)
		{
			GUI.ctime = CourseLoad.FIRSTTABLE[i];// 左边一列按钮
		}
		else
		{
			GUI.ctime = CourseLoad.SECONDTABLE[i-len1];	// 右边一列按钮
		}
		loadResult();							// 重新加载当前时间段的排课结果
	}
	public void loadResult()					// 根据当前ctime的值从excel文件中加载排课数据
	{
		String fn = GUI.ctime + ".xls";			// ctime对应的结果的文件名
		int i = 0;
		try {
			File ff = new File(fn);
			FileInputStream fis = new FileInputStream(ff);
			Workbook wb = Workbook.getWorkbook(fis);	// Excel Workbook类
			Sheet sheet = wb.getSheet(0);	// 标签页，getSheet()里是标签页号
			String result[][] = new String[sheet.getRows()][3];	// 初始化result数组大小
			for (i=0; i<sheet.getRows(); i++)
			{
				result[i][0] = sheet.getCell(1,i).getContents();			// 课程名
				result[i][1] = sheet.getCell(4,i).getContents();			// 教学楼名
				result[i][2] = sheet.getCell(5,i).getContents();			// 教室号
			}
			wb.close();
			GUI.model.setDataVector(result, GUI.head);	// 修改model中的内容，向表格重新加载数据
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		catch (BiffException e)
		{
			e.printStackTrace();			// Biff error
		}
        catch (IOException e) {
            e.printStackTrace();
        }
	}
}