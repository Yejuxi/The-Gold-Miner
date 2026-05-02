package com.sxt;

import java.awt.*;

public class Object {
    //定义金块石块的公共属性，比如坐标，宽高,图片
    int x;
    int y;
    int width;
    int height;
    Image img;
    //标记是否能动
    boolean flag;
    //设置质量
    int m;
    //积分
    int count;
    //类型，1为金块，2为石块
    int type;

    void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
    }

    public int getWidth(){
        return width;
    }

    //获取矩形
    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }
}
