package com.example.nabha.k_project_8.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.nabha.k_project_8.R
import com.example.nabha.k_project_8.adapter.PhotosAdapter
import com.example.nabha.k_project_8.model.Photo
import com.example.nabha.k_project_8.utils.AppUtils
import com.example.nabha.k_project_8.utils.PhotosFetcher

class PhotosActivity : AppCompatActivity(), PhotosAdapter.IClickListener {

    private lateinit var mPhotosRecyclerView : RecyclerView
    private lateinit var mPhotosAdapter : PhotosAdapter
    private var photos = ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        fetchData()
        setupViews()
    }

    private fun setupViews() {
        mPhotosRecyclerView = findViewById(R.id.photos_recycler)
        mPhotosAdapter = PhotosAdapter(photosList = photos, listener = this)
        mPhotosRecyclerView.layoutManager = GridLayoutManager(this, AppUtils.COLUMN_COUNT)
        mPhotosRecyclerView.adapter = mPhotosAdapter
    }

    private fun fetchData() {
        val fetcher = PhotosFetcher(this)
        photos = fetcher.fetchPhotos() as ArrayList<Photo>
        Log.i("Photos", photos.size.toString())
    }

    override fun onItemClick(path: String, position: Int) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra(AppUtils.PHOTO_PATH, path)
        startActivity(detailIntent)
    }
}
