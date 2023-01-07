package com.tapark.military_quest.account

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tapark.military_quest.MainActivity
import com.tapark.military_quest.R
import com.tapark.military_quest.account.adapter.FolderSelectAdapter
import com.tapark.military_quest.account.adapter.ImageSelectAdapter
import com.tapark.military_quest.base.BaseFragment
import com.tapark.military_quest.data.FolderInfo
import com.tapark.military_quest.databinding.FragmentImageSelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageSelectFragment(): BaseFragment<FragmentImageSelectBinding, ImageSelectViewModel>() {

    override val viewModel by viewModels<ImageSelectViewModel>()
    override val layout: Int = R.layout.fragment_image_select

    private var imageSelectAdapter: ImageSelectAdapter? = null
    private var folderSelectAdapter: FolderSelectAdapter? = null

    private val imageList = mutableListOf<Uri>()
    private val folderInfoList = mutableListOf<FolderInfo>()
    private val folderNameList = mutableListOf<String>()

    override fun onBackPressed() {
        if (viewDataBinding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            viewDataBinding.drawerLayout.closeDrawer(Gravity.LEFT)
            return
        }
        (activity as MainActivity).removeFragment()
    }

    override fun addObserver() {

    }

    override fun initViews(savedInstanceState: Bundle?) {
        getContent(requireContext())
//        initAdapter()
        onClick()
    }

    private fun onClick() {
        viewDataBinding.folderMenuImageView.setOnClickListener {
            if (!viewDataBinding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                viewDataBinding.drawerLayout.openDrawer(Gravity.LEFT)
            } else {
                viewDataBinding.drawerLayout.closeDrawer(Gravity.LEFT)
            }
        }
        viewDataBinding.imageRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < -40) {
                    viewDataBinding.topScrollButton.visibility = View.VISIBLE
                } else if (dy > 40) {
                    viewDataBinding.topScrollButton.visibility = View.GONE
                }
            }
        })
        viewDataBinding.topScrollButton.setOnClickListener {
            viewDataBinding.imageRecyclerView.smoothScrollToPosition(0)
        }
    }

    private fun initAdapter() {

        // Image List
        if (imageSelectAdapter == null)
            imageSelectAdapter = ImageSelectAdapter {
                Log.d("박태규", "uri : $it")
                setFragmentResult("imageUri", bundleOf("imageUri" to it))
                (activity as MainActivity).showImageCropFragment()
            }
        viewDataBinding.imageRecyclerView.adapter = imageSelectAdapter
        viewDataBinding.imageRecyclerView.layoutManager = GridLayoutManager(context, 3)
        imageSelectAdapter?.submitList(imageList)
        setFolderInfo(count = imageList.size)
        // Folder Name List
        if (folderSelectAdapter == null) {
            folderSelectAdapter = FolderSelectAdapter { folder, pos ->
                val folderImageList = if (pos == 0) imageList else getFolderImageList(folder)
                imageSelectAdapter?.submitList(folderImageList)
                setFolderInfo(folderNameList[pos], folderImageList.size)
                viewDataBinding.drawerLayout.closeDrawer(Gravity.LEFT)
            }
            viewDataBinding.folderNameRecyclerView.adapter = folderSelectAdapter
            viewDataBinding.folderNameRecyclerView.layoutManager = LinearLayoutManager(context)
            folderSelectAdapter?.submitList(folderNameList)
        }
    }

    private fun getFolderImageList(folder: String): MutableList<Uri> {
        return folderInfoList.filter { it.name == folder }[0].list
    }

    private fun setFolderInfo(name: String = getString(R.string.all_photo), count: Int) {
        viewDataBinding.folderNameTextView.text = name
        viewDataBinding.imageCountTextView.text = context?.getString(R.string.image_count, count)
    }

    @SuppressLint("Recycle")
    private fun getContent(context: Context) {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val project = arrayOf(FOLDER_NAME, IMAGE_ID, DATE_ADDED)
        val selection = null
        val selectionArgs = null
        val sortOrder = "$DATE_ADDED DESC"

        val contentCursor = context.contentResolver.query(uri, project, selection, selectionArgs, sortOrder)
        contentCursor?.let { cursor ->
            while (cursor.moveToNext()) {
                val folderColumn = cursor.getColumnIndex(FOLDER_NAME)
                val idColumn = cursor.getColumnIndex(IMAGE_ID)
                val folderName = cursor.getString(folderColumn)
                val imageId = cursor.getLong(idColumn)
                val imageUri = ContentUris.withAppendedId(uri, imageId)
                imageList.add(imageUri)
                if (folderNameList.contains(folderName)) {
                    val index = folderNameList.indexOf(folderName)
                    if (folderInfoList[index].name == folderName) {
                        folderInfoList[index].size++
                        folderInfoList[index].list.add(imageUri)
                    }
                } else {
                    folderNameList.add(folderName)
                    folderInfoList.add(FolderInfo(folderName, 1, mutableListOf(imageUri)))
                }
            }
            folderNameList.add(0, context.getString(R.string.all_photo))
        }
        initAdapter()
    }

    companion object {
        const val FOLDER_NAME = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
        const val IMAGE_ID = MediaStore.Images.ImageColumns._ID
        const val DATE_ADDED = MediaStore.Images.ImageColumns.DATE_ADDED

    }
}
