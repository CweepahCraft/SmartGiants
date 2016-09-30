package me.jjm_223.smartgiants.entities.v1_8_R2;

import me.jjm_223.smartgiants.api.entities.ILoad;
import me.jjm_223.smartgiants.api.utils.ReflectionUtils;
import me.jjm_223.smartgiants.entities.v1_8_R2.nms.SmartGiant;
import me.jjm_223.smartgiants.entities.v1_8_R2.nms.SmartGiantHostile;
import net.minecraft.server.v1_8_R2.EntityGiantZombie;
import net.minecraft.server.v1_8_R2.EntityTypes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class Load implements ILoad
{
    private Field c;
    private Field d;
    private Field e;
    private Field f;
    private Field g;
    private Method a;

    public Load()
    {
        try
        {
            a = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            c = EntityTypes.class.getDeclaredField("c");
            d = EntityTypes.class.getDeclaredField("d");
            e = EntityTypes.class.getDeclaredField("e");
            f = EntityTypes.class.getDeclaredField("f");
            g = EntityTypes.class.getDeclaredField("g");
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    public void load(boolean hostile)
    {
        try
        {
            ReflectionUtils.setAccessible(a, c, d, e, f, g);

            removeGiant();

            if (hostile)
            {
                a.invoke(null, SmartGiantHostile.class, "Giant", 53);
            }
            else
            {
                a.invoke(null, SmartGiant.class, "Giant", 53);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void cleanup()
    {
        try
        {
            ReflectionUtils.setAccessible(a, c, d, e, f, g);

            removeGiant();

            a.invoke(null, EntityGiantZombie.class, "Giant", 53);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void removeGiant() throws IllegalAccessException
    {
        Map cMap = (Map) c.get(null);
        Map dMap = (Map) d.get(null);
        Map eMap = (Map) e.get(null);
        Map fMap = (Map) f.get(null);
        Map gMap = (Map) g.get(null);

        cMap.remove("Giant");
        dMap.remove(EntityGiantZombie.class);
        eMap.remove(53);
        fMap.remove(EntityGiantZombie.class);
        gMap.remove("Giant");
    }
}
