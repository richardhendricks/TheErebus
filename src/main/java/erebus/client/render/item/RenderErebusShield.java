package erebus.client.render.item;

import erebus.ModItems;
import erebus.items.ItemMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderErebusShield extends TileEntitySpecialRenderer<TileEntity> {
	ItemStack stack = new ItemStack(ModItems.MATERIALS, 1, ItemMaterials.EnumErebusMaterialsType.PLATE_EXO.ordinal());
    private ModelShield MODEL_SHIELD = new ModelShield() { //TODO Will add some custom thing here
    @Override
    public void render() {
    	plate.isHidden = true;
    	handle.render(0.0625F);
    }
    };
    
    public Shieldtype type;

    public RenderErebusShield(Shieldtype type) {
        this.type = type;
    }

    @Override
    public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(type.resloc);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        this.MODEL_SHIELD.render();
        GlStateManager.popMatrix();
        renderItem();
    }
    
    private void renderItem() {
        RenderHelper.enableStandardItemLighting();
		GlStateManager.enableLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0D, 0.0625D, 0.1D);
		GlStateManager.scale(1.25f, 1.25f, 1.25f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, (World) null, (EntityLivingBase) null));
		GlStateManager.popMatrix();
    }

    public enum Shieldtype{
        BAMBOO(new ResourceLocation("erebus", "textures/items/bamboo_shield.png")), //TODO make textures
        EXO_PLATE(new ResourceLocation("erebus", "textures/items/exo_plate_shield.png")),
        JADE(new ResourceLocation("erebus", "textures/items/jade_shield.png")),
        REIN_EXO(new ResourceLocation("erebus", "textures/items/rein_exo_shield.png")),
        RHINO_EXO(new ResourceLocation("erebus", "textures/items/rhino_exo_shield.png"));

        public ResourceLocation resloc;
        Shieldtype(ResourceLocation loc){
            this.resloc = loc;
        }

    }
}