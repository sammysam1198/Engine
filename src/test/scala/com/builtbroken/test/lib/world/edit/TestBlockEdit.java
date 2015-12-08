package com.builtbroken.test.lib.world.edit;

import com.builtbroken.mc.lib.world.edit.BlockEdit;
import com.builtbroken.mc.testing.junit.AbstractTest;
import com.builtbroken.mc.testing.junit.world.FakeWorld;
import net.minecraft.world.World;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 12/7/2015.
 */
public class TestBlockEdit extends AbstractTest
{
    @Test
    public void testEquals()
    {
        World world = FakeWorld.newWorld("BlockEditTest");
        World world2 = FakeWorld.newWorld("BlockEditTest2");

        BlockEdit location = new BlockEdit(world, 0, 0, 0);
        BlockEdit location2 = new BlockEdit(world, 0, 0, 0);

        //Test would equals
        Assert.assertEquals("BlockEdit one should equal itself", location, location);
        Assert.assertEquals("BlockEdit two should equal itself",location2, location2);
        Assert.assertEquals("BlockEdit one & two should equal each other",location, location2);

        location2 = new BlockEdit(world2, 0, 0, 0);

        //Test world not equal
        Assert.assertNotSame("BlockEdit one & two should not equal each other", location, location2);

        location = new BlockEdit(world, 0, 1, 0);

        //Test pos not equal
        Assert.assertNotSame("BlockEdit one & two should not equal each other",location, location2);


        //Test decimal point
        location = new BlockEdit(world, 1, 2.0, 3.0f);
        location2 = new BlockEdit(world, 1, 2.0, 3.0f);

        //Test would equals
        Assert.assertEquals("BlockEdit one should equal itself", location, location);
        Assert.assertEquals("BlockEdit two should equal itself",location2, location2);
        Assert.assertEquals("BlockEdit one & two should equal each other",location, location2);
    }

    @Test
    public void testList()
    {
        List<BlockEdit> list = new ArrayList();
        list.add(new BlockEdit(null, 0, 0, 0));

        //Test basic contains
        Assert.assertTrue("Should contain location", list.contains(new BlockEdit(null, 0, 0, 0)));
        Assert.assertTrue("Should not contain location", !list.contains(new BlockEdit(null, 0, 1, 0)));

        //Test removes
        list.remove(new BlockEdit(null, 0, 0, 0));
        Assert.assertTrue("Should not contain location", !list.contains(new BlockEdit(null, 0, 0, 0)));

        //Repeat with world not null
        World world = FakeWorld.newWorld("BlockEditTest");
        World world2 = FakeWorld.newWorld("BlockEditTest");

        list.add(new BlockEdit(world, 0, 0, 0));

        //Test basic contains
        Assert.assertTrue("Should contain location", list.contains(new BlockEdit(world, 0, 0, 0)));
        Assert.assertTrue("Should not contain location", !list.contains(new BlockEdit(world2, 0, 0, 0)));

        list.add(new BlockEdit(world, 0, 1.00, 0));

        //Test basic contains
        Assert.assertTrue("Should contain location", list.contains(new BlockEdit(world, 0, 1.00, 0)));
        Assert.assertTrue("Should not contain location", !list.contains(new BlockEdit(world2, 0, 1.00, 0)));
    }

    public void testMap()
    {
        HashMap<BlockEdit, Object> map = new HashMap();
        map.put(new BlockEdit(null, 0, 0, 0), "yo");

        //Test basic contains
        Assert.assertTrue("Should contain location", map.containsKey(new BlockEdit(null, 0, 0, 0)));
        Assert.assertTrue("Should not contain location", !map.containsKey(new BlockEdit(null, 0, 1, 0)));

        //Test removes
        map.remove(new BlockEdit(null, 0, 0, 0));
        Assert.assertTrue("Should not contain location", !map.containsKey(new BlockEdit(null, 0, 0, 0)));

        //Repeat with world not null
        World world = FakeWorld.newWorld("BlockEditTest");
        World world2 = FakeWorld.newWorld("BlockEditTest");

        map.put(new BlockEdit(world, 0, 0, 0), "mama");

        //Test basic contains
        Assert.assertTrue("Should contain location", map.containsKey(new BlockEdit(world, 0, 0, 0)));
        Assert.assertTrue("Should not contain location", !map.containsKey(new BlockEdit(world2, 0, 0, 0)));
    }
}
