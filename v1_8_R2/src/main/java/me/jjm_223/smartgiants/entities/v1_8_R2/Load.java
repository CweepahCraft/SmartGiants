package me.jjm_223.smartgiants.entities.v1_8_R2;

import me.jjm_223.smartgiants.api.util.ILoad;
import me.jjm_223.smartgiants.api.util.ReflectionUtils;
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

    private boolean hostile;

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
        catch (NoSuchFieldException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    public void load(boolean hostile)
    {
        this.hostile = hostile;

        try
        {
            ReflectionUtils.setAccessible(a, c, d, e, f, g);

            removeGiant(EntityGiantZombie.class);

            a.invoke(null, hostile ? SmartGiantHostile.class : SmartGiant.class, "Giant", 53);
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

            removeGiant(hostile ? SmartGiantHostile.class : SmartGiant.class);

            a.invoke(null, EntityGiantZombie.class, "Giant", 53);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void removeGiant(Class clazz) throws IllegalAccessException
    {
        Map cMap = (Map) c.get(null);
        Map dMap = (Map) d.get(null);
        Map eMap = (Map) e.get(null);
        Map fMap = (Map) f.get(null);
        Map gMap = (Map) g.get(null);

        cMap.remove("Giant");
        dMap.remove(clazz);
        eMap.remove(53);
        fMap.remove(clazz);
        gMap.remove("Giant");
    }
}
