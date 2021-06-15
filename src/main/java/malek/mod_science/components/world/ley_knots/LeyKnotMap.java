package malek.mod_science.components.world.ley_knots;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import static malek.mod_science.ModScience.MOD_ID;

public class LeyKnotMap implements LeyKnotMapInterface, WorldComponentInitializer, AutoSyncedComponent {
    private Stack<BlockPos> leyKnots = new Stack<>();
    public static final ComponentKey<LeyKnotMap> LEY_KNOT_MAP =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "ley_knot_map"), LeyKnotMap.class);


    public static Stack<BlockPos> get(World world) {
        return LEY_KNOT_MAP.get(world).leyKnots;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        leyKnots = new Stack<>();
        NbtList list = tag.getList("ley_knot_map", NbtType.COMPOUND);
        NbtCompound[] stuff = (NbtCompound[]) list.stream().filter(NbtCompound.class::isInstance).map(NbtCompound.class::cast).toArray();
        for(NbtCompound compoundTag : stuff) {
            BlockPos blockPos = BlockPos.CODEC.decode(NbtOps.INSTANCE, compoundTag.get("block_pos")).getOrThrow(false, (string) -> {}).getFirst();
            leyKnots.add(blockPos);
        }
    }

    /**
     * Writes this component's properties to a {@link }.
     *
     * @param tag a {@code NbtTag} on which to write this component's serializable data
     */
    @Override
    public void writeToNbt(NbtCompound tag) {
        NbtList listTag;
        listTag = leyKnots.stream().parallel().map(blockPos -> {
            NbtCompound mappingTag = new NbtCompound();
           mappingTag.put("block_pos", BlockPos.CODEC.encode(blockPos, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).getOrThrow(false, (string) -> {}));
            return mappingTag;
        }).collect(Collectors.toCollection(NbtList::new));
        tag.put("ley_knot_map", listTag);

    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(LEY_KNOT_MAP, world -> new LeyKnotMap());
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return true;
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        AutoSyncedComponent.super.writeSyncPacket(buf, recipient);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        AutoSyncedComponent.super.applySyncPacket(buf);
    }
}
