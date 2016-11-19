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
    private Method a;

    public Load()
    {
        try
        {
            a = EntityTypes.class.getDeclaredMethod("a", int.class, String.class, Class.class, String.class);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void load(boolean isHostile)
    {
        try
        {
            ReflectionUtils.setAccessible(a);

            if (isHostile)
            {
                a.invoke(null, 53, "giant", SmartGiantHostile.class, "Giant");
            }
            else
            {
                a.invoke(null, 53, "giant", SmartGiant.class, "Giant");
            }
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
            ReflectionUtils.setAccessible(a);

            a.invoke(null, 53, "giant", EntityGiantZombie.class, "Giant");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
