package com.rozsa.crow.screen.ui.buttongroup;


import com.rozsa.crow.screen.sprite.Image;
import com.rozsa.crow.screen.ui.UIIcon;

import javax.swing.*;
import javax.swing.plaf.metal.MetalScrollBarUI;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

class CustomScrollBarUI extends MetalScrollBarUI {
    private Image thumb;
    private Image track;
    private JButton increaseButton;
    private JButton decreaseButton;

    CustomScrollBarUI(CustomScrollBarUITemplate data) {
        setup(data);
    }

    private void setup(CustomScrollBarUITemplate data) {
        thumb = Image.load(data.getThumb());
        track = Image.load(data.getTrack());

        String increaseButtonImage = data.getIncreaseButton();
        if (increaseButtonImage != null) {
            increaseButton = new JButton();
            setupButton(increaseButton, data.getIncreaseButton());
        }

        String decreaseButtonImage = data.getDecreaseButton();
        if (decreaseButtonImage != null) {
            decreaseButton = new JButton();
            setupButton(decreaseButton, data.getDecreaseButton());
        }
    }

    private void setupButton(JButton button, String imagePath) {
        Image image = Image.load(imagePath);
        BufferedImage bufferedImage = image.getContent(20, 20);
        button.setIcon(new ImageIcon(bufferedImage));

        button.setBorder(null);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return decreaseButton;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return increaseButton;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        java.awt.Image imageThumb = thumb.getContent();
        g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor(new Color(0,0,0,0));
        g.drawRect( 0, 0, thumbBounds.width - 2, thumbBounds.height - 1 );
        AffineTransform transform = AffineTransform.getScaleInstance((double)thumbBounds.width/imageThumb.getWidth(null),(double)thumbBounds.height/imageThumb.getHeight(null));
        ((Graphics2D)g).drawImage(imageThumb, transform, null);
        g.translate( -thumbBounds.x, -thumbBounds.y );
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        java.awt.Image imageTrack = track.getContent();
        g.translate(trackBounds.x, trackBounds.y);
        ((Graphics2D)g).drawImage(imageTrack,AffineTransform.getScaleInstance(1,(double)trackBounds.height/imageTrack.getHeight(null)),null);
        g.translate( -trackBounds.x, -trackBounds.y );
    }
}
