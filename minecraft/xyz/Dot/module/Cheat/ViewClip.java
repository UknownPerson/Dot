package xyz.Dot.module.Cheat;

import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.ui.Notification;

public class ViewClip extends Module {
   public ViewClip() {
      super("ViewClip", Keyboard.KEY_NONE, Category.Cheat);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if(!ModuleManager.SigmaMode){
         Notification.sendClientMessage("You are not a Sigma user.You can't enable it.", Notification.Type.WARNING);
      }
   }

   @EventHandler
   public void renderHud(EventRender2D event) {
      if(!ModuleManager.SigmaMode){
         this.setToggle(false);
      }
   }
}
