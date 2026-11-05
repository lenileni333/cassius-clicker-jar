package utils;

import javax.swing.*;
import java.net.URL;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.*;


public class Assets {
    /* PANELS */
    public static JPanel mainPanel;
    public static JPanel shopPanel;

    /* JBUTTONS */
    public static JButton casButton;
    public static JButton auShopButton;
    public static JButton nzShopButton;

    /* JLABELS */
    public static JLabel audCount;
    public static JLabel auShopTitle;
    
    public static JLabel nzdCount;
    public static JLabel nzShopTitle;
    
    /* IMAGES */
    private static ImageIcon casIcon;

    /* AUDIO */
    // public static void playSFX(String path) {
    //     try {
    //         AudioInputStream str = AudioSystem.getAudioInputStream(
    //             new BufferedInputStream(Assets.class.getResourceAsStream(path))
    //         );
    //         Clip cl = AudioSystem.getClip();
    //         cl.open(str); cl.start();
    //         cl.addLineListener(e -> {
    //             if (e.getType() == LineEvent.Type.STOP) cl.close();
    //         });
    //     } catch (Exception e) {
    //         System.err.println("\u001B[31mError: " + e.getMessage() + "\u001B[");
    //     }
    // }

    public static void playSFX(String path) {
        new Thread(() -> {
            try (InputStream rstr = Assets.class.getResourceAsStream(path)) {
                if (rstr == null) {
                    System.err.println("\u001B[31mError: File does not exist -> " + path + "\u001B[0m");
                    return;
                }

                byte[] bytes = rstr.readAllBytes();

                ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
                BufferedInputStream bis = new BufferedInputStream(bai);
                AudioInputStream str = AudioSystem.getAudioInputStream(bis);
                Clip cl = AudioSystem.getClip();
                cl.open(str);
                cl.start();

                cl.addLineListener(e -> {
                    if (e.getType() == LineEvent.Type.STOP) {
                        cl.close();
                        try {
                            str.close();
                            bai.close();
                            bis.close();
                        } catch (Exception e1) {
                            System.err.println("\u001B[1;31mError: " + e1.getMessage() + ", " + e1.getStackTrace() + ", " + e1.getCause() + "\u001B[0m");
                        }
                    }
                });
            } catch (Exception e) {
                System.err.println("\u001B[1;31mError: " + e.getMessage() + ", " + e.getStackTrace() + ", " + e.getCause() + "\u001B[0m");
            }
        }).start();
    }

    public static void playMusic(String path) {
        try {
            URL mp = Assets.class.getResource(path);

            if (mp != null) {
                AudioInputStream str = AudioSystem.getAudioInputStream(mp);
                Clip cl = AudioSystem.getClip(); cl.open(str);

                cl.loop(-1); // -1ms loops forever
                cl.start();
            } else {
                System.err.println("Couldn't find audio for music");
            }
            
        } catch (Exception e) {
            System.err.println("\u001B[1;31mError: " + e.getMessage() + "\u001B[0m");
        }
    }
    /* CONSTRUCTOR */
    static {
            mainPanel = new JPanel();
            mainPanel.setLayout(null);
            mainPanel.setBackground(Color.decode(Colours.SUPER_WHITE));
            mainPanel.setFocusable(false);

            shopPanel = new JPanel();
            shopPanel.setLayout(null);
            shopPanel.setBackground(Color.decode(Colours.SUPER_WHITE));
            shopPanel.setFocusable(false);
        

        URL casImgURL = Assets.class.getResource("/assets/images/cas.png"); // "/assets/images/placeholder.jpeg"

        if (casImgURL != null) {
            casIcon = new ImageIcon(casImgURL);
        } else {
            System.err.println("IMAGE URL: /assets/images/placeholder.jpeg");
            casIcon = new ImageIcon("/assets/images/placeholder.jpeg");
        }

        casButton = new JButton(casIcon);
        casButton.setFocusable(false);
        casButton.setContentAreaFilled(false);
        casButton.setBorderPainted(false);
        casButton.setFocusPainted(false);

        audCount = new JLabel("AUD: 0");
        audCount.setBounds(10, 10, 300, 50);
        audCount.setFont(new Font("Arial", Font.PLAIN, 48));

        nzdCount = new JLabel("NZD: 0");
        nzdCount.setBounds(10, 65, 300, 50);
        nzdCount.setFont(new Font("Arial", Font.PLAIN, 48));

        auShopButton = new JButton("AUD");
        auShopButton.setBounds(0, 0, 300, 120);
        auShopButton.setFocusable(false);
        auShopButton.setBackground(Color.decode(Colours.AUD_COLOUR));
        auShopButton.setOpaque(true);
        auShopButton.setContentAreaFilled(true);
        auShopButton.setBorderPainted(false);
        auShopButton.setForeground(Color.decode(Colours.SUPER_WHITE));
        auShopButton.setFont(new Font("Arial", Font.PLAIN, 72));

        nzShopButton = new JButton("NZD");
        nzShopButton.setBounds(0, 0, 300, 120);
        nzShopButton.setFocusable(false);
        nzShopButton.setBackground(Color.decode(Colours.NZD_COLOUR));
        nzShopButton.setOpaque(true);
        nzShopButton.setContentAreaFilled(true);
        nzShopButton.setBorderPainted(false);
        nzShopButton.setForeground(Color.decode(Colours.SUPER_WHITE));
        nzShopButton.setFont(new Font("Arial", Font.PLAIN, 72));

        auShopTitle = new JLabel("AU Shop");
        auShopTitle.setBounds(10, 10, 300, 50);
        auShopTitle.setFont(new Font("Arial", Font.PLAIN, 48));
        auShopTitle.setForeground(Color.decode(Colours.SUPER_WHITE));

        nzShopTitle = new JLabel("NZ Shop");
        nzShopTitle.setBounds(10, 10, 300, 50);
        nzShopTitle.setFont(new Font("Arial", 0, 48));
        nzShopTitle.setForeground(Color.decode(Colours.SUPER_WHITE));
    }
}