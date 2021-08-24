package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.sprite.Image;
import com.rozsa.crow.screen.ui.api.UIHandler;
import com.rozsa.crow.screen.ui.api.UISlotContent;

import java.awt.*;
import java.awt.image.ImageObserver;

public class UISlot extends UIBaseComponent<UISlotTemplate> implements UIHandler {
    private final UISlotTemplate data;
    private final Offset parentOffset;
    private Image background;
    private UISlotContent content;
    private Image handler;
    private UILabel countLabel;
    private boolean isHandlerEnabled;
    private Offset customOffset;
    private boolean isContentVisible;
    private boolean isBackgroundVisible;

    public UISlot(UISlotTemplate data) {
        this(data, new Offset());
    }

    public UISlot (UISlotTemplate data, Offset parentOffset) {
        super(data);
        this.data = data;
        this.parentOffset = parentOffset;
        this.customOffset = Offset.origin();
        isContentVisible = true;
        isBackgroundVisible = true;
        setup();
    }

    private void setup() {
        background = Image.load(data.getBackgroundImage().getImageFile());
        handler = Image.load(data.getHandlerImage().getImageFile());

        isHandlerEnabled = false;
    }

    public void setHandlerEnabled(boolean enabled) {
        isHandlerEnabled = enabled;
    }

    public void clear() {
        content = null;

        if (countLabel != null) {
            countLabel.setText("");
        }
    }

    public Rect getRect() {
        return data.getRect();
    }

    public UISlotContent getContent() {
        return content;
    }

    public void setContentVisible(boolean isVisible) {
        this.isContentVisible = isVisible;
    }

    public void setBackgroundVisible(boolean isVisible) {
        this.isBackgroundVisible = isVisible;
    }

    public void setContent(UISlotContent content) {
        setContent(content, true);
    }

    public void setContent(UISlotContent content, boolean isContentVisible) {
        this.content = content;
        this.isContentVisible = isContentVisible;
        refreshCountLabel();
    }

    public void refreshCountLabel() {
        if (countLabel == null) {
            return;
        }

        String contentText = content != null ? content.getContentCount() : "";
        countLabel.setText(contentText);
        countLabel.setCustomOffset(customOffset);
    }

    public void setCustomOffset(Offset offset) {
        this.customOffset = offset;
        refreshCountLabel();
    }

    @Override
    public void updateComponentTemplate(UISlotTemplate data) {
        throw new UnsupportedOperationException();
    }

    public void show() {
        isEnabled = true;
    }

    public void hide() {
        isEnabled = false;
    }

    public void setText(String text) {
        if (countLabel == null) {
            return;
        }

        countLabel.setText(text);
    }

    public void updateScreenSize(Size parentSize) {
        // TODO: updateScreenSize
    }


    @Override
    public void wrapUp(Container container) {
        UILabelTemplate countLabelData = data.getCountLabel();
        if (countLabelData != null) {
            countLabel = new UILabel(countLabelData, data.getRect().getOffset().add(parentOffset));
            countLabel.setCustomOffset(customOffset);
            countLabel.wrapUp(container);
        }
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        if (!isEnabled) return;

        Rect rect = data.getRect().add(parentOffset).add(customOffset);
        Rect contentRect = data.getContentRect();

        if (isContentVisible && content != null && content.getImage() != null) {
            int contentX = rect.getX() + contentRect.getX();
            int contentY = rect.getY() + contentRect.getY();

            int width = contentRect.getWidth();
            int height = contentRect.getHeight();

            g.drawImage(content.getImage().getContent(width, height), contentX, contentY, width, height, observer);
        }

        if (isBackgroundVisible) {
            g.drawImage(background.getContent(rect.getWidth(), rect.getHeight()), rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), observer);
        }

        if (isHandlerEnabled) {
            g.drawImage(handler.getContent(rect.getWidth(), rect.getHeight()), rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), observer);
        }
    }
}
