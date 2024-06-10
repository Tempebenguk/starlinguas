import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekstarling.R
import com.example.proyekstarling.TransactionAdapter
import com.example.proyekstarling.dashboardowner
import com.example.proyekstarling.transaksi
import com.google.firebase.database.*

class fraglayananowner : Fragment() {
    lateinit var thisParent: dashboardowner
    private lateinit var database: DatabaseReference
    private lateinit var transactionAdapter: TransactionAdapter
    private val transactionList = mutableListOf<Pair<String, transaksi>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisParent = activity as dashboardowner
        val view = inflater.inflate(R.layout.fraglayananowner, container, false)
        database = FirebaseDatabase.getInstance().reference

        // Setup RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(thisParent)
        transactionAdapter = TransactionAdapter(transactionList) { transactionId, transaction ->
        }
        recyclerView.adapter = transactionAdapter

        // Fetch transactions from Firebase and sort by date
        fetchTransactionsSortedByDate()

        return view
    }

    private fun fetchTransactionsSortedByDate() {
        database.child("transaksi")
            .orderByChild("tgl_transaksi")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    transactionList.clear()
                    snapshot.children.forEach { dataSnapshot ->
                        val transaction = dataSnapshot.getValue(transaksi::class.java)
                        val transactionId = dataSnapshot.key ?: ""
                        transaction?.let { transactionList.add(transactionId to it) }
                    }
                    transactionAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("DashboardAdmin", "Failed to read transactions", error.toException())
                }
            })
    }
}
