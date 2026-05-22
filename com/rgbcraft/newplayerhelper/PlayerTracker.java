package com.rgbcraft.newplayerhelper;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.StringTokenizer;
import java.net.URL;

import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetServerHandler;
import net.minecraft.server.MinecraftServer;

public class PlayerTracker implements IPlayerTracker {

    private static final Random RAND = new Random();
	
	@Override
	public void onPlayerLogin(EntityPlayer player) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		Main.nphLog.info("Player " + player.username + " (id: " + player.entityId + ") joined the server");

		//parte geoip
		
		String ip = "";
		EntityPlayerMP mp = (EntityPlayerMP) player;
		System.out.println(mp.entityId);
		NetServerHandler netHandler = mp.playerNetServerHandler;
		if(netHandler != null) {
			SocketAddress addr = netHandler.netManager.getSocketAddress();
			if (addr instanceof InetSocketAddress) {
				ip = ((InetSocketAddress) addr).getAddress().toString();
				ip = ip.substring(1);
			}
		}
		

		
		URL url;
		String country = "";
		String region = "";
		String city = "";
		
		//TEMP
		
		boolean isLocalIP = false;
		
		if (ip.startsWith("172.16.")) {
			isLocalIP = true;
			country = "Italy";
			region = "Province of Genoa";
			city = "Sori";
		}
		
		if (ip.startsWith("10.86.")) {
			isLocalIP = true;
			country = "Italy";
			region = "Province of Genoa";
			city = "Lumarzo";
		}
		
		if (ip.startsWith("10.87.")) {
			isLocalIP = true;
			country = "Italy";
			region = "Province of Udine";
			city = "Cividale";
		}
		
		if (ip.startsWith("127.0.")) {
			isLocalIP = true;
			country = "Zorya";
			region = "Regione Autonoma del Grande Est";
			city = "Slochd";
		}
		
		if(!isLocalIP) {
			try {
				url = new URL("http://172.16.20.220/luanet/servlets/geoip/?ip=" + ip);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("GET");
		
		        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String inputLine;
		        StringBuilder response = new StringBuilder();
		
		        while ((inputLine = in.readLine()) != null) {
		            response.append(inputLine);
		        }
		        in.close();
		        String[] result = response.toString().split(",");
		        city = result[0];
		        region = result[1];
		        country = result[2];
		        
		        String geoMessage = "\2473" + player.username + " comes from\247a";
		        if (city != "") {
		        	geoMessage = geoMessage + " " + city + ",";
		        }
		        
		        if (region != "") {
		        	geoMessage = geoMessage + " " + region + ",";
		        }
		        
		        geoMessage = geoMessage + " " + country;
		        
		        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(geoMessage);
		
			} catch (Exception e2) {
				Main.nphLog.severe("Problem with GeoIP handling");
				e2.printStackTrace();
			}
		} else {
			String geoMessage = "\2473" + player.username + " comes from\247a";
	        geoMessage = geoMessage + " " + city + ", " + region + ", " + country;
	        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(geoMessage);
		}
				
		File f = new File("./world/playerdata/NewPlayerHelper/" + player.username + ".txt");
		if (!f.exists() && !f.isDirectory()) {
			FileWriter writer;
			try {
				writer = new FileWriter("./world/playerdata/NewPlayerHelper/" + player.username + ".txt");
				try {
					ItemStack ticket = new ItemStack(Item.writtenBook);

					ticket.setTagInfo("author", new NBTTagString("author", "Ministero dell'Accettazione"));
					ticket.setTagInfo("title", new NBTTagString("title", "§r§cIstruzioni-Instructions§r"));
					NBTTagList pages = new NBTTagList();
					pages.appendTag(new NBTTagString("pag1", "Welcome to §lZorya!§r \nBenvenuti a §lZorya! \n\n§l§nENGLISH: Skip to page §l10§r.\n\nIl lungo viaggio a bordo della §oBorealis §r è concluso. E' ora di sbarcare e svolgere le formalità doganali.\n\nLeggi e conserva questo libro: ti sarà prezioso."));
					pages.appendTag(new NBTTagString("pag2", "§lImmigrazione§r\n\nAl §lvarco di frontiera §rtroverai un computer. Completando la procedura, il §oMinistero dell'Abbondanza§r ti regalerà l'essenziale per incominciare la tua nuova vita: §lcellulare, denaro, cibo e acqua§r."));
					pages.appendTag(new NBTTagString("pag2a", "§lTelefonia§r\n\nIl computer del §lvarco di frontiera §rti ha consegnato una §lSIM§r, oltre a §lcellulare§r e una §lbatteria§l. Impugnando il cellulare, fai §lshift+clic destro§r per aprire il retro del telefono e inserire SIM e batteria."));
					pages.appendTag(new NBTTagString("pag3", "§lSei un cittadino!§r\n\nOra che sei un §lzoryano§r a tutti gli effetti, sei libero di muoverti per tutto il territorio. Puoi andare in §lminiera§r a §lGrestin§r per recuperare qualche risorsa."));
					pages.appendTag(new NBTTagString("pag4", "§lDove alloggiare?§r\n\nPer §lfare casa§r, devi §lacquistare il terreno§r. Se non hai modo di farlo, puoi sempre stare in un §lhotel§r, oppure in una §lcabina§r della §oBorealis§r."));
					pages.appendTag(new NBTTagString("pag4a", "§lDenaro - 1§r\n\nPer §lguadagnare denaro§r, puoi vendere alcune risorse allo Stato o ad altri giocatori. Visita §omep.rgbcraft.com§r per un elenco di beni vendibili allo Stato, le loro quotazioni e l'indirizzo delle aziende che li comprano."));
					pages.appendTag(new NBTTagString("pag4b", "§lDenaro - 2§r\n\nPer §lpagare o ricevere denaro§r, puoi creare un account §lnPay§r in banca. Ti servirà nPay anche per ricaricare il §lcredito telefonico§r, per §lvendere beni§r allo Stato e per pagare le §lbollette§r e le §ltasse§r."));
					pages.appendTag(new NBTTagString("pag5", "§lTrasporti§r\n\nPuoi utilizzare la vasta §lrete ferroviaria§r (§orgr.rgbcraft.com§r), mentre a §lNew Radeon§r puoi usare la rete TCN (§otcn.rgbcraft.com§r). Naturalmente, puoi anche usare una §lbarca§r o, in futuro, la §oPowerArmor§r."));
					pages.appendTag(new NBTTagString("pag6", "§lAltre domande?§r\n\nRaggiungici sul nostro server §lDiscord§r: esegui il comando §o/discord§r per avere l'invito."));
					pages.appendTag(new NBTTagString("pag7", "Welcome to §lZorya!§r\n\nThe long journey aboard the §oBorealis§r is over. It's time to disembark and complete the customs formalities.\n\nRead and keep this book: it'll come in handy."));
					pages.appendTag(new NBTTagString("pag8", "§lImmigration§r\n\nAt the §lborder crossing§r you'll find a computer. By completing the procedure, the §oMinistry of Plentifulness§r will give you the essentials for your new life: §lcellphone, money, food and water§r."));
					pages.appendTag(new NBTTagString("pag8a", "§lCellphone usage§r\n\nThe §lborder crossing §rcomputer issued you a §lSIM§r as well as a §lcellphone§r and its §lbattery§l. To install the battery and SIM, §lshift+right click§r while the cellphone is selected."));
					pages.appendTag(new NBTTagString("pag9", "§lYou are a citizen!§r\n\nNow that you are a §lzoryan§r in every sense, you are free to move throughout the country. You can go to the §lmine§r at §lGrestin§r to get some resources."));
					pages.appendTag(new NBTTagString("pag10", "§lWhere to live?§r\n\nTo §lbuild a home§r, you must §lpurchase land§r. If you have no way to do so, you can always stay in a §lhotel§r, or in a §lcabin§r of the §oBorealis§r."));
					pages.appendTag(new NBTTagString("pag4a", "§lMoney - 1§r\n\nTo §earn money§r, you can sell goods to the State or to other players. Visit §omep.rgbcraft.com§r for a list of goods sellable to the State, their prices and the addresses of the companies that buy them."));
					pages.appendTag(new NBTTagString("pag4b", "§lMoney - 2§r\n\nTo §lpay or receive money§r, you can create an §lnPay§r account at the bank. You will also need nPay to top up your §lphone credit§r, to §lsell goods§r to the State and to pay §lbills§r and §ltaxes§r."));
					pages.appendTag(new NBTTagString("pag11", "§lTransport§r\n\nYou can use the extensive §lrail network§r (§orgr.rgbcraft.com§r), while in §lNew Radeon§r you can use the TCN network (§otcn.rgbcraft.com§r). You can also naturally use a §lboat§r or a §oPowerArmor§r once you can afford it."));
					pages.appendTag(new NBTTagString("pag12", "§lOther questions?§r\n\nGet in touch with us on our §lDiscord§r server: run the command §o/discord§r to get the invite."));
					ticket.setTagInfo("pages", pages);
					
					if (Item.itemsList[4258] != null) {
						ItemStack visaSlip = new ItemStack(Item.itemsList[4258]); //printed page ID=4258
						LocalDateTime date = LocalDateTime.now();
						DateTimeFormatter dateFmt = DateTimeFormatter.ISO_LOCAL_DATE;
					
						int maxLength = (player.username.length() < 18)?player.username.length():18;
						NBTTagCompound displayTag = new NBTTagCompound();
						displayTag.setString("Name", "§r§cDocumento d'ingresso");
						visaSlip.setTagInfo("display", displayTag);
						visaSlip.setTagInfo("line0", new NBTTagString("line0", "  REPUBBLICA SOCIALISTA  "));
						visaSlip.setTagInfo("line1", new NBTTagString("line1", "    FEDERALE SOVIETICA   "));
						visaSlip.setTagInfo("line2", new NBTTagString("line2", "        Z O R Y A        "));
						visaSlip.setTagInfo("line3", new NBTTagString("line3", "-------------------------"));
						visaSlip.setTagInfo("line4", new NBTTagString("line4", "  DOCUMENTO D'INGRESSO   "));
						visaSlip.setTagInfo("line5", new NBTTagString("line5", "-------------------------"));
						visaSlip.setTagInfo("line6", new NBTTagString("line6", "NOME: " + String.format("%1$19s", player.username.substring(0, maxLength))));
						visaSlip.setTagInfo("line7", new NBTTagString("line7", "PROVENIENZA:   Extraxenos"));
						if (country != "") {
							maxLength = (country.length() < 13)?country.length():13;
							visaSlip.setTagInfo("line8", new NBTTagString("line8", "DETTAGLIO: " + String.format("%1$14s", country.toUpperCase().substring(0, maxLength))));
						} else {
							visaSlip.setTagInfo("line8", new NBTTagString("line8", "DETTAGLIO:     =========="));
						}
						
						visaSlip.setTagInfo("line9", new NBTTagString("line9", "DATA INGR.:    " + date.format(dateFmt)));
						visaSlip.setTagInfo("line10", new NBTTagString("line10", "SCOPO:       IMMIGRAZIONE"));
						visaSlip.setTagInfo("line11", new NBTTagString("line11", "NUMERO DOC.: " + String.format("%1$12s", generateSlipNumber())));
						visaSlip.setTagInfo("line12", new NBTTagString("line12", "-------------------------"));
						visaSlip.setTagInfo("line13", new NBTTagString("line13", "Questo documento consente"));
						visaSlip.setTagInfo("line14", new NBTTagString("line14", "l'ingresso condizionato  "));
						visaSlip.setTagInfo("line15", new NBTTagString("line15", "alla R.S.F.S. Zorya al   "));
						visaSlip.setTagInfo("line16", new NBTTagString("line16", "titolare secondo i termi-"));
						visaSlip.setTagInfo("line17", new NBTTagString("line17", "ni previsti dalla legge. "));
						visaSlip.setTagInfo("line18", new NBTTagString("line18", "                         "));
						visaSlip.setTagInfo("line19", new NBTTagString("line19", "      Ministero dell'    "));
						visaSlip.setTagInfo("line20", new NBTTagString("line20", "       Accettazione      "));
						
						visaSlip.setTagInfo("colour0", new NBTTagString("colour0", "11111111111111111111111110000000000000000000000000"));
						visaSlip.setTagInfo("colour1", new NBTTagString("colour1", "11111111111111111111111110000000000000000000000000"));
						visaSlip.setTagInfo("colour2", new NBTTagString("colour2", "11111111111111111111111110000000000000000000000000"));
						visaSlip.setTagInfo("colour3", new NBTTagString("colour3", "00000000000000000000000000000000000000000000000000"));
						visaSlip.setTagInfo("colour4", new NBTTagString("colour4", "ccccccccccccccccccccccccc0000000000000000000000000"));
						visaSlip.setTagInfo("colour5", new NBTTagString("colour5", "00000000000000000000000000000000000000000000000000"));
						visaSlip.setTagInfo("colour6", new NBTTagString("colour6", "88888855555555555555555550000000000000000000000000"));
						visaSlip.setTagInfo("colour7", new NBTTagString("colour7", "88888888888855555555555550000000000000000000000000"));
						visaSlip.setTagInfo("colour8", new NBTTagString("colour8", "88888888888555555555555550000000000000000000000000"));
						visaSlip.setTagInfo("colour9", new NBTTagString("colour9", "88888888888555555555555550000000000000000000000000"));
						visaSlip.setTagInfo("colour10", new NBTTagString("colour10", "88888855555555555555555550000000000000000000000000"));
						visaSlip.setTagInfo("colour11", new NBTTagString("colour11", "88888888888855555555555550000000000000000000000000"));
						visaSlip.setTagInfo("colour12", new NBTTagString("colour12", "00000000000000000000000000000000000000000000000000"));
						visaSlip.setTagInfo("colour13", new NBTTagString("colour13", "77777777777777777777777770000000000000000000000000"));
						visaSlip.setTagInfo("colour14", new NBTTagString("colour14", "77777777777777777777777770000000000000000000000000"));
						visaSlip.setTagInfo("colour15", new NBTTagString("colour15", "77777777777777777777777770000000000000000000000000"));
						visaSlip.setTagInfo("colour16", new NBTTagString("colour16", "77777777777777777777777770000000000000000000000000"));
						visaSlip.setTagInfo("colour17", new NBTTagString("colour17", "77777777777777777777777770000000000000000000000000"));
						visaSlip.setTagInfo("colour18", new NBTTagString("colour18", "55555555555555555555555550000000000000000000000000"));
						visaSlip.setTagInfo("colour19", new NBTTagString("colour19", "77777777777777777777777770000000000000000000000000"));
						visaSlip.setTagInfo("colour20", new NBTTagString("colour20", "77777777777777777777777770000000000000000000000000"));
						
						if(player.inventory.addItemStackToInventory(visaSlip)) {
							Main.nphLog.info("Given visa slip to player");
						} else {
							Main.nphLog.severe("Could not give visa slip to player");
						}
					} else {
						Main.nphLog.severe("Could not find Printed Page (ID:4258)");
					}
					
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
	
	public static String generateSlipNumber() {
        StringBuilder sb = new StringBuilder(12);
        sb.append(randomLetter());
        sb.append(randomDigit());
        sb.append(randomLetter());
        sb.append(randomLetter());
        sb.append(randomLetter());
        sb.append('-');
        sb.append(randomLetter());
        sb.append(randomDigit());
        sb.append(randomLetter());
        sb.append(randomLetter());
        sb.append(randomLetter());
        return sb.toString();
    }

    private static char randomLetter() {
        return (char) ('A' + RAND.nextInt(26));
    }

    private static char randomDigit() {
        return (char) ('0' + RAND.nextInt(10));
    }
	
}