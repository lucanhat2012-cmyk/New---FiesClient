package dev.fiesclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class FiesClient implements ClientModInitializer {
    private static KeyMapping openGui;

    @Override
    public void onInitializeClient() {
        openGui = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.fiesclient.open_gui",
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            KeyMapping.Category.MISC
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGui.consumeClick()) {
                client.setScreen(new FiesScreen());
            }
        });
    }

    private static class FiesScreen extends Screen {
        protected FiesScreen() {
            super(Component.literal("FiesClient"));
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
            graphics.fill(0, 0, this.width, this.height, 0xB8000000);
            int centerX = this.width / 2;
            int centerY = this.height / 2;
            int panelWidth = Math.min(420, this.width - 32);
            int left = centerX - panelWidth / 2;
            int top = centerY - 105;

            graphics.fill(left, top, left + panelWidth, top + 210, 0xFF24131F);
            graphics.fill(left, top, left + panelWidth, top + 3, 0xFFE7A6D7);
            graphics.drawCenteredString(this.font, "FiesClient", centerX, top + 18, 0xFFFFDDF5);
            graphics.drawCenteredString(this.font, "Sakura  •  Minecraft 1.21.11", centerX, top + 36, 0xFFD8B9D3);

            String[] sections = {"Donut", "Combat", "Client", "Misc", "Settings"};
            int tileWidth = (panelWidth - 36) / sections.length;
            for (int i = 0; i < sections.length; i++) {
                int x = left + 12 + i * tileWidth;
                int y = top + 72;
                graphics.fill(x, y, x + tileWidth - 4, y + 74, 0xFF513047);
                graphics.fill(x, y, x + tileWidth - 4, y + 2, 0xFFD88FC5);
                graphics.drawCenteredString(this.font, sections[i], x + (tileWidth - 4) / 2, y + 32, 0xFFFFEAF8);
            }
            graphics.drawCenteredString(this.font, "GUI preview — more features coming next", centerX, top + 170, 0xFFD8B9D3);
            super.render(graphics, mouseX, mouseY, delta);
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }
    }
}
