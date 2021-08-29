package my.com.iotassignment

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.com.iotassignment.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val database = Firebase.database
    val ref = database.getReference("tripWire")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var sb = StringBuilder()

                var tripVal = snapshot.child("tripwire").value
                var buzzVal = snapshot.child("buzz").value
                var trippedVal = snapshot.child("tripped").value
                var lcdMessageVal = snapshot.child("lcdMessage").value

                sb.append("$lcdMessageVal")
                binding.edtMessage.setText(sb)

                binding.tripSW.isChecked = tripVal == "ON"
                binding.buzzSW.isChecked = buzzVal == "ON"

                if (trippedVal == "1"){
                    binding.lblTrip.text = "Intruder Found"
                    binding.imgPhoto.setImageResource(R.drawable.danger)
                    binding.lblTrip.setTextColor(Color.parseColor("#EC3F2C"))

                }
                else{
                    binding.lblTrip.text = "No Intruder Found"
                    binding.imgPhoto.setImageResource(R.drawable.safe)
                    binding.lblTrip.setTextColor(Color.parseColor("#FF03DAC5"))
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        ref.addValueEventListener(getData)
        ref.addListenerForSingleValueEvent(getData)


        binding.tripSW.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                ref.child("tripwire")
                    .setValue("ON")
                toast("Tripwire armed.")
            }
            else {
                ref.child("tripwire")
                    .setValue("OFF")
                toast("Tripwire disarmed.")
            }
        }

        binding.buzzSW.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                ref.child("buzz")
                    .setValue("ON")
            }
            else {
                ref.child("buzz")
                    .setValue("OFF")
            }
        }

            binding.btnApply.setOnClickListener {
                if (binding.edtMessage.text.isEmpty())
                   toast("The alert message cannot be empty")
                else
                applyText() }

        // binding.edtMessage.text.toString().trim()


    }

    private fun toast(s: String) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }

    private fun applyText() {
        var text = binding.edtMessage.text.toString().trim()
        ref.child("lcdMessage")
            .setValue(text)
    }

/*Below code i think should be implement on arduino side rather than here
    private fun sendToFirebasePls() {

        ref.child("tripwire")
            .setValue("OFF")
        ref.child("buzz")
            .setValue("OFF")


        ref.child("lcdrgbR")
            .setValue(3)
        ref.child("lcdrgbG")
            .setValue(236)
        ref.child("lcdrgbB")
            .setValue(252)
        ref.child("tripped")
            .setValue(0)
*/
    }


//        ref.child("light")
//            .get()
//            .addOnSuccessListener {
//                binding.txtTest.text = it.value.toString()
//            }
//        ref.child("sound")
//            .get()
//            .addOnSuccessListener {
//                binding.txtTest2.text = it.value.toString()
//            }
//

