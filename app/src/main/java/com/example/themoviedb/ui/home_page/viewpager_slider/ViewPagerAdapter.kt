package com.example.themoviedb.ui.home_page.viewpager_slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.apis.ApiPaths
import com.example.themoviedb.models.TrendingMovies
import com.example.themoviedb.recyclerviews.ViewItemClickListener
import kotlinx.android.synthetic.main.view_pager_item_layout.view.*

class ViewPagerAdapter(private val context: Context, private val items: ArrayList<TrendingMovies.TrendingMoviesList>,
                       private val viewItemClickListener: ViewItemClickListener) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`
    override fun getCount() = items.size
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(context).inflate(R.layout.view_pager_item_layout, container, false)
        val item = items[position]


        viewItemClickListener.onClick(view, position, item)
        Glide
                .with(view.context)
                .load(ApiPaths.POSTERS_BASE_URL + "w1280" + item.backdropPath)
                .placeholder(R.mipmap.tmdb_logo_whole)
                .centerCrop()
                .into(view.cover)
        view.title.text = item.title

        val ratings = "${item.voteAverage} / ${item.voteCount}"
        view.rating.text = ratings

        view.overview.text = item.overview

        container.addView(view)
        return view
    }



}