package com.phicdy.prisonertrainingrecorder.presentation

import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.content.Context
import android.view.View
import android.util.AttributeSet
import kotlin.math.max
import kotlin.math.min

class BottomNavigationBehavior<V : View>(context: Context, attrs: AttributeSet?) :
        CoordinatorLayout.Behavior<V>(context, attrs) {

    override fun onStartNestedScroll(
            coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
            coordinatorLayout: CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = max(0f, min(child.height.toFloat(), child.translationY + dy))
    }
}
