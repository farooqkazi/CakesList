package com.assignment.cakeslist.presentation.common

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    FragmentViewBindingDelegate(T::class.java, this)

class FragmentViewBindingDelegate<T : ViewBinding>(
    private val bindingClass: Class<T>,
    private val fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }
            }
        })
    }


    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (binding == null)
            binding = bindingClass.getMethod("bind", View::class.java)
                .invoke(null, thisRef.requireView()) as T
        return binding!!
    }

}

fun Fragment.snackRetry(
    msg: String,
    rootView: View = requireActivity().findViewById(android.R.id.content),
    callback: () -> Unit
) {
    val snackbar =
        Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT).setAction("Retry") { callback.invoke() }
    snackbar.show()
}