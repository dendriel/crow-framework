package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.game.component.PositionComponent;
import com.vrozsa.crowframework.game.component.StaticRenderer;
import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.screen.Sprite;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.image.ResizableImage;
import com.vrozsa.crowframework.shared.templates.SpriteTemplate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ColliderGizmosRenderer extends StaticRenderer {
    private static final int GIZMOS_LAYER = Integer.MAX_VALUE;
    private static final String GIZMOS_NAME = "COLLISION_GIZMOS";
    private static final int GIZMOS_ALPHA = 35;
    private static final Color GIZMOS_COLOR = Color.green();
    private final java.awt.Color color;

    public ColliderGizmosRenderer(final PositionComponent position) {
        this(position, GIZMOS_COLOR);
    }

    public ColliderGizmosRenderer(final PositionComponent position, final Color color) {
        super(position, GIZMOS_LAYER, GIZMOS_NAME, false, false);

        this.color = new java.awt.Color(color.getR(), color.getG(), color.getB(), GIZMOS_ALPHA);
    }

    @Override
    public void wrapUp() {
        super.wrapUp();

        var collider = getComponent(ColliderComponent.class);
        if (Objects.isNull(collider)) {
            return;
        }

        var rect = collider.getRect();

        int type = BufferedImage.TYPE_INT_ARGB;
        var image = new BufferedImage(rect.getWidth(), rect.getHeight(), type);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, rect.getWidth(), rect.getHeight());
        g2d.dispose();

        var spriteTemplate = SpriteTemplate.builder()
                .imageFile(null)
                .offset(rect.getOffset())
                .size(rect.getSize())
                .enabled(true)
                .build();

        var resizableImage = new ResizableImage(image);
        var sprite = Sprite.recreate(spriteTemplate, resizableImage, true);
        addDrawing(sprite);
    }
}
