package com.example.movieapp.start

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movieapp.databinding.FragmentShareBinding
import com.google.zxing.qrcode.QRCodeWriter
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import java.lang.Exception
import android.content.*
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.data.SessionData
import com.example.movieapp.models.AlertDialogBuilder

class ShareFragment : Fragment() {
    private var _binding: FragmentShareBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareBinding.inflate(inflater, container, false)

        // Compose sharing information
        val databaseId = SessionData.id
        binding.sessionId.text = "Session ID:\n$databaseId"
        val deepLink = "https://" + getString(R.string.domain_name) + "/?id=$databaseId"

        // Copy session ID button
        binding.copySessionId.setOnClickListener {
            val clipboardManager =
                context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("Swovie session ID", databaseId)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, "Session ID was copied to your clipboard", Toast.LENGTH_SHORT)
                .show()
        }

        // Share link button
        binding.shareLinkButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Hi there, let's watch a movie together: $deepLink")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        // Start swiping button
        binding.startSwipingButton.setOnClickListener {
            val swipeIntent = Intents(true, this.context)
            swipeIntent.intentToSwipe()
        }
        binding.shareBack.setOnClickListener {
            val alert = AlertDialogBuilder().createDialog(this.context,activity,R.style.AlertDialog)
            alert.show()
        }

        // Show a QR code
        generateQRCode(deepLink, binding.qrView)

        AlertDialogBuilder().createDialogOnBackButtonPress(this.context,activity,R.style.AlertDialog,R.id.shareFragment)

        return binding.root
    }

    private fun generateQRCode(message: String, qrView: ImageView) {
        val qrCodeWriter = QRCodeWriter()
        try {
            val bitMatrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, 200, 200)
            val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565)
            for (x in 0..199) {
                for (y in 0..199) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            qrView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        } // source https://programmerworld.co/android/how-to-create-or-generate-qr-quick-response-code-in-your-android-app-complete-source-code/
    }
}