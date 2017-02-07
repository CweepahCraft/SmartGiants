package me.jjm_223.smartgiants.entities.v1_11_R1;

import me.jjm_223.smartgiants.api.util.ILoad;
import me.jjm_223.smartgiants.api.util.ReflectionUtils;
import me.jjm_223.smartgiants.entities.v1_11_R1.nms.SmartGiant;
import me.jjm_223.smartgiants.entities.v1_11_R1.nms.SmartGiantHostile;
import net.minecraft.server.v1_11_R1.EntityGiantZombie;
import net.minecraft.server.v1_11_R1.EntityTypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Load implements ILoad
{
    // EntityTypes.class methods
    private Method et_m_a;

    public Load()
    {
        try
        {
            et_m_a = EntityTypes.class.getDeclaredMethod("a", int.class, String.class, Class.class, String.class);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void load(boolean hostile)
    {
        try
        {
            accessFields();
            et_m_a.invoke(null, 53, "giant", hostile ? SmartGiantHostile.class : SmartGiant.class, "Giant");
        }
        catch (InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanup()
    {
        try
        {
            accessFields();
            et_m_a.invoke(null, 53, "giant", EntityGiantZombie.class, "Giant");
        }
        catch (InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private void accessFields()
    {
        ReflectionUtils.setAccessible(et_m_a);
    }
}
