package com.kitaboo.kitaboomachinetest.utils

import android.content.Context
import android.content.res.Configuration

//Common util class for standalone functions
class CommonUtils {
    companion object {

        //method checks if landscape view is on returns true then false otherwise
        fun isLandscapeViewToggled(ctx: Context): Boolean {
            return ctx.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        }

        //method checks if tablet device is used returns true then false otherwise
        fun isTabletDevice(ctx: Context): Boolean {
            val screenSize = ctx.resources.configuration.screenLayout and
                    Configuration.SCREENLAYOUT_SIZE_MASK

            return screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE ||
                    screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE
        }
    }
}