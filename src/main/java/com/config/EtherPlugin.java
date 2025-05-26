package com.config;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.kit.KitType;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.ArrayList;

@Slf4j
@PluginDescriptor(
	name = "Ether Tracker",
	description = "Tracks ether in rev weapons",
	tags = {"revenant,wilderness,weapons,tracker"}
)
public class EtherPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private EtherConfig config;
	@Provides
	EtherConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EtherConfig.class);
	}


	private RevenantWeapon idToWeapon(int id) {
		switch(id){
			case 22542:
				return RevenantWeapon.VIGGORAUNCHARGED;
			case 22545:
				return RevenantWeapon.VIGGORACHARGED;
			case 27657:
				return RevenantWeapon.URSINEUNCHARGED;
			case 27660:
				return RevenantWeapon.URSINECHARGED;
			case 22547:
				return RevenantWeapon.CRAWUNCHARGED;
			case 22550:
				return RevenantWeapon.CRAWCHARGED;
			case 27652:
				return RevenantWeapon.WEBWEAVERUNCHARGED;
			case 27655:
				return RevenantWeapon.WEBWEAVERCHARGED;
			case 27785:
				return RevenantWeapon.AUTOTHAMUNCHARGED;
			case 27788:
				return RevenantWeapon.AUTOTHAMCHARGED;
			case 22552:
				return RevenantWeapon.THAMUNCHARGED;
			case 22555:
				return RevenantWeapon.THAMCHARGED;
			case 27662:
				return RevenantWeapon.ACCUNCHARGED;
			case 27665:
				return RevenantWeapon.ACCCHARGED;
			case 27676:
				return RevenantWeapon.AUTOACCUNCHARGED;
			case 27679:
				return RevenantWeapon.AUTOACCCHARGED;
			default:
				return RevenantWeapon.NOT_REV_WEAPON;
		}
	}
	private void print(String s) {
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", s, null);
	}
	private int etherCount = 0;
	private RevenantWeapon equippedRevWeapon = RevenantWeapon.NOT_REV_WEAPON;

	@Override
	protected void startUp() throws Exception
	{

	}

	@Override
	protected void shutDown() throws Exception
	{

	}
	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		final ItemContainer itemContainer = event.getItemContainer();
		if (event.getContainerId() == 94) {
			equippedRevWeapon = idToWeapon(client.getLocalPlayer().getPlayerComposition().getEquipmentId(KitType.WEAPON));
			return;
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{

		}
	}
	@Subscribe
	public void onHitsplatApplied(HitsplatApplied event) {
		if (!event.getHitsplat().isMine()){
			return;
		}
		if (equippedRevWeapon == RevenantWeapon.NOT_REV_WEAPON){
			return;
		}
		if (event.getActor() == client.getLocalPlayer()) {
			return;
		}
		etherCount -= 1;
		print(String.valueOf(etherCount));
	}
	@Subscribe
	public void onChatMessage(ChatMessage event) {
		String message = event.getMessage();
		if (!message.contains("charges")){
			return;
		}
		ArrayList<Integer> numbersInString = new ArrayList<>();
		for (int i = 0; i < message.length();i++) {
			if (Character.isDigit(message.charAt(i))){
				StringBuilder integer = new StringBuilder();
				integer.append(message.charAt(i));
				while (Character.isDigit(message.charAt(i+1)) && i+1 < message.length()){
					i++;
					integer.append(message.charAt(i));
				}
				numbersInString.add(Integer.valueOf(String.valueOf(integer)));
				break;
			}
		}
		etherCount = numbersInString.get(numbersInString.size()-1);

	}
	}
