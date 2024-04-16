import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.proyekstarling.R
import com.example.proyekstarling.dashboardowner

class fraglayananowner : Fragment () {
    lateinit var thisParent : dashboardowner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisParent = activity as dashboardowner
        val view = inflater.inflate(R.layout.fraglayananowner, container, false)
        val button: Button = view.findViewById(R.id.button)

        button.setOnClickListener {
            val url = "https://wa.me/6282143231811"
            val intentwa = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intentwa)
        }
        return view
    }

    fun onButtonClicked(view: View) {
        val url = "https://wa.me/6282143231811"
        val intentwa = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intentwa)
    }
}
