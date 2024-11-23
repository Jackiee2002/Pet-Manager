package com.kroger.classapp.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.kroger.classapp.ImageAdapter
import com.kroger.classapp.database.ImageDatabase
import com.kroger.classapp.viewmodel.ImageModel
import com.kroger.classapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class PetPictureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pet_picture_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageDatabase = Room.databaseBuilder(
            requireContext(),
            ImageDatabase::class.java,
            "images_db"
        ).build()
        val imageRV = view.findViewById<RecyclerView>(R.id.imageRV)
        val imageAdapter = ImageAdapter()
        imageRV.adapter = imageAdapter

        //get all images
        imageDatabase.imageDao.getAllImages().observe(viewLifecycleOwner) {
            imageAdapter.submitList(it)
        }
        //single upload in room database
        val singleImagePickerBtn = view.findViewById<Button>(R.id.singleImageBtn)
        val singlePhotoPickerLauncher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                //single image upload function
                uri?.let { newImageUpload(imageDatabase, it) }
            }
        singleImagePickerBtn.setOnClickListener {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
        val multiplePhotoPickLauncher =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
                for (image in uris) {
                    newImageUpload(imageDatabase, image)
                }
            }
        val multipleImagePickBtn = view.findViewById<Button>(R.id.multipleImageBtn)
        multipleImagePickBtn.setOnClickListener {
            multiplePhotoPickLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    private fun newImageUpload(imageDatabase: ImageDatabase, imageUri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newImage = requireContext().contentResolver.openInputStream(imageUri)?.readBytes()?.let {
                    ImageModel(
                        UUID.randomUUID().toString(),
                        it
                    )
                }
                newImage?.let {
                    imageDatabase.imageDao.insertImage(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
