/*
 * ����ʵ�Ŀα�����(data.xls)�ͽ�������(room.xls), baseline�㷨�����ſν��
 * ��ÿ��ʱ�ε��ſν���ֱ�д��"11.xls","12.xls",...
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
	// ����ʱָ��ѡ�������ļ���ѡ��ʱ����ַ���(11,12,13,...)
	{
		dataFileName = dfn;
		ctime0 = ct0;
		ctime1 = ct1;
	}
	
	public String dataFileName;
	public String ctime0,ctime1;		// �ſε�ʱ��Σ�ĳ�������ĳ�����磩
	protected Vector<CourseLoad> firstSet = new Vector<CourseLoad>();	// �����һ�ڿοα�
	protected Vector<CourseLoad> secondSet = new Vector<CourseLoad>();	// ����ڶ��ڿοα�
	protected List<BuildingLoad> buildings = new ArrayList<BuildingLoad>();	// ��ѧ¥��
	
	public int loadCourseExcel(String ctime)// ���ļ�dataFileName�ж���ʱ���Ϊctime�Ŀγ�
	// ���ض����ĸ�ʱ��εĿγ�����
	{
		try {
			int cnt = 0;						// ��ʱ��εĿγ�����
			InputStream fis = new FileInputStream(new File(dataFileName));// �����ֽ���
			Workbook wb = Workbook.getWorkbook(fis);					// Excel Workbook��
			Sheet sheet = wb.getSheet(0);	// ��ǩҳ��getSheet()���Ǳ�ǩҳ��
			int i = 0;
			int capa = 0;
			for (i=2; i<sheet.getRows(); i++)	// �ӵ����п�ʼ����ȥ����ͷ
			{
				String depart = sheet.getCell(0,i).getContents();	// ������(column,row)
				String cID = sheet.getCell(1, i).getContents();
				String Capa = sheet.getCell(6, i).getContents();
				String courseName = sheet.getCell(3,i).getContents();
				String ttime = sheet.getCell(7,i).getContents();
				if (cID != "")
				{
					try {
						capa = Integer.parseInt(Capa);
						if (ttime.contains(ctime) && !depart.contains("����ѧԺ") 
								&& !depart.contains("������"))
						// �����Ρ�����ѧԺ�Ŀβ��ڽ�ѧ¥�ϿΣ�Ҫȥ��
						{
							CourseLoad cl = new CourseLoad(cID,courseName,capa,ttime);
							if(cl.getTime()==0)			// ����ǵ�һ�ڿ�
							{
								firstSet.add(cl);
							}
							else 						// ����ǵڶ��ڿ�
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
			return -1;						// file error ����-1��ʾ��ȡ����
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
	public void loadRoomExcel()				// ���ļ�"room.xls"�ж��������Ϣ
	{
		try {
			InputStream fis = new FileInputStream(new File("room.xls"));// �����ֽ���
			Workbook wb = Workbook.getWorkbook(fis);					// Excel Workbook��
			Sheet sheet = wb.getSheet(0);	// ��ǩҳ��getSheet()���Ǳ�ǩҳ��
			int i = 0, bid = 0, rid = 0;	// bid: ��ѧ¥id; rid: ����id
			String myBuil = sheet.getCell(0,i).getContents();
			int roomcapa;
			BuildingLoad bl = new BuildingLoad(bid,myBuil);;
			for (i=0; i<sheet.getRows(); i++)
			{
				String buil = sheet.getCell(0,i).getContents();	// ������(column,row)
				if (buil.length() > 1)
				{
					myBuil = buil;
					bl = new BuildingLoad(bid,myBuil);			// ���������ѧ¥�����½�ѧ¥��
				}
				else if (buil.contentEquals("A"))				// ���������ѧ¥��ֹ��
				{
					bl.sortRoom();		// �����䰴�����Ӵ�С����,�Ա�ʹ�÷����ʱ���˷�����
					buildings.add(bl);	// ����Ӻý��ҵĽ�ѧ¥�ӵ���ѧ¥�б�
					bid++;				// ��ѧ¥ID+1
					continue;			// ������ִ������Ĳ���
				}
				String roomName = sheet.getCell(1, i).getContents();
				String roomCapa = sheet.getCell(2, i).getContents();
				try {
					roomcapa = Integer.parseInt(roomCapa);
					Room rm = new Room(rid,myBuil,roomName,roomcapa);
					bl.addRoom(rm);		// �����Ҽ����ѧ¥
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
	public void printFirstSet()									// ��ӡ�����һ�ڿ��б�
	{
		System.out.println("First course set");
		for (CourseLoad cl : firstSet)
		{
			System.out.println(cl);
		}
	}
	public void printSecondSet()								// ��ӡ����ڶ��ڿα�
	{
		System.out.println("Second course set");
		for (CourseLoad cl : secondSet)
		{
			System.out.println(cl);
		}
	}
	public void printBuildingList()								// ��ӡ��ѧ¥�б�
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
	public void baselineMethod()// baseline�ſ��㷨
	/*
	 * ��firstSet��secondSet��������
	 * ���Ŵ�Σ��Ⱦ���һ����ѧ¥��
	 * �γ̰���������С�Ӵ�С�����Ŵ�κ���С��
	 * ��ѧ¥����ѧ¥���������Ӵ�С�����Ŵ�Ľ�ѧ¥����С�Ľ�ѧ¥
	 */
	{
		Collections.sort(buildings);			// buildings�����������Ӵ�С����
		Collections.sort(firstSet);				// firstSet����������������
		Collections.sort(secondSet);			// secondSet����������������
		for (CourseLoad cl : firstSet)			// �������һ�ڿ�
		{
			for (BuildingLoad bl : buildings)
			{
				if(bl.useRoom(cl))				// �ý�ѧ¥���Է�����γ�
				{
					break;
				}
			}
		}
		for (CourseLoad cl : secondSet)			// ������ڶ��ڿ�
		{
			for (BuildingLoad bl : buildings)
			{
				if(bl.useRoom(cl))				// �ý�ѧ¥���Է�����γ�
				{
					break;
				}
			}
		}
	}
	public void excelprintResult()				// ��ӡ��������excel��
	{
		File file0 = new File(ctime0 + ".xls");	// �ļ���
		File file1 = new File(ctime1 + ".xls");	// �ļ���
		try {
			WritableWorkbook wb0 = Workbook.createWorkbook(file0);	// ����������
			WritableWorkbook wb1 = Workbook.createWorkbook(file1);	// ����������
			WritableSheet sheet0 = wb0.createSheet("sheet0",0);		// ������ҳ
			WritableSheet sheet1 = wb1.createSheet("sheet0",0);		// ������ҳ
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
				Label label = new Label(0,i,cid);//��׼��һ������:(column,row,(String)entry)
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
			wb0.write();		// wb.close()֮ǰһ��Ҫ��wb.write()
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