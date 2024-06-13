package gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class Test extends JFrame {

    public static void main(String[] args) {
        new Test();
    }

    public Test() {

        add(mainScrollPane);
        setTitle("Test");
        setSize(1800, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}