package xyz.Dot.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageNeedLoad {
    public ResourceLocation rl;
    private static final Logger logger = LogManager.getLogger();
    public String location;

    public ImageNeedLoad(String l) {
        location = l;
        ImageLoader.needLoads.add(this);
        load();
    }

    public void load() {
        long starttime = System.nanoTime();
        rl = new ResourceLocation(location);
        Minecraft.getMinecraft().getTextureManager().bindTexture(rl);
        float time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] LoadOK " + location + " " + time + "ms");
    }
}
