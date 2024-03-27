/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JLabelDungChung;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lethi
 */
public class JLabelTime extends JLabel{

    private Timer timer;

    public JLabelTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        setText(format.format(new Date()));

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setText(format.format(new Date()));
            }
        });
        timer.start();
    }
    
}
