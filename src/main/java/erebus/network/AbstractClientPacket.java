package erebus.network;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class AbstractClientPacket extends AbstractPacket {

	@Override
	public void handle(Side side, World world, EntityPlayer player) {
		if (side == Side.CLIENT)
			handle(world, (EntityClientPlayerMP) player);
		else
			throw new UnsupportedOperationException("Tried to handle client packet on server side!");
	}

	@SideOnly(Side.CLIENT)
	protected abstract void handle(World world, EntityClientPlayerMP player);
}