package me.jjm_223.smartgiants.entities.v1_11_R1;

import me.jjm_223.smartgiants.api.util.ILoad;
import me.jjm_223.smartgiants.api.util.ReflectionUtils;
import me.jjm_223.smartgiants.entities.v1_11_R1.nms.SmartGiant;
import me.jjm_223.smartgiants.entities.v1_11_R1.nms.SmartGiantHostile;
import net.minecraft.server.v1_11_R1.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Load implements ILoad
{
    // EntityTypes.class fields
    private Field et_b;
    private Field et_d;
    private Field et_g;

    // RegistryMaterials.class fields
    private Field rm_a;

    // RegistrySimple.class fields
    private Field rs_c;

    // RegistryID.class fields
    private Field ri_b;
    private Field ri_c;

    // EntityTypes.class methods
    private Method et_m_a;

    // RegistryID.class methods
    private Method ri_m_d;

    private boolean hostile;

    public Load()
    {
        try
        {
            et_b = EntityTypes.class.getDeclaredField("b");
            et_d = EntityTypes.class.getDeclaredField("d");
            et_g = EntityTypes.class.getDeclaredField("g");

            rm_a = RegistryMaterials.class.getDeclaredField("a");

            rs_c = RegistrySimple.class.getDeclaredField("c");

            ri_b = RegistryID.class.getDeclaredField("b");
            ri_c = RegistryID.class.getDeclaredField("c");

            et_m_a = EntityTypes.class.getDeclaredMethod("a", int.class, String.class, Class.class, String.class);

            ri_m_d = RegistryID.class.getDeclaredMethod("d", int.class);
        }
        catch (NoSuchMethodException | NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void load(boolean hostile)
    {
        this.hostile = hostile;
        try
        {
            removeGiant(EntityGiantZombie.class);
            et_m_a.invoke(null, 53, "giant", hostile ? SmartGiantHostile.class : SmartGiant.class, "Giant");
        }
        catch (InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void removeGiant(Class clazz) throws IllegalAccessException, InvocationTargetException
    {
        accessFields();

        MinecraftKey key = new MinecraftKey("giant");

        RegistryMaterials et_bObj = (RegistryMaterials) et_b.get(null);

        Map rs_cObj = (Map) rs_c.get(et_bObj);
        rs_cObj.remove(key);

        RegistryID rm_aObj = (RegistryID) rm_a.get(et_bObj);
        Object[] ri_bObj = (Object[]) ri_b.get(rm_aObj);
        int[] ri_cObj = (int[]) ri_c.get(rm_aObj);

        int index = -1;
        for (int x = 0; x < ri_bObj.length; x++)
        {
            if (ri_bObj[x] == clazz)
            {
                index = x;
                break;
            }
        }

        if (index == -1)
        {
            throw new IllegalStateException("Giant cannot be removed - not found");
        }

        ri_bObj[index] = null;
        ri_cObj[index] = 0;

        ri_m_d.invoke(rm_aObj, ri_bObj.length);

        List et_gObj = (List) et_g.get(null);
        et_gObj.set(53, null);

        Set et_dObj = (Set) et_d.get(null);
        et_dObj.remove(key);
    }

    @Override
    public void cleanup()
    {
        if (!MinecraftServer.getServer().isStopped())
        {
            try
            {
                accessFields();
                removeGiant(hostile ? SmartGiantHostile.class : SmartGiant.class);
                et_m_a.invoke(null, 53, "giant", EntityGiantZombie.class, "Giant");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void accessFields()
    {
        ReflectionUtils.setAccessible(et_m_a, ri_m_d, et_b, et_d, et_g, rm_a, rs_c, ri_b, ri_c);
    }
}
