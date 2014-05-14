package resonant.lib.render.fx;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import resonant.core.ResonantEngine;
import universalelectricity.api.vector.IVector3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FxLaser extends FxBeam
{
    public FxLaser(World world, IVector3 position, IVector3 target, float red, float green, float blue, int age)
    {
        super(new ResourceLocation(ResonantEngine.DOMAIN, ResonantEngine.TEXTURE_PATH + "laser.png"), world, position, target, red, green, blue, age);
    }
}