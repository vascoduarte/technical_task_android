package com.outdoors.sliide.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.outdoors.sliide.domain.User
import com.outdoors.sliide.network.NetworkResource
import com.outdoors.sliide.network.NetworkStatus

@BindingAdapter("loadingData")
fun showWhenData(view: View, processing: Boolean )
{
    view.visibility = if(processing) View.VISIBLE else View.GONE
}
@BindingAdapter("onError")
fun showOnError(view: View, error: Boolean )
{
    view.visibility = if(error) View.VISIBLE else View.GONE
}