package erebus.network.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import erebus.ModBlocks;
import erebus.client.fx.EntityRepellentFX;
import erebus.network.AbstractClientPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.*;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PacketParticle extends AbstractClientPacket {

	public static enum ParticleType {
		BEETLE_LARVA_SQUISH,
		SPRAY_CAN,
		CRUSHROOM_BLAM,
		TARANTULA_BLAM,
		BOSS_DEATH,
		ANTLION_BLAM,
		ANTLION_RUMBLE,
		HAMMER_BLAM;

		static final ParticleType[] values = values();
	}

	private int entityId;
	private byte particleType;

	public PacketParticle() {
	}

	public PacketParticle(Entity entity, ParticleType particleType) {
		entityId = entity.getEntityId();
		this.particleType = (byte) particleType.ordinal();
	}

	@Override
	public void write(ByteBuf buffer) {
		buffer.writeInt(entityId).writeByte(particleType);
	}

	@Override
	public void read(ByteBuf buffer) {
		entityId = buffer.readInt();
		particleType = buffer.readByte();
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void handle(World world, EntityClientPlayerMP player) {
		if (particleType < 0 || particleType >= ParticleType.values.length)
			return;

		EffectRenderer eff = Minecraft.getMinecraft().effectRenderer;
		Entity e = player.worldObj.getEntityByID(entityId);

		switch (ParticleType.values[particleType]) {
			case BEETLE_LARVA_SQUISH:
				for (int count = 0; count <= 200; ++count)
					eff.addEffect(new EntityBreakingFX(player.worldObj, e.posX + (player.worldObj.rand.nextDouble() - 0.5D) * e.width, e.posY + player.worldObj.rand.nextDouble() * e.height - e.yOffset, e.posZ + (player.worldObj.rand.nextDouble() - 0.5D) * e.width, Items.slime_ball));
				break;
			case CRUSHROOM_BLAM:
				for (int a = 0; a < 360; a += 4) {
					double ang = a * Math.PI / 180D;
					eff.addEffect(new EntityRepellentFX(player.worldObj, e.posX + -MathHelper.sin((float) ang) * 3, e.posY + 0.1D, e.posZ + MathHelper.cos((float) ang) * 3, 0, 0, 0));
				}
				break;
			case TARANTULA_BLAM:
				for (int a = 0; a < 360; a += 4) {
					double ang = a * Math.PI / 180D;
					eff.addEffect(new EntityCloudFX(player.worldObj, e.posX + -MathHelper.sin((float) ang) * 3, e.posY, e.posZ + MathHelper.cos((float) ang) * 3, -MathHelper.sin((float) ang) * 0.5, 0.1D, MathHelper.cos((float) ang) * 0.5));
				}
				break;
			case ANTLION_BLAM:
				for (int a = 0; a < 360; a += 4) {
					double ang = a * Math.PI / 180D;
					for (int count = 0; count <= 20; ++count)
						eff.addEffect(new EntityDiggingFX(player.worldObj, e.posX + -MathHelper.sin((float) ang) * 3.5D, e.posY + 0.5D, e.posZ + MathHelper.cos((float) ang) * 3.5D, -MathHelper.sin((float) ang) * 0.8, 0.0D, MathHelper.cos((float) ang) * 0.8, ModBlocks.ghostSand, 0));
					eff.addEffect(new EntityCloudFX(player.worldObj, e.posX + -MathHelper.sin((float) ang) * 4.5, e.posY, e.posZ + MathHelper.cos((float) ang) * 4.5, -MathHelper.sin((float) ang) * 1, 0.1D, MathHelper.cos((float) ang) * 1));
				}
				break;
			case BOSS_DEATH:
				float f = (e.worldObj.rand.nextFloat() - 0.5F) * 8.0F;
				float f1 = (e.worldObj.rand.nextFloat() - 0.5F) * 4.0F;
				float f2 = (e.worldObj.rand.nextFloat() - 0.5F) * 8.0F;
				eff.addEffect(new EntityHugeExplodeFX(player.worldObj, e.posX + f, e.posY + 2.0D + f1, e.posZ + f2, 0.0D, 0.0D, 0.0D));
				break;

			case ANTLION_RUMBLE:
				for (int a = 0; a < 360; a += 4) {
					double ang = a * Math.PI / 180D;
					eff.addEffect(new EntityDiggingFX(player.worldObj, e.posX + -MathHelper.sin((float) ang) * 3.5D, e.posY + 0.125D, e.posZ + MathHelper.cos((float) ang) * 3.5D, -MathHelper.sin((float) ang) * 0.8, 0.3D, MathHelper.cos((float) ang) * 0.8, ModBlocks.ghostSand, 0));
				}
				break;
			case HAMMER_BLAM:
				for (int a = 0; a < 360; a += 4) {
					double ang = a * Math.PI / 180D;
					for (int count = 0; count <= 4; ++count)
						eff.addEffect(new EntityFireworkSparkFX(player.worldObj, e.posX + -MathHelper.sin((float) ang) * 1 * count * 0.5, e.posY - 1, e.posZ + MathHelper.cos((float) ang) * 1 * count * 0.5, -MathHelper.sin((float) ang) * 0.5, 0.01D, MathHelper.cos((float) ang) * 0.5, eff));
					eff.addEffect(new EntityCloudFX(player.worldObj, e.posX + -MathHelper.sin((float) ang) * 2, e.posY - 1, e.posZ + MathHelper.cos((float) ang) * 2, -MathHelper.sin((float) ang) * 0.5, 0.01D, MathHelper.cos((float) ang) * 0.5));
				}
				break;
			default:
				;
		}
	}
}