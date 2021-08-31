package my.com.iotassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.com.iotassignment.databinding.ActivitySmartGuardBinding

class SmartGuard : AppCompatActivity() {

    private lateinit var binding: ActivitySmartGuardBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySmartGuardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToSmartDoor.setOnClickListener { navigateToSmartDoor() }
        binding.btntoSmartDetector.setOnClickListener { navigateToSmartDetector() }

    }

    private fun navigateToSmartDetector() {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSmartDoor() {
        val intent = Intent(this, SmartDoor::class.java)
        startActivity(intent)
    }
}