package com.outdoors.sliide.users

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.outdoors.sliide.R
import com.outdoors.sliide.databinding.UsersListFragmentBinding
import com.outdoors.sliide.network.NetworkStatus
import com.outdoors.sliide.utils.validateEmail
import com.outdoors.sliide.utils.validateField
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.insert_user_dialog.view.*


@AndroidEntryPoint
class UsersListFragment : Fragment() {

    private val usersViewModel: UsersListViewModel by activityViewModels()
    private lateinit var dialog:AlertDialog
    private lateinit var dialogView:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<UsersListFragmentBinding>(
            inflater,
            R.layout.users_list_fragment,
            container,
            false
        )

        binding.usersModel=usersViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val listLayoutManager = LinearLayoutManager(activity)
        binding.users.layoutManager=listLayoutManager

       val listAdapter =UsersListAdapter(UserClickListener {
           usersViewModel.setCurrentUser(it)
           showDeleteDialog()
       })

        binding.users.adapter = listAdapter
        usersViewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.status == NetworkStatus.SUCCESS) listAdapter.submitList(it.data)
            }
        })
        binding.addUser.setOnClickListener {
            usersViewModel.showingAdd=true
            showAddDialog()
        }

        usersViewModel.showError.observe(viewLifecycleOwner, Observer {show ->
            if(show){
                usersViewModel.errorMessage.value?.let { it -> showError(it) }
                usersViewModel.errorShown()
            }
        })

        if (savedInstanceState != null) {
            showPreviousState(savedInstanceState)
        }


        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putString("name",dialogView.userName.text.toString())
        outState.putString("email",dialogView.userEmail.text.toString())
        super.onSaveInstanceState(outState)
    }
    private fun showDeleteDialog()
    {
            val builder = AlertDialog.Builder(requireActivity())
            builder.apply {
                setTitle(R.string.delete_user)
                setMessage(R.string.delete_message)
                setPositiveButton(R.string.delete
                ) { _, _ ->
                    usersViewModel.proceedWithDelete()
                }
                setNegativeButton(R.string.cancel
                ) { _, _ ->
                    usersViewModel.clearCurrentUser()
                }
            }
        builder.show()
    }

    @SuppressLint("InflateParams")
    private fun showAddDialog()
    {
        dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.insert_user_dialog,null,false)
        val builder = AlertDialog.Builder(requireActivity()).setView(dialogView)
            .setTitle(R.string.add_new_user)
            . setNegativeButton(R.string.cancel){ _, _ ->
                usersViewModel.showingAdd=false
            }
            . setPositiveButton(R.string.add,null)

        dialog = builder.create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener {
                    if (validateField(dialogView.userName.text.toString()) && validateEmail(
                            dialogView.userEmail.text.toString()
                        )
                    ) {
                        usersViewModel.addUser(
                            dialogView.userName.text.toString(),
                            dialogView.userEmail.text.toString()
                        )
                        dialog.dismiss()
                        usersViewModel.showingAdd=false
                    } else {
                        dialogView.dialogError.visibility = View.VISIBLE
                    }
                }
        }
        dialog.show()

    }
    private fun showError(error:String)
    {
        Toast.makeText(requireContext(),error,Toast.LENGTH_LONG).show()
    }
    private fun showPreviousState(savedInstanceState:Bundle? = null)
    {
        when{
            usersViewModel.showingDelete -> showDeleteDialog()
            usersViewModel.showingAdd -> {
                showAddDialog()
                savedInstanceState?.let {
                    dialogView.userName.setText(savedInstanceState.getString("name"))
                    dialogView.userEmail.setText(savedInstanceState.getString("email"))
                }

            }
        }
    }
}