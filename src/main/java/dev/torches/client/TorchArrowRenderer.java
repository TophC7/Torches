package dev.torches.client;

import dev.torches.TorchArrowEntity;
import dev.torches.TorchesMod;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TorchArrowRenderer extends ArrowRenderer<TorchArrowEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(TorchesMod.MOD_ID, "textures/entity/projectiles/torch_arrow.png");

    public TorchArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(TorchArrowEntity entity) {
        return TEXTURE;
    }
}
