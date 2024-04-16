import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyekstarling.R
import com.example.proyekstarling.dashboardowner
import com.example.proyekstarling.databinding.FraguserownerBinding

class fraguserowner : Fragment() {
    lateinit var thisParent: dashboardowner
    lateinit var binding: FraguserownerBinding
    lateinit var adapterLv: ArrayAdapter<String>
    lateinit var arrGabungan: ArrayList<String>
    var selectedPosition: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FraguserownerBinding.inflate(inflater, container, false)
        val view = binding.root
        thisParent = activity as dashboardowner

        arrGabungan = ArrayList()
        adapterLv = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrGabungan)
        binding.lvadmin.adapter = adapterLv
        binding.lvadmin.choiceMode = ListView.CHOICE_MODE_NONE

        binding.lvadmin.setOnItemLongClickListener { parent, view, position, id ->
            selectedPosition = position
            showPopupMenu(view)
            true
        }

        binding.buttonSimpan.setOnClickListener {
            val idAdmin = binding.editTextIdAdmin.text.toString()
            val namaAdmin = binding.editTextNamaAdmin.text.toString()
            val hpAdmin = binding.editTextHpAdmin.text.toString()
            val passwordAdmin = binding.editTextPasswordAdmin.text.toString()
            val jenkel = when (binding.radioGroupJenkel.checkedRadioButtonId) {
                R.id.radioButtonP -> "Perempuan"
                R.id.radioButtonL -> "Laki-laki"
                else -> ""
            }

            if (idAdmin in arrGabungan.map { it.split("\n")[0].split(": ")[1] }) {
                Toast.makeText(requireContext(), "ID sudah ada", Toast.LENGTH_SHORT).show()
            } else {
                val employeeData = "Id_admin: $idAdmin\nNama: $namaAdmin\nNo HP: $hpAdmin\nPassword: $passwordAdmin\nJenis Kelamin: $jenkel"
                arrGabungan.add(employeeData)
                adapterLv.notifyDataSetChanged()
                clearForm()
                Toast.makeText(requireContext(), "Berhasil menambahkan data admin", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.menu_popup)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.popupEdit -> {
                    editItem(selectedPosition)
                    true
                }
                R.id.popupDelete -> {
                    deleteItem(selectedPosition)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun editItem(position: Int) {
        val selectedItem = arrGabungan[position].split("\n")
        val idAdmin = selectedItem[0].split(": ")[1]
        val namaAdmin = selectedItem[1].split(": ")[1]
        val hpAdmin = selectedItem[2].split(": ")[1]
        val passwordAdmin = selectedItem[3].split(": ")[1]

        binding.editTextIdAdmin.setText(idAdmin)
        binding.editTextNamaAdmin.setText(namaAdmin)
        binding.editTextHpAdmin.setText(hpAdmin)
        binding.editTextPasswordAdmin.setText(passwordAdmin)

        binding.buttonSimpan.setOnClickListener {
            val idAdmin = binding.editTextIdAdmin.text.toString()
            val namaAdmin = binding.editTextNamaAdmin.text.toString()
            val hpAdmin = binding.editTextHpAdmin.text.toString()
            val passwordAdmin = binding.editTextPasswordAdmin.text.toString()

            val employeeData = "Id_admin: $idAdmin\nNama: $namaAdmin\nNo HP: $hpAdmin\nPassword: $passwordAdmin"
            arrGabungan.add(employeeData)
            adapterLv.notifyDataSetChanged()
            clearForm()
            Toast.makeText(requireContext(), "Berhasil mengubah data admin", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteItem(position: Int) {
        arrGabungan.removeAt(position)
        adapterLv.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Data dihapus", Toast.LENGTH_SHORT).show()
    }

    private fun clearForm() {
        binding.editTextIdAdmin.setText("")
        binding.editTextNamaAdmin.setText("")
        binding.editTextHpAdmin.setText("")
        binding.editTextPasswordAdmin.setText("")
    }
}
