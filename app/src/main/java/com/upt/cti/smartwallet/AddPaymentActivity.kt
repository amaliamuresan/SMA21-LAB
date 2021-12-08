package com.upt.cti.smartwallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.upt.cti.smartwallet.model.ExpenseItem

class AddPaymentActivity() : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    lateinit var spinner: Spinner
    lateinit var priceEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var saveButton: Button
    lateinit var deleteButton: Button

    private val spinnerItems = arrayListOf("food", "entertainment", "other things")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment)

        val expenseItem: ExpenseItem = intent.getSerializableExtra("EXPENSE_ITEM") as ExpenseItem

        spinner = findViewById(R.id.spinner)
        priceEditText = findViewById(R.id.price_editText)
        descriptionEditText = findViewById(R.id.description_editText)
        saveButton = findViewById(R.id.save_button)

        priceEditText.setText(expenseItem.price)
        descriptionEditText.setText(expenseItem.name)

        deleteButton = findViewById(R.id.delete_button)

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)

        priceEditText.doOnTextChanged { text, _, _, _ -> expenseItem.price = text.toString() }
        descriptionEditText.doOnTextChanged { text, _, _, _ -> expenseItem.name = text.toString() }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinner.setSelection(0)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                expenseItem.category = parent?.getItemAtPosition(position).toString()
            }
        }


        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        saveButton.setOnClickListener {
            databaseReference.child("wallet").child("${expenseItem.date} ${expenseItem.time}").setValue(expenseItem)
        }

        deleteButton.setOnClickListener {
            databaseReference.child("wallet").child("${expenseItem.date} ${expenseItem.time}").removeValue()
        }

    }
}