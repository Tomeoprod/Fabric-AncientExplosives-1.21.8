package net.tomeoprod.ancient_explosives.util;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import java.util.OptionalDouble;

public class RenderUtils {
    //WorldRenderEvents.AFTER_TRANSLUCENT.register(worldRenderContext -> {WorldRenderContext renderContext = worldRenderContext;});
    public static RenderLayer.MultiPhase LINES = RenderLayer.of("lines:lines", 1536, RenderPipelines.register(RenderPipeline.builder(RenderPipelines.RENDERTYPE_LINES_SNIPPET)
                    .withLocation(Identifier.of("lines:pipeline/lines"))
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).build()),
            RenderLayer.MultiPhaseParameters.builder()
                    .lineWidth(new RenderPhase.LineWidth(OptionalDouble.of(2)))
                    .layering(RenderLayer.VIEW_OFFSET_Z_LAYERING)
                    .target(RenderLayer.ITEM_ENTITY_TARGET)
                    .build(false)
            );

    public static final RenderLayer.MultiPhase QUADS = RenderLayer.of("lines:quads", 1536, false, true, RenderPipelines.register(RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
                    .withLocation(Identifier.of("lines:pipeline/quads"))
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).build()),
            RenderLayer.MultiPhaseParameters.builder().build(false)
    );

    public static void renderOutlinedBox(WorldRenderContext renderContext, Box box, int red, int green, int blue, int alpha) {
        MatrixStack matrices = renderContext.matrixStack();
        Vec3d camera = renderContext.gameRenderer().getCamera().getPos();

        matrices.push();

        matrices.translate(-camera.x, -camera.y, -camera.z);

        VertexConsumerProvider.Immediate consumers = (VertexConsumerProvider.Immediate) renderContext.consumers();
        RenderLayer layer = LINES;
        VertexConsumer buffer = consumers.getBuffer(layer);

        drawOutlinedBox(matrices, buffer, box, red, green, blue, alpha);

        consumers.draw(layer);

        matrices.pop();
    }

    public static void renderSolidBox(WorldRenderContext renderContext, Box box, int red, int green, int blue, int alpha) {
        MatrixStack matrices = renderContext.matrixStack();
        Vec3d camera = renderContext.gameRenderer().getCamera().getPos();

        matrices.push();

        matrices.translate(-camera.x, -camera.y, -camera.z);

        VertexConsumerProvider.Immediate consumers = (VertexConsumerProvider.Immediate) renderContext.consumers();
        RenderLayer layer = QUADS;
        VertexConsumer buffer = consumers.getBuffer(layer);

        drawSolidBox(matrices, buffer,box,red,green,blue,alpha);
        consumers.draw(layer);

        matrices.pop();
    }

    public static void renderLine(WorldRenderContext renderContext, Vec3d start, Vec3d end, int red, int green, int blue, int alpha) {
        MatrixStack matrices = renderContext.matrixStack();
        Vec3d camera = renderContext.gameRenderer().getCamera().getPos();

        matrices.push();

        matrices.translate(-camera.x, -camera.y, -camera.z);

        VertexConsumerProvider.Immediate consumers = (VertexConsumerProvider.Immediate) renderContext.consumers();
        RenderLayer layer = RenderLayer.LINES;
        VertexConsumer buffer = consumers.getBuffer(layer);

        drawLine(matrices,buffer,start,end, red, green, blue, alpha);
        consumers.draw(layer);

        matrices.pop();
    }

    public static void drawLine(MatrixStack matrices, VertexConsumer buffer,
                                Vec3d start, Vec3d end, int r, int g, int b, int a)
    {
        int color = rgbaToInt(r, g, b, a);
        MatrixStack.Entry entry = matrices.peek();
        float x1 = (float)start.x;
        float y1 = (float)start.y;
        float z1 = (float)start.z;
        float x2 = (float)end.x;
        float y2 = (float)end.y;
        float z2 = (float)end.z;
        drawLine(entry, buffer, x1, y1, z1, x2, y2, z2, color);
    }

    public static void drawLine(MatrixStack.Entry entry, VertexConsumer buffer,
                                float x1, float y1, float z1, float x2, float y2, float z2, int color)
    {
        Vector3f normal = new Vector3f(x2, y2, z2).sub(x1, y1, z1).normalize();
        buffer.vertex(entry, x1, y1, z1).color(color).normal(entry, normal);

        // If the line goes through the screen, add another vertex there. This
        // works around a bug in Minecraft's line shader.
        float t = new Vector3f(x1, y1, z1).negate().dot(normal);
        float length = new Vector3f(x2, y2, z2).sub(x1, y1, z1).length();
        if(t > 0 && t < length)
        {
            Vector3f closeToCam = new Vector3f(normal).mul(t).add(x1, y1, z1);
            buffer.vertex(entry, closeToCam).color(color).normal(entry, normal);
            buffer.vertex(entry, closeToCam).color(color).normal(entry, normal);
        }

        buffer.vertex(entry, x2, y2, z2).color(color).normal(entry, normal);
    }

    public static void drawOutlinedBox(MatrixStack matrices,
                                       VertexConsumer buffer, Box box, int r, int g, int b, int a)
    {

        int color = rgbaToInt(r, g, b, a);
        MatrixStack.Entry entry = matrices.peek();
        float x1 = (float)box.minX;
        float y1 = (float)box.minY;
        float z1 = (float)box.minZ;
        float x2 = (float)box.maxX;
        float y2 = (float)box.maxY;
        float z2 = (float)box.maxZ;

        // bottom lines
        buffer.vertex(entry, x1, y1, z1).color(color).normal(entry, 1, 0, 0);
        buffer.vertex(entry, x2, y1, z1).color(color).normal(entry, 1, 0, 0);
        buffer.vertex(entry, x1, y1, z1).color(color).normal(entry, 0, 0, 1);
        buffer.vertex(entry, x1, y1, z2).color(color).normal(entry, 0, 0, 1);
        buffer.vertex(entry, x2, y1, z1).color(color).normal(entry, 0, 0, 1);
        buffer.vertex(entry, x2, y1, z2).color(color).normal(entry, 0, 0, 1);
        buffer.vertex(entry, x1, y1, z2).color(color).normal(entry, 1, 0, 0);
        buffer.vertex(entry, x2, y1, z2).color(color).normal(entry, 1, 0, 0);

        // top lines
        buffer.vertex(entry, x1, y2, z1).color(color).normal(entry, 1, 0, 0);
        buffer.vertex(entry, x2, y2, z1).color(color).normal(entry, 1, 0, 0);
        buffer.vertex(entry, x1, y2, z1).color(color).normal(entry, 0, 0, 1);
        buffer.vertex(entry, x1, y2, z2).color(color).normal(entry, 0, 0, 1);
        buffer.vertex(entry, x2, y2, z1).color(color).normal(entry, 0, 0, 1);
        buffer.vertex(entry, x2, y2, z2).color(color).normal(entry, 0, 0, 1);
        buffer.vertex(entry, x1, y2, z2).color(color).normal(entry, 1, 0, 0);
        buffer.vertex(entry, x2, y2, z2).color(color).normal(entry, 1, 0, 0);

        // side lines
        buffer.vertex(entry, x1, y1, z1).color(color).normal(entry, 0, 1, 0);
        buffer.vertex(entry, x1, y2, z1).color(color).normal(entry, 0, 1, 0);
        buffer.vertex(entry, x2, y1, z1).color(color).normal(entry, 0, 1, 0);
        buffer.vertex(entry, x2, y2, z1).color(color).normal(entry, 0, 1, 0);
        buffer.vertex(entry, x1, y1, z2).color(color).normal(entry, 0, 1, 0);
        buffer.vertex(entry, x1, y2, z2).color(color).normal(entry, 0, 1, 0);
        buffer.vertex(entry, x2, y1, z2).color(color).normal(entry, 0, 1, 0);
        buffer.vertex(entry, x2, y2, z2).color(color).normal(entry, 0, 1, 0);
    }



    public static void drawSolidBox(MatrixStack matrices, VertexConsumer buffer,
                                    Box box, int r, int g, int b, int a)
    {
        int color = rgbaToInt(r, g, b, a);

        MatrixStack.Entry entry = matrices.peek();
        float x1 = (float)box.minX;
        float y1 = (float)box.minY;
        float z1 = (float)box.minZ;
        float x2 = (float)box.maxX;
        float y2 = (float)box.maxY;
        float z2 = (float)box.maxZ;

        buffer.vertex(entry, x1, y1, z1).color(color);
        buffer.vertex(entry, x2, y1, z1).color(color);
        buffer.vertex(entry, x2, y1, z2).color(color);
        buffer.vertex(entry, x1, y1, z2).color(color);

        buffer.vertex(entry, x1, y2, z1).color(color);
        buffer.vertex(entry, x1, y2, z2).color(color);
        buffer.vertex(entry, x2, y2, z2).color(color);
        buffer.vertex(entry, x2, y2, z1).color(color);

        buffer.vertex(entry, x1, y1, z1).color(color);
        buffer.vertex(entry, x1, y2, z1).color(color);
        buffer.vertex(entry, x2, y2, z1).color(color);
        buffer.vertex(entry, x2, y1, z1).color(color);

        buffer.vertex(entry, x2, y1, z1).color(color);
        buffer.vertex(entry, x2, y2, z1).color(color);
        buffer.vertex(entry, x2, y2, z2).color(color);
        buffer.vertex(entry, x2, y1, z2).color(color);

        buffer.vertex(entry, x1, y1, z2).color(color);
        buffer.vertex(entry, x2, y1, z2).color(color);
        buffer.vertex(entry, x2, y2, z2).color(color);
        buffer.vertex(entry, x1, y2, z2).color(color);

        buffer.vertex(entry, x1, y1, z1).color(color);
        buffer.vertex(entry, x1, y1, z2).color(color);
        buffer.vertex(entry, x1, y2, z2).color(color);
        buffer.vertex(entry, x1, y2, z1).color(color);
    }

    public static int rgbaToInt(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                (b & 0xFF);
    }
}
