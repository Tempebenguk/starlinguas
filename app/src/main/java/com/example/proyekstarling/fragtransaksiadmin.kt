package com.example.proyekstarling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import com.example.proyekstarling.databinding.FragtransaksiadminBinding
import java.util.Calendar
import android.widget.ArrayAdapter

class fragtransaksiadmin : Fragment(), View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    lateinit var thisParent: dashboardadmin
    lateinit var binding: FragtransaksiadminBinding
    lateinit var adapterSpin : ArrayAdapter<String>
    val pembayaran = arrayOf("Gopay", "Dana", "OVO", "Shopeepay", "Paypal")

    var tahun = 0
    var bulan = 0
    var hari = 0
    var jam = 0
    var menit = 0

    // ArrayList to store selected items from CheckBox
    var selectedItems: ArrayList<String> = ArrayList()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.jam -> showDialog(100)
            R.id.tgl -> showDialog(200)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragtransaksiadminBinding.inflate(inflater, container, false)
        val view = binding.root
        thisParent = activity as dashboardadmin

        // get instance of Calendar class
        val cal: Calendar = Calendar.getInstance()

        bulan = cal.get(Calendar.MONTH) + 1
        hari = cal.get(Calendar.DAY_OF_MONTH)
        tahun = cal.get(Calendar.YEAR)
        jam = cal.get(Calendar.HOUR_OF_DAY)
        menit = cal.get(Calendar.MINUTE)

        binding.jam.setOnClickListener(this)
        binding.tgl.setOnClickListener(this)
        binding.tran.setOnClickListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Array of checkbox items
        val items = arrayOf("Pop Mie", "Mie Goreng", "Snack", "Aqua", "Teh", "Kopi")

        // Set ArrayAdapter to ListView
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, selectedItems)
        binding.lvtr.adapter = adapter

        // Set ArrayAdapter to Spinner
        adapterSpin = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, pembayaran)
        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sp1.adapter = adapterSpin

        binding.tran.setOnClickListener {
            // Clear the previous selected items
            selectedItems.clear()

            // Add selected items from CheckBox
            if (binding.box1.isChecked) selectedItems.add("Pop Mie")
            if (binding.box2.isChecked) selectedItems.add("Mie Goreng")
            if (binding.box3.isChecked) selectedItems.add("Snack")
            if (binding.box4.isChecked) selectedItems.add("Aqua")
            if (binding.box5.isChecked) selectedItems.add("Teh")
            if (binding.box6.isChecked) selectedItems.add("Kopi")

            // Add selected item from Spinner
            val selectedPayment = binding.sp1.selectedItem.toString()
            if (selectedPayment.isNotEmpty()) {
                selectedItems.add("Metode Pembayaran: $selectedPayment")
            }

            // Add selected date and time
            selectedItems.add("Tanggal : $hari-$bulan-$tahun, $jam:$menit")

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged()
        }
    }

    private fun showDialog(id: Int) {
        when (id) {
            100 -> {
                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    this,
                    jam,
                    menit,
                    true
                )
                timePickerDialog.show()
            }
            200 -> {
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    this,
                    tahun,
                    bulan - 1,
                    hari
                )
                datePickerDialog.show()
            }
        }
    }

    override fun onTimeSet(view: android.widget.TimePicker?, hourOfDay: Int, minute: Int) {
        // Update selected time
        jam = hourOfDay
        menit = minute
    }

    override fun onDateSet(view: android.widget.DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Update selected date
        tahun = year
        bulan = month + 1
        hari = dayOfMonth
    }
}
