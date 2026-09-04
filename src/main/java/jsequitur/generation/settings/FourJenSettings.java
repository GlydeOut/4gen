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
public class FourJenSettings {
	private FourJenSettings() {

	}

	private static boolean hasInit = false;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			registerSettings();
		}
	}

	public static void registerSettings() {
		FourJenSettingsOptions gameSettings = (FourJenSettingsOptions) Minecraft.getMinecraft().gameSettings;

		OptionsPage FOUR_GEN = new OptionsPage("gui.options.page.fourJen.title", new ItemStack(Blocks.WOOL, 1, 14))
			.withComponent(new OptionsCategory("gui.options.page.fourJen.category.generation")
				.withComponent(new ToggleableOptionComponent<>(gameSettings.fourJen$worldSize()))
				.withComponent(new ToggleableOptionComponent<>(gameSettings.fourJen$flatNether()))
			);
		OptionsPages.register(FOUR_GEN);
	}
}
