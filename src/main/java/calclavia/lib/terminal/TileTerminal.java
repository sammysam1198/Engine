package calclavia.lib.terminal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;
import calclavia.lib.network.IPacketReceiver;
import calclavia.lib.network.IPacketSender;
import calclavia.lib.network.PacketHandler;
import calclavia.lib.prefab.tile.IPlayerUsing;
import calclavia.lib.prefab.tile.TileElectrical;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/** @author Calclavia, DarkGuardsman */
public abstract class TileTerminal extends TileElectrical implements ITerminal, IScroll, IPacketReceiver, IPacketSender, IPlayerUsing
{
	public final HashSet<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();

	/** A list of everything typed inside the terminal */
	private final List<String> terminalOutput = new ArrayList<String>();

	/** The amount of lines the terminal can store. */
	public static final int SCROLL_SIZE = 15;

	/** Used on client side to determine the scroll of the terminal. */
	private int scroll = 0;

	/**
	 * Packet Code
	 */

	public abstract Packet getTerminalPacket();

	public abstract Packet getCommandPacket(String username, String cmdInput);

	/** Sends all Terminal data Server -> Client */
	public void sendTerminalOutputToClients()
	{
		Packet packet = getTerminalPacket();

		for (EntityPlayer player : this.getPlayersUsing())
		{
			PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
		}
	}

	/** Send a terminal command Client -> server */
	public void sendCommandToServer(EntityPlayer entityPlayer, String cmdInput)
	{
		if (this.worldObj.isRemote)
		{
			PacketDispatcher.sendPacketToServer(getCommandPacket(entityPlayer.username, cmdInput));
		}
	}

	@Override
	public ArrayList getPacketData(int type)
	{
		ArrayList data = new ArrayList();
		data.add(type);

		switch (type)
		{
			case 0:
			{
				// Server: Description
				NBTTagCompound nbt = new NBTTagCompound();
				this.writeToNBT(nbt);
				data.add(nbt);
				break;
			}
			case 1:
			{
				// Server: Terminal Packet
				data.add(this.getTerminalOuput().size());
				data.addAll(this.getTerminalOuput());
				break;
			}
			case 2:
			{
				// Client: Command Packet
				break;
			}
		}

		return data;
	}

	@Override
	public void onReceivePacket(ByteArrayDataInput data, EntityPlayer player, Object... extra)
	{

		try
		{
			switch (data.readInt())
			{
				case 1:
				{
					// Server: Description
					int size = data.readInt();

					List<String> oldTerminalOutput = new ArrayList(this.terminalOutput);
					this.terminalOutput.clear();

					for (int i = 0; i < size; i++)
					{
						this.terminalOutput.add(data.readUTF());
					}

					if (!this.terminalOutput.equals(oldTerminalOutput) && this.terminalOutput.size() != oldTerminalOutput.size())
					{
						this.setScroll(this.getTerminalOuput().size() - SCROLL_SIZE);
					}
					break;
				}
				case 2:
				{
					// Client: Command Packet
					CommandRegistry.onCommand(this.worldObj.getPlayerEntityByName(data.readUTF()), this, data.readUTF());
					this.sendTerminalOutputToClients();
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getTerminalOuput()
	{
		return this.terminalOutput;
	}

	@Override
	public boolean addToConsole(String msg)
	{
		if (!this.worldObj.isRemote)
		{
			int usedLines = 0;

			msg.trim();
			if (msg.length() > 23)
			{
				msg = msg.substring(0, 22);
			}

			this.getTerminalOuput().add(msg);
			this.sendTerminalOutputToClients();
			return true;
		}

		return false;
	}

	@Override
	public void scroll(int amount)
	{
		this.setScroll(this.scroll + amount);
	}

	@Override
	public void setScroll(int length)
	{
		this.scroll = Math.max(Math.min(length, this.getTerminalOuput().size()), 0);
	}

	@Override
	public int getScroll()
	{
		return this.scroll;
	}

	@Override
	public boolean canUse(String node, EntityPlayer player)
	{
		return false;
	}

	@Override
	public HashSet<EntityPlayer> getPlayersUsing()
	{
		return this.playersUsing;
	}

}
