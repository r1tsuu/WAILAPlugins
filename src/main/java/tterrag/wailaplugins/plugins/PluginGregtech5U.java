package tterrag.wailaplugins.plugins;

import com.enderio.core.common.util.BlockCoord;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Transformer;
import lombok.SneakyThrows;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tterrag.wailaplugins.api.Plugin;
import static mcp.mobius.waila.api.SpecialChars.RESET;
import static mcp.mobius.waila.api.SpecialChars.GOLD;
import static mcp.mobius.waila.api.SpecialChars.BLUE;
import static mcp.mobius.waila.api.SpecialChars.GREEN;
import static mcp.mobius.waila.api.SpecialChars.RED;

import java.util.List;

@Plugin(name = "Gregtech5U", deps = "gregtech")
public class PluginGregtech5U extends PluginBase
{

    @Override
    public void load(IWailaRegistrar registrar)
    {
        super.load(registrar);

        addConfig("machineFacing");
        addConfig("transformer");
        registerBody(BaseTileEntity.class);
        registerNBT(BaseTileEntity.class);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unused")
    protected void getBody(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor)
    {
        final TileEntity tile = accessor.getTileEntity();
        MovingObjectPosition pos = accessor.getPosition();
        NBTTagCompound tag = accessor.getNBTData();
        final int side = (byte)accessor.getSide().ordinal();

        final IGregTechTileEntity tBaseMetaTile = tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile) : null;
        final IMetaTileEntity tMeta = tBaseMetaTile != null ? tBaseMetaTile.getMetaTileEntity() : null;
        final BaseMetaTileEntity mBaseMetaTileEntity = tile instanceof  BaseMetaTileEntity ? ((BaseMetaTileEntity) tile) : null;
        final boolean showTransformer = tMeta instanceof GT_MetaTileEntity_Transformer && getConfig("transformer");
        final boolean allowedToWork = tag.hasKey("isAllowedToWork") && tag.getBoolean("isAllowedToWork");

        if (tMeta != null) {
            String facingStr = "Facing";
            if (showTransformer && tag.hasKey("isAllowedToWork")) {
                final GT_MetaTileEntity_Transformer transformer = (GT_MetaTileEntity_Transformer)tMeta;
                currenttip.add(
                    String.format(
                        "%s %d(%dA) -> %d(%dA)",
                        (allowedToWork ? (GREEN + "Step Down") : (RED + "Step Up")) + RESET,
                        tag.getLong("maxEUInput"),
                        tag.getLong("maxAmperesIn"),
                        tag.getLong("maxEUOutput"),
                        tag.getLong("maxAmperesOut")
                    )
                );
                facingStr = tag.getBoolean("isAllowedToWork") ? "Input" : "Output";
            }
            if (mBaseMetaTileEntity != null && getConfig("machineFacing")) {
                final int facing = mBaseMetaTileEntity.getFrontFacing();
                if(showTransformer) {
                    if((side == facing && allowedToWork) || (side != facing && !allowedToWork)) {
                        currenttip.add(String.format(GOLD + "Input:" + RESET + " %d(%dA)", tag.getLong("maxEUInput"), tag.getLong("maxAmperesIn")));
                    } else {
                        currenttip.add(String.format(BLUE + "Output:" + RESET + " %d(%dA)", tag.getLong("maxEUOutput"), tag.getLong("maxAmperesOut")));
                    }
                } else {
                    currenttip.add(String.format("%s: %s", facingStr, ForgeDirection.getOrientation(facing).name()));
                }
            }
        }

    }


    @Override
    @SneakyThrows
    protected void getNBTData(TileEntity tile, NBTTagCompound tag, World world, BlockCoord pos)
    {
        final IGregTechTileEntity tBaseMetaTile = tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile) : null;
        final IMetaTileEntity tMeta = tBaseMetaTile != null ? tBaseMetaTile.getMetaTileEntity() : null;

        if (tMeta != null) {
            if (tMeta instanceof GT_MetaTileEntity_Transformer) {
                final GT_MetaTileEntity_Transformer transformer = (GT_MetaTileEntity_Transformer)tMeta;
                tag.setBoolean("isAllowedToWork", tMeta.getBaseMetaTileEntity().isAllowedToWork());
                tag.setLong("maxEUInput", transformer.maxEUInput());
                tag.setLong("maxAmperesIn", transformer.maxAmperesIn());
                tag.setLong("maxEUOutput", transformer.maxEUOutput());
                tag.setLong("maxAmperesOut", transformer.maxAmperesOut());
            }
        }

        tile.writeToNBT(tag);
    }
}
