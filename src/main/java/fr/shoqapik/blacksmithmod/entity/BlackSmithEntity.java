package fr.shoqapik.blacksmithmod.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
public class BlackSmithEntity extends Mob {

    public BlackSmithEntity(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Override
    protected InteractionResult mobInteract(Player p_21472_, InteractionHand p_21473_) {
        if(!p_21472_.isLocalPlayer()) {

            /*if (p_21472_.getItemInHand(p_21473_).getItem() == NpcItems.NPC_WRENCH.get()) {
                if (p_21472_ instanceof ServerPlayer && p_21472_.hasPermissions(2) && p_21472_.isCreative()) {
                    NewNpcMod.sendToClient(new ShowNpcEditScreenPacket(this.getUUID(), this.getConfObject()), (ServerPlayer) p_21472_);
                }
                return InteractionResult.SUCCESS;
            } else if (getConfObject().getDialogs() != null && !getConfObject().getDialogs().isEmpty() && getConfObject().getQuest() != null && getConfObject().getQuest() != null && !getConfObject().getQuest().isEmpty()) {
                if(p_21473_ == InteractionHand.MAIN_HAND) {
                    if (PlayerQuestHelper.isQuestCompleted(getConfObject().getNpcName(), getConfObject().getQuest(), PlayerQuestHelper.getQuestDatas(p_21472_, getConfObject().getNpcName()))) {
                        PlayerQuestHelper.rewardPlayer(getConfObject().getNpcName(), p_21472_);
                    } else {
                        NewNpcMod.sendToClient(new NpcSendQuestPacket(getConfObject().getNpcName(), !PlayerQuestHelper.hasQuest(p_21472_, getConfObject().getNpcName()), getConfObject().getDialogs(), PlayerQuestHelper.getQuestDatas(p_21472_, getConfObject().getNpcName()), getConfObject().getQuest()), (ServerPlayer) p_21472_);
                    }
                }
                return InteractionResult.SUCCESS;
            }*/
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    @Override
    public boolean isPersistenceRequired() {
        return true;
    }
    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_) {
        return p_20122_ != DamageSource.OUT_OF_WORLD;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }


    public static AttributeSupplier.Builder getBlacksmithAttributes() {
        return Mob.createMobAttributes().add(ForgeMod.ENTITY_GRAVITY.get(), 1.5f).add(Attributes.MAX_HEALTH, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.7246D);
    }

}
