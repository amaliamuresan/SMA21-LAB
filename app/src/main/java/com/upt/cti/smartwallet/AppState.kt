package com.upt.cti.smartwallet

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.upt.cti.smartwallet.model.ExpenseItem
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class AppState {
    companion object {
        fun updateLocalBackup(context: Context, expenseItem: ExpenseItem, toAdd: Boolean) {
            val filename = "${expenseItem.date} ${expenseItem.time}"
            try {
                if (toAdd) {
                    val fos = context.openFileOutput(filename, Context.MODE_PRIVATE)
                    val os = ObjectOutputStream(fos)
                    os.writeObject(expenseItem)
                    os.close()
                    fos.close()
                } else {
                    context.deleteFile(filename)
                }
            } catch (e: IOException) {
                Toast.makeText(context, "Cannot access local data.", Toast.LENGTH_SHORT).show()

            }
        }

        fun hasLocalStorage(context: Context): Boolean {
            return context.filesDir.listFiles().isNotEmpty()
        }

        fun loadFromLocalBackup(context: Context): List<ExpenseItem>? {
            try {
                val expanseItems = arrayListOf<ExpenseItem>()
                for (file in context.filesDir.listFiles()) {
                    val fis: FileInputStream = context.openFileInput(file.name)
                    val ins = ObjectInputStream(fis)
                    val expanseItem: ExpenseItem = ins.readObject() as ExpenseItem
                    expanseItems.add(expanseItem)
                    fis.close()
                    ins.close()
                }
                return expanseItems
            } catch (e: IOException) {
                Toast.makeText(
                    context,
                    "Local data may not be accessed. An internet connection is required",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}