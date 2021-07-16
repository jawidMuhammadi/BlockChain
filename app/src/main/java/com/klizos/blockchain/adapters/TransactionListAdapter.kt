package com.klizos.blockchain.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klizos.blockchain.data.model.transaction.Transaction
import com.klizos.blockchain.databinding.ItemTransactionBinding
import com.klizos.blockchain.utils.DATE_FORMAT
import com.klizos.blockchain.utils.getBitcoinPrice
import com.klizos.blockchain.utils.getTimeInIST
import java.util.*
import javax.inject.Inject

/**
 * Created by Ahmad Jawid Muhammadi
 * on 16-07-2021.
 */

class TransactionListAdapter @Inject constructor() : RecyclerView.Adapter<TransactionListVH>() {

    private var transactionList = LinkedList<Transaction>()

    fun addItem(transaction: Transaction) {
        if (transactionList.size > 4) {
            transactionList.removeFirst()
        }
        transactionList.add(transaction)
        notifyDataSetChanged()
    }

    fun clearQueue() {
        transactionList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListVH {
        return TransactionListVH.from(parent)
    }

    override fun onBindViewHolder(holder: TransactionListVH, position: Int) {
        holder.bind(transactionList[position])
    }

    override fun getItemCount(): Int = transactionList.size
}

class TransactionListVH private constructor(private val binding: ItemTransactionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): TransactionListVH {
            val layoutInflater = LayoutInflater.from(parent.context)
            return TransactionListVH(
                ItemTransactionBinding.inflate(layoutInflater, parent, false)
            )
        }
    }

    fun bind(transaction: Transaction) {
        val satoshi = transaction.x?.out?.get(0)?.value
        val amount = satoshi?.let { getBitcoinPrice(it) } ?: 0
        val time = getTimeInIST(transaction.x?.time!!, DATE_FORMAT)
        binding.apply {
            tvHash.text = transaction.x?.hash
            tvAmount.text = String.format("\$%.2f", amount)
            tvTime.text = time
        }
    }
}
