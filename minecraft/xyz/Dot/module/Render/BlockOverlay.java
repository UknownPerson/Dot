package xyz.Dot.module.Render;


import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender3D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.utils.RenderUtils;


public class BlockOverlay extends Module {
    public static Setting alpha = new Setting(ModuleManager.getModuleByName("BlockOverlay"), "Alpha", 32d, 0d, 255d, 1d);

    float x = 0, y = 0;

    public BlockOverlay() {
        super("BlockOverlay", Keyboard.KEY_NONE, Category.Render);
        this.addValues(alpha);
    }

    public int getRed() {
        return CustomColor.getColor().getRed();
    }

    public int getGreen() {
        return CustomColor.getColor().getGreen();
    }

    public int getBlue() {
        return CustomColor.getColor().getBlue();
    }

    @EventHandler
    public void onRender3D(final EventRender3D event) {
        if (BlockOverlay.mc.objectMouseOver != null && BlockOverlay.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos pos = BlockOverlay.mc.objectMouseOver.getBlockPos();
            final Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
            BlockOverlay.mc.getRenderManager();
            final double n = pos.getX();
            BlockOverlay.mc.getRenderManager();
            final double x = n - RenderManager.renderPosX;
            BlockOverlay.mc.getRenderManager();
            final double n2 = pos.getY();
            BlockOverlay.mc.getRenderManager();
            final double y = n2 - RenderManager.renderPosY;
            BlockOverlay.mc.getRenderManager();
            final double n3 = pos.getZ();
            BlockOverlay.mc.getRenderManager();
            final double z = n3 - RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glColor4f(this.getRed() / 255.0f, this.getGreen() / 255.0f, this.getBlue() / 255.0f, (float) (alpha.getCurrentValue() / 255f));
            final double minX = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinX();
            final double minY = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinY();
            final double minZ = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinZ();
            RenderUtils.drawCube(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            //GL11.glColor4f(this.getRed() / 255.0f, this.getGreen() / 255.0f, this.getBlue() / 255.0f, (float) alpha.getCurrentValue());
            //GL11.glLineWidth(0.5f);
            //RenderUtils.drawCube(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
