/*
 * ����Room��Ľ�ѧ¥�࣬���ļ�"room.xls"����
 */

package baselineFromFile;

import java.util.*;

public class BuildingLoad implements Comparable<BuildingLoad> {
	public BuildingLoad(int ID, String Name)
	{
		id = ID;
		name = Name;
	}
	public int id;								// Ψһ��id
	public String name;							// ��ѧ¥����
	public List<Room> roomlist = new ArrayList<Room>();	// ��ѧ¥�����б�
	private int roomNum = 0;					// ��������,��ʼ��Ϊ0
	
	public int getRoomNum()						// ���ؽ�������
	{
		return roomNum;
	}
	public int compareTo(BuildingLoad bb)		// ���رȽϺ����������������Ӵ�С����
	{
		int brn = bb.getRoomNum();
		return roomNum > brn ? -1 : ( roomNum==brn? 0 : 1);
	}
	public void addRoom(Room rm)				// ��roomlist�м���һ������
	{
		roomlist.add(rm);
		roomNum++;
	}
	public void sortRoom()						// ��roomlist�еķ��䰴������С��������
	{
		Collections.sort(roomlist);
	}
	public boolean useRoom(CourseLoad cl)		// �γ�cl�ܷ����ý�ѧ¥,���Է���true,����false
	{
		for (Room rm : roomlist)				// ������ѧ¥�����н���
		{
			if (rm.fitin(cl))					// ������Է��£��ͷ���true
			{
				return true;
			}
		}
		return false;							// ÿ�����Ҷ��Ų����򷵻�false
	}
	
}
