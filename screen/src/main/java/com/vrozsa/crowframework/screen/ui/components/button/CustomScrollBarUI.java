package com.vrozsa.crowframework.screen.ui.components.button;


import com.vrozsa.crowframework.screen.ui.components.templates.CustomScrollBarUITemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UIButtonTemplate;
import com.vrozsa.crowframework.shared.api.screen.Image;
import com.vrozsa.crowframework.shared.image.ImageLoader;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalScrollBarUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

class CustomScrollBarUI extends MetalScrollBarUI {
    private Image thumb;
    private Image track;
    private UIButton increaseButton;
    private UIButton decreaseButton;

    CustomScrollBarUI(CustomScrollBarUITemplate data) {
        setup(data);
    }

    private void setup(CustomScrollBarUITemplate data) {
        thumb = ImageLoader.load(data.getThumb());
        track = ImageLoader.load(data.getTrack());

        UIButtonTemplate increaseButtonTemplate = data.getIncreaseButton();
        if (increaseButtonTemplate != null) {
            increaseButton = new UIButton(increaseButtonTemplate);
            setupButton(increaseButton);
        }

        UIButtonTemplate decreaseButtonTemplate = data.getDecreaseButton();
        if (decreaseButtonTemplate != null) {
            decreaseButton = new UIButton(decreaseButtonTemplate);
            setupButton(decreaseButton);
        }
    }

    private void setupButton(UIButton button) {
//        Image image = ImageLoader.load(imagePath);
//        BufferedImage bufferedImage = image.getContent(20, 20);
//        button.setIcon(new ImageIcon(bufferedImage));
//
//        button.setBackground(new Color(0,0,0,0));
//
//        button.setBorder(null);
//        button.setMargin(new Insets(0, 0, 0, 0));
//        button.setFocusPainted(false);
//        button.setContentAreaFilled(false);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return decreaseButton.getJButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return increaseButton.getJButton();
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
        java.awt.Image imageTrack = track.getContent((int)trackBounds.getWidth(), (int)trackBounds.getHeight());
        g.translate(trackBounds.x, trackBounds.y);
        ((Graphics2D)g).drawImage(imageTrack,AffineTransform.getScaleInstance(1,(double)trackBounds.height/imageTrack.getHeight(null)),null);
        g.translate( -trackBounds.x, -trackBounds.y );
    }
}
