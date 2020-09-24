package com.outdoors.sliide.users


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.outdoors.sliide.databinding.UserListItemBinding
import com.outdoors.sliide.domain.User


class UsersListAdapter(private val userClickListener: UserClickListener ): androidx.recyclerview.widget.ListAdapter<User, UsersListAdapter.ViewHolder>(UserListItemDiffCallBack())
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(userClickListener, item)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding:UserListItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(clickListener: UserClickListener, item:User)
        {
            //binding.item.isLongClickable=true
            binding.item.setOnLongClickListener {
               clickListener.onClick(item)
                true
            }
            binding.user=item
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):ViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UserListItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }
}

class UserListItemDiffCallBack : DiffUtil.ItemCallback<User>()
{
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }
}

class UserClickListener(val userClickListener: (user: User) -> Unit)
{
    fun onClick(item: User) = userClickListener(item)
}