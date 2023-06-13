package com.jordy.opensource.soulairconditione;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jordy.opensource.soulairconditione.utils.Player;

public class SoulAirConditionePanel extends JPanel {
    /**
     * 是否拖动
     */
	private boolean isDrag = false;
	/**
	 * 是否打开
	 */
	private boolean open = false;
	
	private JFrame rootFame;
	
	private Player player;
	
	/**
	 * 当前温度
	 */
	private int temperature = 26;
	
	/**
	 * 模式
	 */
	private int model = 0;
	
	private Font mFont;
	
	private Image snowImage,sunImage,logoImage;
	
    public SoulAirConditionePanel(JFrame frame){
    	this.rootFame = frame;
    	final java.awt.Point origin = new java.awt.Point();
    	addMouseListener(new MouseAdapter() {
    	    // 按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
    	    public void mousePressed(MouseEvent e) {
    	        // 当鼠标按下的时候获得窗口当前的位置
    	        origin.x = e.getX();
    	        origin.y = e.getY();
    	        isDrag = true;
    	    }
    	    @Override
    	    public void mouseReleased(MouseEvent e) {
    	    	isDrag = false;
    	    }
    	});
    	addMouseMotionListener(new MouseMotionAdapter() {
    	    // 拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
    	    public void mouseDragged(MouseEvent e) {
    	        if (!isDrag)
    	            return;
    	        // 当鼠标拖动时获取窗口当前位置
    	        java.awt.Point p = rootFame.getLocation();
    	        // 设置窗口的位置
    	        // 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
    	        rootFame.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
    	    }
    	});
    	BufferedImage image = null;
		try {
			image = ImageIO.read(new File("snow.png"));
			snowImage = image.getScaledInstance(image.getWidth(), image.getHeight(), 100);
			image = ImageIO.read(new File("sun.png"));
			sunImage = image.getScaledInstance(image.getWidth(), image.getHeight(), 100);
			image = ImageIO.read(new File("java.png"));
			logoImage = image.getScaledInstance(image.getWidth(), image.getHeight(), 100);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		player = new Player();
		
    }
    
    private float airConditionerScale = 0.80f;
    /**
     * 出风口比例
     */
    private float airOutletScale = 0.10f;
    
    @Override
    protected void paintComponent(Graphics g) {
    	//设置成白色
    	g.setColor(Color.WHITE);
		g.fillRoundRect(1,1,getWidth() - 1,(int)(getHeight() * airConditionerScale),20,20);
		g.setColor(new Color(244, 244, 244));
		g.drawRoundRect(1,1,getWidth() - 1,(int)(getHeight() * airConditionerScale),20,20);
		g.drawLine(1, (int)(getHeight() * airConditionerScale - (getHeight() * airConditionerScale * airOutletScale))
				, getWidth() - 1, (int)(getHeight() * airConditionerScale - (getHeight() * airConditionerScale * airOutletScale)));
		//开始绘制中间的图标
		int x = (int)(getHeight() * airConditionerScale);
		//-8 是因为图片是16 
		g.drawImage(logoImage,getWidth() / 2 - 8, (int)(getHeight() * airConditionerScale - (getHeight() * airConditionerScale * airOutletScale))-8,null,null);
		//绘制节能 
		//指的是所有宽度分为15等份
		int widthScale = 15;
		//设置高度分为10等份
		int heightScale = 10;
		g.setColor(new Color(78, 165, 245));
		//这里指的是这个面板需要分为6等份
		int panScale = getWidth() / 6;
		g.fillRoundRect(getWidth() / widthScale, getHeight() / widthScale, panScale, (int)(x - x / 2), 10, 10);
		g.setColor(Color.WHITE);
		//第一份的面板再分6份
		int step = panScale / 6;
		panScale = panScale / heightScale;
		//开始的位置
		int startX = getWidth() / widthScale + panScale / 12;
		for (int i = 0 ; i < 6;i++) {
			g.fillRoundRect(startX + (i * step), getHeight() / widthScale + panScale / 2, panScale, (int)((x - x / 2) / heightScale * 1), 10, 10);
		}
		g.setColor(Color.WHITE);
		//绘制节能的
		g.fillRect(startX + (0 * step), getHeight() / widthScale + panScale / 2 + (int)((x - x / 2) / heightScale * 1.5), getWidth() / 6, (int)((x - x / 2) / heightScale * 6));
		g.setColor(Color.black);
		Color[] colors = new Color[] {
				null,Color.GREEN,Color.blue,Color.orange,Color.yellow,Color.RED
		};
		//绘制能耗图
		for (int i = 1 ; i < 6;i++) {
			g.setColor(colors[i]);
			g.fillRect(startX + (int)(step), getHeight() / widthScale + panScale / 2 + (int)((x - x / 2) / heightScale * 1) * i + 5, 
					panScale * i + (i * panScale / 2), (int)((x - x / 2) / heightScale / 2));
			if (i == 1) {
				g.fillRect(startX + (int)(4 * step), getHeight() / widthScale + panScale / 2 + (int)((x - x / 2) / heightScale * 1) * i + 5, 
						panScale * i + (i * panScale / 2), (int)((x - x / 2) / heightScale / 2));
			}
		}
		g.setColor(Color.WHITE);
		//绘制节能信息的的
		g.fillRect(startX + (0 * step), getHeight() / widthScale + panScale / 2 + (int)((x - x / 2) / heightScale * 1.5) +  (int)((x - x / 2) / heightScale * 6) +  (int)((x - x / 2) / heightScale * 0.5), getWidth() / 6, (int)((x - x / 2) / heightScale * 2));
		//绘制雪花或者太阳
		g.setColor(Color.black);
		if (open) {
			Font font = null;
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("digital-7-mono.ttf"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			font.deriveFont(20);
			g.setColor(Color.CYAN);
			g.setFont(new Font(font.getName(),Font.BOLD,20));
			g.setColor(new Color(147,197,253));
//			g.drawString(model == 0 ? "❄️" : "🌞", getWidth() - getWidth() / 5, (int)(getHeight() * airConditionerScale - (getHeight() * airConditionerScale * airOutletScale)) / 3);
			if (model == 0) {
				g.drawImage(snowImage,getWidth() - getWidth() / 5, (int)(getHeight() * airConditionerScale - (getHeight() * airConditionerScale * airOutletScale)) / 4,null,null);
			} else {
				g.drawImage(sunImage,getWidth() - getWidth() / 5, (int)(getHeight() * airConditionerScale - (getHeight() * airConditionerScale * airOutletScale)) / 4,null,null);
			}
			g.setColor(new Color(204, 204, 204));
			g.drawString(temperature + "°C", getWidth() - getWidth() / 5, (int)(getHeight() * airConditionerScale - (getHeight() * airConditionerScale * airOutletScale)) / 3 + 20);
			//绘制出风口
			g.drawLine(getWidth() / 3, (int)(getHeight() * airConditionerScale) + 5, getWidth() / 3 - getWidth() / 3/2, getHeight());
			
			g.drawLine(getWidth() / 2, (int)(getHeight() * airConditionerScale) + 5, getWidth() / 2, getHeight());
			
			g.drawLine(getWidth() / 3 * 2, (int)(getHeight() * airConditionerScale) + 5, getWidth() / 3 * 2 + getWidth() / 3/2, getHeight());
		}
    }
    
    /**
     * 打开空调
     */
    public void open() {
    	player.initMusic("di.wav");
    	open = true;
    	updateUI();
    	player.play();
    }
    /**
     * 关闭空调
     */
    public void close() {
    	player.initMusic("di.wav");
    	open = false;
    	updateUI();
    	player.play();
    }
    
    /**
     * 设置显示的模式
     * @param model
     */
    public void setModel(int model) {
    	if (!open || (model != 0 && model != 1)) {
    		return;
    	}
    	this.model = model;
    	updateUI();
    }
    /**
     * 增加温度
     */
    public void setTemperatureIncrease () {
    	if (!open) {
			return;
		}
    	if (temperature <= 16) {
    		showErrorMsg("温度已经到达最低了，再低能耗就上去了..");
    		return;
    	}
    	temperature --;
    	updateUI();
    }
    
    /**
     * 减小温度
     */
	public void setTemperatureReduce () {
		if (!open) {
			return;
		}
		if (temperature >= 32) {
    		showErrorMsg("温度已经到达最高了，再高能耗就上去了..");
    		return;
    	}
    	temperature ++;
    	updateUI();
	}
	
	/**
	 * 显示错误消息
	 * @param msg
	 */
	private void showErrorMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "错误:", JOptionPane.ERROR_MESSAGE);
	}
}