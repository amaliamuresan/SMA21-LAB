package com.upt.cti.smartwallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.upt.cti.smartwallet.model.MonthlyExpenses

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var messageTextView: TextView
    private lateinit var incomeEditText: EditText
    private lateinit var expensesEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var spinner: Spinner
    private lateinit var databaseReference: DatabaseReference

    var months: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        incomeEditText = findViewById(R.id.income_editText)
        expensesEditText = findViewById(R.id.expensed_editText)
        updateButton = findViewById(R.id.update_button)
        messageTextView = findViewById(R.id.message_textView)
        spinner = findViewById(R.id.spinner)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        getMonths()

        spinner.onItemSelectedListener = this

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        updateButton.setOnClickListener {
            val month: String = spinner.selectedItem.toString()
            val income = incomeEditText.text.toString()
            val expenses = expensesEditText.text.toString()

            val monthlyExpenses = MonthlyExpenses(expenses.toDouble(), month, income.toDouble())

            databaseReference.child("calendar").child(month).setValue(monthlyExpenses)

        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.e("MONTH", "1")
                spinner.setSelection(0)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinner.setSelection(position)
                val month = parent?.getItemAtPosition(position)?.toString()
                if (month != null) {
                    Log.e("MONTH", "2")
                    searchMonth(month)
                } else {
                    Log.e("MONTH", "3")

                }
            }
        }

        if(!AppState.isNetworkAvailable(this)){
            if(AppState.hasLocalStorage(this)) {

            }
        }
    }

    private fun getMonths() {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { month -> month.key?.let { it1 -> months.add(it1) } }
                Log.e("MESSAGE! ", months.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "databaseError ${error.message}")
            }
        }
        databaseReference.addValueEventListener(listener)
    }

    fun searchMonth(month: String) {
        var monthData: MonthlyExpenses?
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                monthData = dataSnapshot.child("calendar").child(month).getValue<MonthlyExpenses>()
                incomeEditText.setText(monthData?.income.toString())
                expensesEditText.setText(monthData?.expenses.toString())

                if (monthData == null) {
                    messageTextView.text = "No records found"
                } else {
                    messageTextView.text = ""
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Firebase", "databaseError ${databaseError.message}")
            }
        }
        databaseReference.addValueEventListener(listener)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        spinner.setSelection(position)
        val month = parent?.getItemAtPosition(position)?.toString()
        if (month != null) {
            Log.e("MONTH", "2")
            searchMonth(month)
        } else {
            Log.e("MONTH", "3")

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        parent?.setSelection(0)
    }
}