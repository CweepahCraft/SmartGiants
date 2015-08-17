package me.jjm_223.smartgiants.entities.v1_8_R1;

import me.jjm_223.smartgiants.api.entities.ILoad;
import me.jjm_223.smartgiants.entities.v1_8_R1.nms.SmartGiant;
import me.jjm_223.smartgiants.entities.v1_8_R1.nms.SmartGiantHostile;
import net.minecraft.server.v1_8_R1.EntityTypes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class Load implements ILoad {
    public Load(boolean hostile) {
        try {
            Field fieldc = EntityTypes.class.getDeclaredField("c");
            Field fielde = EntityTypes.class.getDeclaredField("e");
            fieldc.setAccessible(true);
            fielde.setAccessible(true);

            Map c = (Map) fieldc.get(null);
            Map e = (Map) fielde.get(null);

            c.remove("Giant");
            e.remove(53);

            Method a = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            a.setAccessible(true);
            if (hostile) {
                a.invoke(null, SmartGiantHostile.class, "Giant", 53);
            } else {
                a.invoke(null, SmartGiant.class, "Giant", 53);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
