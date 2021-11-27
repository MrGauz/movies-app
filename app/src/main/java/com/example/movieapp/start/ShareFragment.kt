package com.example.movieapp.start

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
import com.example.movieapp.database.models.Filter

class ShareFragment : Fragment() {
    private var _binding: FragmentShareBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareBinding.inflate(inflater, container, false)

        // TODO: 24.11.2021 generate a database id and pass it to the intent
        val databaseId = "todo"
        binding.sessionId.text = "Session ID: $databaseId"
        val deepLink = "https://www.meineurl.com/path?key=$databaseId"
        val qrView = binding.qrView

        binding.copySessionId.setOnClickListener {
            val clipboardManager =
                context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("Swovie session ID", databaseId)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, "Session ID was copied to your clipboard", Toast.LENGTH_SHORT)
                .show()
        }

        binding.startSwipingButton.setOnClickListener {
            val swipeIntent = Intents(true, this.context)
            swipeIntent.intentToSwipe()
        }

        binding.shareLinkButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Hi there, let's watch a movie together: $deepLink")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        generateQRCode(deepLink, qrView)
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