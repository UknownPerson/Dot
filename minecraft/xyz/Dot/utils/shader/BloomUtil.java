package xyz.Dot.utils.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class BloomUtil {

    public static ShaderUtil gaussianBloom = new ShaderUtil("dot/shaders/bloom.frag");

    public static Framebuffer framebuffer = new Framebuffer(1, 1, false);
    private static Framebuffer bloomFramebuffer = new Framebuffer(1, 1, false);

    public static void start() {
        bloomFramebuffer = GaussianBlur.createFrameBuffer(framebuffer);
        bloomFramebuffer.framebufferClear();
        bloomFramebuffer.bindFramebuffer(true);
    }

    public static void end(int radius, int offset) {
        bloomFramebuffer.unbindFramebuffer();
        renderBlur(bloomFramebuffer.framebufferTexture, radius, offset);
    }

    public static void renderBlur(int sourceTexture, int radius, int offset) {
        framebuffer = GaussianBlur.createFrameBuffer(framebuffer);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.0f);
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= radius; i++) {
            weightBuffer.put(GaussianBlur.calculateGaussianValue(i, radius));
        }
        weightBuffer.rewind();

        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0);

        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        gaussianBloom.init();
        setupUniforms(radius, offset, 0, weightBuffer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, sourceTexture);
        ShaderUtil.drawQuads();
        gaussianBloom.unload();
        framebuffer.unbindFramebuffer();


        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);

        gaussianBloom.init();
        setupUniforms(radius, 0, offset, weightBuffer);
        GL13.glActiveTexture(GL13.GL_TEXTURE16);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, sourceTexture);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        gaussianBloom.unload();

        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableAlpha();

        GlStateManager.bindTexture(0);
    }

    public static void setupUniforms(int radius, int directionX, int directionY, FloatBuffer weights) {
        gaussianBloom.setUniformi("inTexture", 0);
        gaussianBloom.setUniformi("textureToCheck", 16);
        gaussianBloom.setUniformf("radius", radius);
        gaussianBloom.setUniformf("texelSize", 1.0F / (float) Minecraft.getMinecraft().displayWidth, 1.0F / (float) Minecraft.getMinecraft().displayHeight);
        gaussianBloom.setUniformf("direction", directionX, directionY);
        GL20.glUniform1(gaussianBloom.getUniform("weights"), weights);
    }
}
