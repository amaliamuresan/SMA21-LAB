package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.upt.cti.smartwallet.model.ExpenseItem
import java.text.SimpleDateFormat
import java.util.*

class AddExpensesActivity() : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var addButton: Button
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exepnses)

        nameEditText = findViewById(R.id.name_editText)
        priceEditText = findViewById(R.id.price_editText)
        categoryEditText = findViewById(R.id.category_editText)
        addButton = findViewById(R.id.add_button)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        addButton.setOnClickListener {
            val calendar = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault())
            val currentTime = formatter.format(calendar)

            if (nameEditText.text != null && priceEditText.text != null && categoryEditText.text != null) {
                val item = ExpenseItem(
                    price = priceEditText.text.toString(),
                    category = categoryEditText.text.toString(),
                    name = nameEditText.text.toString(),
                    date = currentTime.substringBefore(" "),
                    time = currentTime.substringAfter(" ")
                )
                databaseReference.child("wallet").child("${item.date} ${item.time}").setValue(item)
                startActivity(Intent(this, WalletActivity::class.java))
            }
            else
            {
                Toast.makeText(this, "All fiels must be filled!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}