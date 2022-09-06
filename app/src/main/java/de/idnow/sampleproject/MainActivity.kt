package de.idnow.sampleproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import de.idnow.R
import de.idnow.sdk.IDnowSDK

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Environment
        IDnowSDK.setEnvironment(IDnowSDK.Server.TEST)

        // Video Identification
        val startVideoIdentButton = findViewById<Button>(R.id.buttonStartVideoIdent)

        DrawableUtils.setProceedButtonBackgroundSelector(startVideoIdentButton)
        startVideoIdentButton.setOnClickListener { startVideoIdentification() }

        // Photo Identification
        val startPhotoIdentButton = findViewById<Button>(R.id.buttonStartPhotoIdent)

        DrawableUtils.setProceedButtonBackgroundSelector(startPhotoIdentButton)
        startPhotoIdentButton.setOnClickListener { startPhotoIdentification() }
    }

    private fun startVideoIdentification() {
        try {
            IDnowSDK.getInstance().initialize(this, "")
            IDnowSDK.setShowVideoOverviewCheck(true, this)
            IDnowSDK.setShowErrorSuccessScreen(true, this)

            // need to be changed to your own token as described in API documentation, see https://www.idnow.eu/development/api-documentation/
            IDnowSDK.setTransactionToken("TST-KZPDT", this)
            IDnowSDK.getInstance().start(IDnowSDK.getTransactionToken(this))
        } catch (e: Exception) {
            // exception handling required
            e.printStackTrace()
        }
    }

    private fun startPhotoIdentification() {
        try {
            IDnowSDK.getInstance().initialize(this, "idnow")
            IDnowSDK.setShowVideoOverviewCheck(true, this)
            IDnowSDK.setShowErrorSuccessScreen(true, this)

            // need to be changed to your own token as described in API documentation, see https://www.idnow.eu/development/api-documentation/
            IDnowSDK.setTransactionToken("TST-XXXXX", this)
            IDnowSDK.getInstance().start(IDnowSDK.getTransactionToken(this))
        } catch (e: Exception) {
            // exception handling required
            e.printStackTrace()
        }
    }

    /**
     * Callback from the SDK
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == IDnowSDK.REQUEST_ID_NOW_SDK) {
            val toastText: StringBuilder
            when (resultCode) {
                IDnowSDK.RESULT_CODE_SUCCESS -> {
                    toastText = StringBuilder("Identification performed. ")
                    toastText.append(data.getStringExtra(IDnowSDK.RESULT_DATA_TRANSACTION_TOKEN))
                }

                IDnowSDK.RESULT_CODE_CANCEL -> {
                    toastText = StringBuilder("Identification canceled. ")
                    toastText.append(data.getStringExtra(IDnowSDK.RESULT_DATA_ERROR))
                }

                IDnowSDK.RESULT_CODE_FAILED -> {
                    toastText = StringBuilder("Identification failed. ")
                    toastText.append(data.getStringExtra(IDnowSDK.RESULT_DATA_ERROR))
                }

                else -> {
                    toastText = StringBuilder("Result Code: ")
                    toastText.append(resultCode)
                }
            }
            Toast.makeText(this, toastText.toString(), Toast.LENGTH_LONG).show()
        }
    }
}