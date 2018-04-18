/*
 * ������
 */

package baselineFromFile;

public class Room implements Comparable<Room> {
	public Room(int ID, String bn, String rn, int Capa)
	{
		id = ID;
		buildingName = bn;
		roomName = rn;
		capacity = Capa;
	}
	public int id;						// Ψһ��id
	public String buildingName;			// �������ڽ�ѧ¥������
	public String roomName;				// ���ҵ�����
	public int capacity;				// ��������
	protected String firstID = "";		// ����ĵ�һ�ڿε�id
	protected String secondID = "";		// ����ĵڶ��ڿε�id
	
	public String toString()			// ����toString��������syso����ֱ�Ӵ�ӡRoom������
	{
		String ans = roomName + "\t" + String.valueOf(capacity);
		return ans;
	}
	public int compareTo(Room rm)		// ���ؽ�����ıȽϺ��������������Ƚ�
	{
		int capa = rm.getCapacity();
		return ( capacity < capa ? -1 : (capacity==capa)? 0 : 1);
	}
	public int getCapacity()
	{
		return capacity;
	}
	public boolean fitin(CourseLoad cl)	// ����һ�γ̣�����ܷ��¾ͷ���true�����򷵻�false
	{
		int time = cl.getTime();
		int courseCapa = cl.getCapacity();
		if (capacity < courseCapa)		// ����������� < ������
		{
			return false;
		}
		else if (time==0 && firstID=="")// ����ǵ�һ���ұ����ҵ�һ��û��
		{
			firstID = cl.id;
			cl.setBuildingName(buildingName);
			cl.setRoomName(roomName);
			return true;
		}
		else if (time==1 && secondID=="")// ����ǵڶ����ұ����ҵڶ���û��
		{
			secondID = cl.id;
			cl.setBuildingName(buildingName);
			cl.setRoomName(roomName);
			return true;
		}
		else
		{
			return false;
		}
	}
}
