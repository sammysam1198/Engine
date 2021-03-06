package com.builtbroken.mc.debug.data;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 9/6/2017.
 */
public interface IJsonDebugData
{
    /**
     * Called to get a display string for default component
     *
     * @return string
     */
    String buildDebugLineDisplay();

    /**
     * Called when default component is double clicked
     */
    default void onDoubleClicked()
    {
    }
}
