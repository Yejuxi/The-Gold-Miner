package com.sxt;

import java.awt.*;
import java.net.URL;

public class Gold extends Object{

    Gold(){
        this.x = (int)(Math.random()*700);
        this.y = (int)(Math.random()*550+300);
        this.width = 52;
        this.height = 52;
        this.flag = false;
        this.m = 30;
        this.count=4;
        this.type=1;
        URL url = getClass().getClassLoader().getResource("imgs/angle.png");
        this.img = url != null ? Toolkit.getDefaultToolkit().getImage(url) : null;
    }
}

class GoldMini extends Gold{
    GoldMini(){
        this.width = 36;
        this.height = 36;
        this.m = 15;
        this.count = 2;
        URL url = getClass().getClassLoader().getResource("imgs/anna.png");
        this.img = url != null ? Toolkit.getDefaultToolkit().getImage(url) : null;
    }
}

class GoldPlus extends Gold{
    GoldPlus(){
        this.x = (int)(Math.random()*650);
        this.width = 105;
        this.height = 150;
        this.m = 60;
        this.count = 8;
        URL url = getClass().getClassLoader().getResource("imgs/juno.png");
        this.img = url != null ? Toolkit.getDefaultToolkit().getImage(url) : null;
    }
}
