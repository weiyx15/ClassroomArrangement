/*
 * 用真实的课表数据(data.xls)和教室数据(room.xls), baseline算法生成排课结果
 * 将每个时段的排课结果分别写入"11.xls","12.xls",...
 */

package baselineFromFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.*;
import jxl.read.biff.BiffException;

public class ArrangeFromFile{
	public ArrangeFromFile(String dfn, String ct0, String ct1)
	// 构造时指定选课数据文件和选课时间段字符串(11,12,13,...)
	{
		dataFileName = dfn;
		ctime0 = ct0;
		ctime1 = ct1;
	}
	
	public String dataFileName;
	public String ctime0,ctime1;		// 排课的时间段（某个上午或某个下午）
	protected Vector<CourseLoad> firstSet = new Vector<CourseLoad>();	// 上午第一节课课表
	protected Vector<CourseLoad> secondSet = new Vector<CourseLoad>();	// 上午第二节课课表
	protected List<BuildingLoad> buildings = new ArrayList<BuildingLoad>();	// 教学楼表
	
	public int loadCourseExcel(String ctime)// 从文件dataFileName中读入时间段为ctime的课程
	// 返回读到的该时间段的课程门数
	{
		try {
			int cnt = 0;						// 该时间段的课程门数
			InputStream fis = new FileInputStream(new File(dataFileName));// 输入字节流
			Workbook wb = Workbook.getWorkbook(fis);					// Excel Workbook类
			Sheet sheet = wb.getSheet(0);	// 标签页，getSheet()里是标签页号
			int i = 0;
			int capa = 0;
			for (i=2; i<sheet.getRows(); i++)	// 从第三行开始读，去掉表头
			{
				String depart = sheet.getCell(0,i).getContents();	// 索引是(column,row)
				String cID = sheet.getCell(1, i).getContents();
				String Capa = sheet.getCell(6, i).getContents();
				String courseName = sheet.getCell(3,i).getContents();
				String ttime = sheet.getCell(7,i).getContents();
				if (cID != "")
				{
					try {
						capa = Integer.parseInt(Capa);
						if (ttime.contains(ctime) && !depart.contains("美术学院") 
								&& !depart.contains("体育部"))
						// 体育课、美术学院的课不在教学楼上课，要去掉
						{
							CourseLoad cl = new CourseLoad(cID,courseName,capa,ttime);
							if(cl.getTime()==0)			// 如果是第一节课
							{
								firstSet.add(cl);
							}
							else 						// 如果是第二节课
							{
								secondSet.add(cl);
							}
							cnt++;
						}
					}
					catch (NumberFormatException e)
					{
						e.printStackTrace();
					}
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
	public void loadRoomExcel()				// 从文件"room.xls"中读入教室信息
	{
		try {
			InputStream fis = new FileInputStream(new File("room.xls"));// 输入字节流
			Workbook wb = Workbook.getWorkbook(fis);					// Excel Workbook类
			Sheet sheet = wb.getSheet(0);	// 标签页，getSheet()里是标签页号
			int i = 0, bid = 0, rid = 0;	// bid: 教学楼id; rid: 教室id
			String myBuil = sheet.getCell(0,i).getContents();
			int roomcapa;
			BuildingLoad bl = new BuildingLoad(bid,myBuil);;
			for (i=0; i<sheet.getRows(); i++)
			{
				String buil = sheet.getCell(0,i).getContents();	// 索引是(column,row)
				if (buil.length() > 1)
				{
					myBuil = buil;
					bl = new BuildingLoad(bid,myBuil);			// 如果读到教学楼名则建新教学楼类
				}
				else if (buil.contentEquals("A"))				// 如果读到教学楼截止符
				{
					bl.sortRoom();		// 将房间按容量从大到小排序,以便使用房间的时候不浪费容量
					buildings.add(bl);	// 将添加好教室的教学楼加到教学楼列表
					bid++;				// 教学楼ID+1
					continue;			// 跳过不执行下面的步骤
				}
				String roomName = sheet.getCell(1, i).getContents();
				String roomCapa = sheet.getCell(2, i).getContents();
				try {
					roomcapa = Integer.parseInt(roomCapa);
					Room rm = new Room(rid,myBuil,roomName,roomcapa);
					bl.addRoom(rm);		// 将教室加入教学楼
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		catch (BiffException e)
		{
			e.printStackTrace();			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void printFirstSet()									// 打印上午第一节课列表
	{
		System.out.println("First course set");
		for (CourseLoad cl : firstSet)
		{
			System.out.println(cl);
		}
	}
	public void printSecondSet()								// 打印上午第二节课表
	{
		System.out.println("Second course set");
		for (CourseLoad cl : secondSet)
		{
			System.out.println(cl);
		}
	}
	public void printBuildingList()								// 打印教学楼列表
	{
		System.out.println("Building List");
		for (BuildingLoad bl : buildings)
		{
			System.out.println(bl.name + " ****** " + bl.getRoomNum());
			for (Room rm : bl.roomlist)
			{
				System.out.println(rm);
			}
		}
	}
	public void baselineMethod()// baseline排课算法
	/*
	 * 对firstSet和secondSet单独考虑
	 * 先排大课，先尽着一个教学楼排
	 * 课程按课容量大小从大到小，先排大课后排小课
	 * 教学楼按教学楼教室数量从大到小，先排大的教学楼后排小的教学楼
	 */
	{
		Collections.sort(buildings);			// buildings按教室数量从大到小排序
		Collections.sort(firstSet);				// firstSet按课容量降序排列
		Collections.sort(secondSet);			// secondSet按课容量降序排列
		for (CourseLoad cl : firstSet)			// 放上午第一节课
		{
			for (BuildingLoad bl : buildings)
			{
				if(bl.useRoom(cl))				// 该教学楼可以放这个课程
				{
					break;
				}
			}
		}
		for (CourseLoad cl : secondSet)			// 放上午第二节课
		{
			for (BuildingLoad bl : buildings)
			{
				if(bl.useRoom(cl))				// 该教学楼可以放这个课程
				{
					break;
				}
			}
		}
	}
	public void excelprintResult()				// 打印输出结果到excel中
	{
		File file0 = new File(ctime0 + ".xls");	// 文件名
		File file1 = new File(ctime1 + ".xls");	// 文件名
		try {
			WritableWorkbook wb0 = Workbook.createWorkbook(file0);	// 创建工作簿
			WritableWorkbook wb1 = Workbook.createWorkbook(file1);	// 创建工作簿
			WritableSheet sheet0 = wb0.createSheet("sheet0",0);		// 创建表页
			WritableSheet sheet1 = wb1.createSheet("sheet0",0);		// 创建表页
			int i = 0, len0 = firstSet.size(), len1 = secondSet.size();
			String cid, cname, cctime, cbuil, croom;
			int capacity;
			for (i=0; i<len0; i++)
			{
				CourseLoad cl = firstSet.get(i);
				cid = cl.id;
				cname = cl.getName();
				cctime = cl.getCTime();
				capacity = cl.getCapacity();
				cbuil = cl.getBuildingName();
				croom = cl.getRoomName();
				Label label = new Label(0,i,cid);//标准的一条数据:(column,row,(String)entry)
				sheet0.addCell(label);
				label = new Label(1,i,cname);
				sheet0.addCell(label);
				label = new Label(2,i,cctime);
				sheet0.addCell(label);
				label = new Label(3,i,String.valueOf(capacity));
				sheet0.addCell(label);
				label = new Label(4,i,cbuil);
				sheet0.addCell(label);
				label = new Label(5,i,croom);
				sheet0.addCell(label);
			}
			for (i=0; i<len1; i++)
			{
				CourseLoad cl = secondSet.get(i);
				cid = cl.id;
				cname = cl.getName();
				cctime = cl.getCTime();
				capacity = cl.getCapacity();
				cbuil = cl.getBuildingName();
				croom = cl.getRoomName();
				Label label = new Label(0,i,cid);
				sheet1.addCell(label);
				label = new Label(1,i,cname);
				sheet1.addCell(label);
				label = new Label(2,i,cctime);
				sheet1.addCell(label);
				label = new Label(3,i,String.valueOf(capacity));
				sheet1.addCell(label);
				label = new Label(4,i,cbuil);
				sheet1.addCell(label);
				label = new Label(5,i,croom);
				sheet1.addCell(label);
			}
			wb0.write();		// wb.close()之前一定要加wb.write()
			wb0.close();
			wb1.write();
			wb1.close();
		}
		catch (WriteException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}