package thebetweenlands.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import scala.util.Random;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.client.model.block.ModelBlackHatMushroom2;
import thebetweenlands.proxy.ClientProxy.BlockRenderIDs;
import thebetweenlands.utils.ModelConverter;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockModelPlantRenderer implements ISimpleBlockRenderingHandler {
	public static ModelConverter plantModel;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glDisable(GL11.GL_LIGHTING);
		Tessellator.instance.setColorOpaque(255, 255, 255);
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.theWorld != null && mc.thePlayer != null) {
			tessellator.setBrightness(mc.theWorld.getLightBrightnessForSkyBlocks(
					(int)(mc.thePlayer.posX), (int)(mc.thePlayer.posY), (int)(mc.thePlayer.posZ), 0));
		}

		Tessellator.instance.addTranslation(0.5F, 1.5F, 0.5F);

		tessellator.startDrawingQuads();

		if(plantModel == null) {
			plantModel = new ModelConverter(
					new ModelBlackHatMushroom2(),
					0.065D,
					64.0D, 64.0D,
					BLBlockRegistry.modelPlant.modelTexture,
					true);
		}
		plantModel.renderWithTessellator(Tessellator.instance);

		tessellator.draw();

		Tessellator.instance.addTranslation(-0.5F, -1.5F, -0.5F);

		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		Tessellator.instance.setColorOpaque(255, 255, 255);
		Tessellator.instance.addTranslation(x + 0.5F, y + 1.6F, z + 0.5F);

		//if(plantModel == null) {
		Random rnd = new Random();
		rnd.setSeed(x * y * z);
		plantModel = new ModelConverter(
				new ModelBlackHatMushroom2(),
				0.065D,
				64.0D, 64.0D,
				BLBlockRegistry.modelPlant.modelTexture,
				true, 
				rnd.nextFloat() * 180, 
				rnd.nextFloat() * 40 - 20);
		//}
		plantModel.renderWithTessellator(Tessellator.instance);

		Tessellator.instance.addTranslation(-x - 0.5F, -y - 1.6F, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockRenderIDs.MODEL_PLANT.id();
	}
}