package jsequitur.generation.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;

@Environment(EnvType.CLIENT)
public class FourGenSettings {
	private FourGenSettings() {

	}

	private static boolean hasInit = false;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			registerSettings();
		}
	}

	public static void registerSettings() {
		FourGenSettingsOptions gameSettings = (FourGenSettingsOptions) Minecraft.getMinecraft().gameSettings;

		OptionsPage FOUR_GEN = new OptionsPage("gui.options.page.fourGen.title", new ItemStack(Blocks.BEDROCK))
			.withComponent(new OptionsCategory("gui.options.page.fourGen.category.generation")
				.withComponent(new ToggleableOptionComponent<>(gameSettings.fourGen$worldSize()))
				.withComponent(new ToggleableOptionComponent<>(gameSettings.fourGen$flatNether()))
			);
		OptionsPages.register(FOUR_GEN);
	}
}
