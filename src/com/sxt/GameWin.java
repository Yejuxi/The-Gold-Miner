package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

//创建窗口，监听键盘鼠标事件的功能
public class GameWin extends JFrame {

    //0:未开始 1：运行中 2：商店 3：失败 4：胜利
    static int state;
    //存储金块石块
    List<Object> objectList = new ArrayList<>();
    Bg bg = new Bg();
    Line line = new Line(this);

    {
        boolean isPlace = true;
        for (int i = 0; i < 11; i++) {
            double random =  Math.random();
            Gold gold;
            if(random<0.3){
                gold = (new GoldMini());
            }
            else if (random < 0.7) {
                gold = (new Gold());
            }
            else{
                gold = (new GoldPlus());
            }

            for (Object obj: objectList){
                if (gold.getRect().intersects(obj.getRect())){
                    //不可放置，需要重新成生成。
                    isPlace = false;
                }
            }
            if (isPlace){
                objectList.add(gold);
            }
            else{//释放状态，不添加。
                isPlace = true;
                i--;
            }
        }
        for (int i = 0; i < 3; i++) {
            Rock rock = new Rock();
            for (Object obj: objectList){
                if (rock.getRect().intersects(obj.getRect())){
                    isPlace = false;
                }
            }
            if (isPlace){
                objectList.add(rock);
            }
            else{
                isPlace = true;
                i--;
            }
        }
    }

    Image offScreenImage;

    //初始化窗口信息
    void launch(){
        //设置窗口是否可见
        this.setVisible(true);
        //设置窗口大小
        this.setSize(768,1000);
        //设置窗口位置
        this.setLocationRelativeTo(null);//窗口居中
        //设置窗口标题
        this.setTitle("夜居溪黄金矿工");
        //关闭窗口
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                switch (state){
                    case 0:
                        if (e.getButton() == 3){
                            state = 1;
                            bg.startTime = System.currentTimeMillis();
                        }
                        break;
                    case 1:
                        //左右摇摆，点击左键
                        if(e.getButton() == 1 && line.state == 0){
                            line.state = 1;
                        }
                        //抓取返回，点击右键
                        if (e.getButton() == 3 && line.state == 3 &&Bg.waterNum > 0){
                            Bg.waterFlag = true;
                            Bg.waterNum--;
                        }
                        break;
                    case 2:
                        if (e.getButton() == 1){
                            bg.shop = true;
                        }
                        if (e.getButton() == 3){
                            state = 1;
                            bg.startTime = System.currentTimeMillis();
                        }
                        break;
                    case 3:
                    case 4:
                        if (e.getButton() == 1){
                            state = 0;
                            bg.reGame();
                            line. reGame();
                        }
                        break;
                    default:
                }
            }
        });

        while(true){
            repaint();
           nextLevel();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //下一关
    public void nextLevel(){
        if (bg.gameTime() && state == 1){
            if (Bg.count >= bg.goal){
                if (Bg.level == 5){
                    state = 4;
                }else {
                    state = 2;
                    Bg.level++;
                }
            }else {state = 3;}
            dispose();
            GameWin gameWin = new GameWin();
            gameWin.launch();
        }
    }

    @Override
    public void paint(Graphics g) {
        offScreenImage = this.createImage(768,1000);
        Graphics gImage = offScreenImage.getGraphics();

        bg.paintSelf(gImage);
        if (state == 1){
            //绘制物体
            for(Object obj:objectList){
                obj.paintSelf(gImage);
            }
            line.paintSelf(gImage);
        }
        g.drawImage(offScreenImage,0,0,null);

    }



    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }

}
