/*
 * CourseRead�ࣺ���ļ�CourseList�ж���Ŀγ���ģ�壬�����Dataset.Course������
 */

package baseline;

public class CourseRead {
	public Integer ID;								// �γ̺ţ���������ʾ
	private String name;							// �γ�������String��ʾ�����ļ���һ��
	private Integer capacity;						// ������
	private Integer cTime;							// 0: �����һ��; 1: ����ڶ���
	private Building b;								// ���ڽ�ѧ¥
	private Integer roomCapa;						// ���ڽ��ҵ����������������¥���Ž��ң�
	
	public CourseRead(Integer id)
	{
		ID = id;
		name = String.format("%05d",id);	// ��λ��0
	}
	public String getName()
	{
		return name;
	}
	public void setTime(Integer cT)
	{
		cTime = cT;
	}
	public Integer getTime()
	{
		return cTime;
	}
	public void setBuilding(Building buil)
	{
		b = buil;
	}
	public Building getBuilding()
	{
		return b;
	}
	public void setRoomCapa(Integer rc)
	{
		roomCapa = rc;
	}
	public Integer getRoomCapa()
	{
		return roomCapa;
	}
	public void setCapacity(Integer capa)
	{
		capacity = capa;
	}
	public Integer getCapacity()
	{
		return capacity;
	}
}
