package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFProtectionBox;
import twilightforest.entity.EntityTFProtectionBox;

public class RenderTFProtectionBox<T extends EntityTFProtectionBox> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("protectionbox.png");
	private final ModelTFProtectionBox boxModel = new ModelTFProtectionBox();

	public RenderTFProtectionBox(EntityRendererManager manager) {
		super(manager);
		this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(EntityTFProtectionBox entity, double x, double y, double z, float yaw, float partialTicks) {
		GlStateManager.pushMatrix();

		GlStateManager.translatef((float) x, (float) y, (float) z);

		this.bindTexture(textureLoc);

		// move texture
		float f1 = (float) entity.ticksExisted + partialTicks;
		GlStateManager.matrixMode(GL11.GL_TEXTURE);
		GlStateManager.loadIdentity();
		float f2 = f1 * 0.05F;
		float f3 = f1 * 0.05F;
		GlStateManager.translatef(f2, f3, 0.0F);
		GlStateManager.scalef(1.0f, 1.0f, 1.0f);

		GlStateManager.matrixMode(GL11.GL_MODELVIEW);

		// enable transparency, go full brightness
		GlStateManager.colorMask(true, true, true, true);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableAlpha();
		GlStateManager.disableLighting();

		float alpha = 1.0F;
		if (entity.lifeTime < 20) {
			alpha = entity.lifeTime / 20F;
		}

		GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);

		boxModel.render(entity, 0F, 0F, 0F, 0F, 0F, 1F / 16F);

		GlStateManager.disableBlend();
		GlStateManager.enableCull();
		GlStateManager.enableAlpha();
		GlStateManager.enableLighting();

		GlStateManager.matrixMode(GL11.GL_TEXTURE);
		GlStateManager.loadIdentity();
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);

		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}

}
