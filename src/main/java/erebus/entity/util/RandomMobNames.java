package erebus.entity.util;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import erebus.entity.*;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import java.util.HashMap;
import java.util.Map;

public final class RandomMobNames {

	public static final RandomMobNames instance = new RandomMobNames();

	private static final Map<Class<? extends EntityLiving>, NameData> names = new HashMap<Class<? extends EntityLiving>, NameData>();

	static {
		names.put(EntityBotFly.class, new NameData(380, new String[] { "Butt Fly" }));
		names.put(EntityGrasshopper.class, new NameData(360, new String[] { "Grasshumper", "Jimminey" }));
		names.put(EntityBeetleLarva.class, new NameData(320, new String[] { "Trampoline", "Dylan4ever" }));
		names.put(EntityBeetle.class, new NameData(280, new String[] { "John Lennon", "Paul McCartney", "George Harisson", "Ringo Starr" }));
		names.put(EntityCentipede.class, new NameData(220, new String[] { "Centipaedo" }));
		names.put(EntitySolifuge.class, new NameData(220, new String[] { "Fast & Furious" }));
		names.put(EntityScorpion.class, new NameData(150, new String[] { "Nippletwister" }));
		names.put(EntityPunchroom.class, new NameData(150, new String[] { "Bryuf" }));
	};

	@SubscribeEvent
	public void onLivingSpawn(LivingSpawnEvent e) {
		EntityLiving entity = (EntityLiving) e.entityLiving;

		NameData data = names.get(entity.getClass());

		if (data != null && entity.getRNG().nextInt(data.diceSides) == 0 && !entity.hasCustomNameTag())
			entity.setCustomNameTag(data.nameList[entity.getRNG().nextInt(data.nameList.length)]);
	}

	static final class NameData {
		final short diceSides;
		final String[] nameList;

		NameData(int diceSides, String[] nameList) {
			this.diceSides = (short) diceSides;
			this.nameList = nameList;
		}
	}
}
