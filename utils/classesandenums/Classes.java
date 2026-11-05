package utils.classesandenums;

import javax.swing.*;
import java.awt.Image;
import java.net.URL;

public class Classes {
    public static class Upgrade {
        public String Title;
        public long Cost;
        public Enums.Currency Currency;
        public long mulx;
        public boolean bought;
        public int id;

        public Upgrade(String Title, long Cost, Enums.Currency Curr, long x, boolean bought, int uid) {
            this.Title = Title;
            this.Cost = Cost;
            this.Currency = Curr;
            this.mulx = x;
            this.bought = bought;
            this.id = uid;
        }

        // public boolean checkIfUnlocked() {
        //     return this.bought;
        // }

        // public void purchase(long price, Enums.Currency curr) {
        //     if (price >= this.Cost && curr == this.Currency && this.bought == false) {
        //         this.bought = true;
        //     } else {
        //         return;
        //     }
        // }
    }

    public static class UpgradeDisplay extends JPanel{
        private Upgrade upg;
        public int x, y;

        public JButton buyBtn;
        private JLabel Img;
        private JLabel displayLabel;

        public UpgradeDisplay(int x, int y, Upgrade upg) {
            this.x=x;
            this.y=y;

            URL url = (getClass().getResource("/assets/images/"+upg.Title+".png") != null) ? getClass().getResource("/assets/images/"+upg.Title+".png") : getClass().getResource("/assets/images/placeholder.jpeg");
            ImageIcon icon = (url != null) ? new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH)) : null;

            this.setBounds(x, y, 190, 150);

            this.upg = upg;

            this.setLayout(null);
            this.setOpaque(false);
            
            this.displayLabel = new JLabel("<html><center>"+upg.Title + "<br>" + upg.Cost + " " + upg.Currency.name()+"</center></html>");
            this.displayLabel.setHorizontalAlignment(0 /*SwingConstants.CENTER*/);
            this.displayLabel.setBounds(-37, 90, 180, 45);

            String BuyTitle = (upg.bought == false)
                ? "Buy"
                : "Bought"
            ;

            this.buyBtn = new JButton(BuyTitle);
            this.buyBtn.setBounds(5, 132, 90, 15);
            this.buyBtn.setFocusable(false);
            this.buyBtn.setOpaque(true);
            this.buyBtn.setContentAreaFilled(true);
            this.buyBtn.setBorderPainted(false);

            Img = new JLabel(icon);
            if (icon == null) {
                Img.setText("Image does not exist");
            }

            Img.setBounds(5, 5, 90, 90);


            this.add(Img);
            this.add(displayLabel);
            this.add(buyBtn);
        }
    }

    public static class Notification extends JPanel {
        public String Text;
        public JLabel txt = new JLabel(this.Text);

        public Notification(String msg) {
            this.Text = msg;

            this.setBounds(0, 30, 200, 50);
            this.setLayout(null);
            this.setVisible(false);

            final JLabel title = new JLabel("Notification:");
            title.setBounds(5, -15, 200, 50);

            txt.setBounds(5, 10, 200, 50);
            
            this.add(txt);
            this.add(title);
        }
    }
}
