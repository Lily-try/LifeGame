package Life;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


//import Try.myPanel;

import java.awt.Toolkit;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Control extends JFrame {

	JPanel contentPane;
	myPanel mp =null;
	static Control frame ;
	
	public JButton btnInit;
	JButton btnStep;
	JButton btnKeep;
	JButton btnEnd;
	JButton btnClear;
	Graphics gus;
	
	Map map=new Map();
	Timer timer=new Timer();
	JLabel GenerationLabel;
	public int count=0; //演化次数
	int row=map.row;
	int col=map.col;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Control();
					frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Control() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\dell\\Desktop\\1.jpg"));
		
		setTitle("\u751F\u547D\u6E38\u620F");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 981, 614);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		mp=new myPanel();
		contentPane.add(mp);
		mp.setBounds(50,25,row*25+50,col*25+25);
		//Graphics g=mp.getGraphics();
		//contentPane.setVisible(true);
		//jpanel.setVisible(true);
		
		btnClear = new JButton("\u6E05\u7A7A\u753B\u9762");
		btnClear.setEnabled(false);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClear.setEnabled(false);
				Clear();
				
			}
		});
		btnClear.setFont(new Font("楷体_GB2312", Font.PLAIN, 14));
		btnClear.setBounds(655, 60, 259, 47);
		btnClear.setName("btnClear");
		contentPane.add(btnClear);
		
		btnInit = new JButton("\u968F\u673A\u751F\u6210\u65B0\u753B\u9762");
		btnInit.setActionCommand("enable");
		btnInit.setFont(new Font("楷体_GB2312", Font.PLAIN, 14));
		btnInit.setBounds(655, 167, 259, 47);
		contentPane.add(btnInit);
		btnInit.setName("btnInit");
		btnInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//frame.repaint();
				btnStep.setEnabled(true);
				btnKeep.setEnabled(true);
				btnClear.setEnabled(true);
				
				InitMap();
				
			}
		});
		
		
		//单步演化
		btnStep = new JButton("\u8FDB\u884C\u5355\u6B65\u6F14\u5316");
		btnStep.setEnabled(false);
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StepUpdate();
			}
		});
		btnStep.setFont(new Font("楷体_GB2312", Font.PLAIN, 14));
		btnStep.setBounds(655, 274, 259, 47);
		btnStep.setName("btnStep");
		contentPane.add(btnStep);
		
		btnKeep = new JButton("\u5F00\u59CB\u4E0D\u95F4\u65AD\u6F14\u5316");
		btnKeep.setEnabled(false);
		btnKeep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnInit.setEnabled(false);
				
				
				btnClear.setEnabled(false);
				btnStep.setEnabled(false);
				btnEnd.setEnabled(true);
				btnKeep.setEnabled(false);
				KeepUpdate();
			}
		});
		btnKeep.setFont(new Font("楷体_GB2312", Font.PLAIN, 14));
		btnKeep.setBounds(655, 381, 259, 47);
		btnKeep.setName("btnKeep");
		contentPane.add(btnKeep);
		
		btnEnd = new JButton("\u7ED3\u675F\u4E0D\u65AD\u6F14\u5316");
		btnEnd.setEnabled(false);
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnKeep.setEnabled(true);
				btnEnd.setEnabled(false);
				btnInit.setEnabled(true);
				btnClear.setEnabled(true);
				btnStep.setEnabled(true);
				Stop();
			}
		});
		btnEnd.setFont(new Font("楷体_GB2312", Font.PLAIN, 14));
		btnEnd.setBounds(655, 488, 259, 47);
		btnEnd.setName("btnEnd");
		contentPane.add(btnEnd);
		
		GenerationLabel = new JLabel("");
		GenerationLabel.setFont(new Font("楷体_GB2312", Font.PLAIN, 14));
		GenerationLabel.setBounds(99, 10, 198, 15);
		contentPane.add(GenerationLabel);
		
	}
	
	protected void Clear() {
		count=0;
		GenerationLabel.setText("");
		gus=mp.getGraphics();
		gus.clearRect(0, 0, row*25+50, col*25+25);
		/*for(int i=0;i<16;i++) {
    		for(int j=0;j<16;j++) {
    			gc.drawRect(i*25, j*25, 25, 25);
    		}
		}
		*/
		
	}

	protected void Stop() {
		
		timer.cancel();
	}

	//不间断演化
	protected void KeepUpdate() {
		timer=new Timer();
		timer.schedule(new TimerTask()
				{
			      public void run()
			      {
			    	  StepUpdate();
			      }
				}
		,0, 1*1000);
	}

	/*
	 * 单步演化
	 */
	public void StepUpdate() {
		int i=0,j=0;
		count++;
		System.out.println("Count:"+count);
		GenerationLabel.setText("这是手动解决冲突后第"+count+"次演化");
		int[][] cellstatus=new int[row][col];
		Cell[][] cell=map.getCell();
		
		for(i=0;i<row;i++) {
    		for( j=0;j<col;j++) {
    			cellstatus[i][j]=cell[i][j].getStatus();
    		}
		}
    	
		map.getNeighborAlive();
		map.update();
		gus=mp.getGraphics();
		
		/*gus.clearRect(0, 0, 400, 400);
		//frame.update(gus);
		for( i=0;i<row;i++) {
    		for( j=0;j<col;j++) {
    			if(cell[i][j].getStatus()==1) {
    				gus.fillRect(i*25, j*25, 25, 25);
    			}
    			else {
    				gus.drawRect(i*25, j*25, 25, 25);
    			}
    		}
		}*/
		
		for( i=0;i<row;i++) {
    		for( j=0;j<col;j++) {		
    			//判断状态是否改变* 不改变：就不用       * 改变： * 活-----》死，则清空格子。 死--》活，则填充格子
    			if(cellstatus[i][j]!=cell[i][j].getStatus())
    			{
    				if(cell[i][j].getStatus()==1)
    				{
    					gus.fillRect(j*25, i*25, 25, 25);
    				}
    				else
    				{
    					gus.clearRect(j*25, i*25, 25, 25);
    					gus.drawRect(j*25, i*25, 25, 25);
    				}
    			}
    		
    		}
		}
		
	}

	/*
	 * 随机初始化界面
	 */
	public void InitMap() {
		//格子
		count=0;
		map=new Map();
		Cell[][] cell=map.getCell();
		gus=mp.getGraphics();
		//frame.update(g);
		//frame.repaint(50, 25, 450, 425);
		gus.clearRect(0, 0, 400, 400);
		for(int i=0;i<row;i++) {
    		for(int j=0;j<col;j++) {
    			if(cell[i][j].getStatus()==1) 
    			{
    				gus.fillRect(j*25, i*25, 25, 25);
    			}
    			else 
    			{
    				gus.drawRect(j*25, i*25, 25, 25);
    			}
    		}
		}
		GenerationLabel.setText("");
		//System.out.println("InitMap finished!");
	}
	
	
	class myPanel extends JPanel
	{
		
	}
	
}