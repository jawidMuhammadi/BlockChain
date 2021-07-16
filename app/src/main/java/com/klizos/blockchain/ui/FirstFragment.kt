package com.klizos.blockchain.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.klizos.blockchain.R
import com.klizos.blockchain.adapters.TransactionListAdapter
import com.klizos.blockchain.data.model.ConnectionState
import com.klizos.blockchain.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private val viewModel: FirstFragmentViewModel by viewModels()

    @Inject
    lateinit var adapter: TransactionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvTransactions.adapter = adapter

            btnStartConnection.setOnClickListener {
                viewModel.starConnection()
            }

            btnClear.setOnClickListener {
                adapter.clearQueue()
                tvEmpty.visibility = View.VISIBLE
                btnClear.isEnabled = false
            }
        }
        initObservers()
    }

    private fun initObservers() {
        viewModel.connectionState.observe(viewLifecycleOwner, Observer {
            when (it) {
                ConnectionState.CONNECTING -> {
                    binding.apply {
                        tvConnectionState.text = getString(R.string.connecting)
                        tvConnectionState.setTextColor(requireContext().getColor(R.color.purple_700))
                        btnStartConnection.isEnabled = false
                        btnClear.isEnabled = false
                    }
                }
                ConnectionState.DISCONNECTED -> {
                    binding.apply {
                        tvConnectionState.text = getString(R.string.disconnected)
                        tvConnectionState.setTextColor(requireContext().getColor(R.color.grey_500))
                        btnStartConnection.isEnabled = true
                    }
                }
                ConnectionState.CONNECTED -> {
                    binding.apply {
                        tvConnectionState.text = getString(R.string.connected)
                        tvConnectionState.setTextColor(requireContext().getColor(R.color.atlantias))
                        btnStartConnection.isEnabled = false
                        btnClear.isEnabled = true
                        tvEmpty.visibility = View.GONE
                    }
                }
            }

        })

        viewModel.transaction.observe(viewLifecycleOwner, Observer {
            binding.btnClear.isEnabled = true
            adapter.addItem(transaction = it)
            Log.d("transaction", Gson().toJson(it))
        })
    }
}