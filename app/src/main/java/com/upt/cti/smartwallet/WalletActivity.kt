package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.upt.cti.smartwallet.model.ExpenseItem

class WalletActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton

    private lateinit var databaseReference: DatabaseReference

    private var adapter = ExpensesAdapter()

    private var expenseList = MutableLiveData<List<ExpenseItem>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        recyclerView = findViewById(R.id.recycler_view)
        addButton = findViewById(R.id.add_button)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        recyclerView.adapter = adapter

        getExpenses()

        addButton.setOnClickListener {
            val intent = Intent(this, AddExpensesActivity::class.java)
            startActivity(intent)
        }

//        adapter.items = getExpenses()

        expenseList.observe(this, {
            if (expenseList.value != null)
                adapter.items = expenseList.value!!
        })
    }

    private fun getExpenses() {
        val expenses = mutableListOf<ExpenseItem>()
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.child("wallet").children.forEach { child ->
                    Log.d("TAG", child.toString())
                    child.getValue(ExpenseItem::class.java)?.let { expenses.add(it) }
                }
                Log.e("MESSAGE! ", expenses.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "databaseError ${error.message}")
            }
        }
        expenseList.value = expenses
        databaseReference.addValueEventListener(listener)
    }
}