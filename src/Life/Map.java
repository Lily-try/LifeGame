package Life;
import java.util.Random;

public class Map {

	final static public int row=20;
	final static public int col=20;
	public static Cell cell[][];
	//初始化，随机生成地图
	public Map() {
		super();
		Map.cell=new Cell[row][col];
		Random rnd=new Random();
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				cell[i][j]=new Cell();
				rnd=new Random();
				cell[i][j].setStatus(rnd.nextInt(2));
			}
		}
	}
	public  Cell[][] getCell() {
		return cell;
	}
	public static void setCell(Cell[][] cell) {
		Map.cell = cell;
	}
	//获取并设置所有细胞周围的生命状态
	public static void getNeighborAlive()
	{
		for(int i=0;i<row;i++) {
    		for(int j=0;j<col;j++) {
    			int living=0;
    			if(i>0&&j>0) living+=cell[i-1][j-1].getStatus();//左上方
    			if(i>0) living+=cell[i-1][j].getStatus();//正上方
    			if(j>0) living+=cell[i][j-1].getStatus();//正左方
    			if(i<row-1&&j<col-1) living+=cell[i+1][j+1].getStatus();//右下方
    			if(i<row-1) living+=cell[i+1][j].getStatus();//正下方
    			if(j<col-1) living+=cell[i][j+1].getStatus();//正右方
    			if(i<row-1&&j>0) living+=cell[i+1][j-1].getStatus();//左下方
    			if(i>0&&j<col-1) living+=cell[i-1][j+1].getStatus();//右上方
    			cell[i][j].setAlive(living);
    		}
    	}
		
	}
	
	//更新地图
	public int update()
	{
		int count=0;
		int status;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				status=cell[i][j].getStatus();
				
				cell[i][j].UpdateStatus();
				
				if(status==cell[i][j].getStatus())
					count++;
			}
		}
		return count;
	}
	
	
	/*public static void printMap() {
    	for(int i=0;i<row;i++) {
    		for(int j=0;j<col;j++) {
    			System.out.print(cell[i][j].getStatus()+" ");
    		}
    		System.out.println();
    	}
    }*/

}
