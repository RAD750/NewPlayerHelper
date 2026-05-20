package com.rgbcraft.newplayerhelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		Main.nphLog.info("Player " + player.username + " (id: " + player.entityId + ") joined the server");

		File f = new File("./world/playerdata/NewPlayerHelper/" + player.username + ".txt");
		if (!f.exists() && !f.isDirectory()) {
			FileWriter writer;
			try {
				writer = new FileWriter("./world/playerdata/NewPlayerHelper/" + player.username + ".txt");
				try {
					ItemStack ticket = new ItemStack(Item.writtenBook);

					ticket.setTagInfo("author", new NBTTagString("author", "Ministero dell'Accettazione"));
					ticket.setTagInfo("title", new NBTTagString("title", "Istruzioni-Instructions"));
					NBTTagList pages = new NBTTagList();
					pages.appendTag(new NBTTagString("pag1", "Welcome to §lZorya!§r \nBenvenuti a §lZorya! \n\n§l§nENGLISH: Go to page 7!§r\n\nIl lungo viaggio a bordo della §oBorealis §r è concluso. E' ora di sbarcare e svolgere le formalità doganali.\n\nLeggi e conserva questo libro: ti sarà prezioso."));
					pages.appendTag(new NBTTagString("pag2", "§lImmigrazione§r\n\nAl §lvarco di frontiera §rtroverai un computer. Completando la procedura, il §oMinistero dell'Abbondanza§r ti regalerà l'essenziale per incominciare la tua nuova vita: §lcellulare, denaro, cibo e acqua§r."));
					pages.appendTag(new NBTTagString("pag3", "§lSei un cittadino!§r\n\nOra che sei un §lzoryano§r a tutti gli effetti, sei libero di muoverti per tutto il territorio. Puoi andare in §lminiera§r a §lGrestin§r per recuperare qualche risorsa."));
					pages.appendTag(new NBTTagString("pag4", "§lDove alloggiare?§r\n\nPer §lfare casa§r, devi §lacquistare il terreno§r. Se non hai modo di farlo, puoi sempre stare in un §lhotel§r, oppure in una §lcabina§r della §oBorealis§r."));
					pages.appendTag(new NBTTagString("pag5", "§lTrasporti§r\n\nPuoi utilizzare la vasta §lrete ferroviaria§r (§orgr.rgbcraft.com§r), mentre a §lNew Radeon§r puoi usare la rete TCN (§otcn.rgbcraft.com§r). Naturalmente, puoi anche usare una §lbarca§r o, in futuro, la §oPowerArmor§r."));
					pages.appendTag(new NBTTagString("pag6", "§lAltre domande?§r\n\nRaggiungici sul nostro server §lDiscord§r: esegui il comando §o/discord§r per avere l'invito."));
					pages.appendTag(new NBTTagString("pag7", "Welcome to §lZorya!§r\n\nThe long journey aboard the §oBorealis§r is over. It's time to disembark and complete the customs formalities.\n\nRead and keep this book: it'll come in handy."));
					pages.appendTag(new NBTTagString("pag8", "§lImmigration§r\n\nAt the §lborder crossing§r you'll find a computer. By completing the procedure, the §oMinistry of Plentifulness§r will give you the essentials for your new life: §lcellphone, money, food and water§r."));
					pages.appendTag(new NBTTagString("pag9", "§lYou are a citizen!§r\n\nNow that you are a §lzoryan§r in every sense, you are free to move throughout the country. You can go to the §lmine§r at §lGrestin§r to get some resources."));
					pages.appendTag(new NBTTagString("pag10", "§lWhere to live?§r\n\nTo §lbuild a home§r, you must §lpurchase land§r. If you have no way to do so, you can always stay in a §lhotel§r, or in a §lcabin§r of the §oBorealis§r."));
					pages.appendTag(new NBTTagString("pag11", "§lTransport§r\n\nYou can use the extensive §lrail network§r (§orgr.rgbcraft.com§r), while in §lNew Radeon§r you can use the TCN network (§otcn.rgbcraft.com§r). You can also naturally use a §lboat§r or a §oPowerArmor§r once you can afford it."));
					pages.appendTag(new NBTTagString("pag12", "§lOther questions?§r\n\nGet in touch with us on our §lDiscord§r server: run the command §o/discord§r to get the invite."));
					ticket.setTagInfo("pages", pages);

					if(player.inventory.addItemStackToInventory(ticket)) {
						writer.write("given");
						player.sendChatToPlayer("\247c\247lBenvenuto a Zorya! \247r\247bLeggi le istruzioni che trovi nel tuo inventario. \247r\247o~Ministero dell'Accettazione");
						player.sendChatToPlayer("\247c\247lWelcome to Zorya! \247r\247bRead the instructions you have in your inventory. \247r\247o~Ministry of Admission");
						Main.nphLog.info("Given entry ticket to player");
					} else {
						Main.nphLog.severe("Could not give entry ticket to player");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			Main.nphLog.info("Not given ticket: player already has ticket");
		}
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
	}
}
