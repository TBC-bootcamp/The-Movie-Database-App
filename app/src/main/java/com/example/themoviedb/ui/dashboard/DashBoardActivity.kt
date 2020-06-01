package com.example.themoviedb.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.themoviedb.R
import com.example.themoviedb.ui.authentication.AccountUserModel
import com.example.themoviedb.ui.authentication.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_dash_board.*
import java.io.File

class DashBoardActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var db: DatabaseReference
    private lateinit var filePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        // init
        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference
        db = FirebaseDatabase.getInstance().reference.child(mAuth.currentUser!!.uid)


        // set MetaData to UI
        db.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(AccountUserModel::class.java) ?: return
                val name = "Hello, ${user.name}"
                userName.text = name
                userEmail.text = user.email
            }
        })

        // Retrieving Profile picture from server and setting to ImageView
        val localFile = File.createTempFile("Images", "bmp")
        storage.getReference("images/ ${mAuth.currentUser!!.uid}").getFile(localFile).addOnSuccessListener {
            val image = BitmapFactory.decodeFile(localFile.absolutePath)
            profileImageView.setImageBitmap(image)
        }

        // Uploading Profile Picture from External Storage
        uploadImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), 1234)
        }

        signOut.setOnClickListener {
            mAuth.currentUser!!.delete()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        updatePswBtn.setOnClickListener {
            val password = updatePswEditText.text.toString()
            if (password.isNotEmpty()) {
                mAuth.currentUser!!.updatePassword(password).addOnCompleteListener {
                    if (it.isSuccessful) {

                        Toast.makeText(this, "პაროლის შეცვლა წარმატებით განხორციელდა", Toast.LENGTH_LONG).show()
                        updatePswEditText.setText("")
                    } else {
                        Toast.makeText(this, "შეცდომა", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        updateEmailBtn.setOnClickListener {
            val email = updateEmailEditText.text.toString()
            if(email.isNotEmpty()){
                mAuth.currentUser!!.updateEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val updateUser = AccountUserModel()
                        updateUser.name = userName.text.toString()
                        updateUser.email = email
                        Toast.makeText(this, "ელფოსტის შეცვლა წარმატებით განხორციელდა", Toast.LENGTH_LONG).show()
                        updatePswEditText.setText("")
                    } else {
                        Toast.makeText(this, "შეცდომა", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK && data != null) {
            filePath = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
            profileImageView.setImageBitmap(bitmap)

            storageReference.child("images/ ${mAuth.currentUser!!.uid}").putFile(filePath).addOnCompleteListener {
                Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show()
            }
        }
    }
}
