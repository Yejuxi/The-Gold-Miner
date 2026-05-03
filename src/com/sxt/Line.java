package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Line {
    //起点坐标
    javax.swing.Timer timer;
    int x = 380;
    int y = 180;
    //终点坐标
    int endx = 500;
    int endy = 500;
    //设置线段长度
    double length = 100;
    //线长最小值
    double MIN_len = 100;
    //线长最大值
    double MAX_len = 750;
    double n = 0;
    //方向
    double dir = 1;
    //状态为 0 则左右摇摆，为 1 则进行抓取，为 2 则进行收回
    int state;
    //添加钩爪图片
//    Image hook = Toolkit.getDefaultToolkit().getImage("imgs/hook.png");
    URL bgUrl = getClass().getClassLoader().getResource("imgs/hook.png");
    Image hook = bgUrl != null ? Toolkit.getDefaultToolkit().getImage(bgUrl) : null;

    GameWin frame;

    Line(GameWin frame) {this.frame = frame;}

    //碰撞检测，物体是否被抓取
    void logic(){
        for (Object obj:this.frame.objectList){
            if(endx > obj.x && endx < obj.x + obj.width
                    && endy > obj.y && endy < obj.y + obj.height){
                state = 3;
                obj.flag = true;
            }
        }
    }

    //绘制方法
    void lines(Graphics g){

        endx = (int) (x + length*Math.cos(n * Math.PI));
        endy = (int) (y + length*Math.sin(n * Math.PI));
        g.setColor(Color.red);
        g.drawLine(x-1,y-1,endx-1,endy-1);
        g.drawLine(x,y,endx,endy);
        g.drawLine(x+1,y+1,endx+1,endy+1);
        g.drawImage(hook,endx-36,endy-2,null);
    }


    void paintSelf(Graphics g) {
        logic();
        switch (state) {
            case 0:
                if(n < 0.1){
                    dir = 1;
                } else if (n > 0.9) {
                    dir = -1;
                }
                n = n + 0.005 * dir;
               lines(g);
                break;

            case 1:
                if (length < MAX_len){
                    length += 10;
                    lines(g);
                }else {
                    state = 2;
                }
                break;

            case 2:
                if (length > MIN_len){
                    length -= 10;
                    lines(g);
                }else{
                    state = 0;
                }
                    break;

            case 3:
//                try {
//                    Thread.sleep(m);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                if (timer == null || !timer.isRunning()) {
                    timer = new Timer(50,new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int step = 5;
                            for (Object obj:frame.objectList){
                                if (obj.flag){
                                    if (obj instanceof GoldMini){
                                        step = 8;
                                    } else if (obj instanceof Gold) {
                                        step = 6;
                                    } else if (obj instanceof GoldPlus) {
                                        step = 3;
                                    } else if (obj instanceof Rock) {
                                        step = 4;
                                    }
                                    break;
                                }
                            }
                            if (Bg.waterFlag){
                                for (Object obj:frame.objectList){
                                    if (obj.flag && obj.type == 1){
                                        step = step * 2;
                                        break;
                                    }
                                }
                            }

                            //每次定时器触发，执行一次缩短
                            if (length > MIN_len){
                                length -= step;
                                int currentEndx = (int) (x + length*Math.cos(n * Math.PI));
                                int currentEndy = (int) (y + length*Math.sin(n * Math.PI));
//                                frame.repaint();//触发重绘，显示新位置
                                for (Object obj:frame.objectList){
                                    if (obj.flag){
                                        obj.x = currentEndx - obj.getWidth() / 2;
                                        obj.y = currentEndy;

                                        if (length <= MIN_len){
                                            obj.x = -150;
                                            obj.y = -150;
                                            obj.flag = false;
                                            Bg.waterFlag = false;
                                            Bg.count += obj.count;//加分
                                            state = 0;
                                            timer.stop();
                                        }
                                        if (Bg.waterFlag && obj.type == 2){
                                                obj.x = -150;
                                                obj.y = -150;
                                                obj.flag = false;
                                                Bg.waterFlag = false;
                                                state = 2;
                                                timer.stop();
                                        }
                                        break;
                                    }
                                }
                                frame.repaint();
                            }else {
                                timer.stop();
                                state = 0;
                            }
                        }
                    });
                     timer.start();
                }
                lines(g);
                break;
        }
        }
        //重置线
        void reGame(){
            n = 0;
            length = 100;
            state = 0;
            if (timer != null && timer.isRunning()) {
                timer.stop();
                timer = null;
            }
        }
    }

