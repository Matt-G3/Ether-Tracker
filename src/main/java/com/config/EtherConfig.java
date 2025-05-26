package com.config;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Settings")
public interface EtherConfig extends Config
{
	@ConfigItem(
		keyName ="threshold",
		name ="Ether Warning Threshold",
		description = "Put zero for no warning",
		position = 0
	)
	default int threshold() { return 300; }

	@ConfigItem(
		keyName = "highlightTeleports",
		name = "Highlight Teleports",
		description = "Highlights wilderness teleports in red when low on ether",
		position = 2
	)
	default boolean highlightTeleports() { return true; }

	@ConfigItem(
		keyName = "showTracker",
		name = "Display Tracker",
		description = "Reveals or Hides the HUD tracker element",
		position = 3
	)
	default boolean showTracker() { return true; }

	@ConfigItem(
		keyName = "etherCounts",
		name = "",
		description = ""
	)
	default int[] etherCounts() { return new int[]{0, 0, 0}; }

}
