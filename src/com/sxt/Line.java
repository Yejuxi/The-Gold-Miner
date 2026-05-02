package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Line {
    //起点坐标
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
                int m = 1;
                if (length > MIN_len){
                    length -= 5;
                    lines(g);
                    for (Object obj:this.frame.objectList){
                        if (obj.flag){
                            m = obj.m;
                            obj.x = endx - obj.getWidth()/2;
                            obj.y = endy;
                            if (length <= MIN_len){
                                obj.x = -150;
                                obj.y = -150;
                                obj.flag = false;
                                Bg.waterFlag = false;
                                Bg.count += obj.count;//加分
                                state = 0;
                            }
                            if (Bg.waterFlag){
                                if (obj.type == 1) {
                                    m = 1;
                                }
                                if (obj.type == 2) {
                                    obj.x = -150;
                                    obj.y = -150;
                                    obj.flag = false;
                                    Bg.waterFlag = false;
                                    state = 2;
                                }
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(m);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
        }
        //重置线
        void reGame(){
            n = 0;
            length = 100;
        }
    }

