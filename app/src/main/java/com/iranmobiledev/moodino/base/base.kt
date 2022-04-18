package com.iranmobiledev.moodino.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.utlis.MoodinoDialog

/**
 * All activities should extend this base activity
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {
    override val mRootView: ViewGroup?
        get() = window.findViewById(android.R.id.content) as ViewGroup
    override val viewContext: Context
        get() = this
}

/**
 * All fragments should extend this base fragment
 */
abstract class BaseFragment : Fragment(), BaseView {
    override val mRootView: ViewGroup?
        get() = view as ViewGroup
    override val viewContext: Context
        get() = requireContext()

}

/**
 * All View Models should extend this view model
 */
open class BaseViewModel : ViewModel() {

}

interface BaseView {

    val mRootView: ViewGroup?
    val viewContext: Context

    /**
     * @return instance of MoodinoDialog
     * @author MohammadJavad Khoshneshin
     * @param mainText top text of dialog
     * @param subText text under the main text
     * @param icon dialog icon
     * you can set nothing to each them and their view will being gone
     * */
    fun makeDialog(
        mainText: String = "",
        subText: String = "",
        @DrawableRes icon: Int = 0,
        deleteText: String = "",
        cancelText: String = "",
    ): MoodinoDialog {
        return MoodinoDialog(mainText, subText, icon, deleteText, cancelText)
    }

    /**
     * @author MohammadJavad Khoshneshin
     * @param mustShow empty state should show or not
     * for using this feature you should have a view group with id '@id/emptyStateContainer'
     * this is meaning that you wanna use empty state view in this view group.
     */
    fun showEmptyState(mustShow: Boolean) {
        val viewGroup = getEmptyStateViewGroup()
        when (mustShow) {
            true -> {
                viewGroup?.let { emptyStateContainer ->
                    var emptyStateView = emptyStateContainer.findViewById<ConstraintLayout>(R.id.emptyStateView)
                    if(emptyStateView == null){
                        emptyStateView = LayoutInflater.from(viewContext).inflate(R.layout.empty_state, mRootView, false) as ConstraintLayout?
                        emptyStateContainer.addView(emptyStateView)
                    }
                }
            }
            false -> {
                viewGroup?.let { emptyStateContainer ->
                    var emptyStateView = emptyStateContainer.findViewById<ConstraintLayout>(R.id.emptyStateView)
                    if(emptyStateView != null){
                        emptyStateContainer.removeView(emptyStateView)
                    }
                }
            }
        }
    }

    /**
     * @return returns to you empty state view group you were set the id '@id/emptyStateContainer'
     */
    private fun getEmptyStateViewGroup(): ViewGroup? {
        println("mRootView $mRootView")
        var emptyStateContainer: ViewGroup? = null

            if(mRootView?.id == R.id.emptyStateContainer)
                emptyStateContainer = mRootView
            else{
                mRootView?.let {
                    emptyStateContainer = it.findViewById(R.id.emptyStateContainer)
                }
            }
        return emptyStateContainer
    }
}
