package com.vrozsa.crowframework.screen.ui.components;

import com.vrozsa.crowframework.screen.ui.components.templates.UILabelTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UISlotTemplate;
import com.vrozsa.crowframework.shared.api.screen.Image;
import com.vrozsa.crowframework.shared.image.ImageLoader;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.screen.ui.components.api.UIHandler;
import com.vrozsa.crowframework.screen.ui.components.api.UISlotContent;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public final class UISlot extends AbstractUIComponent<UISlotTemplate> implements UIHandler {
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
        background = ImageLoader.load(data.getBackgroundImage().getImageFile());
        handler = ImageLoader.load(data.getHandlerImage().getImageFile());

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
            countLabel = UILabel.from(countLabelData, data.getRect().getOffset().sum(parentOffset));
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
