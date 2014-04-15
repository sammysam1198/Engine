package calclavia.lib.prefab;

import calclavia.lib.prefab.damage.ElectricalDamage;
import calclavia.lib.prefab.damage.ObjectDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import universalelectricity.api.UniversalElectricity;
import universalelectricity.api.electricity.IElectricalNetwork;

/** Extend this class to create more custom damage sources.
 * 
 * @author Calclavia */
public class CustomDamageSource extends DamageSource
{
    /** Use this damage source for all types of electrical attacks. */
    public static ElectricalDamage electrocution = new ElectricalDamage(null);

    protected Object damageSource;

    public CustomDamageSource(String damageType)
    {
        super(damageType);
    }

    public CustomDamageSource(String damageType, Object source)
    {
        this(damageType);
        this.damageSource = source;
    }

    @Override
    public CustomDamageSource setDamageBypassesArmor()
    {
        super.setDamageBypassesArmor();
        return this;
    }

    @Override
    public CustomDamageSource setDamageAllowedInCreativeMode()
    {
        super.setDamageAllowedInCreativeMode();
        return this;
    }

    @Override
    public CustomDamageSource setFireDamage()
    {
       super.setFireDamage();
       return this;
    }
    
    @Override
    public CustomDamageSource setProjectile()
    {
        super.setProjectile();
        return this;
    }

    @Override
    public Entity getEntity()
    {
        return damageSource instanceof Entity ? ((Entity) damageSource) : null;
    }

    public TileEntity getTileEntity()
    {
        return damageSource instanceof TileEntity ? ((TileEntity) damageSource) : null;
    }

    public Object attacker()
    {
        return damageSource;
    }

}
