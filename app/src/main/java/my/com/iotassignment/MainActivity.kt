package my.com.iotassignment

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.com.iotassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val database = Firebase.database
    val ref = database.getReference("tripWire")

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener{ sendToFirebasePls() }



    }

    private fun sendToFirebasePls() {
        var text = binding.edtLight.text.toString().trim()
        var text2 = binding.edtSound.text.toString().trim()

        ref.child("light")
            .setValue(text)
        ref.child("sound")
            .setValue(text2)

        ref.child("light")
            .get()
            .addOnSuccessListener {
                binding.txtTest.text = it.value.toString()
            }
        ref.child("sound")
            .get()
            .addOnSuccessListener {
                binding.txtTest2.text = it.value.toString()
            }
    }


//    private fun getData(){
//        ref.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var list = ArrayList<DataModel>()
//                for (data in snapshot.children){
//                    var model = data.getValue(DataModel::class.java)
//                    list.add(model as DataModel)
//                }
//                list.
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//                    errorDialog("Some error occurred")
//            }
//
//        })
//    }
//
//    fun errorDialog(text: String) {
//        AlertDialog.Builder(this)
//            .setIcon(R.drawable.ic_error)
//            .setTitle("Error")
//            .setMessage(text)
//            .setPositiveButton("Dismiss", null)
//            .show()
//    }

}