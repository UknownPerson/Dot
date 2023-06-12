package xyz.Dot.custom.components;

import net.minecraft.item.ItemStack;
import xyz.Dot.custom.base.Component;

import java.util.Arrays;

public class Armors extends Component {
    public Armors() {
        super(50, 84, "Armor");
    }

    @Override
    public void drawHUD(float x, float y, float partialTicks) {
        drawBackground((int) x, (int) y,"Armors");
        setWidth(50);
        y += 12;
        if(Arrays.equals(mc.thePlayer.inventory.armorInventory, new ItemStack[4]))
            return;
        try{
            mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.inventory.armorInventory[3], (int) (x + 4), (int) (y + 2));
            if(mc.thePlayer.inventory.armorInventory[3] != null)
                mc.fontRendererObj.drawCenteredString(String.valueOf(mc.thePlayer.inventory.armorInventory[3].getMaxDamage() - mc.thePlayer.inventory.armorInventory[3].getItemDamage()),(int) (x + 34), (int) (y + 6),-1);
            mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.inventory.armorInventory[2], (int) (x + 4), (int) (y + 16 + 2));
            if(mc.thePlayer.inventory.armorInventory[2] != null)
                mc.fontRendererObj.drawCenteredString(String.valueOf(mc.thePlayer.inventory.armorInventory[2].getMaxDamage() - mc.thePlayer.inventory.armorInventory[2].getItemDamage()),(int) (x + 34), (int) (y + 6 + 16),-1);
            mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.inventory.armorInventory[1], (int) (x + 4), (int) (y + 34 + 2));
            if(mc.thePlayer.inventory.armorInventory[1] != null)
                mc.fontRendererObj.drawCenteredString(String.valueOf(mc.thePlayer.inventory.armorInventory[1].getMaxDamage() - mc.thePlayer.inventory.armorInventory[1].getItemDamage()),(int) (x + 34), (int) (y + 6 + 34),-1);
            mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.inventory.armorInventory[0], (int) (x + 4), (int) (y + 51 + 2));
            if(mc.thePlayer.inventory.armorInventory[0] != null)
                mc.fontRendererObj.drawCenteredString(String.valueOf(mc.thePlayer.inventory.armorInventory[0].getMaxDamage() - mc.thePlayer.inventory.armorInventory[0].getItemDamage()),(int) (x + 34), (int) (y + 6 + 51),-1);
        }catch (Exception e){}
    }
}
