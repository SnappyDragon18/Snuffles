package mod.schnappdragon.snuffles.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.schnappdragon.snuffles.common.entity.animal.Snuffle;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class SnuffleModel<T extends Snuffle> extends EntityModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tongue;
    private final ModelPart extra;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;

    public SnuffleModel(ModelPart part) {
        this.root = part;
        this.body = part.getChild("body");
        this.tongue = this.body.getChild("tongue");
        this.extra = part.getChild("extra");
        this.rightFrontLeg = part.getChild("right_front_leg");
        this.leftFrontLeg = part.getChild("left_front_leg");
        this.rightHindLeg = part.getChild("right_hind_leg");
        this.leftHindLeg = part.getChild("left_hind_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -0.0436F, 0.0F, 0.0F));
        body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 25).addBox(-9.0F, -6.0F, -10.0F, 18.0F, 11.0F, 20.0F), PartPose.offset(0.0F, -10.0F, 0.0F));
        body.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(48, 0).addBox(-6.0F, 0.0F, -7.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -6.0F, -10.0F, 0.2618F, 0.0F, 0.0F));
        PartDefinition extra = partdefinition.addOrReplaceChild("extra", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -0.0436F, 0.0F, 0.0F));
        extra.addOrReplaceChild("fluff", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, 5.0F, -10.0F, 18.0F, 5.0F, 20.0F), PartPose.offset(0.0F, -10.0F, 0.0F));
        extra.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(102, 0).addBox(-3.0F, -7.0F, -6.0F, 3.0F, 8.0F, 8.0F), PartPose.offsetAndRotation(-9.0F, -14.0F, -6.0F, 0.0873F, 0.0F, 0.0F));
        extra.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(80, 0).addBox(0.0F, -7.0F, -6.0F, 3.0F, 8.0F, 8.0F), PartPose.offsetAndRotation(9.0F, -14.0F, -6.0F, 0.0873F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 7.0F, 5.0F), PartPose.offsetAndRotation(-4.5F, 17.0F, -5.5F, 0.0F, 0.1309F, 0.0F));
        partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 7.0F, 5.0F), PartPose.offsetAndRotation(4.5F, 17.0F, -5.5F, 0.0F, -0.1309F, 0.0F));
        partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 7.0F, 5.0F), PartPose.offsetAndRotation(-4.5F, 17.0F, 5.5F, 0.0F, 0.1309F, 0.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 7.0F, 5.0F), PartPose.offsetAndRotation(4.5F, 17.0F, 5.5F, 0.0F, -0.1309F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.young) {
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0.0D, 1.5F, 0.0D);
            ImmutableList.of(this.body, this.rightFrontLeg, this.leftFrontLeg, this.rightHindLeg, this.leftHindLeg).forEach((part) -> {
                part.render(poseStack, consumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            poseStack.popPose();
        }
        else {
            ImmutableList.of(this.body, this.extra, this.rightFrontLeg, this.leftFrontLeg, this.rightHindLeg, this.leftHindLeg).forEach((part) -> {
                part.render(poseStack, consumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
        }
    }

    public void setupAnim(Snuffle snuffle, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.yRot = netHeadYaw * ((float)Math.PI / 180F);

        this.body.zRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.1F * limbSwingAmount;
        this.extra.zRot = this.body.zRot;

        this.tongue.xRot = 0.2618F + Mth.sin(ageInTicks * 0.067F) * 0.16F;

        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = this.leftHindLeg.xRot;
        this.leftFrontLeg.xRot = this.rightHindLeg.xRot;
    }
}