package com.sxt;

import java.awt.*;
import java.net.URL;

public class Bg {
    static int level = 1;//关卡数
    public int getGoal(){
        return level * 15;
    }
    static int count = 0;//总分
    static int waterNum = 3;//药水数量
    static boolean waterFlag = false;//药水状态，默认F，T表示正在使用
    long startTime;//开始时间
    long endTime;//结束时间
    int price = (int) (Math.random() * 10);//药水价格
    boolean shop = false;//是否进入商店 f 不购买
    //载入图片
//    Image bg = Toolkit.getDefaultToolkit().getImage("imgs/bg.jpg");
//    Image bg1 = Toolkit.getDefaultToolkit().getImage("imgs/bg1.jpg");
//    Image peo = Toolkit.getDefaultToolkit().getImage("imgs/peo.png");
//    Image water = Toolkit.getDefaultToolkit().getImage("imgs/water.png");
    URL bgUrl = getClass().getClassLoader().getResource("imgs/bg.jpg");
    Image bg = bgUrl != null ? Toolkit.getDefaultToolkit().getImage(bgUrl) : null;

    URL bg1Url = getClass().getClassLoader().getResource("imgs/bg1.jpg");
    Image bg1 = bg1Url != null ? Toolkit.getDefaultToolkit().getImage(bg1Url) : null;

    URL peoUrl = getClass().getClassLoader().getResource("imgs/luba.png");
    Image peo = peoUrl != null ? Toolkit.getDefaultToolkit().getImage(peoUrl) : null;

    URL waterUrl = getClass().getClassLoader().getResource("imgs/boom.png");
    Image water = waterUrl != null ? Toolkit.getDefaultToolkit().getImage(waterUrl) : null;

    //绘制
    void paintSelf(Graphics g) {
        g.drawImage(bg1,0,0,null);
        g.drawImage(bg,0,200,null);
        switch (GameWin.state){
            case 0:
                drawWord(g,80,Color.green,"点击右键开始",200,400);
                break;
            case 1:
                g.drawImage(peo,230,50,null);
                drawWord(g,30,Color.black,"积分:"+count,30,150);

                //药水组件
                g.drawImage(water,450,40,null);
                drawWord(g,30,Color.black,"*"+waterNum,510,70);
                //关卡数
                drawWord(g,20,Color.black,"第"+level+"关",30,60);
                //目标积分
                drawWord(g,30,Color.black,"目标："+getGoal(),30,110);
                //时间组件
                endTime = System.currentTimeMillis();
                long tim = 20 - (endTime - startTime)/1000;
                drawWord(g,30,Color.black,"时间"+(tim>0?tim:0),520,150);
                break;
            case 2:
                g.drawImage(water,300,400,null);
                drawWord(g,30,Color.black,"价格"+price,300,500);
                drawWord(g,30,Color.black,"是否购买？(左键购买，右键跳过)",200,550);
//                if (shop){
//                    count = count - price;
//                    waterNum++;
//                    shop =  false;
//                    GameWin.state = 1;
//                    startTime = System.currentTimeMillis();
//                }
                break;
            case 3:
                drawWord(g,80,Color.cyan,"失败",250,350);
                drawWord(g,80,Color.cyan,"积分:"+count,200,450);
                break;
            case 4:
                drawWord(g,80,Color.red,"成功",250,350);
                drawWord(g,80,Color.red,"积分:"+count,200,450);
                break;
            default:
        }
    }

    //t倒计时完成，f正在倒计时
    boolean gameTime(){
        long tim = (endTime - startTime)/1000;
        if (tim>20){
            return true;
        }
        return false;
    }

    //重置元素
    void reGame(){
        level = 1;//关卡数
        count = 0;//总分
        waterNum = 3;//药水数量
        waterFlag = false;
    }

    //绘制字符串
    public static void drawWord(Graphics g,int size,Color color,String str,int x,int y) {
        g.setColor(color);
        g.setFont(new Font("仿宋",Font.BOLD,size));
        g.drawString(str, x, y);
    }

}
