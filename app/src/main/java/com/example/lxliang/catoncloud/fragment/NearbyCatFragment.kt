package com.example.lxliang.catoncloud.fragment

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.lxliang.catoncloud.R
import com.example.lxliang.catoncloud.activity.CatDetailActivity
import com.example.lxliang.catoncloud.adapter.NearbyCatAdapter
import com.example.lxliang.catoncloud.model.GetNearbyCatResponse
import com.example.lxliang.catoncloud.presenter.NearbyCatContract
import com.example.lxliang.catoncloud.presenter.NearbyCatPresenter

/**
 * Created by lxliang on 02/11/2017.
 */
class NearbyCatFragment : BaseFragment(), NearbyCatAdapter.OnItemClickedListener, NearbyCatContract.View {

    private val TAG = "NearbyCatFragment"

    @BindView(R.id.listview)
    lateinit var mListView: ListView

    private lateinit var nearbyCatAdapter: NearbyCatAdapter

    private val mCatsNearbyPresenter: NearbyCatContract.Presenter = NearbyCatPresenter(this)

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_nearby_cat, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
    }

    override fun onResume() {
        super.onResume()
        mCatsNearbyPresenter.start()
        mCatsNearbyPresenter.loadingCatsNearbyList()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCatsNearbyPresenter.stop()
    }

    override fun onItemClicked(view: View, position: Int) {
        val intent = Intent(activity, CatDetailActivity::class.java)

        val avatarImageView = view.findViewById<ImageView>(R.id.avatar)
        val bitmap = (avatarImageView.drawable as BitmapDrawable).bitmap
        intent.putExtra("avatarBitmap", bitmap)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view.findViewById(R.id.avatar), "avatar_transition")

        ActivityCompat.startActivity(activity, intent, options.toBundle())
    }

    override fun onGetDataSucceed(data: List<GetNearbyCatResponse.MomentsBean>?) {
        nearbyCatAdapter.data = data
        nearbyCatAdapter.notifyDataSetChanged()
    }

    override fun onGetDetailFail(msg: String) {
        Log.e(TAG, "get data failed! " + msg)
        Toast.makeText(context, "get data failed!", Toast.LENGTH_SHORT).show()
    }

    private fun setupListView() {
        var data = ArrayList<GetNearbyCatResponse.MomentsBean>()
        nearbyCatAdapter = NearbyCatAdapter(activity, data, this)
        mListView.adapter = nearbyCatAdapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onStop() {
        super.onStop()
    }
}