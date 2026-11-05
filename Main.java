/* IMPORTS */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import utils.*;
import utils.data.*;
import utils.classesandenums.*; // CUSTOM: Holds classes and enums

/* CODE */
public class Main {

    public static long aud = 0;
    public static long audx = 1;

    public static long nzd = 0;
    public static long nzdx = 1;

    private static int auyd = 0;

    public static long a=0;

    public static boolean nzUnlocked = false;

    private static Classes.Notification notif = new Classes.Notification("TEST");

    private static Enums.ShopStatus shopStat = Enums.ShopStatus.NONE;

    public static JFrame mainFrame;
    public static JFrame shopFrame;
    public static JFrame terminalFrame;

    private static void updateCounter(JLabel counter, String currency, long variable) /*throws Exception*/ {
        if (counter != null) counter.setText(currency + ": " + variable);

        counter.revalidate();
        if (counter.getParent() != null) {
            counter.getParent().repaint();
        }
    }

    private static void Notify(String txt) {
        notif.txt.setText(txt);
        notif.setVisible(true);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.err.println("\u001B[1;31mERROR: INTERUPT -> FUNC[PRIVATE STATIC VOID NOTIFY()] -> THREAD.SLEEP(2000);\u001B[0m");
                    Thread.currentThread().interrupt();
                }
                return null;
            }

            @Override 
            protected void done() {
                notif.setVisible(false);
            }
        }.execute();
    }

    private static JFrame makeFrame(String Title, int width, int height, int onClose, boolean visible, Color clr) {
        JFrame f = new JFrame(Title);
            f.setSize(width, height);
            f.setDefaultCloseOperation(onClose);
            f.setLocationRelativeTo(null);
            f.setResizable(false);
            f.setFocusable(true);
            f.getRootPane().setBackground(clr);
            f.setVisible(visible);

            return f;
    }

    private static void removeAllEls(Container panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    private static void changeShopDisplay() {
            switch (shopStat) {
                case Enums.ShopStatus.NONE:
                    removeAllEls(Assets.shopPanel);
                    Assets.shopPanel.setBackground(Color.decode("0XFFFFFF"));

                    Assets.casButton.setBounds(
                        (Macro.MainWidth - Assets.casButton.getIcon().getIconWidth()) / 2, 
                        (Macro.MainHeight - Assets.casButton.getIcon().getIconHeight()) / 2, 
                        Assets.casButton.getIcon().getIconWidth(), 
                        Assets.casButton.getIcon().getIconHeight()
                    );

                    Assets.auShopButton.setBounds(
                        (Macro.ShopWidth - Assets.auShopButton.getWidth()) / 2,
                        ((Macro.ShopHeight - Assets.auShopButton.getHeight()) / 2)-auyd,
                        Assets.auShopButton.getWidth(),
                        Assets.auShopButton.getHeight()
                    );

                    if (nzUnlocked) Assets.nzShopButton.setBounds(
                        (Macro.ShopWidth - Assets.nzShopButton.getWidth()) / 2,
                        ((Macro.ShopHeight - Assets.nzShopButton.getHeight()) / 2)+50,
                        Assets.nzShopButton.getWidth(),
                        Assets.nzShopButton.getHeight()
                    );

                    Assets.shopPanel.add(Assets.auShopButton);
                    if (nzUnlocked) Assets.shopPanel.add(Assets.nzShopButton);
                    break;
                case Enums.ShopStatus.AUD: {
                        removeAllEls(Assets.shopPanel);

                        Assets.shopPanel.setBackground(Color.decode(Colours.AUD_COLOUR));
                        Assets.shopPanel.add(Assets.auShopTitle);
                        int cauux = 60; int cauuy = 60; int count = 0;
                        for (int i = 0; i < Data.auUpgradeDisplays.length; ++i) {   
                            final int in = i;              
                            if (count == 3) {
                                cauuy += 150;
                                cauux = 60;
                            }

                            Data.auUpgradeDisplays[in] = new Classes.UpgradeDisplay(cauux, cauuy, Data.upgrs[in]); ++count;

                            if (!Data.upgrs[in].bought) {
                                Data.auUpgradeDisplays[in].buyBtn.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e0) {
                                        if (aud >= Data.upgrs[in].Cost) {
                                            aud -= Data.upgrs[in].Cost;
                                            Data.upgrs[in].bought = true;
                                            System.out.println("CLICKt"); 
                                            switch (Data.upgrs[in].id) { // Adds the effect
                                                case 0:
                                                    Assets.playMusic("/assets/audio/aus.wav");
                                                    break;
                                                case 1:
                                                    new Timer(1000, e1 -> {
                                                        aud += audx;
                                                        updateCounter(Assets.audCount, "AUD", aud);
                                                    }).start();
                                                    break;
                                                case 4:
                                                    System.out.println("NZ");
                                                    nzUnlocked = true;
                                                    nzdx = 150;
                                                    new Timer(1000, e1 -> {
                                                        System.out.println("A");
                                                        nzd += nzdx;
                                                        Assets.nzdCount.setText("NZD: " + nzd);
                                                    }).start();
                                                    auyd = 90;
                                                    Notify("<html>Check the shop menu, there's a new currency!</html>");
                                                    break;
                                                default:
                                                    break;
                                            }
                                        } else {
                                            System.out.println("CANT AFFORD");
                                        }

                                        if (Data.upgrs[in].bought) {
                                            Data.auUpgradeDisplays[in].buyBtn.setText("Bought");
                                            audx *= Data.upgrs[in].mulx;
                                            Data.auUpgradeDisplays[in].buyBtn.setBackground(null);
                                            Assets.audCount.setText("AUD: " + aud);
                                            Assets.playSFX("/assets/audio/baka.wav");
                                            Data.auUpgradeDisplays[in].buyBtn.removeActionListener(this);
                                        }
                                    }
                                });
                            }

                            Assets.shopPanel.add(Data.auUpgradeDisplays[i]);
                            cauux += 150;
                        }
                    break;
                    }
                case Enums.ShopStatus.NZD: {
                        removeAllEls(Assets.shopPanel);
                        Assets.shopPanel.setBackground(Color.decode(Colours.NZD_COLOUR));
                        Assets.shopPanel.add(Assets.nzShopTitle);

                        int cnzux = 60, cnzuy = 60, count0 = 0;
                        for (int i = 0; i < Data.nzUpgradeDisplays.length; ++i) {
                            final int in = i+6;
                            final int in2 = i;

                            if (count0 == 3) {
                                cnzux = 60;
                                cnzuy += 150;
                            }

                            if (in >= Data.upgrs.length) break;

                            Data.nzUpgradeDisplays[in2] = new Classes.UpgradeDisplay(cnzux, cnzuy, Data.upgrs[in]); ++count0;
                            if (!Data.upgrs[in].bought) {
                                Data.nzUpgradeDisplays[in2].buyBtn.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e1) {
                                        if (nzd >= Data.upgrs[in].Cost) {
                                            System.out.println("DO");
                                            nzd -= Data.upgrs[in].Cost;
                                            Data.upgrs[in].bought = true;
                                            switch (Data.upgrs[in].id) {
                                                case 10:
                                                    AbstractAction termOpen = new AbstractAction() {
                                                        @Override 
                                                        public void actionPerformed(ActionEvent e) {
                                                            terminalFrame.setVisible(!terminalFrame.isVisible());
                                                            Assets.playSFX("/assets/audio/btn121.wav");
                                                        }
                                                    };

                                                    if (mainFrame != null && terminalFrame != null) {
                                                        mainFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0), "toggleTerminal");
                                                        mainFrame.getRootPane().getActionMap().put("toggleTerminal", termOpen);
                                                    }

                                                    Notify("<html></html>");
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }

                                        if (Data.upgrs[in].bought) {
                                            Data.nzUpgradeDisplays[in2].buyBtn.setText("Bought");
                                            nzdx *= Data.upgrs[in].mulx;
                                            Data.nzUpgradeDisplays[in2].buyBtn.setBackground(null);
                                            Assets.nzdCount.setText("NZD: " + nzd);
                                            Assets.playSFX("/assets/audio/baka.wav");
                                            Data.nzUpgradeDisplays[in2].buyBtn.removeActionListener(this);
                                        }
                                    }
                                });
                            }

                            Assets.shopPanel.add(Data.nzUpgradeDisplays[in2]);
                            cnzux += 150;
                        }
                        break;
                    }
                default:
                    System.out.println("Nothing");
                    break;
            }
        }

    public static void main(String[] args) {
        // final SplashScreen splash = SplashScreen.getSplashScreen(); // making a custom splash screen
        // if (splash != null) {
        //     Graphics2D g = splash.createGraphics();
        //     if (g != null) {
        //         g.setColor(Color.BLACK);
        //         g.drawString("Loading JVM and other stuff", 720 / 2, 600 / 2);
        //         splash.update();
        //     }
        // } else {
        //     System.err.println("Splash screen not found"); 
        // }
        System.out.println("\u001B[1;32mGameLoaded\u001B[0m");
        System.out.println(Data.upgrs.length);
        SwingUtilities.invokeLater(() -> {
            mainFrame = makeFrame("Cassius Clicker Java Edition", Macro.MainWidth, Macro.MainHeight, JFrame.EXIT_ON_CLOSE, true, Color.decode(Colours.SUPER_WHITE));
            shopFrame = makeFrame("Shop Selector", Macro.ShopWidth, Macro.ShopHeight, JFrame.HIDE_ON_CLOSE /* handled by window listener */, false, Color.decode(Colours.SUPER_WHITE));
            terminalFrame  = makeFrame("Term", Macro.MainWidth, Macro.MainHeight, JFrame.HIDE_ON_CLOSE, false, Color.decode(Colours.SUPER_WHITE));

            AbstractAction toggles = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shopFrame.setVisible(!shopFrame.isVisible());
                    Assets.playSFX("/assets/audio/btn121.wav");
                }
            };

            AbstractAction back = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Assets.playSFX("/assets/audio/btn121.wav");
                    shopStat = Enums.ShopStatus.NONE;
                    changeShopDisplay();
                }
            };

            if (mainFrame != null) {
                mainFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "openShop");
                mainFrame.getRootPane().getActionMap().put("openShop", toggles);
            }

            if (shopFrame != null) {
                shopFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "closeShop");
                shopFrame.getRootPane().getActionMap().put("closeShop", back);
            }

            Assets.mainPanel.setBounds(0, 0, Macro.MainWidth, Macro.MainHeight);
            Assets.shopPanel.setBounds(0, 0, Macro.ShopWidth, Macro.ShopHeight);

            Assets.casButton.addActionListener(e -> {
                aud += audx;
                updateCounter(Assets.audCount, "AUD", aud);
                Assets.playSFX("/assets/audio/pop.wav");
            });

            Assets.auShopButton.addActionListener(e -> {
                shopStat = Enums.ShopStatus.AUD;
                changeShopDisplay();
                Assets.playSFX("/assets/audio/btn121.wav");
            });

            Assets.nzShopButton.addActionListener(e -> {
                shopStat = Enums.ShopStatus.NZD;
                changeShopDisplay();
                Assets.playSFX("/assets/audio/btn121.wav");
            });

            /* RENDER */
            Assets.casButton.setBounds(
                (Macro.MainWidth - Assets.casButton.getIcon().getIconWidth()) / 2, 
                (Macro.MainHeight - Assets.casButton.getIcon().getIconHeight()) / 2, 
                Assets.casButton.getIcon().getIconWidth(), 
                Assets.casButton.getIcon().getIconHeight()
            );

            Assets.auShopButton.setBounds(
                (Macro.ShopWidth - Assets.auShopButton.getWidth()) / 2,
                ((Macro.ShopHeight - Assets.auShopButton.getHeight()) / 2)-auyd,
                Assets.auShopButton.getWidth(),
                Assets.auShopButton.getHeight()
            );

            // if (nzUnlocked == true) Assets.nzShopButton.setBounds(
            //     (Macro.ShopWidth - Assets.nzShopButton.getWidth()) / 2,
            //     ((Macro.ShopHeight - Assets.nzShopButton.getHeight()) / 2)+50,
            //     Assets.nzShopButton.getWidth(),
            //     Assets.nzShopButton.getHeight()
            // );

            Assets.shopPanel.add(Assets.auShopButton);
            if (nzUnlocked){
                Assets.shopPanel.add(Assets.nzShopButton);
            }

            Assets.mainPanel.add(notif);
            Assets.mainPanel.add(Assets.nzdCount);

            Assets.mainPanel.add(Assets.casButton);
            Assets.mainPanel.add(Assets.audCount);

            mainFrame.add(Assets.mainPanel);
            shopFrame.add(Assets.shopPanel);

            Assets.mainPanel.validate();
            Assets.mainPanel.repaint();

            Assets.shopPanel.validate();
            Assets.shopPanel.repaint();
        });
    }
}

// 54 arnold str.
// Alimtaha