package com.medialink.deco24sharedpreferences

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val REQUEST_CODE = 100
    }

    private lateinit var mUserPref: UserPreference
    private var isPrefEmpty = false;
    private lateinit var userModel: UserModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "My User Preference"
        mUserPref = UserPreference(this)
        showExistingPreference()

        btn_save.setOnClickListener(this)
    }

    private fun showExistingPreference() {
        userModel = mUserPref.getUser()
        populateView(userModel)
        checkForm(userModel)
    }

    private fun checkForm(userModel: UserModel) {
        when {
            userModel.name.toString().isNotEmpty() -> {
                btn_save.text = getString(R.string.change)
                isPrefEmpty = false
            }
            else -> {
                btn_save.text = getString(R.string.save)
                isPrefEmpty = true
            }
        }
    }

    private fun populateView(userModel: UserModel) {
        tv_name.text = if (userModel.name.toString().isEmpty()) "Tidak Ada" else userModel.name
        tv_email.text = if (userModel.email.toString().isEmpty()) "Tidak Ada" else userModel.email
        tv_age.text =
            if (userModel.age.toString().isEmpty()) "Tidak Ada" else userModel.age.toString()
        tv_phone.text =
            if (userModel.phoneNumber.toString().isEmpty()) "Tidak Ada" else userModel.phoneNumber
        tv_is_love_mu.text = if (userModel.isLove) "Ya" else "Tidak"
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_save) {
            val intent = Intent(this@MainActivity, FormUserPreferenceActivity::class.java)
            when {
                isPrefEmpty -> {
                    intent.putExtra(
                        FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                        FormUserPreferenceActivity.TYPE_ADD
                    )
                    intent.putExtra("USER", userModel)
                }
                else -> {
                    intent.putExtra(
                        FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                        FormUserPreferenceActivity.TYPE_EDIT
                    )
                    intent.putExtra("USER", userModel)
                }

            }
            startActivityForResult(intent, REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == FormUserPreferenceActivity.RESULT_CODE) {
                userModel = data?.getParcelableExtra(FormUserPreferenceActivity.EXTRA_RESULT) as UserModel
                populateView(userModel)
                checkForm(userModel)
            }
        }
    }
}
