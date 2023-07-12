package xyz.Dot.ui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.Session;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ReSetSessionUI extends GuiScreen {
    private List<GuiTextField> textFieldList = Lists.<GuiTextField>newArrayList();

    @Override
    public void initGui() {
        super.initGui();
        textFieldList.clear();
        this.textFieldList.add(new GuiTextField(0, fontRendererObj, RenderUtils.width() / 2 - 100, RenderUtils.height() / 4, 200, 20));
        this.textFieldList.add(new GuiTextField(1, fontRendererObj, RenderUtils.width() / 2 - 100, RenderUtils.height() / 4 + 32, 200, 20));
        this.textFieldList.add(new GuiTextField(2, fontRendererObj, RenderUtils.width() / 2 - 100, RenderUtils.height() / 4 + 64, 200, 20));
        this.textFieldList.add(new GuiTextField(3, fontRendererObj, RenderUtils.width() / 2 - 100, RenderUtils.height() / 4 + 96, 200, 20));

        for (GuiTextField tf : textFieldList) {
            if (tf.getId() == 0) {
                tf.setText(Minecraft.session.getUsername());
            }
            if (tf.getId() == 1) {
                tf.setText(Minecraft.session.getPlayerID());
            }
            if (tf.getId() == 2) {
                tf.setText(Minecraft.session.getToken());
            }
            if (tf.getId() == 3) {
                tf.setText(Minecraft.session.getStringSessionType());
            }
        }

        this.buttonList.add(new GuiButton(0, RenderUtils.width() / 2 - 100, RenderUtils.height() / 4 + 128, "OK"));
        this.buttonList.add(new GuiButton(1, RenderUtils.width() / 2 - 100, RenderUtils.height() / 4 + 152, "Cancel"));
    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public void updateScreen() {
        for (GuiTextField tf : textFieldList) {
            tf.updateCursorCounter();
        }
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        fontRendererObj.drawString("Username", this.width / 2 - 100, RenderUtils.height() / 4 - 10, new Color(128, 128, 128).getRGB());
        fontRendererObj.drawString("PlayerID", this.width / 2 - 100, RenderUtils.height() / 4 + 32 - 10, new Color(128, 128, 128).getRGB());
        fontRendererObj.drawString("Token", this.width / 2 - 100, RenderUtils.height() / 4 + 64 - 10, new Color(128, 128, 128).getRGB());
        fontRendererObj.drawString("SessionType", this.width / 2 - 100, RenderUtils.height() / 4 + 96 - 10, new Color(128, 128, 128).getRGB());

        for (GuiTextField tf : textFieldList) {
            tf.drawTextBox();
        }

        for (GuiButton button : buttonList) {
            button.drawButton(mc, mouseX, mouseY);
        }
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {

            GuiTextField usernameField = null;
            GuiTextField playerIDField = null;
            GuiTextField tokenField = null;
            GuiTextField sessiontypeField = null;

            for (GuiTextField tf : textFieldList) {
                if (tf.getId() == 0) {
                    usernameField = tf;
                }
                if (tf.getId() == 1) {
                    playerIDField = tf;
                }
                if (tf.getId() == 2) {
                    tokenField = tf;
                }
                if (tf.getId() == 3) {
                    sessiontypeField = tf;
                }
            }

            Minecraft.session = new Session(usernameField.getText(), playerIDField.getText(), tokenField.getText(), sessiontypeField.getText());
            mc.displayGuiScreen(new GuiMainMenu());
        }
        if (button.id == 1) {
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    @Override
    protected void keyTyped(char keyChar, int keyCode) throws IOException {
        super.keyTyped(keyChar, keyCode);

        for (GuiTextField tf : textFieldList) {
            if (tf.isFocused()) {
                tf.textboxKeyTyped(keyChar, keyCode);
            }
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (GuiTextField tf : textFieldList) {
            tf.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
}
